package de.hswt.hrm.i18n;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Factory class that creates I18n classes.
 */
public class I18nFactory {
	
	/**
	 * Create a new i18n object for the given class.
	 * 
	 * @param clazz
	 */
	public static <T> I18n getI18n(final Class<T> clazz) {
		Bundle bundle = FrameworkUtil.getBundle(clazz);
		
		// TODO: maybe we can inject the cache class here ..
		I18n i18n = new I18n(bundle);
		return i18n;
	}
}
