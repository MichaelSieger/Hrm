package de.hswt.hrm.component.ui.wizard;


import javax.inject.Inject;

import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.CategoryService;
import de.hswt.hrm.component.service.ComponentService;

public class ComponentWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(ComponentWizard.class);
    
    @Inject
    private ComponentService service;
    
    private ComponentWizardPageOne first;
    private ComponentWizardPageTwo second;
    private Optional<Component> component;
    
    public ComponentWizard(Optional<Component> component) {
        this.component = component;
        first = new ComponentWizardPageOne("Erste Seite", component);
        second = new ComponentWizardPageTwo("Second Page", component);
        
        if (component.isPresent()) {
            setWindowTitle("Edit Component : "+component.get().getName());
        } else {
            setWindowTitle("Add new Component");
        }
    }
    
    public void addPages() {
        addPage(first);
        addPage(second);
    }
    
    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (component.isPresent()) {
            return editExistingComponent();
        } else {
            return insertNewComponent();
        }
    }
    
    private boolean editExistingComponent() {
//        Component c = this.component.get();
//        try {
//            c = service.findById(c.getId());
//            c = setValues(component);
//            component.update(c);
//            component = Optional.of(c);
//        } catch (DatabaseException e) {
//            LOG.error("An error occured: ", e);
//        }
        return true;
    }
    
    private boolean insertNewComponent() {
        Component c = setValues(Optional.<Component>absent());
//        try {
//           component = Optional.of(service.insert(c));
//        } catch (SaveException e) {
//            LOG.error("Could not save Element: "+component+" into Database", e);
//        }
        return true;
    }
    
    private Component setValues(Optional<Component> c) {
        Component component = null;
        if (c.isPresent()) {
            component = c.get();
            component.setName(first.getName());
            component.setQuantifier(Integer.parseInt(first.getQuantifier()));
        } 
//            component = new Category(first.getName(), 
//            		first.getWidth(), 
//            		first.getHeight(), 
//            		first.getWeight(),
//                    first.isRating());
//        }
        
        return component;
    }
    
    public Optional<Component> getComponent() {
        return component;
    }

}
