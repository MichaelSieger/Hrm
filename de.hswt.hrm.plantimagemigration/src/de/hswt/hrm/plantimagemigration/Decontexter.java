package de.hswt.hrm.plantimagemigration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
        fout.createNewFile();
        try (Scanner sc = new Scanner(new FileInputStream(fin), StandardCharsets.ISO_8859_1.name())) {
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(fout))) {
                appendPreamble(writer);
                appendFrame(writer, fin.getName());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    for (String[] rep : REPLACE) {
                        line = line.replaceAll(rep[0], rep[1]);
                    }
                    if (isBlacklisted(line)) {
                        continue;
                    }
                    writer.println(line);
                }
                appendFooter(writer);
            }
        }
    }

    private boolean isBlacklisted(String s) {
        for (String black : BLACKLIST) {
            if (s.startsWith(black)) {
                return true;
            }
        }
        return false;
    }

    //\psframe[linewidth=0.1pt](0,0)(1,1)

    private void appendPreamble(PrintWriter w) {
        w.println("\\documentclass{minimal}");
        w.println("\\usepackage{auto-pst-pdf}");
        w.println("\\usepackage{pstricks}");
        w.println("\\usepackage{pstricks-add}");
        w.println("\\usepackage{pst-all}");
        w.println("\\usepackage[utf8]{inputenc}");
        w.println("\\begin{document}");
        w.println("\\thispagestyle{empty}");
        w.println("\\begin{pspicture}");
    }

    private void appendFrame(PrintWriter w, String fn) {
    	if (!fn.startsWith("p")) {
    		return;
       	}

    	String name = fn.substring(0, fn.lastIndexOf("."));
    	
    	int width = 1;
    	int height = 1;
    	
    	if (name.contains(Integer.toString(2))) {
        	if (name.endsWith("l") || name.endsWith("r")) {
        		height = 2;
        	} else {
        		width = 2;
        	}
    	}

        w.println("\\psframe[linewidth=0.1pt](0,0)(" + width + "," + height + ")");
    }
    
    private void appendFooter(PrintWriter w) {
        w.println("\\end{pspicture}");
        w.println("\\end{document}");
    }

}
