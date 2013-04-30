package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.IOException;

/**
 * The base class for the conversion from one format to another.
 * 
 * @author Michael Sieger
 * 
 */
public abstract class Converter {

    protected File tmpDir;
    private File resultDir, srcDir;

    /**
     * 
     * @param resultDir
     *            The directory where the resulting files are placed
     * @param srcDir
     *            The directory where the source files are
     * @param tmpDir
     */
    public Converter(File resultDir, File srcDir, File tmpDir) {
        this.tmpDir = tmpDir;
        this.resultDir = resultDir;
        this.srcDir = srcDir;
    }

    /**
     * Calls the subprogram, which does the actual conversion.
     * 
     * @param fin
     * @return
     * @throws IOException
     */
    protected abstract File executeConversion(File fin) throws IOException;

    public File getResultDir() {
        return resultDir;
    }

    public File getSrcDir() {
        return srcDir;
    }

    /**
     * Converts the file and saves it in the result directory.
     * 
     * @param fin
     * @throws IOException
     */
    public void convertFile(File fin) throws IOException {
        File tmpFile = executeConversion(fin);
        tmpFile.renameTo(new File(resultDir, tmpFile.getName()));
    }

    protected String changeFileend(String name, String newEnd) {
        return name.substring(0, name.lastIndexOf('.')) + newEnd;
    }

    /**
     * Calls a program with the given name, the fileending that it produces, and the inputfile as
     * sole parameter.
     * 
     * @param progname
     * @param ending
     * @param inputFile
     * @return
     * @throws IOException
     */
    protected File executeProgram(String progname, String ending, File inputFile)
            throws IOException {
        try {
            ProcessBuilder builder = new ProcessBuilder(progname, inputFile.toString());
            builder.directory(tmpDir);
            int resultCode = builder.start().waitFor();
            if (resultCode != 0) {
                throw new RuntimeException(progname + " error");
            }
        }
        catch (InterruptedException e) {
        }
        return new File(tmpDir, changeFileend(inputFile.getName(), ending));
    }
}
