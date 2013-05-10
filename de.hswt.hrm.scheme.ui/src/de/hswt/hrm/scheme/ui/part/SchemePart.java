package de.hswt.hrm.scheme.ui.part;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Tree;

import de.hswt.hrm.scheme.ui.GridDNDManager;
import de.hswt.hrm.scheme.ui.SchemeGrid;
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

	@PostConstruct
	public void postConstruct(Composite parent) {
		URL url = SchemePart.class.getResource(
				SchemePart.class.getSimpleName() + IConstants.XWT_EXTENSION_SUFFIX);

		try {
			root = (Composite) XWT.load(parent, url);
			initTree();
			grid = new SchemeGrid(getSchemeComposite(), SWT.NONE, 40, 20, 40);
			grid.setBackground(new Color(root.getDisplay(), WHITE));
			getSchemeComposite().setContent(grid);
	        DropTarget gridDropTarget = new DropTarget(grid, DROP_OPS);
	        gridDropTarget.setTransfer(TRANSFER);
	        DragSource gridDragSource = new DragSource(grid, DRAG_OPS);
	        gridDragSource.setTransfer(TRANSFER);
	        initTreeDND(gridDropTarget);
			initGridDND(gridDropTarget, gridDragSource);
			createSlider();
		} catch (Throwable e) {
			throw new Error("Unable to load ", e);
		}
	}
	
	private void initTreeDND(DropTarget gridDropTarget){
		TreeDNDManager m = new TreeDNDManager(getTree(), grid);
		getTree().addDragSupport(DRAG_OPS, TRANSFER, m);
		gridDropTarget.addDropListener(m);
	}
	
	private void initGridDND(DropTarget gridDropTarget, DragSource gridDragSource){
		GridDNDManager gridDND = new GridDNDManager(grid);
		gridDropTarget.addDropListener(gridDND);
		gridDragSource.addDragListener(gridDND);
	}
	
	private void initTree(){
		TreeViewer tree = getTree();
		tree.setContentProvider(new TreeContentProvider());
		tree.setLabelProvider(new SchemeTreeLabelProvider());
		tree.setInput(ImageTreeModelFactory.create(
		        root.getDisplay()).getImages());
	}

	private Slider getZoomSlider(){
		return (Slider) XWT.findElementByName(root, "zoomSlider");
	}
	
	private TreeViewer getTree() {
		return (TreeViewer) XWT.findElementByName(root, "tree");
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

}