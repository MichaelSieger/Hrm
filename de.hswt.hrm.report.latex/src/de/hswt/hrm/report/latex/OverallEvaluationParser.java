package de.hswt.hrm.report.latex;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Creates the file "overallevaluation.tex"
 */

public class OverallEvaluationParser {

    private String rating;

    /**
     * @param OverallEvaluation
     */
    public void setOverallRating(String rating) {
        this.rating = rating;

    }

    /**
     * 
     * @return status, true if O.K. false if not
     * @exception IOException
     * 
     */
    public Boolean write() {
        Path path = Paths.get("PATH_TO_overallevaluation.tex");
        InputStream is = new ByteArrayInputStream(rating.getBytes());
        try {
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

}
