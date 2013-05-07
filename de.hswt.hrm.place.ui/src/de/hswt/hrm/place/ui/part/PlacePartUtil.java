package de.hswt.hrm.place.ui.part;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.ui.wizard.PlaceWizard;


public class PlacePartUtil {
    
    public static Optional<Place> showWizard(Shell shell, Optional<Place> place) {

        PlaceWizard pw = new PlaceWizard(place);
        WizardDialog wd = new WizardDialog(shell, pw);
        wd.open();
        return pw.getPlace();
    }
}
