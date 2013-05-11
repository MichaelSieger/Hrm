package de.hswt.hrm.scheme.ui.part;

import java.net.URL;

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
	
	private static final Transfer[] TRANSFER = new Transfer[] {TextTransfer.getInstance()};
	
	private static final RGB WHITE = new RGB(255, 255, 255);
	
	private static final int DRAG_OPS = DND.DROP_COPY, DROP_OPS = DND.DROP_COPY;
	
	private static final int MIN_PPG = 20, MAX_PPG = 70;

	private Composite root;
	
	private SchemeGrid grid;
	
	private TreeViewer tree;
	
	private PatternFilter filter;
	
	private DragSource gridDragSource;
	
	private DropTarget gridDropTarget;

	@PostConstruct
	public void postConstruct(Composite parent) {
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
			createSlider();

		} catch (Throwable e) {
			throw new Error("Unable to load ", e);
		}
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
		grid.setBackground(new Color(root.getDisplay(), WHITE));
		getSchemeComposite().setContent(grid);
	}
	
	private void initTree(){
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
		formData.bottom = new FormAttachment(100, -10);
		filteredTree.setLayoutData(formData);
		tree = filteredTree.getViewer();
		tree.setContentProvider(new TreeContentProvider());
		tree.setLabelProvider(new SchemeTreeLabelProvider());
		tree.setInput(ImageTreeModelFactory.create(
		        root.getDisplay()).getImages());
	}

	private Slider getZoomSlider(){
		return (Slider) XWT.findElementByName(root, "zoomSlider");
	}

	private ScrolledComposite getSchemeComposite() {
		return (ScrolledComposite) XWT.findElementByName(root, "schemeComposite");
	}

	private void createSlider(){
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

	private void updateZoom(){
		grid.setPixelPerGrid(getZoomSlider().getSelection());
	}
	
	private void updatePatternFilter(String text){
		filter.setPattern(text);
		tree.refresh();
	}

}