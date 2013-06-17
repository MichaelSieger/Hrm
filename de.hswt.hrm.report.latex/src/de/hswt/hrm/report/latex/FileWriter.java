package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * writes parsed LaTeX files
 */

public class FileWriter {

    public void writeToFile(String dirPath, String fileName, String content) throws IOException {
        Path filePath = Paths.get(dirPath, fileName);
        Files.write(filePath, content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

}
