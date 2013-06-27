package de.hswt.hrm.component.ui.event;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.CategoryService;
import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.component.ui.filter.ComponentFilter;
import de.hswt.hrm.component.ui.part.ComponentCompositeUtil;
import de.hswt.hrm.scheme.service.ComponentConverter;

public class ComponentEventHandler {
    private final static Logger LOG = LoggerFactory.getLogger(ComponentEventHandler.class);
    private static final String DEFAULT_SEARCH_STRING = "Search";
    private static final String EMPTY = "";

    private final IEclipseContext context;
    private final ComponentService componentService;
    @Inject
    private CategoryService catService;
    
    
    @Inject
    public ComponentEventHandler(IEclipseContext context, ComponentService componentService) {

        if (context == null) {
            LOG.error("EclipseContext was not injected to ComponentEventHandler.");
        }

        if (componentService == null) {
            LOG.error("Component was not injected to ComponentEventHandler.");
        }

        this.context = context;
        this.componentService = componentService;

    }

    /**
     * This event is called whenever the Search Text Field is leaved. If the the field is blank, the
     * value of the Field {@link #DEFAULT_SEARCH_STRING} is inserted.
     * 
     * @param event
     *            Event which occured in SWT
     */
    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "componentTable");
        tf.refresh();

    }

    /**
     * This Event is called whenever the add buttion is pressed.
     * 
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void buttonSelected(Event event) {

        Button b = (Button) event.widget;
        
        Optional<Component> newComponent = ComponentCompositeUtil.showWizard(componentService,catService, context,
                event.display.getActiveShell(), Optional.<Component> absent());

        TableViewer tv = (TableViewer) XWT.findElementByName(b, "componentTable");

        Collection<Component> components = (Collection<Component>) tv.getInput();
        if (newComponent.isPresent()) {
            components.add(newComponent.get());
            tv.refresh();
        }
    }

    /**
     * This event is called whenever a Text is entered into the Search textField
     * 
     * @param event
     */
    public void onKeyUp(Event event) {

        Text searchText = (Text) event.widget;
        TableViewer tv = (TableViewer) XWT.findElementByName(searchText, "componentTable");
        ComponentFilter filter = (ComponentFilter) tv.getFilters()[0];
        filter.setSearchString(searchText.getText());
        tv.refresh();

    }

    /**
     * This event is called whenever the Search text field is entered
     * 
     * @param event
     */
    public void enterText(Event event) {
        Text text = (Text) event.widget;
        if (text.getText().equals(DEFAULT_SEARCH_STRING)) {
            text.setText(EMPTY);
        }

    }
    
    public void onClick(Event event){        
    	byte[] bytes;
    	Label previewImage = (Label) XWT.findElementByName(event.widget, "imagePreview");
    	TableViewer viewer = (TableViewer) XWT.findElementByName(event.widget, "componentTable");
    	
        IStructuredSelection sel =  (IStructuredSelection) viewer.getSelection();
        Component selectedComponent = (Component) sel.getFirstElement();
        
        bytes = selectedComponent.getRightLeftImage();
        if(bytes == null){
        	bytes = selectedComponent.getDownUpImage();
        }
         if(bytes == null){
        	bytes = selectedComponent.getUpDownImage();
         }
         if(bytes == null){
        	bytes = selectedComponent.getLeftRightImage();
         }
        

		
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		PDFFile pdffile;
		try {
			pdffile = new PDFFile(buf);
			PDFPage page = pdffile.getPage(0);
			
			Image imge = ComponentConverter.getSWTImage(previewImage.getDisplay(),ComponentConverter.renderImage(page, 100, 100));
	        previewImage.setImage(imge);
	    	previewImage.setSize( previewImage.computeSize( SWT.DEFAULT, SWT.DEFAULT ));
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   	
    	
    }

    /**
     * This method is called whenever a doubleClick onto the Tableviewer occurs. It obtains the
     * contact from the selected column of the TableViewer. The Contact is passed to the
     * ContactWizard. When the Wizard has finished, the contact will be updated in the Database
     * 
     * @param event
     *            Event which occured within SWT
     */
    public void tableEntrySelected(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "componentTable");

        // obtain the contact in the column where the doubleClick happend
        Component selectedComponent = (Component) tv.getElementAt(tv.getTable().getSelectionIndex());
        if (selectedComponent == null) {
            return;
        }
//        try {
//            componentService.refresh(selectedComponent);
            Optional<Component> updatedComponent = ComponentCompositeUtil.showWizard(componentService, catService,context,
                    event.display.getActiveShell(), Optional.of(selectedComponent));
//
//            if (updatedComponent.isPresent()) {
//                tv.refresh();
//            }
//        }
//        catch (DatabaseException e) {
//            LOG.error("Could not retrieve the Component from database.", e);
//
//            // TODO: übersetzen
//            MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
//                    "Could not update selected Component from database.");
//        }
    }
}
