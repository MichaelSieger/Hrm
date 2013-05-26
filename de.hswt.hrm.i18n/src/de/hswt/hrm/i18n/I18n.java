package de.hswt.hrm.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.hswt.hrm.common.BundleUtil;

// FIXME: we have to provide one properties file per bundle!!! 
public final class I18n {
    private static Properties cache = new Properties();
    private static List<String> alreadyLoad = new ArrayList<>();
    
    private I18n() { }
    
    private static <T> void load(final Class<T> clazz) throws IOException {
        Bundle bundle = FrameworkUtil.getBundle(clazz);
        String symbolicName = bundle.getSymbolicName();
        
        if (alreadyLoad.contains(symbolicName)) {
            return;
        }
        
        // TODO: refactor folder to a separate folder with default "i18n"
        // TODO: refactor file to the current language..
        InputStream inStream = BundleUtil.getStreamForFile(bundle, "i18n/de_de.properties");
        Properties props = loadFromFile(inStream);
        inStream.close();
        
        // TODO: maybe we can simple load into the cache
        cache.putAll(props);
        alreadyLoad.add(symbolicName);
    }
    
    private static Properties getCache() {
        return cache;
    }
    
    private static Properties loadFromFile(final InputStream inStream) throws IOException {
        Properties props = new Properties();
        props.load(inStream);
        
        return props;
    }
    
    public static String tr(final String key) {
        Properties cache = getCache();
        
        return cache.getProperty(escape(key), key);
    }
    
    /**
     * Escape all spaces with a backslash.
     * 
     * @param key
     * @return The escaped key.
     */
    private static String escape(final String key) {
        return key.replaceAll(" ", "\\ ");
    }

}
