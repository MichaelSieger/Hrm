package de.hswt.hrm.plant.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.plant.dao.IPlantPartDAO;
import de.hswt.hrm.plant.model.PlantPart;

public class PlantBuilderFrame extends Composite{
    
    private PlantGrid toolbox, builderField;

    public PlantBuilderFrame(
            Composite parent, 
            int style,
            PlantPart[] parts) {
        super(parent, style);
        setLayout(new RowLayout());
        toolbox = new PlantGrid(this, SWT.NONE, 
                2, 
                parts.length/2+1);
        builderField = new PlantGrid(this, SWT.NONE, 20, 20);
    }

}
