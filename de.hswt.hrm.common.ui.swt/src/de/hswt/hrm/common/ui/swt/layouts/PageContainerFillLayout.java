package de.hswt.hrm.common.ui.swt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * Extracted from org.eclipse.jface.wizard.WizardDialog.PageContainerFillLayout
 *
 */
public class PageContainerFillLayout extends Layout {
	/**
	 * The margin width; <code>5</code> pixels by default.
	 */
	public int marginWidth = 0;

	/**
	 * The margin height; <code>5</code> pixels by default.
	 */
	public int marginHeight = 0;

	/**
	 * The minimum width; <code>0</code> pixels by default.
	 */
	public int minimumWidth = 0;

	/**
	 * The minimum height; <code>0</code> pixels by default.
	 */
	public int minimumHeight = 0;

	/**
	 * Creates new layout object.
	 * 
	 */
	public PageContainerFillLayout() {
	}
	
	/**
	 * Creates new layout object.
	 * 
	 * @param mw
	 *            the margin width
	 * @param mh
	 *            the margin height
	 * @param minW
	 *            the minimum width
	 * @param minH
	 *            the minimum height
	 */
	public PageContainerFillLayout(int mw, int mh, int minW, int minH) {
		marginWidth = mw;
		marginHeight = mh;
		minimumWidth = minW;
		minimumHeight = minH;
	}

	/*
	 * (non-Javadoc) Method declared on Layout.
	 */
	public Point computeSize(Composite composite, int wHint, int hHint,
			boolean force) {
		if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
			return new Point(wHint, hHint);
		}
		Point result = null;
		Control[] children = composite.getChildren();
		if (children.length > 0) {
			result = new Point(0, 0);
			for (int i = 0; i < children.length; i++) {
				Point cp = children[i].computeSize(wHint, hHint, force);
				result.x = Math.max(result.x, cp.x);
				result.y = Math.max(result.y, cp.y);
			}
			result.x = result.x + 2 * marginWidth;
			result.y = result.y + 2 * marginHeight;
		} else {
			Rectangle rect = composite.getClientArea();
			result = new Point(rect.width, rect.height);
		}
		result.x = Math.max(result.x, minimumWidth);
		result.y = Math.max(result.y, minimumHeight);
		if (wHint != SWT.DEFAULT) {
			result.x = wHint;
		}
		if (hHint != SWT.DEFAULT) {
			result.y = hHint;
		}
		return result;
	}

	/**
	 * Returns the client area for the given composite according to this
	 * layout.
	 * 
	 * @param c
	 *            the composite
	 * @return the client area rectangle
	 */
	public Rectangle getClientArea(Composite c) {
		Rectangle rect = c.getClientArea();
		rect.x = rect.x + marginWidth;
		rect.y = rect.y + marginHeight;
		rect.width = rect.width - 2 * marginWidth;
		rect.height = rect.height - 2 * marginHeight;
		return rect;
	}

	/*
	 * (non-Javadoc) Method declared on Layout.
	 */
	public void layout(Composite composite, boolean force) {
		Rectangle rect = getClientArea(composite);
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].setBounds(rect);
		}
	}

	/**
	 * Lays outs the page according to this layout.
	 * 
	 * @param w
	 *            the control
	 */
	public void layoutPage(Control w) {
		w.setBounds(getClientArea(w.getParent()));
	}

	/**
	 * Sets the location of the page so that its origin is in the upper left
	 * corner.
	 * 
	 * @param w
	 *            the control
	 */
	public void setPageLocation(Control w) {
		w.setLocation(marginWidth, marginHeight);
	}
}
