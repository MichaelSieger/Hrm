package de.hswt.hrm.plant.ui;

import java.awt.GridLayout;
import java.awt.Image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.plant.model.PlantImage;


public class PlantGrid extends Composite{
    
    private static final int FIELD_WIDTH = 20;

    public PlantGrid(Composite parent, int style, 
            int width, int height) {
        super(parent, style);
        GridLayout layout = new GridLayout();
        layout.setColumns(width);
        layout.setRows(height);
    }

    public void setPartAt(PlantImage image){
        ImageWidget widget = new ImageWidget(this, SWT.NONE);
        GridData gridData = new GridData();
        gridData.horizontalSpan = image.getWidth();
        gridData.verticalSpan = image.getHeight();
        widget.setLayoutData(gridData);
        widget.setSize(image.getWidth()*FIELD_WIDTH, 
                image.getHeight()*FIELD_WIDTH);
    }
}
