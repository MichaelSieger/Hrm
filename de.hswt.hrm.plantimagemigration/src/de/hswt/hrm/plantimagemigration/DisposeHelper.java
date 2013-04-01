package de.hswt.hrm.plantimagemigration;

import java.io.Closeable;
import java.io.Writer;
import java.util.Scanner;

/**
 * Functions that are used to close closeable things. These are used usually in a finally block.
 * 
 * All overloaded functions work like this: if null is passed, do nothing. close the object, and
 * consume a possible exception.
 * 
 * @author Michael Sieger
 * 
 */
public class DisposeHelper {

    public static void tryClose(Closeable c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (Exception e) {
            }
        }
    }

    public static void tryClose(Writer w) {
        if (w != null) {
            try {
                w.close();
            }
            catch (Exception e) {
            }
        }
    }

    public static void tryClose(Scanner sc) {
        if (sc != null) {
            try {
                sc.close();
            }
            catch (Exception e) {
            }
        }
    }
}
