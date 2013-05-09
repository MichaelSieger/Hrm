package de.hswt.hrm.plant.ui.part;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.ui.filter.PlantFilter;

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

        // plants = PlantService.findAll();
        Plant p = new Plant(3, "Das ist ein Test");
        Place place = new Place("Somewhere", "11123", "SimCity", "Skywalkerway 5", "110a",
                "Todesstern", "\"Laser\"");
        p.setPlace(place);
        plants = new ArrayList<>();
        plants.add(p);
        viewer.setInput(plants);

        // catch (DatabaseException e) {
        // LOG.error("Unable to retrieve list of plants.", e);
        //
        // MessageDialog.openError(parent.getShell(), "Connection Error",
        // "Could not load Plants from Database.");
        // }

    }

    private void initializeTable(Composite parent, TableViewer viewer2) {
        List<ColumnDescription<Plant>> columns = PlantPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Plant> filler = new TableViewerController<>(viewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Plant> comparator = new ColumnComparator<>(columns);
        filler.enableSorting(comparator);

        // Add dataprovider that handles our collection
        viewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        viewer.addFilter(new PlantFilter());
    }

}
