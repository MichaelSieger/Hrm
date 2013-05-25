package de.hswt.hrm.catalog.ui.wizzard;

import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogWizard extends Wizard {
    
    private static final Logger LOG = LoggerFactory.getLogger(CatalogWizard.class);
    private CatalogWizzardPageOne first;
    private Optional<ICatalogItem> item;

    @Override
    public boolean performFinish() {
        // TODO Auto-generated method stub
        return false;
    }

}
