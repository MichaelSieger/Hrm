package de.hswt.hrm.plant.ui;

import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PSDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

import de.hswt.hrm.plant.model.PSGridImage;

/**
 * This is a rendered version of PSGridImage. The PostScript is converted to an image object by
 * ghost4j.
 * 
 * @author Michael Sieger
 * 
 */
public class RenderedGridImage {

    private static final int DPI = 300;

    private PSGridImage gridImage;
    private Image renderedImage;

    public RenderedGridImage(Device device, PSGridImage gridImage) throws IOException,
            RendererException, DocumentException, NotSinglePageException {
        this.gridImage = gridImage;
        PSDocument doc = new PSDocument();
        doc.load(new ByteArrayInputStream(gridImage.getPostScript().getBytes("UTF-8")));
        SimpleRenderer ren = new SimpleRenderer();
        ren.setResolution(DPI);
        List<java.awt.Image> renderResult = ren.render(doc);
        if (renderResult.size() != 1) {
            throw new NotSinglePageException();
        }
        renderedImage = new Image(device, convertImage(device, renderResult.get(0)));
    }

    private ImageData convertImage(Device device, java.awt.Image awtImage) {
        BufferedImage bufferedImage = toBufferedImage(awtImage);
        if (bufferedImage.getColorModel() instanceof DirectColorModel) {
            DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
            PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask());
            ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    int rgb = bufferedImage.getRGB(x, y);
                    int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF)); 
                    data.setPixel(x, y, pixel);
                    if (colorModel.hasAlpha()) {
                        data.setAlpha(x, y, (rgb >> 24) & 0xFF);
                    }
                }
            }
            return data;        
        } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
            IndexColorModel colorModel = (IndexColorModel)bufferedImage.getColorModel();
            int size = colorModel.getMapSize();
            byte[] reds = new byte[size];
            byte[] greens = new byte[size];
            byte[] blues = new byte[size];
            colorModel.getReds(reds);
            colorModel.getGreens(greens);
            colorModel.getBlues(blues);
            RGB[] rgbs = new RGB[size];
            for (int i = 0; i < rgbs.length; i++) {
                rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
            }
            PaletteData palette = new PaletteData(rgbs);
            ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
            data.transparentPixel = colorModel.getTransparentPixel();
            WritableRaster raster = bufferedImage.getRaster();
            int[] pixelArray = new int[1];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    raster.getPixel(x, y, pixelArray);
                    data.setPixel(x, y, pixelArray[0]);
                }
            }
            return data;
        }
        return null;
    }

    private BufferedImage toBufferedImage(java.awt.Image img) {
        if (img.getClass() == BufferedImage.class) {
            return (BufferedImage) img;
        }
        BufferedImage bufImg = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        bufImg.getGraphics().drawImage(img, 0, 0, null);
        return bufImg;
    }

    public PSGridImage getSchemeGrid() {
        return gridImage;
    }

    public Image getRenderedImage() {
        return renderedImage;
    }

}
