package de.hswt.hrm.scheme.ui.dialog;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.service.SchemeService;

public class SchemeCopySelectionDialog extends TitleAreaDialog {

	@Inject
	private SchemeService schemeService;
	
    private Scheme scheme;
    private Button okButton;
	private Combo schemeCombo;

	private Plant plant;
	
	
    public SchemeCopySelectionDialog(Shell parentShell, IEclipseContext context, Plant plant) {
        super(parentShell);
        this.plant = plant;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        draw(composite); // Contents of Dialog

        setMessage("Please select a scheme to copy.");
        setTitle("Scheme selection");

        return composite;

    }

    private void draw(Composite parent) {
		Section headerSection = new Section(parent, Section.TITLE_BAR);
		headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		headerSection.setText("Plants");
		headerSection.setExpanded(true);
		FormUtil.initSectionColors(headerSection);
		headerSection.setLayout(new FillLayout());

		Composite headerComposite = new Composite(headerSection, SWT.NONE);
		GridLayout gl = new GridLayout(2, false);
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		headerComposite.setLayout(gl);

        Label schemeLabel = new Label(headerComposite, SWT.NONE);
        schemeLabel.setText("Scheme");
        schemeLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
        
        schemeCombo = new Combo(headerComposite, SWT.READ_ONLY | SWT.DROP_DOWN);
        schemeCombo.setLayoutData(LayoutUtil.createHorzCenteredFillData(2));
        initSchemeCombo();
        schemeCombo.select(schemeCombo.getItemCount() - 1);
        schemeCombo.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		scheme = (Scheme)schemeCombo.getData(schemeCombo.getText());
        		validate();
        	}
        });
        
        headerSection.setClient(headerComposite);
    }

    private void initSchemeCombo() {
    	schemeCombo.removeAll();
    	
    	if (plant == null) {
    		return;
    	}

    	try {
			for (Scheme scheme : schemeService.findByPlant(plant)) {
				Date date = new Date();
				date.setTime(scheme.getTimestamp().get().getTime());
				String time = DateFormat.getInstance().format(date);
				schemeCombo.add(time);
				schemeCombo.setData(time, scheme);
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	private void validate() {
        if (scheme != null) {
            okButton.setEnabled(true);
        }
        else {
            okButton.setEnabled(false);
        }
    }

    public Scheme getScheme() {
        return scheme;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        okButton = getButton(IDialogConstants.OK_ID);
        okButton.setEnabled(false);
    }
}