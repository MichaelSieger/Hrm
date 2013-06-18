package de.hswt.hrm.inspection.ui.dialog;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.part.ContactComposite;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.ui.part.PlantComposite;

public class PlantSelectionDialog extends TitleAreaDialog {

    private PlantComposite plantComposite;
    private IEclipseContext context;
    private Plant plant;
    private Button okButton;

    public PlantSelectionDialog(Shell parentShell, IEclipseContext context) {
        super(parentShell);
        this.context = context;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        draw(composite); // Contents of Dialog

        setMessage("Please select a plant.");
        setTitle("Plant selection");

        return composite;

    }

    private void draw(Composite parent) {
		Section headerSection = new Section(parent, Section.TITLE_BAR);
		headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		headerSection.setText("Plants");
		headerSection.setExpanded(true);
		FormUtil.initSectionColors(headerSection);
		headerSection.setLayout(new FillLayout());

        plantComposite = new PlantComposite(headerSection);
        ContextInjectionFactory.inject(plantComposite, context);
        plantComposite.setAllowEditing(false);
        plantComposite.setBackgroundMode(SWT.INHERIT_NONE);
        plantComposite.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                validate();
            }
        });
        headerSection.setClient(plantComposite);
    }

    private void validate() {
        plant = plantComposite.getSelectedPlant();
        if (plant != null) {
            okButton.setEnabled(true);
        }
        else {
            okButton.setEnabled(false);
        }
    }

    public Plant getPlant() {
        return plant;
    }

    @Override
    public boolean close() {
        if (plantComposite != null) {
            plantComposite.dispose();
        }
        return super.close();
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        okButton = getButton(IDialogConstants.OK_ID);
        okButton.setEnabled(false);
    }
}