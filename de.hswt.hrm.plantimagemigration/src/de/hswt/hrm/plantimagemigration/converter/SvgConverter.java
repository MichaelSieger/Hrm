package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.IOException;

/**
 * Converts Pdf files to Svg
 * 
 * @author Michael Sieger
 * 
 */
public class SvgConverter extends Converter {

    public SvgConverter(File resultDir, File srcDir, File tmpDir) {
        super(resultDir, srcDir, tmpDir);
    }

    @Override
    protected File executeConversion(File fin) throws IOException {
        File fout = new File(tmpDir, changeFileend(fin.getName(), ".svg"));
        ProcessBuilder b = new ProcessBuilder("pdf2svg", fin.getAbsolutePath(),
                fout.getAbsolutePath());
        b.directory(tmpDir);
        try {
            int resultCode = b.start().waitFor();
            if (resultCode != 0) {
                throw new RuntimeException("pdf2svg error");
            }
        }
        catch (InterruptedException e) {
        }
        return fout;
    }

}
