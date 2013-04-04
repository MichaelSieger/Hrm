package de.hswt.hrm.plant.ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

public class ImageWidget extends Composite{
    
    private Canvas canvas;

    public ImageWidget(Composite parent, int style) {
        super(parent, style);
        Frame frame = SWT_AWT.new_Frame(this);
        frame.setLayout(new BorderLayout());
        canvas = new Canvas();
        frame.add(canvas, BorderLayout.CENTER);
    }
    
    public void setImage(Image img){
        clearGraphics(canvas);
        canvas.getGraphics()
        .drawImage(img, 0, 0, 
                img.getWidth(canvas), 
                img.getHeight(canvas),
                canvas);
    }
    
    private void clearGraphics(Canvas c){
        c.getGraphics()
        .fillRect(0, 0, c.getWidth(), c.getHeight());
    }
    
}
