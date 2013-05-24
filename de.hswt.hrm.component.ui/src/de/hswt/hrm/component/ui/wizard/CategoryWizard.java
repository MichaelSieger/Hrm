package de.hswt.hrm.component.ui.wizard;

import java.util.HashMap;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.service.CategoryService;

public class CategoryWizard extends Wizard {
    
    private static final Logger LOG = LoggerFactory.getLogger(CategoryWizard.class);
    private CategoryWizardPageOne first;
    private Optional<Category> category;
    
    public CategoryWizard(Optional<Category> category) {
        this.category = category;
        first = new CategoryWizardPageOne("Erste Seite", category);
        
        if (category.isPresent()) {
            setWindowTitle("Kategorie bearbeiten");
        } else {
            setWindowTitle("Neue Kategorie erstellen");
        }
    }
    
    public void addPages() {
        addPage(first);
    }
    
    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (category.isPresent()) {
            return editExistingCategory();
        } else {
            return insertNewCategory();
        }
    }
    
    private boolean editExistingCategory() {
        Category c = this.category.get();
        try {
            // Update category from DB
            c = CategoryService.findById(c.getId());
            // Set values to fields from WizardPage
            c = setValues(category);
            // Update category in DB
            CategoryService.update(c);
            category = Optional.of(c);
        } catch (DatabaseException e) {
            LOG.error("An error occured: ", e);
        }
        return true;
    }
    
    private boolean insertNewCategory() {
        Category c = setValues(Optional.<Category>absent());
        try {
            category = Optional.of(CategoryService.insert(c));
        } catch (SaveException e) {
            LOG.error("Could not save Element: "+category+" into Database", e);
        }
        return true;
    }
    
    private Category setValues(Optional<Category> c) {
        HashMap<String, Text> mandatoryWidgets = first.getMandatoryWidgets();
        String name = mandatoryWidgets.get("name").getText();
        int defaultQuantifier = Integer.parseInt(mandatoryWidgets.get("defaultQuantifier").getText());
        boolean defaultBoolRating = first.getBoolRatingCheckbox().getSelection();
        int width = Integer.parseInt(mandatoryWidgets.get("width").getText());
        int height = Integer.parseInt(mandatoryWidgets.get("height").getText());
        
        Category category;
        if (c.isPresent()) {
            category = c.get();
            category.setName(name);
            category.setDefaultQuantifier(defaultQuantifier);
            category.setDefaultBoolRating(defaultBoolRating);
            category.setWidth(width);
            category.setHeight(height);
        } else {
            category = new Category(name, width, height, defaultQuantifier,
                    defaultBoolRating);
        }
        
        return category;
    }

}
