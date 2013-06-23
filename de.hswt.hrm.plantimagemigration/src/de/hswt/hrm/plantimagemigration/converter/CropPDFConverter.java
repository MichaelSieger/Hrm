package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Get transparency and remove margins of PDF pictures
 * 
 * @author Marco Luthardt
 *
 */

public class CropPDFConverter extends Converter{

    private File scriptFile;

	public CropPDFConverter(File resultDir, File srcDir, File tmpDir) {
        super(resultDir, srcDir, tmpDir);
        // if you know how to parse the margins parameter to pdfcrop 
        // with the java ProcessBuilder, you can replace this throw a 
        // direct call via ProcessBuilder
        createPDFCrobScript();
    }
    
    protected File executeConversion(File inputFile) throws IOException {
    	File tmpFile = new File(tmpDir, inputFile.getName());

    	String[] args;
    	
    	if (inputFile.getName().startsWith("p")) {
    		args = new String[4];
    		args[0] = "/bin/bash";
    		args[1] = scriptFile.toString();
    		args[2] = inputFile.toString();
    		args[3] = tmpFile.toString();
    	} else {
    		args = new String[3];
    		args[0] = "pdfcrop";
    		args[1] = inputFile.toString();
    		args[2] = tmpFile.toString();
    	}
    	
    	System.out.println("cropping pdf " + inputFile.toString());
    		
        return executeProgram(args, ".pdf", tmpFile);
    }

    // if you know how to parse the margins parameter to pdfcrop 
    // with the java ProcessBuilder, you can replace this throw a 
    // direct call via ProcessBuilder
    private void createPDFCrobScript() {
    	scriptFile = new File(tmpDir, "crop.sh");
    	PrintWriter writer;
		try {
			writer = new PrintWriter(scriptFile.toString(), "UTF-8");
	    	writer.println("#!/bin/sh");
	    	writer.println("pdfcrop -margins '-1 -1 -1 -1' $1 $2");
	    	writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    
}
