package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Properties;

import de.hswt.hrm.photo.model.Photo;

// TODO more than 2 photos possible. To implement: even-odd handler 
public class TargetPerformanceComparisonImageParser {

    private String target;

    private StringBuffer buffer = new StringBuffer();

    private Properties prop = new Properties();

    private final String IMAGE_HEADER_ONE = ":nameFirstImage:";
    private final String IMAGE_HEADER_TWO = ":nameSecondImage:";
    private final String IMAGE_ONE = ":imageOne:";
    private final String IMAGE_TWO = ":imageTwo:";

    private Photo[] pics;

    public String parse(String pathDir, Collection<Photo> pictures) throws IOException {

        prop.load(Files.newInputStream(Paths.get(pathDir, "templates",
                "targetperformancecomparisonimagerow.properties")));
        this.pics = pictures.toArray(new Photo[pictures.size()]);
        buffer.setLength(0);
        
        //TODO array indices
        if (pictures.size() == 1) {
            buffer.append(prop.getProperty("targetperformancecomparison.image.header.oneimage")
                    .replace(IMAGE_HEADER_ONE, pics[0].getLabel()));
            this.appendNewLine();
            buffer.append(prop.getProperty("targetperformancecomparison.image.row.oneimage")
                    .replace(IMAGE_ONE, pics[0].getName()));
            target = buffer.toString();
        }
        else if (pictures.size() == 2) {
            buffer.append(prop.getProperty("targetperformancecomparison.image.header.twoimage")
                    .replace(IMAGE_HEADER_ONE, pics[0].getLabel())
                    .replace(IMAGE_HEADER_TWO, pics[1].getLabel()));
            this.appendNewLine();
            buffer.append(prop.getProperty("targetperformancecomparison.image.row.twoimage")
                    .replace(IMAGE_ONE, pics[0].getName()).replace(IMAGE_TWO, pics[1].getName()));
            this.appendNewLine();
            target = buffer.toString();
        }
        else if ((pictures.size() % 2 == 1)) {
            for (int i = 0; i < pics.length; i++) {
                while (i < pics.length - 1) {
                    buffer.append(prop
                            .getProperty("targetperformancecomparison.image.header.twoimage")
                            .replace(IMAGE_HEADER_ONE, pics[i * 2].getLabel())
                            .replace(IMAGE_HEADER_TWO, pics[i * 2 + 1].getLabel()));
                    this.appendNewLine();
                    buffer.append(prop
                            .getProperty("targetperformancecomparison.image.row.twoimage")
                            .replace(IMAGE_ONE, pics[i * 2].getName())
                            .replace(IMAGE_TWO, pics[i * 2 + 1].getName()));
                    this.appendNewLine();

                }
                buffer.append(prop.getProperty("targetperformancecomparison.image.header.oneimage")
                        .replace(IMAGE_HEADER_ONE, pics[0].getLabel()));
                this.appendNewLine();
                buffer.append(prop.getProperty("targetperformancecomparison.image.row.oneimage")
                        .replace(IMAGE_ONE, pics[0].getName()));
                this.appendNewLine();
                target = buffer.toString();
            }
        }
        else {
            for (int i = 0; i < pics.length; i++) {
                buffer.append(prop.getProperty("targetperformancecomparison.image.header.twoimage")
                        .replace(IMAGE_HEADER_ONE, pics[0].getLabel())
                        .replace(IMAGE_HEADER_TWO, pics[1].getLabel()));
                this.appendNewLine();
                buffer.append(prop.getProperty("targetperformancecomparison.image.row.twoimage")
                        .replace(IMAGE_ONE, pics[0].getName())
                        .replace(IMAGE_TWO, pics[1].getName()));
                this.appendNewLine();
                target = buffer.toString();
            }

        }

        return target;

    }

    private void appendNewLine() {
        buffer.append("\n");
    }

}
