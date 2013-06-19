package de.hswt.hrm.photo.ui.wizard;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JFileChooser;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.common.EMFPlugin.InternalEclipsePlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.photo.model.Photo;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.FormToolkit;

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
	
	private Composite container;

	private Label lblFiles;

	private Canvas canvas;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Label lblNewLabel;

	/**
	 * Create the wizard.
	 */
	public PhotoWizardPageOne(Optional<List<Photo>> photos) {
		super("Photo import");
		setTitle("Photo import");
        if (photos != null && photos.isPresent()) {
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
    	
		container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new FillLayout());
		
		Section headerSection = new Section(container, Section.TITLE_BAR);
		headerSection.setText("Photo import");
		headerSection.setExpanded(true);
		headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		headerSection.setBackgroundMode(SWT.INHERIT_DEFAULT);
		FormUtil.initSectionColors(headerSection);
    	
		Composite composite = new Composite(headerSection, SWT.NULL);
		headerSection.setClient(composite);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(3, false));
		
		Label lblDirectory = new Label(composite, SWT.NONE);
		lblDirectory.setLayoutData(LayoutUtil.createLeftGridData());
		lblDirectory.setText("Directory");
		
		directoyText = new Text(composite, SWT.BORDER);
		directoyText.setLayoutData(LayoutUtil.createHorzFillData());
		
		Button btnSelect = new Button(composite, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showFileSelectionDialog();
			}
		});
	
		btnSelect.setText("Select ...");

		lblFiles = new Label(composite, SWT.NONE);
		lblFiles.setLayoutData(LayoutUtil.createLeftGridData());
		lblFiles.setText("Files");

		photosTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = LayoutUtil.createFillData();
		gd.heightHint = 200;
		photosTable.setLayoutData(gd);
		photosTable.setHeaderVisible(true);
		photosTable.setLinesVisible(true);
		photosTable.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPreview();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});

		
		fileClmn = new TableColumn(photosTable, SWT.NONE);
		fileClmn.setText("File");
		fileClmn.setWidth(250);
		
		nameClmn = new TableColumn(photosTable, SWT.NONE);
		nameClmn.setText("Name");
		nameClmn.setWidth(250);

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSelectedPhoto();
				setPreview();
			}
		});
		btnDelete.setText("Delete");
		btnDelete.setLayoutData(LayoutUtil.createRightGridData());

		Label lblPhoto = new Label(composite, SWT.NONE);
		lblPhoto.setLayoutData(LayoutUtil.createLeftGridData());
		lblPhoto.setText("Photo");

		canvas = new Canvas(composite, SWT.NO_FOCUS);
		canvas.setVisible(true);
		canvas.setEnabled(false);
		canvas.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		canvas.setLayoutData(LayoutUtil.createFillData());
		canvas.setLayout(new GridLayout(1, false));
		
		lblNewLabel = formToolkit.createLabel(canvas, "", SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_lblNewLabel.heightHint = 300;
		gd_lblNewLabel.widthHint = 300;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setBounds(0, 0, 55, 15);
		new Label(composite, SWT.NONE);
	}

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, 
		image.getBounds().width, image.getBounds().height, 
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
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
		photosTable.remove(photosTable.getSelectionIndex());
		photosTable.redraw();
		
		
	}
	
	private boolean showDeleteConfirmation(String photo) {
		return MessageDialog.openConfirm(this.getShell(), "Delete",
				"Are you shure you want to delete the photo " + photo + "?");
	}
	
	private void showFileSelectionDialog() {
		FileDialog fileDialog = new FileDialog(container.getShell(), SWT.MULTI);
//
//        fileDialog.setFilterPath(fileFilterPath);
//        
        fileDialog.setFilterExtensions(new String[]{"*.jpg", "*.bmp",  "*.gif", "*.png"});
//        
        String firstFile = fileDialog.open();

        if(firstFile != null) {
//          fileFilterPath = fileDialog.getFilterPath();
          String[] selectedFiles = fileDialog.getFileNames();
//          StringBuffer sb = new StringBuffer("Selected files under dir " + fileDialog.getFilterPath() +  ": \n");
//          for(int i=0; i<selectedFiles.length; i++) {
//            sb.append(selectedFiles[i] + "\n");
//          }
          List<String> paths = new ArrayList<String>();
          for( int i = 0; i < selectedFiles.length ; i++){
        	  paths.add(i, fileDialog.getFilterPath() + File.separatorChar + selectedFiles[i]);        	  
          }
          
          for(String path : paths){
        	  File a = new File(path);
        	  addFileToTable(a,a.getName(),a.getName());          
          }
        }
	}
	
	private void setPreview(){
		if(photosTable.getSelectionIndex() != -1){
			TableItem [] tableItems = photosTable.getItems();
			File file = (File)tableItems[photosTable.getSelectionIndex()].getData();
			Image image = new Image(canvas.getDisplay(),file.getAbsolutePath());
			Image sacledImage = resize(image, lblNewLabel.getBounds().width, lblNewLabel.getBounds().height);
			lblNewLabel.setBackgroundImage(sacledImage);
		} else {
			lblNewLabel.setBackgroundImage(null);
		}
		
	}
}
