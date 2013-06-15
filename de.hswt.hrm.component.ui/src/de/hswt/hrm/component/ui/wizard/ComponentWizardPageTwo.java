package de.hswt.hrm.component.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.scheme.service.ComponentConverter;

public class ComponentWizardPageTwo extends WizardPage {
    
    private static final Logger LOG = LoggerFactory.getLogger(ComponentWizardPageTwo.class);
    private static int MAX_QUANIFIER = 10;
    
    private Composite container;
    private Optional<Component> component;
    
    private Label imageLR;
    private Text pathLR;   
    private Button buttonLR;
    
    private Label imageRL;
    private Text pathRL;
    private Button buttonRL;
    
    private Label imageUD;
    private Text pathUD;
    private Button buttonUD;
    
    private Label imageDU;
    private Text pathDU;
    private Button buttonDU;
    
    public ComponentWizardPageTwo(String title, Optional<Component> component) {
        super(title);
        this.component = component;
        setDescription(createDescription());
    }
    
    private String createDescription() {
        if (component.isPresent()) {
            return "Change a category.";
        }
        return "Define a new category";
    }

    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        URL url = ComponentWizardPageTwo.class.getClassLoader().getResource(
                "de/hswt/hrm/component/ui/xwt/ComponentWizardPageTwo"+IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        } catch (Exception e) {
            LOG.error("An error occured: ",e);
        }
        imageLR = (Label) XWT.findElementByName(container,"imageLR");
        pathLR = (Text) XWT.findElementByName(container,"pathLR");   
        buttonLR = (Button) XWT.findElementByName(container,"buttonLR");
        
        imageRL = (Label) XWT.findElementByName(container,"imageRL");
        pathRL = (Text) XWT.findElementByName(container,"pathRL");
        buttonRL = (Button) XWT.findElementByName(container,"buttonRL");
        
        imageUD = (Label) XWT.findElementByName(container,"imageUD");
        pathUD = (Text) XWT.findElementByName(container,"pathUD");
        buttonUD = (Button) XWT.findElementByName(container,"buttonUD");
        
        imageDU = (Label) XWT.findElementByName(container,"imageDU");
        pathDU = (Text) XWT.findElementByName(container,"pathDU");
        buttonDU = (Button) XWT.findElementByName(container,"buttonDU");
        
        setUploadButtons(pathLR, imageLR, buttonLR);
        setUploadButtons(pathRL, imageRL, buttonRL);
        setUploadButtons(pathUD, imageUD, buttonUD);
        setUploadButtons(pathDU, imageDU, buttonDU);

        
        if (this.component.isPresent()) {
            updateFields(container);
        }
        
        setControl(container);
        checkPageComplete();
    }
    
    
    private void updateFields(Composite c) {

    }
    
    private void checkPageComplete() {

    }
    
    private void setUploadButtons(final Text path, final Label preview, Button upload){
    	upload.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				FileDialog fileDialog = new FileDialog(container.getShell(), SWT.OPEN);
		        fileDialog.setText("Open");
		        String[] filterExt = { "*.pdf"};
		        fileDialog.setFilterExtensions(filterExt);
		        String selected = fileDialog.open();				
				path.setText(selected);
				try {
					setPreviewImage(selected, preview);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    	
    	
    	
    }
    
    private void setPreviewImage(String path, Label preview) throws IOException{
    	File f = new File(path);
    	FileInputStream in;
    	byte[] data = null;
		try {
			in = new FileInputStream(f);
	    	data = new byte[in.available()];
	    	in.read(data);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	
		ByteBuffer buf = ByteBuffer.wrap(data);
		PDFFile pdffile;
		try {
			pdffile = new PDFFile(buf);
			PDFPage page = pdffile.getPage(0);
			
			Image imge = ComponentConverter.getSWTImage(preview.getDisplay(),ComponentConverter.renderImage(page, 100, 100));
	        preview.setImage(imge);
	    	preview.setSize( preview.computeSize( SWT.DEFAULT, SWT.DEFAULT ));

		} catch (IOException e) {
			e.printStackTrace();
		}	    	
    }
}
