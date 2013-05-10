package de.hswt.hrm.scheme.ui.part;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.FilteredTree;

import de.hswt.hrm.scheme.ui.GridDNDManager;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.TreeDNDManager;
import de.hswt.hrm.scheme.ui.tree.ImageTreeModelFactory;
import de.hswt.hrm.scheme.ui.tree.SchemeTreeLabelProvider;
import de.hswt.hrm.scheme.ui.tree.SchemeTreeViewerFilter;
import de.hswt.hrm.scheme.ui.tree.TreeContentProvider;

/**
 * This is the Part for the scheme builder frame.
 * 
 * @author Michael Sieger
 *
 */
public class SchemePart {
	
	private static final String SEARCH_TEXT = "Suche";
	
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
			createSearchText();
		} catch (Throwable e) {
			throw new Error("Unable to load ", e);
		}
	}
	
	private void initTreeDND(DropTarget gridDropTarget){
		TreeDNDManager m = new TreeDNDManager(getTree().getViewer(), grid);
		getTree().getViewer().addDragSupport(DRAG_OPS, TRANSFER, m);
		gridDropTarget.addDropListener(m);
	}
	
	private void initGridDND(DropTarget gridDropTarget, DragSource gridDragSource){
		GridDNDManager gridDND = new GridDNDManager(grid);
		gridDropTarget.addDropListener(gridDND);
		gridDragSource.addDragListener(gridDND);
	}
	
	private void initTree(){
		FilteredTree tree = getTree();
		tree.getViewer().setContentProvider(new TreeContentProvider());
		tree.getViewer().setLabelProvider(new SchemeTreeLabelProvider());
		tree.getViewer().setInput(ImageTreeModelFactory.create(
		        root.getDisplay()).getImages());
		//tree.setFilters(new ViewerFilter[]{
		//		new SchemeTreeViewerFilter(getSearchText().getText())});
	}

	private Slider getZoomSlider(){
		return (Slider) XWT.findElementByName(root, "zoomSlider");
	}
	
	private FilteredTree getTree() {
		return (FilteredTree) XWT.findElementByName(root, "tree");
	}
	
	private Text getSearchText(){
		return (Text) XWT.findElementByName(root, "searchtext");
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
	
	private void createSearchText(){
		Text text = getSearchText();
		text.setMessage(SEARCH_TEXT);
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				updateTreeFilter();
			}
		});
	}
	
	private void updateTreeFilter(){
		getTree().getViewer().refresh();
	}
	
	private void updateZoom(){
		grid.setPixelPerGrid(getZoomSlider().getSelection());
	}

}