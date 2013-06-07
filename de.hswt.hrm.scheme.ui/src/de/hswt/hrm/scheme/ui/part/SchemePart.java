package de.hswt.hrm.scheme.ui.part;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.service.ComponentConverter;
import de.hswt.hrm.scheme.service.SchemeService;
import de.hswt.hrm.scheme.ui.ComponentLoadThread;
import de.hswt.hrm.scheme.ui.ItemClickListener;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;
import de.hswt.hrm.scheme.ui.SchemeTreePatternFilter;
import de.hswt.hrm.scheme.ui.dnd.DragData;
import de.hswt.hrm.scheme.ui.dnd.DragDataTransfer;
import de.hswt.hrm.scheme.ui.dnd.GridDragListener;
import de.hswt.hrm.scheme.ui.dnd.GridDropTargetListener;
import de.hswt.hrm.scheme.ui.dnd.TreeDragListener;
import de.hswt.hrm.scheme.ui.tree.SchemeTreeLabelProvider;
import de.hswt.hrm.scheme.ui.tree.TreeContentProvider;

/**
 * This is the Part for the scheme builder frame.
 * 
 * @author Michael Sieger
 *
 */
public class SchemePart {
	
	private static final int MOVE_AMOUNT = 3;
	
    private final static Logger LOG = LoggerFactory.getLogger(SchemePart.class);
	private static final String DELETE = "Löschen";
	
	@Inject
	SchemeService schemeService;
	
	/**
	 * The DND transfer type
	 */
	private static final Transfer[] TRANSFER = new Transfer[] {DragDataTransfer.getInstance()};
	
	/**
	 * The background color of the scheme editor.
	 */
	private static final RGB EDITOR_BACKGROUND = new RGB(255, 255, 255);
	
	private static final int DRAG_OPS = DND.DROP_COPY, DROP_OPS = DND.DROP_COPY;
	
	/**
	 * The pixel per grid range. Defines how far you can zoom in and out
	 */
	private static final int MIN_PPG = 20, MAX_PPG = 70;

	/**
	 * The topmost gui parent
	 */
	private Composite root;

	private SchemeGrid grid;
	
	private TreeViewer tree;
	
	@Inject
	EPartService service;
	
	@Inject
	ComponentService compService;
	
	/**
	 * The PatternFilter defines which TreeItems are visible for a given search pattern
	 */
	private PatternFilter filter;
	
	private List<RenderedComponent> comps;
	
	private Plant plant;
	
	/*
	 * DND items for the grid
	 */
	private DragSource gridDragSource;
	private DropTarget gridDropTarget;
	
	private GridDropTargetListener gridListener;
	private GridDragListener gridDragListener;
	private TreeDragListener treeDragListener;

	@PostConstruct
	public void postConstruct(Composite parent) {
	    if (schemeService == null) {
	        LOG.error("SchemeService not properly injected to SchemePart.");
	    }
	    
		URL url = SchemePart.class.getResource(
				SchemePart.class.getSimpleName() + IConstants.XWT_EXTENSION_SUFFIX);

		try {
			root = (Composite) XWT.load(parent, url);
			initTree();
			initSchemeGrid();
			initGridDropTarget();
			initGridDragSource();
			initGridDropTargetListener();
	        initTreeDND();
			initGridDND();
			initSaveBtn();
			initSlider();
			initMoveSchemeBtn();
			initItemClickListener();
			
			//TODO remove
			newScheme(new Plant(1, "bla"));
            ((Button) XWT.findElementByName(root, "abortbtn")).addListener(SWT.Selection, new Listener() {
 				@Override
 				public void handleEvent(Event event) {
 					service.findPart("Clients").setVisible(false);
 					service.findPart("Places").setVisible(false);
 					service.findPart("Plants").setVisible(false);
 					service.findPart("Scheme").setVisible(false);
 					service.findPart("Catalog").setVisible(false);
 					service.findPart("Category").setVisible(false);
 					service.findPart("Main").setVisible(true);
 					service.showPart("Main", PartState.VISIBLE);
 				}
 			});
            new ComponentLoadThread(this, root.getDisplay(), compService).start();
		} catch (Throwable e) {
			throw new Error("Unable to load ", e);
		}
	}
	
	/**
	 * Shows a empty grid. The user can enter a scheme, and save it for the given plant.
	 * 
	 * @param plant
	 */
	public void newScheme(Plant plant){
		this.plant = plant;
		grid.setItems(new ArrayList<SchemeGridItem>());
		grid.clearDirty();
	}
	
	/**
	 * The given scheme is loaded into the editor. The user can save a 
	 * new scheme for the given plant.
	 * 
	 * @param scheme
	 * @throws IOException If a pdf file could not be read
	 */
	public void modifyScheme(Scheme scheme) throws IOException{
		if(!scheme.getPlant().isPresent()){
			throw new IllegalArgumentException("The plant must be present here");
		}
		this.plant = scheme.getPlant().get();
		grid.setItems(toSchemeGridItems(scheme.getSchemeComponents()));
		grid.clearDirty();
	}
	
	/**
	 * @return Was the grid changed since last modifyScheme() or newScheme() call
	 */
	public boolean isDirty(){
	    return grid.isDirty();
	}
	
	private List<SchemeGridItem> toSchemeGridItems(Collection<SchemeComponent> sc) throws IOException{
		List<SchemeGridItem> l = new ArrayList<>();
		for(SchemeComponent c : sc){
			l.add(new SchemeGridItem(
					ComponentConverter.convert(root.getDisplay(), c.getComponent()), 
					c.getDirection(), c.getX(), c.getY()));
		}
		return l;
	}
	
	/*
	 * init gui elements
	 */
	
	private void initMoveSchemeBtn(){
		Button btn = getMoveBtn();
		btn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collection<SchemeGridItem> items = grid.getItems();
				items = Collections2.transform(items, new Function<SchemeGridItem, SchemeGridItem>() {
					public SchemeGridItem apply(SchemeGridItem item){
						item.setX(item.getX() + MOVE_AMOUNT);
						item.setY(item.getY() + MOVE_AMOUNT);
						return item;
					}
				});
				grid.setItems(items);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	private void initItemClickListener(){
		grid.setItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClicked(final MouseEvent e, final SchemeGridItem item) {
				if(e.button == 3){		//right mouse click
					final Menu menu = new Menu(root.getShell(), SWT.POP_UP);
					final MenuItem delete = new MenuItem(menu, SWT.PUSH);
					delete.addSelectionListener(new SelectionListener() {
						
						@Override
						public void widgetSelected(SelectionEvent e) {
							grid.removeItem(item);
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent e) {}
					});
					delete.setText(DELETE);
					menu.setVisible(true);
				}
			}
		});
	}
	
	private void initGridDropTargetListener(){
		gridListener = new GridDropTargetListener(grid, comps, this);
		gridDropTarget.addDropListener(gridListener);
	}
	
	private void initGridDragSource(){
        gridDragSource = new DragSource(grid, DRAG_OPS);
        gridDragSource.setTransfer(TRANSFER);
	}
	
	private void initGridDropTarget(){
        gridDropTarget = new DropTarget(grid, DROP_OPS);
        gridDropTarget.setTransfer(TRANSFER);
	}
	
	private void initTreeDND(){
		treeDragListener = new TreeDragListener(tree, comps, grid);
		tree.addDragSupport(DRAG_OPS, TRANSFER, treeDragListener);
	}
	
	private void initGridDND(){
		gridDragListener = new GridDragListener(grid, comps);
		gridDragSource.addDragListener(gridDragListener);
	}
	
	private void initSchemeGrid(){
		grid = new SchemeGrid(getSchemeComposite(), SWT.NONE, 40, 20, 40);
		grid.setBackground(new Color(root.getDisplay(), EDITOR_BACKGROUND));
		getSchemeComposite().setContent(grid);
		grid.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseScrolled(MouseEvent e) {
				addZoom(e.count);
			}
		});
	}
	
	private void initTree(){
		/*
		 * A FilteredTree cant be created in XWT, so its done here
		 */
		filter = new SchemeTreePatternFilter();
		final FilteredTree filteredTree = new FilteredTree(root, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, 
				filter, true);
		filteredTree.getFilterControl().addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				updatePatternFilter(filteredTree.getFilterControl().getText());
			}
		});
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 40);
		formData.left = new FormAttachment(0, 10);
		formData.right = new FormAttachment(0, 200);
		formData.bottom = new FormAttachment(100, -50);
		filteredTree.setLayoutData(formData);
		tree = filteredTree.getViewer();
		tree.setContentProvider(new TreeContentProvider());
		tree.setLabelProvider(new SchemeTreeLabelProvider());
	}
	
	private void initSlider(){
		Slider zoomSlider = getZoomSlider();
		zoomSlider.setMaximum(MAX_PPG);
		zoomSlider.setMinimum(MIN_PPG);
		zoomSlider.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateZoom();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		updateZoom();
	}
	
	private void initSaveBtn(){
		Button btn = getSaveBtn();
		btn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collection<SchemeComponent> schemeComps = Collections2.transform(
						grid.getItems(), new Function<SchemeGridItem, SchemeComponent>(){
							public SchemeComponent apply(SchemeGridItem item){
								return item.asSchemeComponent();
							}
						});
				try {
					schemeService.insert(plant, schemeComps);
				} catch (SaveException e1) {
		            MessageDialog.openError(root.getShell(), "Fehler beim Speichern",
		                    "Das Schema konnte nicht gespeichert werden");
				}
			};
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	/*
	 * setter
	 */
	
	private void addZoom(int amount){
		Slider slider = getZoomSlider();
		slider.setSelection(slider.getSelection() + amount);
		updateZoom();
	}
	
	/*
	 * getter
	 */
	
	private Button getSaveBtn(){
		return (Button) XWT.findElementByName(root, "savebtn");
	}
	
	private Button getMoveBtn(){
		return (Button) XWT.findElementByName(root, "movebtn");
	}

	private Slider getZoomSlider(){
		return (Slider) XWT.findElementByName(root, "zoomSlider");
	}

	private ScrolledComposite getSchemeComposite() {
		return (ScrolledComposite) XWT.findElementByName(root, "schemeComposite");
	}

	/*
	 * updates
	 */
	
	private void updateZoom(){
		grid.setPixelPerGrid(getZoomSlider().getSelection());
	}
	
	private void updatePatternFilter(String text){
		filter.setPattern(text);
		tree.refresh();
		if(text.trim().length() == 0){
			tree.collapseAll();
		}else{
			tree.expandAll();
		}
	}

    public int getRenderedComponentId(RenderedComponent comp) {
        return comps.indexOf(comp);
    }

    public RenderedComponent getRenderedComponent(int id) {
        return comps.get(id);
    }

	public DragData getDraggingItem() {
		DragData i1 = gridDragListener.getDraggingItem();
		DragData i2 = treeDragListener.getDraggingItem();
		if(i1 == null && i2 == null){
			throw new RuntimeException("No drag and drop is running");
		}
		if(i1 != null){
			return i1;
		}else{
			return i2;
		}
	}

	public void setRenderedComponents(List<RenderedComponent> comps){
		this.comps = comps;
		tree.setInput(comps);
	}

}