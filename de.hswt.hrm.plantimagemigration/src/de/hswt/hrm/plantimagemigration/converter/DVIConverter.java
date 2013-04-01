package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Converts the .tex files to .dvi
 * 
 * @author Michael Sieger
 * 
 */
public class DVIConverter extends Converter{


    public DVIConverter(File resultDir, File srcDir, File tmpDir) {
        super(resultDir, srcDir, tmpDir);
    }

    @Override
    protected File executeConversion(File fin) throws IOException {
        return executeProgram("latex", ".dvi", fin);
    }

}
