package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.IOException;

public class PSConverter extends Converter{

    public PSConverter(File resultDir, File srcDir, File tmpDir) {
        super(resultDir, srcDir, tmpDir);
    }

    @Override
    protected File executeConversion(File fin) throws IOException {
        return executeProgram("dvips", ".ps", fin);
    }

}
