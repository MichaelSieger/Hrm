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
                        line = addOpacity(line);
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

	private String addOpacity(String line) {
		if (!line.startsWith("\\psframe") &&
				!line.startsWith("\\pspolygon")) {
			return line;
		}
		// if this makes no transparency, try to add opacity=0.0
		String l = line.replaceFirst("fillstyle=solid", "fillstyle=none");
		return l.replace("]", ", opacity=0.0]");
	}

	private boolean isBlacklisted(String s) {
        for (String black : BLACKLIST) {
            if (s.startsWith(black)) {
                return true;
            }
        }
        return false;
    }

    private void appendPreamble(PrintWriter w) {
        w.println("\\documentclass{minimal}");
        w.println("\\usepackage{auto-pst-pdf}");
        w.println("\\usepackage{pstricks}");
        w.println("\\usepackage{pstricks-add}");
        w.println("\\usepackage{pst-all}");
        w.println("\\usepackage[utf8]{inputenc}");
//        w.println("\\usepackage[T1]{fontenc}");
        w.println("\\usepackage{helvet}");
        w.println("\\renewcommand{\\rmdefault}{\\sfdefault}");
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

        w.println("\\psframe[fillstyle=none, linewidth=0.1pt, opacity=0.0](0,0)(" + width + "," + height + ")");
    }

    private void appendFooter(PrintWriter w) {
        w.println("\\end{pspicture}");
        w.println("\\end{document}");
    }

}
