package de.hswt.hrm.catalog.ui.part;

import java.net.URL;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.component.model.Category;

public class CatalogMatchingPart {

    @Inject
    CatalogService catalogService;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {

        URL url = CatalogPart.class.getClassLoader().getResource(
                "de/hswt/hrm/catalog/ui/xwt/CatalogMatching" + IConstants.XWT_EXTENSION_SUFFIX);

        try {
            final Composite composite = (Composite) XWT.load(parent, url);

            final List targets = ((List) XWT.findElementByName(composite, "availableTarget"));
            final List currents = (List) XWT.findElementByName(composite, "availableCurrent");
            final List activities = (List) XWT.findElementByName(composite, "availableActivity");
            final List categories = (List) XWT.findElementByName(composite, "components");
            Collection<ICatalogItem> items = catalogService.findAllCatalogItem();
            initializeAvailableItems(items, targets, currents, activities);

            Category c = new Category("Luftfilter", 2, 2, 2, true);
            Category c2 = new Category("Nacherhitzer", 2, 2, 2, true);

            categories.add(c.getName());
            categories.add(c2.getName());

            currents.setEnabled(false);
            activities.setEnabled(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeAvailableItems(Collection<ICatalogItem> items, List targets,
            List currents, List activities) {

        for (ICatalogItem i : items) {
            if (i instanceof Activity) {
                activities.add(i.getName());
            }
            else if (i instanceof Target) {
                targets.add(i.getName());
            }
            else {
                currents.add(i.getName());
            }
        }

    }
}
