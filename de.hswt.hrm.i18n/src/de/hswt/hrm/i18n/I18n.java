package de.hswt.hrm.i18n;

import java.io.IOException;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class I18n {
	private final static Logger LOG = LoggerFactory.getLogger(I18n.class);
    private final I18nCache cache;
    private final Bundle bundle;
    private Properties translations;
    
    public I18n(final Bundle bundle) {
    	this(bundle, new I18nCache());
    }
    
    public I18n(final Bundle bundle, final I18nCache cache) {
    	this.bundle = bundle;
    	this.cache = cache;
    }
    
    public String tr(final String key) {
    	if (translations == null) {
    		try {
				this.translations = cache.getTranslations(bundle);
			} 
    		catch (IOException e) {
				LOG.error("Could not load translation file for bundle '"
						+ bundle.getSymbolicName() + "'.", e);
				return key;
			}
    	}
        
        return translations.getProperty(escape(key), key);
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
