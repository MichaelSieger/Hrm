package de.hswt.hrm.plant.logic;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import de.hswt.hrm.plant.model.GridImage;
import de.hswt.hrm.plant.model.RenderedGridImage;

/**
 * This class converts GridImages to RenderedGridImages
 * 
 * @author Michael Sieger
 *
 */
public class GridImageConverter {
	
	private static final int PPG = 100,			//Pixel per grid
								PPG_THUMBNAIL = 20;
	
	public static RenderedGridImage convert(Display display, GridImage gridImage) throws IOException{
		ByteBuffer buf = ByteBuffer.wrap(gridImage.getImage());
		PDFFile pdffile = new PDFFile(buf);
		PDFPage page = pdffile.getPage(0);
		return new RenderedGridImage(
				getSWTImage(display, renderImage(page, gridImage.getWidth()*PPG_THUMBNAIL, gridImage.getHeight()*PPG_THUMBNAIL)),
				getSWTImage(display, renderImage(page, gridImage.getWidth()*PPG, gridImage.getHeight()*PPG)), 
				gridImage);
	}
	
	private static Image renderImage(final PDFPage page, final int w, final int h){
		Rectangle rec = new Rectangle(0, 0, w, h);
		return page.getImage(w, h, rec, null, true, true);
	}
	
	private static org.eclipse.swt.graphics.Image getSWTImage(Display display, Image awtImage){
		return new org.eclipse.swt.graphics.Image(display, getSWTData((BufferedImage) awtImage));
	}
	
	private static ImageData getSWTData(BufferedImage bufferedImage) {
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

}
