package de.hswt.hrm.component.ui.part;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.xwt.XwtHelper;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.component.ui.event.ComponentEventHandler;
import de.hswt.hrm.component.ui.filter.ComponentFilter;

public class ComponentPart {
    private final static Logger LOG = LoggerFactory.getLogger(ComponentPart.class);

    @Inject
    private ComponentService componentService;

    private TableViewer viewer;
    private Collection<Component> components;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {
        FillLayout layout = new FillLayout();
        layout.marginHeight = 2;
        layout.marginWidth = 2;
        parent.setLayout(layout);
        URL url = ComponentPart.class.getClassLoader().getResource(
                "de/hswt/hrm/component/ui/xwt/ComponentView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {

            final ComponentEventHandler eventHandler = ContextInjectionFactory.make(
                    de.hswt.hrm.component.ui.event.ComponentEventHandler.class, context);
            // Obtain root element of the XWT file
            final Composite comp = XwtHelper.loadFormWithEventHandler(parent, url, eventHandler);

            LOG.debug("XWT load successfully");

            // Obtain TableViwer to fill it with data
            viewer = (TableViewer) XWT.findElementByName(comp, "componentTable");
            initializeTable(parent, viewer);
            refreshTable(parent);

        }
        catch (Exception e) {
            LOG.error("Could not load XWT file from resource", e);
        }

        if (componentService == null) {
            LOG.error("ComponentService not injected to ComponentPart");
        }

    }

    private void refreshTable(Composite parent) {
        try {
            components = componentService.findAll();
            viewer.setInput(components);
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of components.", e);

            MessageDialog.openError(parent.getShell(), "Connection Error",
                    "Could not load components from Database.");
        }
    }

    private void initializeTable(Composite parent, TableViewer viewer) {
        List<ColumnDescription<Component>> columns = ComponentPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Component> filler = new TableViewerController<>(viewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Component> comperator = new ColumnComparator<>(columns);
        filler.enableSorting(comperator);

        // Add dataprovider that handles our collection
        viewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        viewer.addFilter(new ComponentFilter());
    }

}