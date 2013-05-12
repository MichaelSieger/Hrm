package de.hswt.hrm.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds application methods like "init" or "shutdown".
 */
public final class Hrm {

    private static final Logger LOG = LoggerFactory.getLogger(Hrm.class);
    private static boolean initialized = false;

    private Hrm() {
    }

    /**
     * Initializes the application. (E. g. load configuration file)
     */
    public static void init() {
        // Check if we've already initialized
        if (initialized) {
            return;
        }
        
        // Try to find the current config
        Path configPath = getConfigPath().resolve("hrm.properties");
        if (!Files.exists(configPath)) {
            try {
                // Create a default one
                Files.createDirectories(configPath.getParent());
                Files.createFile(configPath);

                OutputStream targetFile = Files.newOutputStream(configPath);
                InputStream configFile = BundleUtil.getStreamForFile(Hrm.class,
                        "resources/hrm.properties");
                IOUtils.copy(configFile, targetFile);

                // Close resources
                targetFile.close();
                configFile.close();
                LOG.info("New configuration written to: '" + configPath.toString() + "'.");
            }
            catch (IOException e) {
                LOG.error("Could not create default configuration file from bundle.", e);
                return;
            }

        }

        Config cfg = Config.getInstance();
        try {
            cfg.load(configPath);
            LOG.info("Configuration load successfully.");
            
            boolean lockingEnabled = cfg.getBoolean(Config.Keys.DB_LOCKING);
            if (lockingEnabled) {
                LOG.info("DB locking enabled.");
            }
        }
        catch (IOException e) {
            LOG.error("Could not load configuration at '" + configPath.toString() + "'.", e);
        }
        
        initialized = true;
    }

    /**
     * @return The correct home directory of the current user.
     */
    private static Path getConfigPath() {
        // We have to do this as the java standard way seems to be broken
        // for Windows
        if (SystemUtils.IS_OS_WINDOWS) {
            String path = System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH");
            Path userHome = Paths.get(path);
            return userHome.resolve("AppData\\hrm");
        }
        else {
            // This is a linux way, maybe this should be changed to also reflect
            // Mac OSX style
            Path userHome = SystemUtils.getUserHome().toPath();
            return userHome.resolve(".config/hrm");
        }
    }

}
