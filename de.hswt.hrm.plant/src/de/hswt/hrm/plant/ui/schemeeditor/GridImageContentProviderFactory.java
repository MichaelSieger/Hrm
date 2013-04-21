package de.hswt.hrm.plant.ui.schemeeditor;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Display;

import test.GridImageContentProviderMock;

/**
 * Hides where the grid images come from
 * 
 * @author Michael Sieger
 * 
 */
public class GridImageContentProviderFactory {

    public static ITreeContentProvider create(Display display) {
        return new GridImageContentProviderMock(display);
    }

}
