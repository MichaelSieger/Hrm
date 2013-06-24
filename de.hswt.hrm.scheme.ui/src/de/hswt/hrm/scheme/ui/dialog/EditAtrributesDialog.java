package de.hswt.hrm.scheme.ui.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.component.model.Component;

public class EditAtrributesDialog extends TitleAreaDialog {

    private Component component;

    public EditAtrributesDialog(Shell parentShell, Component component) {
        super(parentShell);
        this.component = component;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        // Contents of Dialog
        draw(composite);

        setMessage("Please select a scheme to copy.");
        setTitle("Edit Attributes for Component " + component.getName());

        return composite;

    }

    private void draw(Composite parent) {
        Section headerSection = new Section(parent, Section.TITLE_BAR);
        headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        headerSection.setText("Scheme selection");
        headerSection.setExpanded(true);
        headerSection.setLayoutData(LayoutUtil.createFillData());
        FormUtil.initSectionColors(headerSection);
        headerSection.setLayout(new FillLayout());
        headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        Composite headerComposite = new Composite(headerSection, SWT.NONE);
        GridLayout gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        headerComposite.setLayout(gl);
        headerComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
    }

}
