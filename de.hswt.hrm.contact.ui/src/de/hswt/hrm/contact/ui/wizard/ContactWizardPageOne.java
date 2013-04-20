package de.hswt.hrm.contact.ui.wizard;

import java.util.HashMap;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ContactWizardPageOne extends WizardPage {
    private Composite container;
    ContactWizardWindow window;

    protected ContactWizardPageOne(String pageName) {
        super(pageName);
    }

    @Override
    public void createControl(Composite parent) {
        window = new ContactWizardWindow(parent, 0);
        container = window;
        // setKeyListener();
        setControl(container);
        setPageComplete(false);
    }

    public HashMap getWidgets() {
        return window.getWidgets();
    }

    // public void setKeyListener() {
    // HashMap widgets = window.getWidgets();
    // for (Object object : widgets.values()) {
    //
    // ((Control) object).addKeyListener(new KeyListener() {
    //
    // @Override
    // public void keyPressed(KeyEvent e) {
    // }
    //
    // @Override
    // public void keyReleased(KeyEvent e) {
    // getWizard().getContainer().updateButtons();
    // }
    // });
    // }
    // }
}