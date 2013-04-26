package de.hswt.hrm.plant.ui.schemeeditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.hswt.hrm.plant.model.GridImage;

public class TreeManager {

    private static final int DRAG_OPS = DND.DROP_COPY;
    
    private static final int THUMBNAIL_GRID_SIZE = 10;

    private final Display display;
    private final IImageTreeModel model;
    private final Tree tree;

    public TreeManager(Display display, IImageTreeModel model, Tree tree) {
        super();
        this.model = model;
        this.tree = tree;
        this.display = display;
        generateTreeItems();
    }

    private void generateTreeItems(){
		tree.clearAll(true);
		for(GridImage img : model.getImages()){
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setImage(getThumbnail(img));
			item.setData(img);
			DragSource src = new DragSource(tree, DRAG_OPS);
			src.setTransfer(new Transfer[]{
			        TextTransfer.getInstance()
			});
			src.addDragListener(new DragSourceListener() {
                
                @Override
                public void dragStart(DragSourceEvent ev) {
                    //TreeItem item = tree.getSelection()[0];
                    //ev.data = item.getData();
                }
                
                @Override
                public void dragSetData(DragSourceEvent arg0) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void dragFinished(DragSourceEvent arg0) {
                    // TODO Auto-generated method stub
                    
                }
            });
		}
	}

    private Image getThumbnail(GridImage img) {
        ImageData data = img
                .getRenderedImage()
                .getImageData()
                .scaledTo(img.getWidth() * THUMBNAIL_GRID_SIZE,
                        img.getHeight() * THUMBNAIL_GRID_SIZE);
        return new Image(display, data);
    }
}
