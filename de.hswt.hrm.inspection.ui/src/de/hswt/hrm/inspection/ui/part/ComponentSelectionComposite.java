package de.hswt.hrm.inspection.ui.part;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.google.common.base.Preconditions;

import de.hswt.hrm.common.observer.Observable;
import de.hswt.hrm.common.observer.Observer;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.inspection.ui.grid.InspectionSchemeGrid;
import de.hswt.hrm.inspection.ui.grid.SchemeComponentSelectionListener;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class ComponentSelectionComposite extends Composite {

    @Inject
    private IEclipseContext context;

    private Class<? extends AbstractComponentRatingComposite> ratingCompositeClass;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private ListViewer componentsList;

    private AbstractComponentRatingComposite ratingComposite;

    private InspectionSchemeGrid schemeGrid;

    private Observable<SchemeComponent> selectedComponent = new Observable<>();

    /**
     * Do not use this constructor when instantiate this composite! It is only included to make the
     * WindowsBuilder working.
     * 
     * @param parent
     * @param style
     */
    private ComponentSelectionComposite(Composite parent, int style) {
        super(parent, SWT.NONE);
        createControls();
    }

    /**
     * Create the composite.
     * 
     * @param parent
     * @param ratingCompositeClass
     */
    public ComponentSelectionComposite(Composite parent,
            Class<? extends AbstractComponentRatingComposite> ratingCompositeClass) {
        super(parent, SWT.NONE);
        this.ratingCompositeClass = ratingCompositeClass;
        toolkit.dispose();
        toolkit = FormUtil.createToolkit();
    }

    @PostConstruct
    public void createControls() {
        FillLayout fl = new FillLayout();
        fl.marginHeight = 5;
        fl.marginWidth = 5;
        setLayout(fl);

        SashForm horizontalSash = new SashForm(this, SWT.NONE);
        horizontalSash.setBackgroundMode(SWT.INHERIT_DEFAULT);
        toolkit.adapt(horizontalSash);
        toolkit.paintBordersFor(horizontalSash);

        Section listSection = toolkit.createSection(horizontalSash, Section.TITLE_BAR);
        toolkit.paintBordersFor(listSection);
        listSection.setText("Components");
        FormUtil.initSectionColors(listSection);

        componentsList = new ListViewer(listSection, SWT.BORDER);
        listSection.setClient(componentsList.getList());
        toolkit.adapt(componentsList.getList(), true, true);
        componentsList.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection sel = (IStructuredSelection) componentsList.getSelection();
                Preconditions.checkArgument(sel.size() < 2);
                if (sel.isEmpty()) {
                    selectedComponent.set(null);
                }
                else {
                    selectedComponent.set((SchemeComponent) sel.getFirstElement());
                }
            }
        });

        SashForm verticalSash = new SashForm(horizontalSash, SWT.VERTICAL);
        verticalSash.setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.adapt(verticalSash);
        toolkit.paintBordersFor(verticalSash);

        ratingComposite = getRatingInstance(verticalSash);
        ContextInjectionFactory.inject(ratingComposite, context);

        Section schemeSection = toolkit.createSection(verticalSash, Section.TITLE_BAR);
        toolkit.paintBordersFor(schemeSection);
        schemeSection.setText("Scheme");
        schemeSection.setExpanded(true);
        FormUtil.initSectionColors(schemeSection);

        Composite schemeComposite = new Composite(schemeSection, SWT.NONE);
        schemeSection.setClient(schemeComposite);
        toolkit.adapt(schemeComposite);
        toolkit.paintBordersFor(schemeComposite);
        FillLayout fl_schemeComposite = new FillLayout(SWT.HORIZONTAL);
        fl_schemeComposite.marginHeight = 5;
        schemeComposite.setLayout(fl_schemeComposite);
        verticalSash.setWeights(new int[] { 3, 1 });
        horizontalSash.setWeights(new int[] { 1, 5 });

        ScrolledComposite schemeScroll = new ScrolledComposite(schemeComposite, SWT.BORDER
                | SWT.H_SCROLL | SWT.V_SCROLL);
        schemeScroll.setBackgroundMode(SWT.INHERIT_DEFAULT);
        schemeScroll.setExpandHorizontal(false);
        schemeScroll.setExpandVertical(false);
        schemeScroll.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
        schemeGrid = new InspectionSchemeGrid(schemeScroll, SWT.NONE);
        schemeScroll.setContent(schemeGrid.getControl());
        schemeGrid.setSelectionListener(new SchemeComponentSelectionListener() {

            @Override
            public void selected(SchemeComponent selected) {
                selectedComponent.set(selected);
            }
        });
        selectedComponent.addObserver(new Observer<SchemeComponent>() {

            @Override
            public void changed(SchemeComponent item) {
                schemeGrid.setSelected(item);
                setListSelection(item);
            }
        });

        initListViewer();
    }
    
    private void setListSelection(SchemeComponent c){
    	if(c == null){
    		componentsList.getList().deselectAll();
    	}else{
        	for(int i = 0; i < componentsList.getList().getItemCount(); i++){
        		if(componentsList.getElementAt(i).equals(c)){
        			componentsList.getList().select(i);
        			return;
        		}
        	}
        	throw new RuntimeException("Internal Error");
    	}
    }

    private void initListViewer() {
        componentsList.setContentProvider(ArrayContentProvider.getInstance());
        componentsList.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                SchemeComponent schemeComponent = (SchemeComponent) element;
                return schemeComponent.getComponent().getName();
            }
        });
        componentsList.setComparator(new ViewerComparator() {
            @Override
            public int compare(Viewer viewer, Object e1, Object e2) {

                SchemeComponent item1 = (SchemeComponent) e1;
                SchemeComponent item2 = (SchemeComponent) e2;
                return item1.getComponent().getName()
                        .compareToIgnoreCase(item2.getComponent().getName());

            }

        });

    }

    private AbstractComponentRatingComposite getRatingInstance(Control control) {
        AbstractComponentRatingComposite composite = null;
        if (ratingCompositeClass != null) {
            try {
                composite = ratingCompositeClass.getDeclaredConstructor(Composite.class)
                        .newInstance(control);
            }
            catch (NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return composite;
    }

    public ListViewer getComponentsList() {
        return componentsList;
    }

    public void setSchemeGridItems(Collection<SchemeGridItem> items) {
        schemeGrid.setItems(items);
    }
    
    public InspectionSchemeGrid getInspectionSchemeGrid(){
    	return schemeGrid;
    }

    @Override
    public void dispose() {
        toolkit.dispose();
        super.dispose();
    }

    public void addSchemeComponentSelectionObserver(Observer<SchemeComponent> o) {
        selectedComponent.addObserver(o);
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
