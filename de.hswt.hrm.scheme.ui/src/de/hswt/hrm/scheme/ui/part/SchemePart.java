package de.hswt.hrm.scheme.ui.part;

import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.TextTransfer;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.ui.DirectedRenderedComponent;
import de.hswt.hrm.scheme.ui.GridDNDManager;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeTreePatternFilter;
import de.hswt.hrm.scheme.ui.TreeDNDManager;
import de.hswt.hrm.scheme.ui.tree.ImageTreeModelFactory;
import de.hswt.hrm.scheme.ui.tree.SchemeTreeLabelProvider;
import de.hswt.hrm.scheme.ui.tree.TreeContentProvider;

/**
 * This is the Part for the scheme builder frame.
 * 
 * @author Michael Sieger
 *
 */
public class SchemePart {
	
	/**
	 * The DND transfer type
	 */
	private static final Transfer[] TRANSFER = new Transfer[] {TextTransfer.getInstance()};
	
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
	
	/**
	 * The PatternFilter defines which TreeItems are visible for a given search pattern
	 */
	private PatternFilter filter;
	
	/*
	 * DND items for the grid
	 */
	private DragSource gridDragSource;
	private DropTarget gridDropTarget;
	
	private List<RenderedComponent> comps;

	@PostConstruct
	public void postConstruct(Composite parent) {
	    comps = ImageTreeModelFactory.create(
                root.getDisplay()).getImages(); 
		URL url = SchemePart.class.getResource(
				SchemePart.class.getSimpleName() + IConstants.XWT_EXTENSION_SUFFIX);

		try {
			root = (Composite) XWT.load(parent, url);
			initTree();
			initSchemeGrid();
			initGridDropTarget();
			initGridDragSource();
	        initTreeDND();
			initGridDND();
			initSlider();

		} catch (Throwable e) {
			throw new Error("Unable to load ", e);
		}
	}
	
	/*
	 * init gui elements
	 */
	
	private void initGridDragSource(){
        gridDragSource = new DragSource(grid, DRAG_OPS);
        gridDragSource.setTransfer(TRANSFER);
	}
	
	private void initGridDropTarget(){
        gridDropTarget = new DropTarget(grid, DROP_OPS);
        gridDropTarget.setTransfer(TRANSFER);
	}
	
	private void initTreeDND(){
		TreeDNDManager m = new TreeDNDManager(tree, grid);
		tree.addDragSupport(DRAG_OPS, TRANSFER, m);
		gridDropTarget.addDropListener(m);
	}
	
	private void initGridDND(){
		GridDNDManager gridDND = new GridDNDManager(grid);
		gridDropTarget.addDropListener(gridDND);
		gridDragSource.addDragListener(gridDND);
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
		formData.top = new FormAttachment(0, 10);
		formData.left = new FormAttachment(0, 10);
		formData.right = new FormAttachment(0, 200);
		formData.bottom = new FormAttachment(100, -50);
		filteredTree.setLayoutData(formData);
		tree = filteredTree.getViewer();
		tree.setContentProvider(new TreeContentProvider());
		tree.setLabelProvider(new SchemeTreeLabelProvider());
		tree.setInput(comps);
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

}