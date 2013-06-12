package de.hswt.hrm.photo.ui.wizard;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.photo.model.Photo;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.TableColumn;

// TODO read a given single image file or a directory (not recursive),
// see showFileSelectionDialog(...)
// support image types: jpg, png, bmp, gif
//
// user addFileToTable(...) to add files to the table
// table left column is the file name
// table right column must be editable to set the name of the photo
// if the file object is available, it is contained in the table item data (getData())
//
// the page is complete, when _one_ or more photos contained in the table

public class PhotoWizardPageOne extends WizardPage {
	private Text directoyText;
	private Table photosTable;
	private TableColumn nameClmn;
	private TableColumn fileClmn;

	/**
	 * Create the wizard.
	 */
	public PhotoWizardPageOne(Optional<List<Photo>> photos) {
		super("wizardPage");
        if (photos.isPresent()) {
    		setTitle("Edit photos");
    		setDescription("Select a directory to add photos. Rename or delete existing ones.");
        } else {
    		setTitle("Import photos");
    		setDescription("Select a directory to add photos.");
        } 
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
    	parent.setLayout(new PageContainerFillLayout());
    	
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackgroundMode(SWT.INHERIT_FORCE);
		container.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		
		Label lblDirectory = new Label(container, SWT.NONE);
		lblDirectory.setLayoutData(LayoutUtil.createLeftGridData());
		lblDirectory.setText("Directory");
		
		directoyText = new Text(container, SWT.BORDER);
		directoyText.setLayoutData(LayoutUtil.createHorzFillData());
		
		Button btnSelect = new Button(container, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showFileSelectionDialog();
			}
		});
		btnSelect.setText("Select ...");

		Label lblFiles = new Label(container, SWT.NONE);
		lblFiles.setLayoutData(LayoutUtil.createLeftGridData());
		lblFiles.setText("Files");

		photosTable = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		photosTable.setLayoutData(LayoutUtil.createFillData());
		photosTable.setHeaderVisible(true);
		photosTable.setLinesVisible(true);
		photosTable.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				int width = photosTable.getSize().x / 2;
				fileClmn.setWidth(width);
				nameClmn.setWidth(width);
			}
		});
		
		fileClmn = new TableColumn(photosTable, SWT.NONE);
		fileClmn.setText("File");
		fileClmn.setWidth(200);
		
		nameClmn = new TableColumn(photosTable, SWT.NONE);
		nameClmn.setText("Name");
		nameClmn.setWidth(200);

		Button btnDelete = new Button(container, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSelectedPhoto();
			}
		});
		btnDelete.setText("Delete");
		btnDelete.setLayoutData(LayoutUtil.createRightGridData());

		Label lblPhoto = new Label(container, SWT.NONE);
		lblPhoto.setLayoutData(LayoutUtil.createLeftGridData());
		lblPhoto.setText("Photo");

		Canvas canvas = new Canvas(container, SWT.NO_FOCUS);
		canvas.setVisible(true);
		canvas.setEnabled(false);
		canvas.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		canvas.setLayoutData(LayoutUtil.createFillData());
	}

	protected void addFileToTable(String file, String name) {
		addFileToTable(null, file, name);
	}

	protected void addFileToTable(File file, String fileName, String name) {
		TableItem item = new TableItem(photosTable, SWT.NONE);
		item.setText(0, fileName);
		item.setText(1, name);
		item.setData(file);
	}

	protected void addFileToTable(File file, String name) {
		addFileToTable(file.getName(), name);
	}

	protected void addFileToTable(File file) {
		addFileToTable(file.getName(), file.getName());
	}
	
	protected void deleteSelectedPhoto() {
		if (photosTable.getSelectionIndex() < 0) {
			return;
		}
		
		TableItem item = photosTable.getItem(photosTable.getSelectionIndex());
		if (!showDeleteConfirmation(item.getText(0))) {
			return;
		}
		
		//TODO remove selected photo
	}
	
	private boolean showDeleteConfirmation(String photo) {
		return MessageDialog.openConfirm(this.getShell(), "Delete",
				"Are you shure you want to delete the photo " + photo + "?");
	}
	
	private void showFileSelectionDialog() {
		FileDialog fileDialog = new FileDialog(null, SWT.MULTI);
//        fileDialog.setFilterPath(fileFilterPath);
//        
//        fileDialog.setFilterExtensions(new String[]{"*.rtf", "*.html", "*.*"});
//        fileDialog.setFilterNames(new String[]{ "Rich Text Format", "HTML Document", "Any"});
//        
//        String firstFile = fileDialog.open();
//
//        if(firstFile != null) {
//          fileFilterPath = fileDialog.getFilterPath();
//          String[] selectedFiles = fileDialog.getFileNames();
//          StringBuffer sb = new StringBuffer("Selected files under dir " + fileDialog.getFilterPath() +  ": \n");
//          for(int i=0; i<selectedFiles.length; i++) {
//            sb.append(selectedFiles[i] + "\n");
//          }
//          label.setText(sb.toString());
//        }
	}
}
