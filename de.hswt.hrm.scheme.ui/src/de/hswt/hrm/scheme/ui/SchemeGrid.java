package de.hswt.hrm.scheme.ui;


import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * A Widget that displays components in a grid.
 * 
 * @author Michael Sieger
 * 
 */
public class SchemeGrid extends Canvas{
    
    private final List<SchemeGridItem> images = new ArrayList<>();
    private final int width, height;
    
    public SchemeGrid(Composite parent, int style, int width, int height, int pixelPerGrid) {
        super(parent, style);
        this.width = width;
        this.height = height;
        setPixelPerGrid(pixelPerGrid);
        super.addPaintListener(new PaintListener() {

            @Override
            public void paintControl(PaintEvent ev) {
                draw(ev.gc);
            }
        });
      
    }

    /**
     * Draws the widget into this GC object
     * 
     * @param gc
     */
    private void draw(GC gc) {
        drawHorizontalLines(gc);
        drawVerticalLines(gc);
        drawImages(gc);
    }

    /**
     * Draws all images
     * 
     * @param gc
     */
    private void drawImages(GC gc) {
        for(SchemeGridItem item : images){
            drawImage(gc, 
            		  item.getRenderedComponent()
            				.getByDirection(item.getDirection()).getImage(), 
            		  item.getBoundingBox());
        }
    }

    /**
     * Draws a single image.
     * 
     * @param gc
     * @param image The image
     * @param rec The space where the image shall be drawn
     */
    private void drawImage(GC gc, Image image, Rectangle rec) {
        final float quadW = getQuadWidth();
        final float quadH = getQuadHeight();
        Rectangle imageBounds = image.getBounds();
        gc.drawImage(image, 
        		0, 
        		0, 
        		imageBounds.width, 
        		imageBounds.height, 
        		Math.round(quadW * rec.x) + 1, 
        		Math.round(quadH * rec.y) + 1,
                Math.round(quadW * rec.width) - 1, 
                Math.round(quadH * rec.height) - 1);
    }

    /**
     * Draws the horizontal lines. One line per gridbox.
     * 
     * @param gc
     */
    private void drawHorizontalLines(GC gc) {
        Rectangle rec = this.getBounds();
        final float quadH = getQuadHeight();
        for (int i = 0; i < height; i++) {
            final int y = Math.round(i * quadH);
            gc.drawLine(0, y, rec.width, y);
        }
        gc.drawLine(0, rec.height-1, rec.width, rec.height-1);
    }
    
    /**
     * Draws the vertical lines. One line per gridbox.
     * 
     * @param gc
     */
    private void drawVerticalLines(GC gc) {
        Rectangle rec = this.getBounds();
        final float quadW = getQuadWidth();
        for (int i = 0; i < width; i++) {
            final int x = Math.round(i * quadW);
            gc.drawLine(x, 0, x, rec.height);
        }
        gc.drawLine(rec.width-1, 0, rec.width-1, rec.height);
    }

    /**
     * @return The pixel width of a gridbox
     */
    private float getQuadWidth() {
        return ((float) getBounds().width) / width;
    }
    
    /**
     * @return The pixel height of a gridbox
     */
    private float getQuadHeight() {
        return ((float) getBounds().height) / height;
    }
    
    /**
     * Coverts pixel position to grid position
     * 
     * @param x
     * @return
     */
    private int getGridX(int x){
    	return (int)(((float)x)/getQuadWidth());
    }
    
    /**
     * Coverts pixel position to grid position
     * 
     * @param x
     * @return
     */
    private int getGridY(int y){
    	return (int)(((float)y)/getQuadHeight());
    }

    /**
     * The image is placed at the given grid position.
     * Throws an IllegalArgumentException if the image is outside of the grid
     * 
     * @param item The item to be placed
     * @throws PlaceOccupiedException If the space is occupied already
     */
    public void setImage(SchemeGridItem item) throws PlaceOccupiedException {
    	Rectangle r = item.getBoundingBox();
        checkArgument(r.x >= 0 && r.x + r.width <= width);
        checkArgument(r.y >= 0 && r.y + r.height <= height);
        for(SchemeGridItem c : images){
            if(c.intersects(item)){
                throw new PlaceOccupiedException("The image intersects with an image that is already there");
            }
        }
        images.add(item);
        this.redraw();
    }
    
    /**
     * The image is placed at the given position in pixel.
     * The image snaps to the nearest grid position.
     * 
     * @param image The item to be placed
     * @param direction The rotation direction of the image
     * @param x The x position in pixel
     * @param y The y position in pixel
     * @throws PlaceOccupiedException If the space is occupied already
     */
    public void setImageAtPixel(RenderedComponent image, Direction direction, int x, int y) throws PlaceOccupiedException{
    	setImage(new SchemeGridItem(image, direction, getGridX(x), getGridY(y)));
    }
    
    /**
     * Removes and returns the image at the given grid position.
     * Returns null if there is no image.
     * 
     * @param x
     * @param y
     * @return
     */
    public SchemeGridItem removeImage(int x, int y){
    	Point p = new Point(x, y);
    	SchemeGridItem res = null;
    	for(SchemeGridItem cont : images){
    		if(cont.intersects(p)){
    			res = cont;
    			break;
    		}
    	}
    	if(res != null){
    		images.remove(res);
    		this.redraw();
    		return res;
    	}
    	return null;
    }

    /**
     * Removes and returns the image at the given pixel position
     * Returns null if there is no image.
     * 
     * @param x
     * @param y
     * @return
     */
	public SchemeGridItem removeImagePixel(int x, int y) {
		return removeImage(getGridX(x), getGridY(y));
	}

	/**
	 * Sets the pixel per grid value.
	 * 
	 * @param ppg how many pixel does a gridbox use
	 */
	public void setPixelPerGrid(int ppg) {
		setSize(width*ppg, height*ppg);
	}

}
