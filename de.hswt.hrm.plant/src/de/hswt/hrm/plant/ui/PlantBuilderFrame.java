package de.hswt.hrm.plant.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

public class PlantBuilderFrame extends Composite{
    
    private PlantGrid toolbox, builderField;

    public PlantBuilderFrame(Composite parent, int style) {
        super(parent, style);
        setLayout(new RowLayout());
        toolbox = new PlantGrid(this, SWT.NONE, 2, 10);
        builderField = new PlantGrid(this, SWT.NONE, 20, 20);
    }

}
