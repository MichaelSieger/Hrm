package de.hswt.hrm.inspection.ui.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ContactSelectionDialog extends TitleAreaDialog {

    public ContactSelectionDialog(Shell parentShell) {
        super(parentShell);
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
        Group empgroup = new Group(composite, SWT.SHADOW_IN);
        empgroup.setText("Employee Details");
        empgroup.setLayout(new GridLayout(3, true));
    }
}