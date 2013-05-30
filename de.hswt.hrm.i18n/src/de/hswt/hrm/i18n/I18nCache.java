package de.hswt.hrm.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.Bundle;

import de.hswt.hrm.common.BundleUtil;

public class I18nCache {
	private static Map<String, Properties> cache = new HashMap<>();
	
	public Properties getTranslations(final Bundle bundle) throws IOException {
		String symbolicName = bundle.getSymbolicName();
		
		if (cache.containsKey(symbolicName)) {
			return cache.get(symbolicName);
		}
		
		return load(bundle);
	}
	
	private Properties load(final Bundle bundle) throws IOException {
		String symbolicName = bundle.getSymbolicName();

		// TODO: refactor folder to a separate folder with default "i18n"
		// TODO: refactor file to the current language..
		InputStream inStream = BundleUtil.getStreamForFile(bundle,
				"i18n/de_de.properties");
		Properties props = loadFromFile(inStream);
		inStream.close();

		cache.put(symbolicName, props);
		return props;
	}
	
    private Properties loadFromFile(final InputStream inStream) throws IOException {
        Properties props = new Properties();
        props.load(inStream);
        
        return props;
    }
}
