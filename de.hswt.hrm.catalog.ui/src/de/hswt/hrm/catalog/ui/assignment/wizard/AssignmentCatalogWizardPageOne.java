package de.hswt.hrm.catalog.ui.assignment.wizard;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

import javax.inject.Inject;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;

public class AssignmentCatalogWizardPageOne extends WizardPage {

    private Optional<Catalog> catalog;
    private Composite container;
    private Text nameText;

    private Collection<Catalog> catalogs;
    private boolean first = true;

    @Inject
    private CatalogService catService;

    private static final Logger LOG = LoggerFactory.getLogger(AssignmentCatalogWizardPageOne.class);

    public AssignmentCatalogWizardPageOne(String title, Optional<Catalog> catalog) {
        super(title);
        this.catalog = catalog;
        setDescription(createDescription());
        setTitle("Catalog Wizard");
    }

    private String createDescription() {
        if (catalog.isPresent()) {
            return "Change a Catalog";
        }
        return "Add a new Catalog";
    }

    @Override
    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        URL url = AssignmentCatalogWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/catalog/ui/assignment/wizard/AssignmentCatalogWizardWindow"
                        + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            LOG.error("An error occured: ", e);
        }

        nameText = (Text) XWT.findElementByName(container, "name");
        

        if (this.catalog.isPresent()) {
            updateFields();
        }
        try {
            this.catalogs = catService.findAllCatalog();
        }
        catch (DatabaseException e) {
            LOG.error("An error occured", e);
        }
       
        
        FormUtil.initSectionColors((Section) XWT.findElementByName(container, "Mandatory"));
        addKeyListener(nameText);
        setControl(container);
       // setPageComplete(false);
        checkPageComplete();

    }

    private void addKeyListener(Text text) {
        text.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                checkPageComplete();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }

    private void updateFields() {
        Catalog e = catalog.get();
        nameText.setText(e.getName());
    }

    private void checkPageComplete() {

//        if (first) {
//            first = false;
//            setPageComplete(false);
//            return;
//        }

        setErrorMessage(null);

        if (nameText.getText().isEmpty()) {
            setErrorMessage("Name must not be empty...");
        }

        else if (isAlreadyPresent(nameText.getText())) {
            setErrorMessage("A Catalog with name " + nameText.getText() + " is already present");
        }

    }

    private boolean isAlreadyPresent(String text) {

        boolean present = false;

        if (text == null | text.isEmpty()) {
            present = true;
        }

        if(!catalog.isPresent()){
	        for (Catalog e : this.catalogs) {
	            if (e.getName().equals(text)) {
	                present = true;
	            }
	        }
        }else{
        	for (Catalog e : this.catalogs) {
	            if (e.getName().equals(text) && e.getId() != catalog.get().getId()) {
	                present = true;
	            }
	        }     	
        	
        }

        return present;

    }

    @Override
    public void setErrorMessage(String newMessage) {
        if (newMessage == null || newMessage.isEmpty()) {
            setPageComplete(true);
        }
        else {
            setPageComplete(false);
        }
        super.setErrorMessage(newMessage);
    }

    public String getName() {
        return nameText.getText();
    }
}
