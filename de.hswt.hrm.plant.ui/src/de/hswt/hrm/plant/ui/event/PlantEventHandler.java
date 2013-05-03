package de.hswt.hrm.plant.ui.event;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;

import de.hswt.hrm.plant.ui.wizard.PlantWizard;

public class PlantEventHandler {

    public void clickButton(Event event) {
        System.out.println("Klick");
        Button b = (Button) event.widget;
        WizardDialog dialog = new WizardDialog(b.getShell(), new PlantWizard());
        dialog.open();
    }

}
