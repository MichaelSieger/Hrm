package de.hswt.hrm.common.ui.swt.constants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class SearchFieldConstants {

	private SearchFieldConstants(){}
	
	public static final String DEFAULT_SEARCH_STRING = "Search";

	public static final String EMPTY = "";

	public static final Color DEFAULT_SEARCH_COLOR = 
			Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	
}
