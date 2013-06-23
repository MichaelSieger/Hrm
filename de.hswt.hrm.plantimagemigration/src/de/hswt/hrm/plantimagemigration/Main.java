package de.hswt.hrm.plantimagemigration;

/**
 * This program converts the pstricks latex files from the previous hrm project, to svg files.
 * The program needs pdflatex and pdfcrop installed.
 * 
 * @author Michael Sieger
 * 
 */
public class Main {

    private static final String HELP = "Usage: java -jar plantimagemigration.jar <path>\n\n"
            + "The latex files must be in a folder named symbols. "
            + "The selected path has to be the parent of the symbols folder";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println(HELP);
        }
        else {
            System.out.println("Selected dir: " + args[0]);
            new PSLatex2PDFConverter(args[0]);
        }
    }

}
