package de.hswt.hrm.contact.ui.event;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.contact.ui.wizard.ContactWizard;

public class ContactEventHandler {

    public void onFocusOut(Event event) {

        Text text = (Text) event.widget;
        text.setText("Suche");
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

}
