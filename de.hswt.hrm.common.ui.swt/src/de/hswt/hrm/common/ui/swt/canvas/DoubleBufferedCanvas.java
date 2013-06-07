package de.hswt.hrm.common.ui.swt.canvas;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * This is a Canvas whose content is first drawn into a image, and then
 * this image is drawn to the display.
 * 
 * @author Michael Sieger
 *
 */
public class DoubleBufferedCanvas extends Canvas{
	
	private Image backbuffer;

	public DoubleBufferedCanvas(Composite parent, int style) {
		super(parent, style);
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				Image img = getBackbuffer();
				GC gc = new GC(img);
				drawBuffered(gc);
				e.gc.drawImage(img, 0, 0);	//Copy backbuffer to frontbuffer
			}
		});
	}
	/**
	 * The subclass should draw all content here.
	 * 
	 * @param gc
	 */
	protected void drawBuffered(GC gc){
		
	}
	
	private Image getBackbuffer(){
		Rectangle bufRec = backbuffer.getBounds();
		Rectangle widRec = getBounds();
		if(backbuffer == null || bufRec.width != widRec.width || bufRec.height != widRec.height){
			backbuffer = new Image(getDisplay(), widRec.width, widRec.height);
		}
		return backbuffer;
	}
	
}
