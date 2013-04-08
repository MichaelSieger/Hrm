package de.hswt.hrm.plant.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.plant.model.PlantImage;


public class PlantBuilderFrame extends Composite{
    
    private static final int TOOLBOX_WIDTH = 200;
    
    private PlantGrid toolbox, builderField;

    public PlantBuilderFrame(
            Composite parent, 
            int style,
            PlantImage[] images) {
        super(parent, style);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        setLayout(layout);
        createBuilderField();
        createToolbox(images);
        this.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
        this.layout();
    }
    
    private void createBuilderField(){
        builderField = new PlantGrid(this, SWT.NONE, 20, 20);
        GridData gridData = new GridData(GridData.FILL, 
                GridData.FILL, true, true);
        builderField.setLayoutData(gridData);
    }
    
    private void createToolbox(PlantImage[] images){
        toolbox = new PlantGrid(this, SWT.NONE, 
                2, 
                images.length/2+1);
        toolbox.setSize(TOOLBOX_WIDTH, 100);
        GridData gridData = new GridData();
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        toolbox.setLayoutData(gridData);
    }

}
