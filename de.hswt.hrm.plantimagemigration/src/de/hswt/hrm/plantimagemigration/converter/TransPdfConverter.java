package de.hswt.hrm.plantimagemigration.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Get transparency and remove margins of PDF pictures
 * 
 * @author Anton Schreck
 *
 */

public class TransPdfConverter extends Converter{

    public TransPdfConverter(File resultDir, File srcDir, File tmpDir) {
        super(resultDir, srcDir, tmpDir);       
    }
    
    protected File executeConversion(File inputFile) throws IOException {
        File tmpFile = new File(tmpDir, inputFile.getName());
        try {
            // TODO: Fix transparency
            Runtime runner = Runtime.getRuntime();
            Process p = runner.exec("inkscape -f "+inputFile.toString()+
                    " --verb=EditSelectAll --verb=SelectionUngroup --verb=EditDeselect"+
                    " --verb=EditSelectNext --verb=SelectionCutPath -D"+
                    " -A "+tmpFile.toString());
            int resultCode = p.waitFor();
            if (resultCode != 0) {
                throw new RuntimeException("TransPDF error");
            }
        } catch (InterruptedException e){
            
        }
        return tmpFile;
    }
    
}
