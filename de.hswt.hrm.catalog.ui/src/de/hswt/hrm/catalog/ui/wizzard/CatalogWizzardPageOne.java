package de.hswt.hrm.catalog.ui.wizzard;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogWizzardPageOne extends WizardPage {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogWizzardPageOne.class);

    private Composite container;
    private Optional<ICatalogItem> item;

    public CatalogWizzardPageOne(String pageName, Optional<ICatalogItem> item) {
        super(pageName);
        this.item = item;
        setDescription(createDiscription());
    }

    public void createControl(Composite parent) {

        URL url = CatalogWizzardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/catalog/ui/xwt/CatalogWizard" + IConstants.XWT_EXTENSION_SUFFIX);

        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            LOG.error("Coult not load Wizzard XWT file.", e);
            return;
        }
        
        setControl(container);
        setPageComplete(false);

    }

    private String createDiscription() {
        if (item.isPresent()) {
            return "Soll/Ist/Maßnahme bearbeiten";
        }

        return "Neue Soll/Ist/Maßnahme anlegen";
    }

    public ICatalogItem getItem() {

        return updateItem(item);
    }

    private ICatalogItem updateItem(Optional<ICatalogItem> item2) {
        return item.get();
    }
}
