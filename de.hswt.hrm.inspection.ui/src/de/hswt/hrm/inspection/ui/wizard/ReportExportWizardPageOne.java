package de.hswt.hrm.inspection.ui.wizard;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;

public class ReportExportWizardPageOne extends WizardPage {

    @Inject
    private IShellProvider shellProvider;

    private FormToolkit formToolkit;
    
	private Label directoryLabel;
	private Text directoryText;
	private Button browseButton;

	private Path rootPath;

	private Button keepCheckButton;

	private boolean keepFiles = true;
	
    /**
     * Create the wizard.
     */
    public ReportExportWizardPageOne() {
        super("wizardPage");
        setTitle("Create report");
        setDescription("Please provide the basic report informations.");
    }

    /**
     * Create contents of the wizard.
     * 
     * @param parent
     */
    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        
        formToolkit = FormUtil.createToolkit();

        Section exportSection = formToolkit.createSection(parent, Section.TITLE_BAR);
        formToolkit.paintBordersFor(exportSection);
        exportSection.setText("Report export");
        exportSection.setLayoutData(LayoutUtil.createHorzFillData());
        FormUtil.initSectionColors(exportSection);

        Composite exportComposite = new Composite(exportSection, SWT.NONE);
        exportComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
        formToolkit.adapt(exportComposite);
        formToolkit.paintBordersFor(exportComposite);
        GridLayout gl = new GridLayout(5, false);
        gl.marginWidth = 1;
        exportComposite.setLayout(gl);
        exportSection.setClient(exportComposite);

        directoryLabel = new Label(exportComposite, SWT.NONE);
        directoryLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
        directoryLabel.setText("Standard directory");

        directoryText = new Text(exportComposite, SWT.READ_ONLY);
        directoryText.setToolTipText("A local directory where all the reports are created.");
        directoryText.setLayoutData(LayoutUtil.createHorzCenteredFillData());

        browseButton = new Button(exportComposite, SWT.NONE);
        browseButton
                .setToolTipText("Browse for a local directory where all the reports are created.");
        browseButton.setText("Browse ...");
        browseButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setReportDirectory();
            }
        });
        
		keepCheckButton = new Button(exportComposite, SWT.CHECK);
		keepCheckButton.setLayoutData(LayoutUtil.createLeftCenteredGridData(
				3));
		formToolkit.adapt(keepCheckButton, true, true);
		keepCheckButton.setText("keep all files");
		keepCheckButton.setSelection(keepFiles);
		keepCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				keepFiles = keepCheckButton.getSelection();
			}
		});
		
        Config config = Config.getInstance();
        String dir = config.getProperty(Config.Keys.REPORT_STYLE_FOLDER,
        		System.getProperty("user.dir"));
        if(dir != null){        
        	directoryText.setText(dir);
            rootPath = Paths.get(dir);
        }
        
        setControl(exportSection);
        
        checkPage();
    }

    protected void setReportDirectory() {
        Config config = Config.getInstance();
    	
    	DirectoryDialog dialog = new DirectoryDialog(shellProvider.getShell());
        dialog.setText("Report directory selection");
        dialog.setMessage("Select a directory as root for the report export.");
        dialog.setFilterPath(config.getProperty(Config.Keys.REPORT_STYLE_FOLDER, ""));
        String dir = dialog.open();
        if (dir != null) {
            Path temp = Paths.get(dir);
            if (Files.isDirectory(temp)) {
                directoryText.setText(dir);
                rootPath = temp;
            }
        }
	}

    private void checkPage() {
        setPageComplete(false);

        boolean ok = false;

        if (!Files.isDirectory(rootPath)) {
        	setErrorMessage("Select a valid directory.");
        } else {
        	ok = true;
        }
        
        if (ok) {
        	setErrorMessage(null);
        	setPageComplete(true);
        }
    }

    @Override
    public void dispose() {
        formToolkit.dispose();
        super.dispose();
    }

    protected Path getRootPath() {
    	return rootPath;
    }
    
    protected boolean isKeepFiles() {
    	return keepFiles ;
    }
}
