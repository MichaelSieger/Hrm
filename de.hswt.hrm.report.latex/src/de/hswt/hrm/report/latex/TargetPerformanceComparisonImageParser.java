package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Properties;

import de.hswt.hrm.photo.model.Photo;

public class TargetPerformanceComparisonImageParser {

    private Properties prop = new Properties();
    private StringBuffer buffer = new StringBuffer();
    private String target;

    private final String IMAGE_HEADER_ONE = ":nameFirstImage:";
    private final String IMAGE_HEADER_TWO = ":nameSecondImage:";
    private final String IMAGE_ONE = ":imageOne:";
    private final String IMAGE_TWO = ":imageTwo:";

    public String parse(String pathDir, LinkedList<Photo> pictures) throws IOException {

        prop.load(Files.newInputStream(Paths.get(pathDir, "templates",
                "targetperformancecomparisonimagerow.properties")));

        if (pictures.size() == 1) {
            Photo picture = pictures.pop();
            buffer.append(prop.getProperty("targetperformancecomparison.image.header.oneimage")
                    .replace(IMAGE_HEADER_ONE, picture.getLabel()));
            buffer.append("\n");
            buffer.append(prop.getProperty("targetperformancecomparison.image.row.oneimage")
                    .replace(IMAGE_ONE, picture.getName()));
            target = buffer.toString();
        }
        else {

            Photo picture = pictures.pop();
            buffer.append(prop.getProperty("targetperformancecomparison.image.header.twoimage")
                    .replace(IMAGE_HEADER_ONE, picture.getLabel()));
            buffer.append("\n");
            buffer.append(prop.getProperty("targetperformancecomparison.image.row.twoimage")
                    .replace(IMAGE_ONE, picture.getName()));

            picture = pictures.pop();
            target = buffer.toString();
            target.replace(IMAGE_HEADER_TWO, picture.getLabel());
            target.replace(IMAGE_TWO, picture.getName());

        }

        return target;

    }

}
