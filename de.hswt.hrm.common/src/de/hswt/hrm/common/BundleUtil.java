package de.hswt.hrm.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Holds utility methods to access bundles.
 */
public final class BundleUtil {
    
    private BundleUtil() { }
    
    /**
     * Returns an InputStream to the file in the given bundle with the given path.
     * <p>
     * Example
     * <pre><code>
     * {@code
     * InputStream is = BundleUtil.getStreamForFile("de.hswt.hrm.main", "media/somefile.ext");
     * }
     * </code></pre>
     * </p>
     * 
     * @param bundleName Name of the bundle.
     * @param path Path to the file within the bundle.
     * @return An InputStream for the file.
     * @throws IOException
     */
    public static InputStream getStreamForFile(final String bundleName, final String path) 
            throws IOException {
        URL url = new URL("platform:/plugin/" + bundleName + "/" + path);
        return url.openConnection().getInputStream();
    }
}
