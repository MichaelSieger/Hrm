package de.hswt.hrm.place.ui.wizard;

import java.net.URL;
import java.util.HashMap;

import org.apache.commons.validator.routines.RegexValidator;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.place.model.Place;

public class PlaceWizardPageOne extends WizardPage {
    private static final Logger LOG = LoggerFactory.getLogger(PlaceWizardPageOne.class);
    private static final I18n I18N = I18nFactory.getI18n(PlaceWizardPageOne.class);
    
	private Composite container;
	private HashMap<String, Text> widgets;
	private Optional<Place> place;
	
	private RegexValidator plzVal = new RegexValidator("[0-9]{5}");
    private RegexValidator cityVal = new RegexValidator("([A-ZÄÖÜ]{1}[a-zäöü]+[\\s]?[\\-]?)*");
    private RegexValidator streetNoVal = new RegexValidator("[0-9]+[a-z]?");


    protected PlaceWizardPageOne(String pageName, Optional<Place> place) {
        super(pageName);
        this.place = place;
        setDescription(createDiscription());
        setTitle(I18N.tr("Place Wizard"));
    }

    private String createDiscription() {
        if (place.isPresent()) {
            return I18N.tr("Edit the location.");
        }

        return I18N.tr("Add a new location.");
    }
    
    public Place getPlace() {
        // We have to return a valid place here!
        return updatePlace(place);
    }

    @Override
    public void createControl(Composite parent) {
    	parent.setLayout(new PageContainerFillLayout());

    	URL url = PlaceWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/place/ui/xwt/PlaceWizardPageOne" + IConstants.XWT_EXTENSION_SUFFIX);
    	try {
    		container = (Composite) XWTForms.load(parent, url);
		} 
    	catch (Exception e) {
    	    LOG.error("Could not load PlaceWizardPageOne XWT file.", e);
    	    return;
		}

    	translate(container);
    	
        if (place.isPresent()) {
            updateFields(place.get());
        }
    	FormUtil.initSectionColors((Section) XWT.findElementByName(container, "Mandatory"));
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
    }
    
    private Place updatePlace(Optional<Place> place) {
        HashMap<String, Text> widgets = getWidgets();
        String name = widgets.get(Fields.NAME).getText();
        String street = widgets.get(Fields.STREET).getText();
        String streetNo = widgets.get(Fields.STREET_NO).getText();
        String zipCode = widgets.get(Fields.ZIP_CODE).getText();
        String city = widgets.get(Fields.CITY).getText();
        
        if (place.isPresent()) {
            Place p = place.get();
            p.setPlaceName(name);
            p.setStreet(street);
            p.setStreetNo(streetNo);
            p.setPostCode(zipCode);
            p.setCity(city);
            return p;
        }

        Place p = new Place(name, zipCode, city, street, streetNo);
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
        }
        
        return widgets; 
    }
    
    @Override
    public boolean isPageComplete(){
        HashMap<String, Text> widgets = getWidgets();
        // Sorted arry
        Text[] widArray = { widgets.get(Fields.NAME), widgets.get(Fields.STREET),
                widgets.get(Fields.STREET_NO), widgets.get(Fields.ZIP_CODE),
                widgets.get(Fields.CITY) };
        boolean isValid;
        for (int i = 0; i < widArray.length; i++) {
            isValid = checkValidity(widArray[i]);
            if (widArray[i].getText().length() == 0) {
                setErrorMessage(I18N.tr("Field is mandatory")+ ": " + I18N.tr(XWT.getElementName((Object) widArray[i])));
                return false;
            }
            if (!isValid) {
                setErrorMessage(I18N.tr("Invalid input for field")+ " " + I18N.tr(XWT.getElementName((Object) widArray[i])));
                return false;
            }
        }
        setErrorMessage(null);
        return true;
    }
    
    private boolean checkValidity(Text textField) {
        String textFieldName = XWT.getElementName((Object) textField);
        boolean isInvalidStreetNo = textFieldName.equals(Fields.STREET_NO) && !streetNoVal.isValid(textField.getText());
        boolean isInvalidZipCode = textFieldName.equals(Fields.ZIP_CODE) && !plzVal.isValid(textField.getText());
        boolean isInvalidCity = textFieldName.equals(Fields.CITY) && !cityVal.isValid(textField.getText());
        
        if (isInvalidStreetNo || isInvalidZipCode || isInvalidCity) {
            return false;
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
    
    private void translate(Composite container) {
        // Labels
        setLabelText(container, "lblName", I18N.tr("Name")+":");
        setLabelText(container, "lblStreet", I18N.tr("Street / No.")+":");
        setLabelText(container, "lblZipCode", I18N.tr("Zipcode / City")+":");
    }
    
    private void setLabelText(Composite container, String labelName, String text) {
        Label l = (Label) XWT.findElementByName(container, labelName);
        if (l==null) {
            LOG.error("Label '"+labelName+"' not found.");
            return;
        }
        l.setText(text);
    }
        
    private static final class Fields {
        public static final String NAME = "name";
        public static final String STREET = "street";
        public static final String STREET_NO = "streetNumber";
        public static final String ZIP_CODE = "zipCode";
        public static final String CITY = "city";
    }
}