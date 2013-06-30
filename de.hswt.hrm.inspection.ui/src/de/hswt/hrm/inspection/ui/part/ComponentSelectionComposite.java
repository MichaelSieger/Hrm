package de.hswt.hrm.inspection.ui.part;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Collections2;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.ui.grid.InspectionSchemeGrid;
import de.hswt.hrm.inspection.ui.grid.SchemeComponentSelectionListener;
import de.hswt.hrm.inspection.ui.listener.ComponentSelectionChangedListener;
import de.hswt.hrm.inspection.ui.listener.InspectionObserver;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.service.ComponentConverter;
import de.hswt.hrm.scheme.service.SchemeService;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class ComponentSelectionComposite extends Composite implements InspectionObserver {

	private static final String RENDER_ERROR = "Error on rendering image";

    private final static Logger LOG = LoggerFactory.getLogger(ComponentSelectionComposite.class);
    
    private static final I18n I18N = I18nFactory.getI18n(ComponentSelectionComposite.class);
	
    @Inject
    private IEclipseContext context;

    @Inject
    private SchemeService schemeService;
    
    private Class<? extends AbstractComponentRatingComposite> ratingCompositeClass;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private ListViewer componentsList;

    private AbstractComponentRatingComposite ratingComposite;

    private InspectionSchemeGrid schemeGrid;

    private SchemeComponent selectedComponent;

    private boolean updateFromOutside = false;
    
    private java.util.List<ComponentSelectionChangedListener> componentSelectionListeners;
    
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
        componentSelectionListeners = new ArrayList<>();
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
        listSection.setText(I18N.tr("Components"));
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
                    setSelectComponent(null);
                }
                else {
                    setSelectComponent((SchemeComponent) sel.getFirstElement());
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
        schemeSection.setText(I18N.tr("Scheme"));
        schemeSection.setExpanded(true);
        FormUtil.initSectionColors(schemeSection);

        Composite schemeComposite = new Composite(schemeSection, SWT.NONE);
        schemeSection.setClient(schemeComposite);
        toolkit.adapt(schemeComposite);
        toolkit.paintBordersFor(schemeComposite);
        FillLayout fl_schemeComposite = new FillLayout(SWT.HORIZONTAL);
        fl_schemeComposite.marginHeight = 5;
        schemeComposite.setLayout(fl_schemeComposite);
        verticalSash.setWeights(new int[] { 2, 1 });
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
            public void selected(SchemeComponent component) {
                setSelectComponent(component);
            }
        });

        initListViewer();
    }
    
    private void setSelectComponent(SchemeComponent component) {
    	selectedComponent = component;
        schemeGrid.setSelected(component);
        setListSelection(component);
        if (!updateFromOutside) {
        	updateFromOutside = false;
            fireComponentSelectionChanged(component);
        }
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
        	componentsList.getList().deselectAll();
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
                e.printStackTrace();
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return composite;
    }

    private void plantChangedInternal(Plant plant) {
        Scheme scheme;
        try {
            scheme = schemeService.findCurrentSchemeByPlant(plant);
        }
        catch (ElementNotFoundException e) {
            // TODO what to do if there is no scheme?
            throw Throwables.propagate(e);
        }
        catch (DatabaseException e) {
            throw Throwables.propagate(e);
        }
    	
        Collection<SchemeGridItem> schemeGridItems = Collections2.transform(
                scheme.getSchemeComponents(), new Function<SchemeComponent, SchemeGridItem>() {
                    public SchemeGridItem apply(SchemeComponent c) {
                        try {
                            return new SchemeGridItem(ComponentConverter.convert(
                                    getDisplay(), c.getComponent()), c);
                        }
                        catch (IOException e) {
                            LOG.error(RENDER_ERROR);
                            return null;
                        }
                    }
                });
        schemeGrid.setItems(schemeGridItems);
        
        List<SchemeComponent> input = new ArrayList<>();
        for (SchemeGridItem item : schemeGridItems) {
        	if (item.getSchemeComponent().getComponent().getBoolRating()) {
                input.add(item.getSchemeComponent());
        	}
        }

        componentsList.setInput(input);
    }
    
    public InspectionSchemeGrid getInspectionSchemeGrid(){
    	return schemeGrid;
    }
    
    public AbstractComponentRatingComposite getRatingComposite(){
    	return ratingComposite;
    }

	public SchemeComponent getSelectedSchemeComponent() {
		return selectedComponent;
	}

	@Override
	public void inspectionChanged(Inspection inspection) {
		plantChangedInternal(inspection.getPlant());
		ratingComposite.inspectionChanged(inspection);
	}

	@Override
	public void inspectionComponentSelectionChanged(SchemeComponent component) {
		updateFromOutside = true;
		setSelectComponent(component);
		ratingComposite.inspectionComponentSelectionChanged(component);
	}

	@Override
	public void plantChanged(Plant plant) {
		plantChangedInternal(plant);
		ratingComposite.plantChanged(plant);
	}

	private void fireComponentSelectionChanged(SchemeComponent component) {
		for (ComponentSelectionChangedListener listener : componentSelectionListeners)  {
			listener.componentSelectionChanged(component);
		}
	}

	public void addComponentSelectionListener(ComponentSelectionChangedListener listener) {
		componentSelectionListeners.add(listener);
	}

	public void removeComponentSelectionListener(ComponentSelectionChangedListener listener) {
		componentSelectionListeners.remove(listener);
	}
    
    @Override
    public void dispose() {
        toolkit.dispose();
        super.dispose();
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
