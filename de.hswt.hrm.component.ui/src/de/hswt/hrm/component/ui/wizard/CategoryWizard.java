package de.hswt.hrm.component.ui.wizard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.service.CategoryService;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

public class CategoryWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryWizard.class);
    private static final I18n I18N = I18nFactory.getI18n(CategoryWizard.class);

    @Inject
    private CategoryService categoryService;

    @Inject
    private IEclipseContext context;

    private CategoryWizardPageOne first;
    private Optional<Category> category;

    public CategoryWizard(Optional<Category> category) {
        this.category = category;
        first = new CategoryWizardPageOne("Erste Seite", category);

        if (category.isPresent()) {
            setWindowTitle(I18N.tr("Edit Category"));
        }
        else {
            setWindowTitle(I18N.tr("Add Category"));
        }
    }

    public void addPages() {
        first = new CategoryWizardPageOne(I18N.tr("Category Wizard"), category);
        ContextInjectionFactory.inject(first, context);
        addPage(first);
    }

    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (category.isPresent()) {
            return editExistingCategory();
        }
        else {
            return insertNewCategory();
        }
    }

    private boolean editExistingCategory() {
        Category c = this.category.get();
        try {
            // Update category from DB
            c = categoryService.findById(c.getId());
            // Set values to fields from WizardPage
            c = setValues(category);
            // Update category in DB
            categoryService.update(c);
            category = Optional.of(c);
        }
        catch (DatabaseException e) {
            LOG.error("An error occured: ", e);
        }
        return true;
    }

    private boolean insertNewCategory() {
        Category c = setValues(Optional.<Category> absent());
        try {
            category = Optional.of(categoryService.insert(c));
        }
        catch (SaveException e) {
            LOG.error("Could not save Element: " + category + " into Database", e);
        }
        return true;
    }

    private Category setValues(Optional<Category> c) {
        Category category;
        if (c.isPresent()) {
            category = c.get();
            category.setName(first.getName());
            category.setDefaultQuantifier(first.getWeight());
            category.setDefaultBoolRating(first.isRating());
            category.setWidth(first.getWidth());
            category.setHeight(first.getHeight());
            category.setCatalog(first.getCatalog().get());
        }
        else {
            category = new Category(first.getName(), first.getWidth(), first.getHeight(),
                    first.getWeight(), first.isRating());
        }

        return category;
    }

    public Optional<Category> getCategory() {
        return category;
    }

}
