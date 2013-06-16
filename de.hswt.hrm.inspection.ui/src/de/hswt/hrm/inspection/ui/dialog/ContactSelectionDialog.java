package de.hswt.hrm.inspection.ui.dialog;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.part.ContactComposite;

public class ContactSelectionDialog extends TitleAreaDialog {

    private ContactComposite contactComposite;
    private IEclipseContext context;
    private Contact contact;

    public ContactSelectionDialog(Shell parentShell, IEclipseContext context) {
        super(parentShell);
        this.context = context;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        composite.getShell().setText("Add Employee");

        composite.setLayout(new GridLayout(1, true));

        draw(composite); // Contents of Dialog

        return composite;

    }

    private void draw(Composite composite) {
        contactComposite = new ContactComposite(composite, SWT.NONE);
        contactComposite = new ContactComposite(composite, SWT.NONE);
        ContextInjectionFactory.inject(contactComposite, context);
        contactComposite.setAllowEditing(false);
        contactComposite.setLayoutData(LayoutUtil.createFillData());

    }

    @Override
    protected void okPressed() {
        System.out.println("OK pressed");
        Contact c = (Contact) contactComposite.getTableViewer().getElementAt(
                contactComposite.getTableViewer().getTable().getSelectionIndex());

        if (c == null) {
            setErrorMessage("no contact selected");
            return;
        }
        this.contact = c;

        super.okPressed();
    }

    public Contact getContact() {
        return contact;
    }
}