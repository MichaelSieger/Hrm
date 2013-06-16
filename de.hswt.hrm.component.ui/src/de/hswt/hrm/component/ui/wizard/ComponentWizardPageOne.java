package de.hswt.hrm.component.ui.wizard;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.CategoryService;

public class ComponentWizardPageOne extends WizardPage {
    
    private static final Logger LOG = LoggerFactory.getLogger(ComponentWizardPageOne.class);
    private static int MAX_QUANIFIER = 10;
    
    private Composite container;
    private Optional<Component> component;
    
    private int[] validGridSizeValue = {1, 2, 4, 6};

    private Text nameText;
    
    private Text quantifier;
    
    private ComboViewer category;
    
    private Button ratingNo;
    
    private Button ratingYes;
    
    private String name;
    
    Boolean rating = null;
    
    private CategoryService catService;
    
    
    private boolean first = true;
    
    public ComponentWizardPageOne(String title, Optional<Component> component, CategoryService cat) {
        super(title);
        this.component = component;
        this.catService = cat;
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
        URL url = ComponentWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/component/ui/xwt/ComponentWizardPageOne"+IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        } catch (Exception e) {
            LOG.error("An error occured: ",e);
        }
        
        nameText = (Text) XWT.findElementByName(container, "name");
        quantifier = (Text) XWT.findElementByName(container, "quantifier");
        category = (ComboViewer) XWT.findElementByName(container, "category");
        ratingNo = (Button) XWT.findElementByName(container, "ratingNo");
        ratingYes = (Button) XWT.findElementByName(container, "ratingYes");
        
        if (this.component.isPresent()) {
            updateFields(container);
        }
        
        nameText.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
            	checkPageComplete();
            }    
        });

        quantifier.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
            	checkPageComplete();
            }    
        });
        addSelectionListenerForRadio(ratingNo);
        addSelectionListenerForRadio(ratingYes);

        setControl(container);
        checkPageComplete();
        
        initializeCombo(category);
    }
    
    
    private void initializeCombo(ComboViewer category) {
    	category.setContentProvider(ArrayContentProvider.getInstance());
    	category.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                return ((Category)element).getName();
            }
        });
    	
    	try {
			category.setInput(catService.findAll());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    	category.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                checkPageComplete();
            }
        });
		
		
	}

	private void addSelectionListenerForRadio(Button button) {
    	button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
            	if(event.widget.equals(ratingNo)){
            		rating = false;        
            	}else{
            		rating = true;            		
            	}		
				checkPageComplete();
			}});
	}

	private void updateFields(Composite c) {
        Component comp = component.get();
        nameText.setText(comp.getName());
    }
    
    private void checkPageComplete() {
    	if(nameText.getText().isEmpty()){
    		setPageComplete(false);
    		return;    		
    	}
    	if(quantifier.getText().isEmpty()){
       		setPageComplete(false);
    		return;    	    		
    	}
    	if(category.getSelection().isEmpty()){
    		setPageComplete(false);
    		return;
    	}

    	setPageComplete(true);
    }
    
    private void addSelectionListener(Control control) {
    	control.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
            	checkPageComplete();
			}
		});
    }

	public String getName() {
		return nameText.getText();
	}

	public String getQuantifier() {
		return quantifier.getText();
	}


	public Boolean getRating(){
		return rating;
	}

}
