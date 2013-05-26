package de.hswt.hrm.catalog.ui.event;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;

public class CatalogMatchingEventHandler {

    public void onAvailableTargetMouseDoubleClick(Event event) {
        
        List l = (List)event.widget;
        System.out.println(l.getItem(l.getSelectionIndex()));
        
    }
}
