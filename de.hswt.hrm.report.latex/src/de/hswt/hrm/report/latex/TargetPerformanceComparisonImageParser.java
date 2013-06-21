package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Properties;

import de.hswt.hrm.photo.model.Photo;

// TODO more than 2 photos possible. To implement: even-odd handler 
public class TargetPerformanceComparisonImageParser {

    private StringBuffer buffer = new StringBuffer();

    private Properties prop = new Properties();

    private final String IMAGE_HEADER_ONE = ":nameFirstImage:";
    private final String IMAGE_HEADER_TWO = ":nameSecondImage:";
    private final String IMAGE_ONE = ":imageOne:";
    private final String IMAGE_TWO = ":imageTwo:";

    private Photo[] pics;

    public String parse(String pathDir, Collection<Photo> pictures) throws IOException {
        // TODO check paths
        prop.load(Files.newInputStream(Paths.get(pathDir, "templates",
                "targetperformancecomparisonimagerow.properties")));
        this.pics = pictures.toArray(new Photo[pictures.size()]);
        buffer.setLength(0);
        
        // Decide, depending on number Photos, what to parse and how

        // 5 = odd
        // i = 1 => indices 0 and 1
        // i = 3 => indices 2 and 3
        // i = 5 => end
        
        // 4 = even
        // i = 1 => indices 0 and 1
        // i = 3 => indices 2 and 3
        // i = 5 => end
        
        // parse two images lines 
        for (int i = 1; i <= pics.length - 1; i += 2) {
        	parseTwoImagesLine(i);
        }
        
        // parse one image line
        if (pics.length % 2 > 0) {
        	parseOneImageLine();
        }
       
//        for (int i = 0; i <= Math.floor(this.pics.length / 2); i++) {
//            if (pics.length % 2 == 1) {
//                if ((i * 2 + 1) <= pics.length - 2) {
//                    buffer.append(prop
//                            .getProperty("targetperformancecomparison.image.header.twoimage")
//                            .replace(IMAGE_HEADER_ONE, pics[i * 2].getLabel())
//                            .replace(IMAGE_HEADER_TWO, pics[i * 2 + 1].getLabel()));
//                    this.appendNewLine();
//                    buffer.append(prop
//                            .getProperty("targetperformancecomparison.image.row.twoimage")
//                            .replace(IMAGE_ONE, pics[i * 2].getName())
//                            .replace(IMAGE_TWO, pics[i * 2 + 1].getName()));
//                    this.appendNewLine();
//
//                }
//                else {
//                    buffer.append(prop.getProperty(
//                            "targetperformancecomparison.image.header.oneimage").replace(
//                            IMAGE_HEADER_ONE, pics[pics.length - 1].getLabel()));
//                    this.appendNewLine();
//                    buffer.append(prop
//                            .getProperty("targetperformancecomparison.image.row.oneimage").replace(
//                                    IMAGE_ONE, pics[pics.length - 1].getName()));
//                    this.appendNewLine();
//
//                }
//            }
//            else {
//                if ((i * 2 + 1) <= pics.length - 1) {
//                    buffer.append(prop
//                            .getProperty("targetperformancecomparison.image.header.twoimage")
//                            .replace(IMAGE_HEADER_ONE, pics[i * 2].getLabel())
//                            .replace(IMAGE_HEADER_TWO, pics[i * 2 + 1].getLabel()));
//                    this.appendNewLine();
//                    buffer.append(prop
//                            .getProperty("targetperformancecomparison.image.row.twoimage")
//                            .replace(IMAGE_ONE, pics[i * 2].getName())
//                            .replace(IMAGE_TWO, pics[i * 2 + 1].getName()));
//                    this.appendNewLine();
//                }
//            }
//
//        }

        return buffer.toString();

    }

    private void parseOneImageLine() {
        buffer.append(prop.getProperty(
                "targetperformancecomparison.image.header.oneimage").replace(
                IMAGE_HEADER_ONE, pics[pics.length - 1].getLabel()));
        this.appendNewLine();
        buffer.append(prop
                .getProperty("targetperformancecomparison.image.row.oneimage").replace(
                        IMAGE_ONE, pics[pics.length - 1].getName()));
        this.appendNewLine();
    }
    
    private void parseTwoImagesLine(int i) {
        buffer.append(prop
                .getProperty("targetperformancecomparison.image.header.twoimage")
                .replace(IMAGE_HEADER_ONE, pics[i - 1].getLabel())
                .replace(IMAGE_HEADER_TWO, pics[i].getLabel()));
        this.appendNewLine();
        buffer.append(prop
                .getProperty("targetperformancecomparison.image.row.twoimage")
                .replace(IMAGE_ONE, pics[i - 1].getName())
                .replace(IMAGE_TWO, pics[i].getName()));
        this.appendNewLine();
    }
    
    private void appendNewLine() {
        buffer.append("\n");
    }

}
