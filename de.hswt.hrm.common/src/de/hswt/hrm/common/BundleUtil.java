package de.hswt.hrm.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Holds utility methods to access bundles.
 */
public final class BundleUtil {
    
    private static final String PLUGIN_PREFIX = "platform:/plugin/";

	private BundleUtil() { }
    
    /**
     * Returns an InputStream to the file in the given bundle with the given path.
     * <p>
     * Example
     * <pre><code>
     * {@code
     * InputStream is = BundleUtil.getStreamForFile(bundle, "media/somefile.ext");
     * }
     * </code></pre>
     * </p>
     * 
     * @param bundle The bundle.
     * @param path Path to the file within the bundle.
     * @return An InputStream for the file.
     * @throws IOException
     */
    public static InputStream getStreamForFile(final Bundle bundle, final String path) 
            throws IOException {
    	
        URL url = new URL(PLUGIN_PREFIX + bundle.getSymbolicName() + "/" + path);
        return url.openConnection().getInputStream();
    }
    
    /**
     * Returns an InputStream to the file in the bundle of the given class with the given path.
     * <p>
     * Example
     * <pre><code>
     * {@code
     * InputStream is = BundleUtil.getStreamForFile(SomeClass.class, "media/somefile.ext");
     * }
     * </code></pre>
     * </p>
     * 
     * @param clazz Class where bundle should be retrieved.
     * @param path Path to the file within the bundle.
     * @return An InputStream for the file.
     * @throws IOException
     */
    public static <T> InputStream getStreamForFile(final Class<T> clazz, final String path) 
    		throws IOException {
    	
    	Bundle bundle = FrameworkUtil.getBundle(clazz);
    	return getStreamForFile(bundle, path);
    }
    
}
