package de.hswt.hrm.component.ui.wizard;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.component.model.Category;

public class CategoryWizardPageOne extends WizardPage {
    
    private static final Logger LOG = LoggerFactory.getLogger(CategoryWizardPageOne.class);
    private static int MAX_SIZE = 6;
    private static int MAX_QUANIFIER = 10;
    
    private Composite container;
    private Optional<Category> category;
    
    public CategoryWizardPageOne(String title, Optional<Category> category) {
        super(title);
        this.category = category;
        setDescription(createDescription());
    }
    
    private String createDescription() {
        if (category.isPresent()) {
            return "Kategorie bearbeiten";
        }
        return "Neue Kategorie erstellen";
    }

    public void createControl(Composite parent) {
        parent.setLayout(new FillLayout());
        URL url = CategoryWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/component/ui/xwt/CategoryWizardWindow"+IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        } catch (Exception e) {
            LOG.error("An error occured: ",e);
        }
        loadDropdownItems();
        if (this.category.isPresent()) {
            updateFields(container);
        }
        setControl(container);
        setKeyListener();
        setPageComplete(false);
    }
    
    private void loadDropdownItems() {
        Combo defQuant = (Combo) XWT.findElementByName(container, "defaultQuantifier");
        Combo width = (Combo) XWT.findElementByName(container, "width");
        Combo height = (Combo) XWT.findElementByName(container, "height");
        for (int i=1; i<=MAX_SIZE; i++) {
            width.add(String.valueOf(i));
            height.add(String.valueOf(i));
        }
        for (int i=1; i<=MAX_QUANIFIER; i++) {
            defQuant.add(String.valueOf(i));
        }
    }
    
    private void updateFields(Composite c) {
        Category cat = category.get();
        Text t = (Text) XWT.findElementByName(c, "name");
        t.setText(cat.getName());
        Combo combo = (Combo) XWT.findElementByName(c, "defaultQuantifier");
        combo.select(combo.indexOf(String.valueOf(cat.getDefaultQuantifier())));
        Button cb = (Button) XWT.findElementByName(c, "defaultBoolRating");
        cb.setSelection(cat.getDefaultBoolRating());
        combo = (Combo) XWT.findElementByName(c, "width");
        combo.select(combo.indexOf(String.valueOf(cat.getWidth())));
        combo = (Combo) XWT.findElementByName(c, "height");
        combo.select(combo.indexOf(String.valueOf(cat.getHeight())));
    }
    
    public HashMap<String, Text> getMandatoryWidgets() {
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("name", (Text) XWT.findElementByName(container, "name"));
        return widgets;
    }
    
    public HashMap<String, Combo> getMandatoryCombos() {
        HashMap<String, Combo> combos = new HashMap<String, Combo>();
        combos.put("defaultQuantifier", (Combo) XWT.findElementByName(container, "defaultQuantifier"));
        combos.put("width", (Combo) XWT.findElementByName(container, "width"));
        combos.put("height", (Combo) XWT.findElementByName(container, "height"));
        return combos;
    }
    
    public Button getBoolRatingCheckbox() {
        return (Button) XWT.findElementByName(container, "defaultBoolRating");
    }
    
    public boolean isPageComplete() {
        for (Text textField : getMandatoryWidgets().values()) {
            if (textField.getText().length() == 0) {
                return false;
            }
        }
        for (Combo combo : getMandatoryCombos().values()) {
            if (combo.getSelectionIndex() == -1) {
                return false;
            }
        }
        return true;
    }
    
    public void setKeyListener() {
        for (Text text : getMandatoryWidgets().values()) {
            text.addKeyListener(new KeyListener() {
                public void keyPressed(KeyEvent e) {
  
                }

                public void keyReleased(KeyEvent e) {
                    getWizard().getContainer().updateButtons();
                    
                }    
            });
        }
        for (Combo combo : getMandatoryCombos().values()) {
            combo.addSelectionListener(new SelectionListener() {
                
                @Override
                public void widgetSelected(SelectionEvent e) {
                    getWizard().getContainer().updateButtons();
                    
                }
                
                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                    // TODO Auto-generated method stub
                    
                }
            });
        }
    }

}
