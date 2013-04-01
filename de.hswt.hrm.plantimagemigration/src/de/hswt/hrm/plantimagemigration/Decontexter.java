package de.hswt.hrm.plantimagemigration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manipulates the Latex files so, that they can be compiled individual.
 * 
 * @author Michael Sieger
 * 
 */
public class Decontexter {

    private static final String[][] REPLACE = new String[][] { { "\\\\msymbolbackground", "white" } };

    private static final String[] BLACKLIST = new String[] { "\\renewcommand" };

    public void convertFile(File fin, File fout) throws IOException {
        InputStream sin = null;
        OutputStream sout = null;
        Scanner sc = null;
        PrintWriter writer = null;
        try {
            fout.createNewFile();
            sin = new FileInputStream(fin);
            sout = new FileOutputStream(fout);
            sc = new Scanner(sin);
            writer = new PrintWriter(sout);
            appendPreamble(writer);
            nextLine: while (sc.hasNextLine()) {
                String line = sc.nextLine();
                for (String[] rep : REPLACE) {
                    line = line.replaceAll(rep[0], rep[1]);
                }
                for (String black : BLACKLIST) {
                    if (line.startsWith(black)) {
                        continue nextLine;
                    }
                }
                writer.println(line);
            }
            appendFooter(writer);
        }
        finally {
            DisposeHelper.tryClose(sc);
            DisposeHelper.tryClose(writer);
            DisposeHelper.tryClose(sin);
            DisposeHelper.tryClose(sout);
        }
    }

    private void appendPreamble(PrintWriter w) {
        w.println("\\documentclass{article}");
        w.println("\\usepackage{pstricks}");
        w.println("\\begin{document}");
        w.println("\\thispagestyle{empty}");
    }

    private void appendFooter(PrintWriter w) {
        w.println("\\end{document}");
    }

}
