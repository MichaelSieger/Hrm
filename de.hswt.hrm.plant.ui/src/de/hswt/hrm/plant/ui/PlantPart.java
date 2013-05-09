package de.hswt.hrm.plant.ui;

import java.net.URL;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.service.PlantService;

public class PlantPart {

    private TableViewer viewer;
    private Collection<Plant> plants;

    private final static Logger LOG = LoggerFactory.getLogger(PlantPart.class);

    @PostConstruct
    public void postConstruct(Composite parent) {

        URL url = PlantPart.class.getClassLoader().getResource(
                "de/hswt/hrm/plant/ui/xwt/PlantView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {
            // Obtain root element of the XWT file
            final Composite comp = (Composite) XWT.load(parent, url);
            // Obtain TableViwer to fill it with data
            viewer = (TableViewer) XWT.findElementByName(comp, "plantTable");
            initializeTable(parent, viewer);
            refreshTable(parent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable(Composite parent) {

        try {
            plants = PlantService.findAll();
            viewer.setInput(plants);
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of plants.", e);

            MessageDialog.openError(parent.getShell(), "Connection Error",
                    "Could not load Plants from Database.");
        }

    }

    private void initializeTable(Composite parent, TableViewer viewer2) {

    }

}
