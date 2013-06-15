package de.hswt.hrm.component.ui.wizard;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;

public class ComponentWizardPageOne extends WizardPage {
    
    private static final Logger LOG = LoggerFactory.getLogger(ComponentWizardPageOne.class);
    private static int MAX_QUANIFIER = 10;
    
    private Composite container;
    private Optional<Component> component;
    
    private int[] validGridSizeValue = {1, 2, 4, 6};

    private Text nameText;
    
    private Text quantifier;
    
    private Text category;
    
    private Text rating;
    
    private String name;
    
    
    private boolean first = true;
    
    public ComponentWizardPageOne(String title, Optional<Component> component) {
        super(title);
        this.component = component;
        setDescription(createDescription());
    }
    
    private String createDescription() {
        if (component.isPresent()) {
            return "Change a category.";
        }
        return "Define a new category";
    }

    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        URL url = ComponentWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/component/ui/xwt/ComponentWizardPageOne"+IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        } catch (Exception e) {
            LOG.error("An error occured: ",e);
        }
        
        nameText = (Text) XWT.findElementByName(container, "name");
        quantifier = (Text) XWT.findElementByName(container, "quantifier");
        category = (Text) XWT.findElementByName(container, "category");
        rating = (Text) XWT.findElementByName(container, "rating");
        
        if (this.component.isPresent()) {
            updateFields(container);
        }
        
        nameText.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
            	checkPageComplete();
            }    
        });

        addSelectionListener(quantifier);
        addSelectionListener(category);
        addSelectionListener(rating);

        setControl(container);
        checkPageComplete();
    }
    
    
    private void updateFields(Composite c) {
        Component comp = component.get();
        nameText.setText(comp.getName());
    }
    
    private void checkPageComplete() {
    	if (first) {
    		first = false;
    		setPageComplete(false);
    		return;
    	}

    	// FIXME check if category is not empty or it already exists
    	
//    	if (weightCombo.getSelectionIndex() > -1) {
//    		weight = Integer.parseInt(weightCombo.getItem(weightCombo.getSelectionIndex()));
//    	} else {
//    		setErrorMessage("Select a standard weight.");
//    		return;
//    	}
    }
    
    private void addSelectionListener(Control control) {
    	control.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
            	checkPageComplete();
			}
		});
    }

	public String getName() {
		return nameText.getText();
	}

	public String getQuantifier() {
		return quantifier.getText();
	}

	public String getCategory() {
		return category.getText();
	}

	public String getRating() {
		return rating.getText();
	}


}
