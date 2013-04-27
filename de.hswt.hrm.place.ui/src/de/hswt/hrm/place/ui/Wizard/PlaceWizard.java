package de.hswt.hrm.place.ui.Wizard;

import java.util.HashMap;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.service.PlaceService;

public class PlaceWizard extends Wizard {
    PlaceWizardPageOne one;

    public PlaceWizard() {
        setWindowTitle("Neuen Standort hinzufügen");

    }

    @Override
    public void addPages() {
        one = new PlaceWizardPageOne("First Page");
        addPage(one);
    }

    @Override
    public boolean canFinish() {
    	if(one.isPageComplete()){
    		return true;
    	}else{
    		return false;
    	}
    }

    @Override
    public boolean performFinish() {
    	HashMap <String,Text> mandatoryWidgets = one.getMandatoryWidgets();
        String name = mandatoryWidgets.get("name").getText();
        String street = mandatoryWidgets.get("street").getText();        
        String streetNumber = mandatoryWidgets.get("streetNumber").getText();
        String zipCode = mandatoryWidgets.get("zipCode").getText();
        String city = mandatoryWidgets.get("city").getText();
        String location = mandatoryWidgets.get("location").getText();
        String area = mandatoryWidgets.get("area").getText();
        
        Place place = new Place(name,zipCode,city,street,streetNumber,location,area);
       
		try {
			PlaceService.insert(place);
		} catch (SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }

}
