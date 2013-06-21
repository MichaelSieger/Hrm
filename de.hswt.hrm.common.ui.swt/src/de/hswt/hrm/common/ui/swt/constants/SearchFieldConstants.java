package de.hswt.hrm.common.ui.swt.constants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.i18n.I18nFactory;

public class SearchFieldConstants {

	private SearchFieldConstants(){}
	
	public static final String DEFAULT_SEARCH_STRING = I18nFactory.getI18n(SearchFieldConstants.class).tr("Search");

	public static final String EMPTY = "";

	public static final Color DEFAULT_SEARCH_COLOR = 
			Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	
}
