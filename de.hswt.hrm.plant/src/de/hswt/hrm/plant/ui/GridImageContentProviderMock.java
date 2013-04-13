package de.hswt.hrm.plant.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.plant.model.GridImage;

/**
 * A Mock for the grid image tree data.
 * 
 * @author Michael Sieger
 * 
 */
public class GridImageContentProviderMock implements ITreeContentProvider {

    private List<GridImage> images;
    private Display display;

    public GridImageContentProviderMock(Display display) {
        this.display = display;
    }

    @Override
    public void dispose() {
        for (GridImage i : images) {
            i.getRenderedImage().dispose();
        }
        images = null;
    }

    @Override
    public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
        images = new ArrayList<>();
        GridImage img = new GridImage();
        img.setWidth(4);
        img.setHeight(4);
        img.setRenderedImage(new Image(display, "/home/tamaran/tmp/220px-Tux.png"));
        images.add(img);
    }

    @Override
    public Object[] getChildren(Object arg0) {
        return null;
    }

    @Override
    public Object[] getElements(Object arg0) {
        return images.toArray();
    }

    @Override
    public Object getParent(Object arg0) {
        return null;
    }

    @Override
    public boolean hasChildren(Object arg0) {
        return false;
    }

}
