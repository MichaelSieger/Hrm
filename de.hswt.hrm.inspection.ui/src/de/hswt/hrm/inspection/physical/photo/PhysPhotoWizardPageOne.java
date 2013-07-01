package de.hswt.hrm.inspection.physical.photo;

import java.awt.Graphics;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JFileChooser;

import org.eclipse.e4.core.contexts.IEclipseContext;
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
import de.hswt.hrm.photo.service.PhotoService;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
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

public class PhysPhotoWizardPageOne extends WizardPage {
	
	private Table photosTable;
	
	private TableColumn nameClmn;
	
	private TableColumn fileClmn;
	
	private Composite container;

	private Label lblFiles;
	
	private List<Photo> photos;
	
	private List<Photo> selectedPhotos;
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Text newEditor;

	/**
	 * Create the wizard.
	 */
	public PhysPhotoWizardPageOne(List<Photo> photos,List<Photo> selectedPhotos) {		
		super("Photo import");
		this.photos = photos;
		this.selectedPhotos = selectedPhotos;
		setTitle("Photo import");
		setDescription("Select a directory to add photos. Rename or delete existing ones.");

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
		composite.setLayout(new GridLayout(2, false));

		lblFiles = new Label(composite, SWT.NONE);
		lblFiles.setLayoutData(LayoutUtil.createLeftGridData());
		lblFiles.setText("Files");

		photosTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		GridData gd = LayoutUtil.createFillData();
		gd.heightHint = 200;
		photosTable.setLayoutData(gd);
		photosTable.setHeaderVisible(true);
		photosTable.setLinesVisible(true);
		photosTable.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPageComplete();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});

		
		fileClmn = new TableColumn(photosTable, SWT.NONE);
		fileClmn.setText("Name");
		fileClmn.setWidth(250);
		
		nameClmn = new TableColumn(photosTable, SWT.NONE);
		nameClmn.setText("File");
		nameClmn.setWidth(250);
		
		final TableEditor editor = new TableEditor(photosTable);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		// editing the second column
	
		
		if(photos != null && photos.size() != 0){
			for(Photo photo : photos){
				addFileToTable(photo,photo.getLabel(),photo.getName());				
			}		
		}
		
		checkPageComplete();
		
	}

	
	protected void addFileToTable(String file, String name) {
		addFileToTable(null, file, name);
	}

	protected void addFileToTable(Photo photo, String fileName, String name) {
		TableItem item = new TableItem(photosTable, SWT.NONE);
		item.setText(0, fileName);
		item.setText(1, name);
		item.setData(photo);
	}

	protected void addFileToTable(Photo photo, String name) {
		addFileToTable(photo.getName(), name);
	}

	protected void addFileToTable(Photo photo) {
		addFileToTable(photo.getName(), photo.getName());
	}
	

	
	private void checkPageComplete(){
		if(photosTable.getItems().length == 0){
			setPageComplete(false);	
			return;
		}
//		getSelectedPhotos();
		setPageComplete(true);	
	}
	
	
	public Table getListItems(){
		return photosTable;		
	}

	public TableItem[] getTableItems() {
		return photosTable.getItems();
	}
	
	public void getSelectedPhotos(){
		for(TableItem item : photosTable.getSelection()){			
			if(selectedPhotos.contains((Photo)item.getData()) == false){
				selectedPhotos.add((Photo)item.getData());
			}
		}		
	}
	public java.util.List<Photo> getSelectedPhotoList(){
		return selectedPhotos;		
	}
	
}
