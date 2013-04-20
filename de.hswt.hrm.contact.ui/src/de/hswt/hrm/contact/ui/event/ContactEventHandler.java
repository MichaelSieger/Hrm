package de.hswt.hrm.contact.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.contact.ui.filter.ContactFilter;
import de.hswt.hrm.contact.ui.wizard.ContactWizard;

public class ContactEventHandler {

    public void onFocusOut(Event event) {

        Text text = (Text) event.widget;
        text.setText("Suche (Nachname,Vorname,Stadt)");
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "contactTable");
        tf.refresh();

    }

    public void onFocusIn(Event event) {
        Text text = (Text) event.widget;
        text.setText("");

    }

    public void onSelection(Event event) {
        Button b = (Button) event.widget;
        WizardDialog dialog = new WizardDialog(b.getShell(), new ContactWizard());
        dialog.open();
    }

    public void onKeyUp(Event event) {

        Text searchText = (Text) event.widget;
        TableViewer tf = (TableViewer) XWT.findElementByName(searchText, "contactTable");
        ContactFilter f = (ContactFilter) tf.getFilters()[0];
        f.setSearchString(searchText.getText());
        tf.refresh();

    }
}
