package de.hswt.hrm.place.ui.wizard;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.place.model.Place;

public class PlaceWizardPageOne extends WizardPage {
    private static final Logger LOG = LoggerFactory.getLogger(PlaceWizardPageOne.class);
	private Composite container;
	private HashMap<String, Text> widgets;
	private Optional<Place> place;


    protected PlaceWizardPageOne(String pageName, Optional<Place> place) {
        super(pageName);
        this.place = place;
        setDescription(createDiscription());
    }

    private String createDiscription() {
        if (place.isPresent()) {
            return "Standort bearbeiten";
        }

        return "Neuen Standort hinzufügen";
    }
    
    public Place getPlace() {
        // We have to return a valid place here!
        return updatePlace(place);
    }

    @Override
    public void createControl(Composite parent) {
    	parent.setLayout(new PageContainerFillLayout());

    	URL url = PlaceWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/place/ui/xwt/PlaceWizardWindow" + IConstants.XWT_EXTENSION_SUFFIX);
    	try {
    		container = (Composite) XWTForms.load(parent, url);
		} 
    	catch (Exception e) {
    	    LOG.error("Could not load PlaceWizardPageOne XWT file.", e);
    	    return;
		}
    	
        if (place.isPresent()) {
            updateFields(place.get());
        }
    	
    	setKeyListener();
        setControl(container);
        setPageComplete(false);
    }
    
    private void updateFields(Place place) {
        HashMap<String, Text> widgets = getWidgets();
        widgets.get(Fields.NAME).setText(place.getPlaceName());
        widgets.get(Fields.STREET).setText(place.getStreet());
        widgets.get(Fields.STREET_NO).setText(place.getStreetNo());
        widgets.get(Fields.ZIP_CODE).setText(place.getPostCode());
        widgets.get(Fields.CITY).setText(place.getCity());
        widgets.get(Fields.LOCATION).setText(place.getLocation());
        widgets.get(Fields.AREA).setText(place.getArea());
    }
    
    private Place updatePlace(Optional<Place> place) {
        HashMap<String, Text> widgets = getWidgets();
        String name = widgets.get(Fields.NAME).getText();
        String street = widgets.get(Fields.STREET).getText();
        String streetNo = widgets.get(Fields.STREET_NO).getText();
        String zipCode = widgets.get(Fields.ZIP_CODE).getText();
        String city = widgets.get(Fields.CITY).getText();
        String location = widgets.get(Fields.LOCATION).getText();
        String area = widgets.get(Fields.AREA).getText();
        
        if (place.isPresent()) {
            Place p = place.get();
            p.setPlaceName(name);
            p.setStreet(street);
            p.setStreetNo(streetNo);
            p.setPostCode(zipCode);
            p.setCity(city);
            p.setLocation(location);
            p.setArea(area);
            return p;
        }

        Place p = new Place(name, zipCode, city, street, streetNo, location, area);
        return p;
    }

    public HashMap<String,Text> getWidgets() {
        // We cache the widgets for later calls
        if (widgets == null) {
            widgets = new HashMap<String, Text>();
            widgets.put(Fields.NAME, (Text) XWT.findElementByName(container, Fields.NAME));
            widgets.put(Fields.STREET, (Text) XWT.findElementByName(container, Fields.STREET));
            widgets.put(Fields.STREET_NO, (Text) XWT.findElementByName(container, Fields.STREET_NO));
            widgets.put(Fields.ZIP_CODE, (Text) XWT.findElementByName(container, Fields.ZIP_CODE));
            widgets.put(Fields.CITY, (Text) XWT.findElementByName(container, Fields.CITY));
            widgets.put(Fields.LOCATION, (Text) XWT.findElementByName(container, Fields.LOCATION));
            widgets.put(Fields.AREA, (Text) XWT.findElementByName(container, Fields.AREA));
        }
        
        return widgets; 
    }
    
    @Override
    public boolean isPageComplete(){
    	for(Text textField : getWidgets().values()){
    		if(textField.getText().length() == 0){
    			return false;    			
    		}    		
    	}
    	return true;
    }

    public void setKeyListener() {
        HashMap<String,Text> widgets = getWidgets();
        for (Text text : widgets.values()) {

            text.addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    getWizard().getContainer().updateButtons();
                }
            });
        }
    }
    
    private static final class Fields {
        public static final String NAME = "name";
        public static final String STREET = "street";
        public static final String STREET_NO = "streetNumber";
        public static final String ZIP_CODE = "zipCode";
        public static final String CITY = "city";
        public static final String LOCATION = "location";
        public static final String AREA = "area";
    }
}