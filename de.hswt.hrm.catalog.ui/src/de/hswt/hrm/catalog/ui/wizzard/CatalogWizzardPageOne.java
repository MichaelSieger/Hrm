package de.hswt.hrm.catalog.ui.wizzard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogWizzardPageOne extends WizardPage {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogWizzardPageOne.class);

    private Composite container;
    private Optional<ICatalogItem> catalogItem;

    public CatalogWizzardPageOne(String pageName, Optional<ICatalogItem> catalogItem) {
        super(pageName);
        this.catalogItem = catalogItem;
        setDescription(createDiscription());
    }

    public void createControl(Composite parent) {

    }

    private String createDiscription() {
        if (catalogItem.isPresent()) {
            return "Soll/Ist/Maßnahme bearbeiten";
        }

        return "Neue Soll/Ist/Maßnahme anlegen";
    }
}
