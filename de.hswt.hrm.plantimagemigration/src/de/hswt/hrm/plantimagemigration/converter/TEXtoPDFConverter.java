package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Converts the .tex files to .pdf
 * 
 * @author Marco Luthardt
 * 
 */
public class TEXtoPDFConverter extends Converter{

    public TEXtoPDFConverter(File resultDir, File srcDir, File tmpDir) {
        super(resultDir, srcDir, tmpDir);
    }

    @Override
    protected File executeConversion(File fin) throws IOException {
    	File tmpFile = new File(tmpDir, fin.getName());
        Files.copy(fin.toPath(), tmpFile.toPath());
    	String[] args = new String[] {
   			"pdflatex",
   			"-interaction=nonstopmode",
   			"-shell-escape",
   			tmpFile.toString()
    	};
    	
    	System.out.println("creating pdf from " + fin.toString());
    	
        return executeProgram(args, ".pdf", tmpFile);
    }
}
