package de.hswt.hrm.common;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 * Singleton that holds the current configuration.
 */
public final class Config {
    private static Config instance;
    private Properties props = new Properties();

    private Config() { }

    /**
     * @return The current configuration instance.
     */
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    /**
     * Replace the current configuration with the content of the given properties file.
     * 
     * @param path
     *            Path to the properties file.
     * @throws IOException
     */
    public void load(Path path) throws IOException {
        checkArgument(Files.exists(path), "Configuration file is not present.");
        props = loadFromFile(path);
    }
    
    public void store(Path path) throws IOException {
    	store(path, false, false);
    }
    
    /**
     * Write out the current properties to the target specified by path.
     * 
     * @param path
     * @param createParentPath
     * @param override
     * @throws IOException 
     * @throws IllegalStateException If createParentPath is false and the target path does not exist.
     * @throws IllegalStateException If override is false and the target file already exists.
     */
    public void store(Path path, boolean createParentPath, boolean override) throws IOException {
    	checkNotNull(path, "Path must not be null.");
    	
    	if (!createParentPath) {
    		checkState(Files.exists(path.getParent()), "Parent folder does not exist.");
    	}
    	
    	if (!override) {
    		checkArgument(Files.exists(path), "Target file already exists.");
    	}
    	
    	OutputStream stream = Files.newOutputStream(
    			path,
    			StandardOpenOption.CREATE,
    			StandardOpenOption.WRITE);
    	props.store(stream, "Automatically written by hrm");
    }

    /**
     * You mostly should use keys from {@link Keys} for the key.
     * 
     * @see {@link Properties#setProperty(String, String)}
     */
    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }

    /**
     * You mostly should use keys from {@link Keys} for the key.
     * 
     * @see {@link Properties#getProperty(String)}
     */
    public String getProperty(String key) {
        return props.getProperty(key);
    }
    
    /**
     * Return boolean representation of the given key. The following scheme is used
     * to get the correct representation:
     * 1. If value equals ignore case to "1", "yes" or "true", true is returned.
     * 2. Otherwise return false (even when the key is not present).
     * If you want to check if the key exists before retrieving it you can use "containsKey".
     * 
     * @param key
     * @return True if the value equals ignore case to "1", "yes" or "true", otherwise false.
     */
    public boolean getBoolean(final String key) {
        String value = props.getProperty(key, "").toLowerCase();
        if (value.equals("1") || value.equals("true") || value.equals("yes")) {
            return true;
        }
        
        return false;
    }
    
    public boolean containsKey(String key) {
        return props.containsKey(key);
    }

    /**
     * You mostly should use keys from {@link Keys} for the key.
     * 
     * @see {@link Properties#getProperty(String, String)}
     */
    public String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    private static Properties loadFromFile(Path path) throws IOException {
        Properties props = new Properties();
        
        try (InputStream is = Files.newInputStream(path)) {
            props.load(is);
        }
        
        return props;
    }

    /**
     * Holds the possible keys from the configuration.
     */
    public static class Keys {
        public static final String DB_HOST = "db.host";
        public static final String DB_USER = "db.user";
        public static final String DB_PASSWORD = "db.password";
        public static final String DB_NAME = "db.name";
        public static final String DB_LOCKING = "db.locking.enabled";
        public static final String SESSION_UUID = "session.uuid";
        public static final String LOCALE = "locale";
        public static final String REPORT_STYLE_FOLDER = "report.style.directory";
    }
}
