package de.hswt.hrm.plantimagemigration;

import java.io.File;
import java.io.IOException;

import de.hswt.hrm.plantimagemigration.converter.Converter;
import de.hswt.hrm.plantimagemigration.converter.DVIConverter;
import de.hswt.hrm.plantimagemigration.converter.PdfConverter;
import de.hswt.hrm.plantimagemigration.converter.TransPdfConverter;
/**
 * This class handles the conversion process from the latex files to svg. It creates a number of
 * folders each one containing the intermediate step. The last one contains the resulting svg.
 * 
 * @author Michael Sieger
 * 
 */
public class PSLatex2PDFConverter {

    private static final String SOURCE_FOLDER = "symbols";
    private static final String DECONTEXT_FOLDER = "1";
    private static final String DVI_FOLDER = "2";
    private static final String PDF_FOLDER = "3";
    private static final String TRANS_PDF_FOLDER = "4";
    private static final String TMP_FOLDER = "tmp";

    private String dir;

    public PSLatex2PDFConverter(String dir) throws IOException {
        this.dir = dir;
        deleteOldData();
        createFolders();
        doDecontextStep();

        doStep(new DVIConverter(getDVIFolder(), getDecontextFolder(), getTmpFolder()));
        doStep(new PdfConverter(getPDFFolder(), getDVIFolder(), getTmpFolder()));
        doStep(new TransPdfConverter(getTransPdfFolder(),getPDFFolder(), getTmpFolder()));
        deleteDir(getTmpFolder());
    }

    /**
     * Processes all files from the previous step, and saves it in the next step.
     * 
     * @param c
     * @throws IOException
     */
    private void doStep(Converter c) throws IOException {
        for (File fin : c.getSrcDir().listFiles()) {
            c.convertFile(fin);
        }
    }

    private void doDecontextStep() {
        Decontexter decon = new Decontexter();
        File path = new File(dir, SOURCE_FOLDER);
        File[] files = path.listFiles();
        if (files == null) {
            System.out.println("symbols folder is missing");
            return;
        }
        File decontextFolder = getDecontextFolder();
        for (File fin : files) {
            try {
                File fout = new File(decontextFolder.getAbsolutePath(), fin.getName());
                decon.convertFile(fin, fout);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteOldData() {
        for (File dir : getResultFolders()) {
            deleteDir(dir);
        }
    }

    private void deleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        dir.delete();
    }

    private void createFolders() {
        for (File dir : getResultFolders()) {
            dir.mkdir();
        }
    }

    private File[] getResultFolders() {
        return new File[] { getDecontextFolder(), getTmpFolder(), getDVIFolder(),
                getPDFFolder(), getTransPdfFolder() };
    }

    private File getPDFFolder(){
        return new File(dir, PDF_FOLDER);
    }

    private File getTmpFolder() {
        return new File(dir, TMP_FOLDER);
    }

    private File getDVIFolder() {
        return new File(dir, DVI_FOLDER);
    }

    private File getDecontextFolder() {
        return new File(dir, DECONTEXT_FOLDER);
    }

    public File getTransPdfFolder() {
        return new File(dir, TRANS_PDF_FOLDER);
    }
}
