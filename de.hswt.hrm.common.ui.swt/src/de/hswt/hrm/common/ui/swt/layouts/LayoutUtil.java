package de.hswt.hrm.common.ui.swt.layouts;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;

public class LayoutUtil {

	public static final int DEFAULT_SPACE = 7;

	public static final Color LABEL_COLOR = 
			Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);

	public static final Color HIGHLIGHT_COLOR = 
			new Color(Display.getCurrent(), 253, 196, 0);

	public static final Color RESULTS_COLOR = 
			new Color(Display.getCurrent(), 168, 206, 92);
	
	public static final DecimalFormat accurateMassFormat = new DecimalFormat("#0.0000");

	public static final DecimalFormat logPFormat = new DecimalFormat("#0.00");

	public static final DecimalFormat rtiFormat = new DecimalFormat("#0.0");

	public static final DecimalFormat rtFormat = new DecimalFormat("#0.0");

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	
	public static GridLayout createMainLayout(int numColumns) {
		GridLayout result = new GridLayout(numColumns, true);
		result.marginWidth = 0;
		result.marginHeight = 0;
		result.marginTop = 0;
		result.verticalSpacing = 0;
		result.horizontalSpacing = 60;
		return result;
	}

	public static GridLayout createMainLayout(int numColumns, int horzSpacing) {
		GridLayout result = new GridLayout(numColumns, true);
		result.marginWidth = 0;
		result.marginHeight = 0;
		result.marginTop = 0;
		result.verticalSpacing = 0;
		result.horizontalSpacing = horzSpacing;
		return result;
	}

	public static GridLayout createGridLayout(int numColumns,
			boolean makeColsEqualWidth, boolean setTopMargin,
			boolean setVertSpacing) {
		GridLayout result = new GridLayout(numColumns, makeColsEqualWidth);
		result.marginWidth = DEFAULT_SPACE;
		result.marginHeight = 0;
		result.marginBottom = DEFAULT_SPACE;
		result.horizontalSpacing = DEFAULT_SPACE;
		if (setTopMargin) {
			result.marginTop = DEFAULT_SPACE;
		}
		if (setVertSpacing) {
			result.verticalSpacing = DEFAULT_SPACE;
		}
		return result;
	}

	public static GridLayout createGridLayoutWithoutMargin(int numColumns,
			boolean makeColsEqualWidth) {
		GridLayout result = new GridLayout(numColumns, makeColsEqualWidth);
		result.marginHeight = 0;
		result.marginWidth = 0;
		return result;
	}

	public static RowLayout createRowLayout(int type, boolean setMargin) {
		RowLayout result = new RowLayout(type);
		result.marginTop = 0;
		result.marginLeft = 0;
		result.marginHeight = 0;
		if (setMargin) {
			result.marginBottom = DEFAULT_SPACE;
			result.marginWidth = DEFAULT_SPACE;
		} else {
			result.marginBottom = 0;
			result.marginWidth = 0;
		}
		return result;
	}

	public static FillLayout createFillLayout(boolean setMargin) {
		return createFillLayout(setMargin, SWT.HORIZONTAL);
	}

	public static FillLayout createFillLayout(boolean setMargin, int type) {
		FillLayout result = new FillLayout();
		if (setMargin) {
			result.marginWidth = DEFAULT_SPACE;
			result.marginHeight = DEFAULT_SPACE;
		}
		return result;
	}

	public static GridData createHorzFillData() {
		return createHorzFillData(1);
	}

	public static GridData createHorzFillData(int colSpan) {
		return new GridData(SWT.FILL, SWT.TOP, true, false, colSpan, 1);
	}

	public static GridData createLeftGridData() {
		return createLeftGridData(1);
	}

	public static GridData createLeftGridData(int colSpan) {
		return new GridData(SWT.LEFT, SWT.TOP, false, false, colSpan, 1);
	}

	public static GridData createRightGridData() {
		return createRightGridData(1);
	}

	public static GridData createRightGridData(int colSpan) {
		return new GridData(SWT.FILL, SWT.TOP, false, false, colSpan, 1);
	}

	public static GridData createFillData() {
		return createFillData(1);
	}

	public static GridData createFillData(int colSpan) {
		return new GridData(SWT.FILL, SWT.FILL, true, true, colSpan, 1);
	}

	private LayoutUtil() {
		// prevent instantiation
	}

}
