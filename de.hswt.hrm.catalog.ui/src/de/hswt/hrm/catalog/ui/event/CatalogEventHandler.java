package de.hswt.hrm.catalog.ui.event;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.catalog.ui.filter.CatalogSelectionFilter;
import de.hswt.hrm.catalog.ui.filter.CatalogTextFilter;
import de.hswt.hrm.catalog.ui.part.CatalogPartUtil;
import de.hswt.hrm.common.database.exception.DatabaseException;

public class CatalogEventHandler {

    private static final String DEFAULT_SEARCH_STRING = "Search";
    private static final String EMPTY = "";
    private static final Logger LOG = LoggerFactory.getLogger(CatalogEventHandler.class);
    private final IEclipseContext context;
    private final CatalogService catalogService;

    @Inject
    public CatalogEventHandler(IEclipseContext context, CatalogService catalogService) {
        if (context == null) {
            LOG.error("EclipseContext was not injected to CatalogEventHandler.");
        }

        if (catalogService == null) {
            LOG.error("CatalogService was not injected to CatalogEventHandler.");
        }

        this.context = context;
        this.catalogService = catalogService;
    }

    public void onSelection(Event event) {

        Button b = (Button) event.widget;

        TableViewer tf = (TableViewer) XWT.findElementByName(b, "catalogTable");
        CatalogSelectionFilter f = (CatalogSelectionFilter) tf.getFilters()[0];

        enableFilter(b.getText(), f);
        tf.refresh();
        return;

    }

    private void enableFilter(String s, CatalogSelectionFilter f) {
        disableAll(f);
        switch (s) {
        case "all":
            f.setActivitySelected(true);
            break;
        case "ist":
            f.setCurrentSelected(true);
            break;
        case "soll":
            f.setTargetSelected(true);
            break;
        case "maßnahmen":
            f.setActivitySelected(true);
            break;

        }

    }

    private void disableAll(CatalogSelectionFilter f) {
        f.setCurrentSelected(false);
        f.setAllSelected(false);
        f.setTargetSelected(false);
        f.setActivitySelected(false);

    }

    /**
     * This event is called whenever the Search text field is entered
     * 
     * @param event
     */
    public void enterText(Event event) {
        Text text = (Text) event.widget;
        if (text.getText().equals(DEFAULT_SEARCH_STRING)) {
            text.setText(EMPTY);
        }
    }

    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "catalogTable");
        tf.refresh();

    }

    public void onKeyUp(Event event) {
        Text searchText = (Text) event.widget;
        TableViewer tf = (TableViewer) XWT.findElementByName(searchText, "catalogTable");
        CatalogTextFilter ctf = (CatalogTextFilter) tf.getFilters()[1];
        ctf.setSearchString(searchText.getText());
        tf.refresh();
    }

    public void onMouseDoubleClick(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "catalogTable");

        // obtain the place in the column where the doubleClick happend
        ICatalogItem selectedItem = (ICatalogItem) tv.getElementAt(tv.getTable()
                .getSelectionIndex());
        if (selectedItem == null) {
            return;
        }

        // Refresh the selected place with values from the database
        try {

            if (selectedItem instanceof Activity) {
                catalogService.refresh((Activity) selectedItem);
            }
            else if (selectedItem instanceof Current) {
                catalogService.refresh((Current) selectedItem);
            }
            else {
                catalogService.refresh((Target) selectedItem);
            }

            Optional<ICatalogItem> updatedItem = CatalogPartUtil.showWizard(context,
                    event.display.getActiveShell(), Optional.of(selectedItem));

            if (updatedItem.isPresent()) {
                tv.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the CatalogItem from database.", e);

            // TODO: übersetzen
            MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
                    "Could not update selected item from database.");
        }

    }

    @SuppressWarnings("unchecked")
    public void onSelection_1(Event event) {

        Button b = (Button) event.widget;
        Optional<ICatalogItem> newItem = CatalogPartUtil.showWizard(context,
                event.display.getActiveShell(), Optional.<ICatalogItem> absent());

        if (newItem.isPresent()) {
            TableViewer tv = (TableViewer) XWT.findElementByName(b, "catalogTable");
            Collection<ICatalogItem> items = (Collection<ICatalogItem>) tv.getInput();
            items.add(newItem.get());
            tv.refresh();
        }
    }

}
