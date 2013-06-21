package de.hswt.hrm.scheme.ui;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import de.hswt.hrm.common.ui.swt.canvas.DoubleBufferedCanvas;
import de.hswt.hrm.scheme.listener.ModifyListener;
import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * A Widget that displays components in a grid.
 * 
 * @author Michael Sieger
 * 
 */
public class SchemeGrid extends DoubleBufferedCanvas {
	
	private static final int ENLARGE_TRESH = 5;
	private static final int ENLARGE = 5;

	private List<SchemeGridItem> images = new ArrayList<>();
	private final List<Colorbox> colors = new ArrayList<>();

	private final List<ModifyListener> modifyListeners = new ArrayList<>();
	
	/**
	 * Width and Height in grid
	 */
	private int width, height;
	
	/**
	 * A item that is listening for a item click
	 */
	private ItemClickListener listener;

	/**
	 * Was this SchemeGrid changed since last clearDirty call?
	 */
	private boolean dirty;
	
	/**
	 * The pixel per grid value. 
	 */
	private int ppg;

	public SchemeGrid(Composite parent, int style, int width, int height,
			int pixelPerGrid) {
		super(parent, style);
		this.width = width;
		this.height = height;
		setPixelPerGrid(pixelPerGrid);
		/*
		super.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent ev) {
				draw(ev.gc);
			}
		});
		*/
		initMouseListener();
	}
	
	
	private void initMouseListener(){
		super.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				SchemeGridItem item = getImage(getGridX(e.x), getGridY(e.y));
				if(item != null){
					listener.itemClicked(e, item);
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {}

			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
	}

	@Override
	protected void drawBuffered(GC gc) {
		super.drawBuffered(gc);
		draw(gc);
	}


	/**
	 * Draws the widget into this GC object
	 * 
	 * @param gc
	 */
	private void draw(GC gc) {
		drawColors(gc);
		drawHorizontalLines(gc);
		drawVerticalLines(gc);
		drawImages(gc);
	}

	private void drawColors(GC gc) {
		final float quadW = getQuadWidth();
		final float quadH = getQuadHeight();
		for (Colorbox box : colors) {
			gc.setBackground(box.getColor());
			gc.fillRectangle(Math.round(quadW * box.getX()) + 1,
					Math.round(quadH * box.getY()) + 1,
					Math.round(quadW * box.getWidth()) - 1,
					Math.round(quadH * box.getHeight()) - 1);
		}
	}

	/**
	 * Draws all images
	 * 
	 * @param gc
	 */
	private void drawImages(GC gc) {
		for (SchemeGridItem item : images) {
			drawImage(
					gc,
					item.getImage().getImage(),
					item.getBoundingBox());
		}
	}

	/**
	 * Draws a single image.
	 * 
	 * @param gc
	 * @param image
	 *            The image
	 * @param rec
	 *            The space where the image shall be drawn
	 */
	private void drawImage(GC gc, Image image, Rectangle rec) {
		final float quadW = getQuadWidth();
		final float quadH = getQuadHeight();
		Rectangle imageBounds = image.getBounds();
		gc.drawImage(image, 0, 0, imageBounds.width, imageBounds.height,
				Math.round(quadW * rec.x) + 1, Math.round(quadH * rec.y) + 1,
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
		gc.drawLine(0, rec.height - 1, rec.width, rec.height - 1);
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
		gc.drawLine(rec.width - 1, 0, rec.width - 1, rec.height);
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
	private int getGridX(int x) {
		return (int) (((float) x) / getQuadWidth());
	}

	/**
	 * Coverts pixel position to grid position
	 * 
	 * @param x
	 * @return
	 */
	private int getGridY(int y) {
		return (int) (((float) y) / getQuadHeight());
	}

	/**
	 * The image is placed at the given grid position. Throws an
	 * IllegalArgumentException if the image is outside of the grid
	 * 
	 * @param item
	 *            The item to be placed
	 * @throws PlaceOccupiedException
	 *             If the space is occupied already
	 */
	public void setImage(SchemeGridItem item) throws PlaceOccupiedException {
		Rectangle r = item.getBoundingBox();
		checkArgument(r.x >= 0 && r.x + r.width <= width);
		checkArgument(r.y >= 0 && r.y + r.height <= height);
		for (SchemeGridItem c : images) {
			if (c.intersects(item)) {
				throw new PlaceOccupiedException(
						"The image intersects with an image that is already there");
			}
		}
		images.add(item);
		setDirty();
		enlarge(r.x + r.width, r.y + r.height);
		this.redraw();
	}
	
	/**
	 * Enlarges the grid by ENLARGE, if the distance of the point to the border
	 * is below ENLARGE_TRESH.
	 * 
	 * @param x
	 * @param y
	 */
	private void enlarge(int x, int y){
		if(width - x < ENLARGE_TRESH){
			width += ENLARGE;
		}
		if(height - y < ENLARGE_TRESH) {
			height += ENLARGE;
		}
		updateSize();
	}

	/**
	 * The image is placed at the given position in pixel. The image snaps to
	 * the nearest grid position.
	 * 
	 * @param image
	 *            The item to be placed
	 * @param direction
	 *            The rotation direction of the image
	 * @param x
	 *            The x position in pixel
	 * @param y
	 *            The y position in pixel
	 * @throws PlaceOccupiedException
	 *             If the space is occupied already
	 */
	public void setImageAtPixel(RenderedComponent image, Direction direction,
			int x, int y) throws PlaceOccupiedException {
		setImage(new SchemeGridItem(image, direction, getGridX(x), getGridY(y)));
	}

	/**
	 * Removes and returns the image at the given grid position. Returns null if
	 * there is no image.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public SchemeGridItem removeImage(int x, int y) {
		SchemeGridItem res = getImage(x, y);
		if (res != null) {
			if(images.remove(res)){
			    this.redraw();
			    setDirty();
			}
			return res;
		}
		return null;
	}
	
	/**
	 * Returns the image at the given grid position. Returns null if
	 * there is no image.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public SchemeGridItem getImage(int x, int y){
		Point p = new Point(x, y);
		SchemeGridItem res = null;
		for (SchemeGridItem cont : images) {
			if (cont.intersects(p)) {
				res = cont;
				break;
			}
		}
		return res;
	}

	/**
	 * Removes and returns the image at the given pixel position Returns null if
	 * there is no image.
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
	 * @param ppg
	 *            how many pixel does a gridbox use
	 */
	public void setPixelPerGrid(int ppg) {
		this.ppg = ppg;
		updateSize();
	}
	
	private void updateSize(){
		setSize(width * ppg, height * ppg);
	}

	public void clearColors() {
		colors.clear();
		this.redraw();
	}

	public void setColor(Color shadowColor, int x, int y, int w, int h) {
		colors.add(new Colorbox(getGridX(x), getGridY(y), w, h, shadowColor));
		this.redraw();
	}
	
	public void setItemClickListener(ItemClickListener l){
		listener = l;
	}


	public void removeItem(SchemeGridItem item) {
		if(images.remove(item)){
			setDirty();
			this.redraw();
		}
	}
	
	/**
	 * Was the grid changed since last clear
	 * 
	 * @return
	 */
	public boolean isDirty(){
	    return dirty;
	}
	
	/**
	 * Set dirty to false
	 */
	public void clearDirty(){
	    dirty = false;
	}

	private void setDirty() {
		dirty = true;
		fireModifyEvent();
	}
	
	private void fireModifyEvent() {
		for (ModifyListener listener : modifyListeners) {
			Event e = new Event();
			e.widget = this;
			listener.modified(e);
		}
	}


	public Collection<SchemeGridItem> getItems() {
		/*
		 * Copy everything so that the caller can't play around with internal data.
		 */
		List<SchemeGridItem> r = new ArrayList<>();
		for(SchemeGridItem item : images){
			r.add(new SchemeGridItem(item));
		}
		return r;
	}

	/**
	 * Sets the SchemeGridItems of this SchemeGrid
	 * 
	 * @param c
	 * @throws PlaceOccupiedException If two or more Items overlap
	 */
	public void setItems(Collection<SchemeGridItem> c) throws PlaceOccupiedException{
		setDirty();
		images.clear();
	    for(SchemeGridItem item : c){
	    	setImage(item);
	    }
		redraw();
	}
	
	public void addModifyListener(ModifyListener listener) {
		if (modifyListeners.contains(listener)) {
			return;
		}
		modifyListeners.add(listener);
	}
	
	public void removeModifyListener(ModifyListener listener) {
		modifyListeners.remove(listener);
	}
}
