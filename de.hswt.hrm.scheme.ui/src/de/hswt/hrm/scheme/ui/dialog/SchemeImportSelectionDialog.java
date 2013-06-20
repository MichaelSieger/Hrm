package de.hswt.hrm.scheme.ui.dialog;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import de.hswt.hrm.plant.ui.shared.PlantComposite;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.service.SchemeService;

public class SchemeImportSelectionDialog extends TitleAreaDialog {

	@Inject
	private SchemeService schemeService;
	
    private PlantComposite plantComposite;
    private IEclipseContext context;
    private Scheme scheme;
    private Button okButton;
	private Combo schemeCombo;

    public SchemeImportSelectionDialog(Shell parentShell, IEclipseContext context) {
        super(parentShell);
        this.context = context;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        draw(composite); // Contents of Dialog

        setMessage("Please select a plant and a scheme to import.");
        setTitle("SCheme selection");

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

		Composite headerComposite = new Composite(headerSection, SWT.NONE);
		GridLayout gl = new GridLayout(2, false);
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		headerComposite.setLayout(gl);
		headerComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		plantComposite = new PlantComposite(headerComposite);
        ContextInjectionFactory.inject(plantComposite, context);
        plantComposite.setAllowEditing(false);
        plantComposite.setLayoutData(LayoutUtil.createFillData(2));
        plantComposite.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
            	initSchemeCombo();
            	setSelectedScheme();
            	validate();
            }
        });

        Label schemeLabel = new Label(headerComposite, SWT.NONE);
        schemeLabel.setText("Scheme");
        schemeLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
        
        schemeCombo = new Combo(headerComposite, SWT.READ_ONLY | SWT.DROP_DOWN);
        schemeCombo.setLayoutData(LayoutUtil.createHorzCenteredFillData(0));
        schemeCombo.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
            	setSelectedScheme();
        		validate();
        	}
        });
        
        headerSection.setClient(headerComposite);
    }

    private void initSchemeCombo() {
    	schemeCombo.removeAll();
    	
    	Plant plant = plantComposite.getSelectedPlant();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	schemeCombo.select(schemeCombo.getItemCount() - 1);
	}

	private void setSelectedScheme() {
    	scheme = (Scheme)schemeCombo.getData(schemeCombo.getText());
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