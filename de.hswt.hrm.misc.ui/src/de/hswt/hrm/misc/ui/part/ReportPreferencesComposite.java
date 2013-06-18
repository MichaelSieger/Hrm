package de.hswt.hrm.misc.ui.part;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.misc.ui.dialog.StyleCreationDialog;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;

import org.eclipse.swt.widgets.TableColumn;

public class ReportPreferencesComposite extends Composite {

    @Inject
    private IEclipseContext context;

	private Text directoyText;
	
	private Table table;

	/**
	 * Do not use this constructor when instantiate this composite! It is only
	 * included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
	private ReportPreferencesComposite(Composite parent, int style) {
		super(parent, style);
		createControls();
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 */
	public ReportPreferencesComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	@PostConstruct
	private void createControls() {
		setLayout(new GridLayout(3, false));
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label directoryLabel = new Label(this, SWT.NONE);
		directoryLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		directoryLabel.setText("Standard directory");
		
		directoyText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		directoyText.setToolTipText("A local directory where all the reports are created.");
		directoyText.setLayoutData(LayoutUtil.createHorzCenteredFillData());
		
		Button directorySelectionButton = new Button(this, SWT.NONE);
		directorySelectionButton.setToolTipText("Browse for a local directory where all the reports are created.");
		directorySelectionButton.setText("Brwose ...");
		directorySelectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setStandardReportDirectory();
			}
		});
		
		Label stylesLabel = new Label(this, SWT.NONE);
		stylesLabel.setText("Styles");
		stylesLabel.setLayoutData(LayoutUtil.createLeftGridData());
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = LayoutUtil.createHorzFillData(2);
		gd.heightHint = 250;
		table.setLayoutData(gd);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setWidth(200);
		nameColumn.setText("Name");
		
		TableColumn fileNameColumn = new TableColumn(table, SWT.NONE);
		fileNameColumn.setWidth(200);
		fileNameColumn.setText("File name");

		// TODO fill with styles;
//		for (...) {
//			addTableRow(name, fileName);
//		}
	}

	private void setStandardReportDirectory() {
		DirectoryDialog dialog = new DirectoryDialog(this.getShell());
		dialog.setText("Report standard directory selection");
		dialog.setMessage("Select a directory as root of all created reports.");
        String dir = dialog.open();
        if (dir != null) {
        	directoyText.setText(dir);
        }
        // TODO write it to the local properties file
        // ask Ben
	}

	public void addStyle() {
        StyleCreationDialog dialog = new StyleCreationDialog(getShell(), new ArrayList<String>());
        dialog.create();
        if (dialog.open() == Window.OK) {
        	addTableRow(dialog.getName(), dialog.getFileName());
        }
	}

	public void editStyle() {
		if (table.getSelectionIndex() < 0) {
			return;
		}
		TableItem item = table.getItem(table.getSelectionIndex());
		// init with selected row
        StyleCreationDialog dialog = new StyleCreationDialog(getShell(), 
        		new ArrayList<String>(), item.getText(0), item.getText(0));
        dialog.create();
        if (dialog.open() == Window.OK) {
        	item.setText(0, dialog.getName());
        	item.setText(1, dialog.getFileName());
        	table.update();
        }
	}
	
	private void addTableRow(String name, String fileName) {
		if (name == null || name.isEmpty() || fileName == null || fileName.isEmpty()) {
			return;
		}
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[]{name, fileName});
	}
	
	@Override
	protected void checkSubclass() {
	}
}
