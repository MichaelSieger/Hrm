package de.hswt.hrm.plant.ui;

import java.awt.GridLayout;
import java.awt.Image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.plant.model.PlantImage;


public class PlantGrid extends Canvas{
    
    private RenderedPlantImage[][] quads;

    public PlantGrid(Composite parent, int style, 
            int width, int height) {
        super(parent, style);
        quads = new RenderedPlantImage[height][width];
        super.addPaintListener(new PaintListener() {
            
            @Override
            public void paintControl(PaintEvent ev) {
                draw(ev.gc);
            }
        });
    }
    
    private void draw(GC gc){
        drawHorizontalLines(gc);
        drawVerticalLines(gc);
    }
    
    private void drawHorizontalLines(GC gc){
        Rectangle rec = this.getBounds();
        final int h = getGridHeight();
        final float quadH = ((float)rec.y)/h;
        for(int i = 0; i < h; i++){
            final int y = Math.round(i*quadH);
            gc.drawLine(0, y, rec.x, y);
        }
    }

    private void drawVerticalLines(GC gc){
        Rectangle rec = this.getBounds();
        final int w = getGridWidth();
        final float quadW = ((float)rec.x)/w;
        for(int i = 0; i < w; i++){
            final int x = Math.round(i*quadW);
            gc.drawLine(x, 0, x, rec.y);
        }
    }
    
    private int getGridWidth(){
        return quads[0].length;
    }
    
    private int getGridHeight(){
        return quads.length;
    }
    
    public void setPartAt(RenderedPlantImage rendered,
                          int x, int y){
        final int w = rendered.getPlantImage().getWidth();
        final int h = rendered.getPlantImage().getHeight();
        for(int i = y; i < y+h; i++){
            for(int j = x; j < x+w; j++){
                quads[i][j] = rendered;
            }
        }
    }
    
    
}
