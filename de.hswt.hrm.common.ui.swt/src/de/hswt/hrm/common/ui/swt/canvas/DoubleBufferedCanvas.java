package de.hswt.hrm.common.ui.swt.canvas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
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
		/*
		 * SWT.NO_BACKGROUND disables background filling. 
		 * I do it myself bacause i want to do it in the backbuffer.
		 */
		super(parent, style | SWT.NO_BACKGROUND);
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				Image backbuffer = getBackbuffer();
				GC gc = null;
				try{
					gc = new GC(backbuffer);
					//Fill image with background color
					gc.fillRectangle(0, 0, backbuffer.getBounds().width, backbuffer.getBounds().height);
					//The actual drawing is done here
					drawBuffered(gc);
					e.gc.drawImage(backbuffer, 0, 0);	//Copy backbuffer to frontbuffer
				}finally{
					if(gc != null){
						gc.dispose();
					}
				}
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
		Rectangle widRec = getBounds();
		if(backbuffer != null){
			Rectangle bufRec = backbuffer.getBounds();
			if(bufRec.width != widRec.width || bufRec.height != widRec.height){
				initBackbuffer();
			}
		}else{
			initBackbuffer();
		}
		return backbuffer;
	}
	
	private void initBackbuffer(){
		if(backbuffer != null){
			backbuffer.dispose();
		}
		Rectangle widRec = getBounds();
		backbuffer = new Image(getDisplay(), widRec.width, widRec.height);
	}
	
}
