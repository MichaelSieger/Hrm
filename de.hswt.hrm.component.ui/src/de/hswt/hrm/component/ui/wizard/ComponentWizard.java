package de.hswt.hrm.component.ui.wizard;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.CategoryService;
import de.hswt.hrm.component.service.ComponentService;

public class ComponentWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(ComponentWizard.class);

    @Inject
    private CategoryService catService;

    @Inject
    private IEclipseContext context;

    @Inject
    private ComponentService service;

    private ComponentWizardPageOne first;
    private ComponentWizardPageTwo second;
    private Optional<Component> component;

    public ComponentWizard(Optional<Component> component) {
        this.component = component;

        if (component.isPresent()) {
            setWindowTitle("Edit Component : " + component.get().getName());
        }
        else {
            setWindowTitle("Add new Component");
        }
    }

    public void addPages() {
        first = new ComponentWizardPageOne(component);
        ContextInjectionFactory.inject(first, context);
        second = new ComponentWizardPageTwo(component);
        ContextInjectionFactory.inject(second, context);
        addPage(first);
        addPage(second);
    }

    public boolean canFinish() {
        return first.isPageComplete() && second.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (component.isPresent()) {
            return editExistingComponent();
        }
        else {
            return insertNewComponent();
        }
    }

    private boolean editExistingComponent() {
        Component c = this.component.get();
        try {
            c = service.findById(c.getId());
            c = setValues(component);
            service.update(c);
            component = Optional.of(c);
        }
        catch (DatabaseException e) {
            LOG.error("An error occured: ", e);
        }
        List<String> attributes = first.getAttributes();
        List<String> atts = new LinkedList<String>();
        try {
            for (Attribute attFromDB : service.findAttributesByComponent(c)) {
                atts.add(attFromDB.getName());
            }
            for (String attFromList : attributes) {
                if (atts.contains(attFromList) == false) {
                    service.addAttribute(c, attFromList);
                }
            }
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }

        try {
            for (Attribute attFromDb : service.findAttributesByComponent(c)) {
                if (attributes.contains(attFromDb.getName()) == false) {
                    service.deleteAttribute(attFromDb);
                }
            }
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }

        // List<String> attributes = first.getAttributes();
        // Attribute existingAttributes = service.findAttributesByComponent(c).toArray()[0];
        //
        //
        // for(String attribute : first.getAttributes()){
        // for(Attribute att : service.findAttributesByComponent(c)){
        // if(att.getName() != attribute){
        // service.addAttribute(c, attribute);
        // }
        // }

        // }
        return true;
    }

    private boolean insertNewComponent() {
        Component c = setValues(Optional.<Component> absent());
        try {
            component = Optional.of(service.insert(c));
        }
        catch (SaveException e) {
            LOG.error("Could not save Element: " + component + " into Database", e);
        }
        for (String attribute : first.getAttributes()) {
            try {
                service.addAttribute(component.get(), attribute);
            }
            catch (SaveException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private Component setValues(Optional<Component> c) {
        Component component = null;
        if (c.isPresent()) {
            component = c.get();
            component.setName(first.getName());
            component.setQuantifier(first.getQuantifier());
            component.setCategory(first.getCategory());
            component.setBoolRating(first.getRating());
            if (second.getImageLR() != null) {
                component.setLeftRightImage(second.getImageLR());
            }
            if (second.getImageRL() != null) {
                component.setRightLeftImage(second.getImageRL());
            }
            if (second.getImageDU() != null) {
                component.setDownUpImage(second.getImageDU());
            }
            if (second.getImageUD() != null) {
                component.setUpDownImage(second.getImageUD());
            }

        }
        else {
            component = new Component(first.getName(), second.getImageLR(), second.getImageRL(),
                    second.getImageUD(), second.getImageDU(), first.getQuantifier(),
                    first.getRating());
            component.setCategory(first.getCategory());
        }

        return component;
    }

    public Optional<Component> getComponent() {
        return component;
    }

}
