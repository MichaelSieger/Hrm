package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.IOException;

/**
 * Converts Dvi files to Pdf
 * 
 * @author Michael Sieger
 * 
 */
public class PdfConverter extends Converter {

    public PdfConverter(File resultDir, File srcDir, File tmpDir) {
        super(resultDir, srcDir, tmpDir);
    }

    @Override
    protected File executeConversion(File fin) throws IOException {
        return executeProgram("dvipdf", ".pdf", fin);
    }

}
