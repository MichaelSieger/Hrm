package de.hswt.hrm.plant.ui;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.List;

import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PSDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

import de.hswt.hrm.plant.model.PlantImage;

public class RenderedPlantImage {
    
    private static final int DPI = 300;
    
    private PlantImage plantImage;
    private Image renderedImage;
    
    public RenderedPlantImage(PlantImage plantImage) throws IOException, RendererException, DocumentException{
        this.plantImage = plantImage;
        PSDocument doc = new PSDocument();
        doc.load(new ByteArrayInputStream(
                plantImage.getPostScript().getBytes("UTF-8")));
        SimpleRenderer ren = new SimpleRenderer();
        ren.setResolution(DPI);
        List<Image> renderResult = ren.render(doc);
        if(renderResult.size() != 1){
            throw new RuntimeException(
                    "Rendering the PostScript didnt result in one image");
        }
        renderedImage = renderResult.get(0);
    }

    public PlantImage getPlantImage() {
        return plantImage;
    }

    public Image getRenderedImage() {
        return renderedImage;
    }
    
    

}
