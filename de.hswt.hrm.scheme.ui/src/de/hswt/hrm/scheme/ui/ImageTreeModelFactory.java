package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.widgets.Display;

import test.ImageTreeModelMock;

/**
 * Hides where the grid images come from
 * 
 * @author Michael Sieger
 * 
 */
public class ImageTreeModelFactory {

    public static IImageTreeModel create(Display display) {
        return new ImageTreeModelMock(display);
    }

}
