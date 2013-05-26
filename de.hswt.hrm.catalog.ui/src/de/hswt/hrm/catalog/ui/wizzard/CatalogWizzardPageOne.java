package de.hswt.hrm.catalog.ui.wizzard;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
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

        URL url = CatalogWizzardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/catalog/ui/xwt/CatalogWizardWindow" + IConstants.XWT_EXTENSION_SUFFIX);

        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            LOG.error("Coult not load CatalogWizardPageOne XWT file.", e);
            return;
        }

    }

    private String createDiscription() {
        if (catalogItem.isPresent()) {
            return "Soll/Ist/Maßnahme bearbeiten";
        }

        return "Neue Soll/Ist/Maßnahme anlegen";
    }

    public ICatalogItem getItem() {
        // TODO Auto-generated method stub
        return null;
    }
}
