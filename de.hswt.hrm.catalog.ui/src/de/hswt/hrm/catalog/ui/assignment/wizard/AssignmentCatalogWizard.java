package de.hswt.hrm.catalog.ui.assignment.wizard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;

public class AssignmentCatalogWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(AssignmentCatalogWizard.class);

    @Inject
    private CatalogService catService;

    private AssignmentCatalogWizardPageOne first;
    private Optional<Catalog> catalog;

    public AssignmentCatalogWizard(IEclipseContext context, Optional<Catalog> cat) {
        this.catalog = cat;
        this.first = new AssignmentCatalogWizardPageOne("First Page", cat);
        ContextInjectionFactory.inject(first, context);

        if (cat.isPresent()) {
            setWindowTitle("Edit Catalog: " + cat.get().getName());
        }
        else {
            setWindowTitle("Create new Catalog");
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
        if (catalog.isPresent()) {
            return editExistingCatalog();
        }
        return insertNewCatalog();
    }

    private boolean insertNewCatalog() {

    Catalog p = new Catalog(first.getName());
        try {
            this.catalog = Optional.of(catService.insertCatalog(p));
        }
        catch (SaveException e2) {
            LOG.error("An eror occured", e2);
            return false;
        }

        return true;

    }

    private boolean editExistingCatalog() {
    	Catalog e = this.catalog.get();

        try {
            e = setValues(catalog);
            catService.updateCatalog(e);
            catalog = Optional.of(e);
        }
        catch (DatabaseException de) {
            LOG.error("An error occured: ", de);
            return false;
        }

        return true;
    }

    private Catalog setValues(Optional<Catalog> p) {

        Catalog cat = null;

        if (p.isPresent()) {
            cat = p.get();
            cat.setName(first.getName());
        }

        return cat;
    }

    public Optional<Catalog> getCatalog() {
        return catalog;
    }

}
