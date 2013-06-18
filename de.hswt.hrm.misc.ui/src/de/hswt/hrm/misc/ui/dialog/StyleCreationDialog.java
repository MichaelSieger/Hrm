package de.hswt.hrm.misc.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;

import org.eclipse.swt.widgets.Label;

public class StyleCreationDialog extends TitleAreaDialog {

    private Button okButton;

	private String name;
	
	private String fileName;

	private ArrayList<String> exisitingNames;

	private Text nameText;

	private Text fileNameText;
	
	private boolean nameFirst = true;
	
	private boolean fileNameFirst = true;
	
	
    /**
     * @wbp.parser.constructor
     */
    public StyleCreationDialog(Shell parentShell, List<String> existing) {
    	this(parentShell, existing, null, null);
    }

	// TODO change the constructor to take a model object instead of the two strings
    public StyleCreationDialog(Shell parentShell, List<String> existing, String name, String fileName) {
        super(parentShell);
        this.exisitingNames = new ArrayList<String>(existing);
        this.name = name;
        this.fileName = fileName;
        if (this.name != null) {
            nameFirst = false;
        }
        if (this.fileName != null) {
            fileNameFirst = false;
        }
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

		Section headerSection = new Section(composite, Section.TITLE_BAR);
		headerSection.setText("Style");
		headerSection.setExpanded(true);
		FormUtil.initSectionColors(headerSection);
		headerSection.setLayoutData(LayoutUtil.createFillData());
		headerSection.setLayout(new FillLayout());
		headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Composite c = new Composite(headerSection, SWT.NONE);
		c.setLayout(new GridLayout(2, false));
		c.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Label nameLabel = new Label(c, SWT.NONE);
		nameLabel.setText("Name");
		nameLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		
		nameText = new Text(c, SWT.BORDER);
		nameText.setLayoutData(LayoutUtil.createHorzCenteredFillData());
		if (name != null && !name.isEmpty()) {
			nameText.setText(name);
		}
		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (nameFirst) {
					nameFirst = false;
				}
				validate();
			}
		});

		Label fileNameLabel = new Label(c, SWT.NONE);
		fileNameLabel.setText("File name");
		fileNameLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		
		fileNameText = new Text(c, SWT.BORDER);
		fileNameText.setLayoutData(LayoutUtil.createHorzCenteredFillData());
		if (fileName != null && !fileName.isEmpty()) {
			fileNameText.setText(fileName);
		}
		fileNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (fileNameFirst) {
					fileNameFirst = false;
				}
				validate();
			}
		});

		headerSection.setClient(c);

		setMessage("Please provide a name and a style file name.");
		setTitle("Style configuration");

        return composite;
    }

    private void validate() {
    	// TODO look for existing names, but wait for the model
    	// to check if the object is equal when the name already exists
        okButton.setEnabled(false);

        boolean ok = false, firstCheck = true;
        
    	if (!nameFirst) {
        	if (nameText.getText().isEmpty()) {
        		setErrorMessage("Pleade provide a file name for the style.");
        		return;
        	} else {
        		name = nameText.getText();
        		ok = true;
        	}
        } else {
        	firstCheck = false;
        }

        if (!fileNameFirst) {
            if (fileNameText.getText().isEmpty()) {
        		setErrorMessage("Pleade provide a name for the style.");
        		return;
        	} else {
        		fileName = fileNameText.getText();
        		ok = true;
        	}
        } else {
        	firstCheck = false;
        }

    	setErrorMessage(null);
    	okButton.setEnabled(ok && firstCheck);
    }
    
    public String getName() {
    	return name;
    }
    
    public String getFileName() {
    	return fileName;
    }
    
    @Override
    public boolean close() {
    	return super.close();
    }
    
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
    	super.createButtonsForButtonBar(parent);
        okButton = getButton(IDialogConstants.OK_ID);
        validate();
    }
}