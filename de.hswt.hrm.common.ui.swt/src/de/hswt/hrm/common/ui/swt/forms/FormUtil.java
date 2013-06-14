package de.hswt.hrm.common.ui.swt.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class FormUtil {

	public static FormToolkit createToolkit() {
		Display display = Display.getCurrent();
		FormColors result = new FormColors(display);
		result.createColor(IFormColors.H_GRADIENT_START, new Color(display,
//				255, 244, 163).getRGB());
		255, 185, 49).getRGB());
		result.createColor(IFormColors.H_GRADIENT_END, new Color(display,
//				255, 247, 232).getRGB());
//		255, 236, 198).getRGB());
		255, 244, 163).getRGB());
		result.createColor(IFormColors.H_BOTTOM_KEYLINE1, new Color(display,
				255, 168, 0).getRGB());
		result.createColor(IFormColors.H_BOTTOM_KEYLINE2, new Color(display,
				255, 168, 0).getRGB());
		result.createColor(IFormColors.TITLE, display.getSystemColor(
				SWT.COLOR_BLACK).getRGB());
//		result.createColor(IFormColors.TITLE, new Color(Display.getCurrent(),
//				122, 184, 0).getRGB());
//				255, 168, 0).getRGB());
		result.createColor(IFormColors.TB_FG, display.getSystemColor(
				SWT.COLOR_BLACK).getRGB());
		result.createColor(IFormColors.TB_BG, 
				new Color(Display.getCurrent(), 255, 185, 49).getRGB());
		result.createColor(IFormColors.TB_BORDER, 
				new Color(Display.getCurrent(), 255, 168, 0).getRGB());
		
		return new FormToolkit(result);
	}
	
	public static void initSectionColors(Section section) {
		Display display = Display.getCurrent();
		section.setTitleBarForeground(
				display.getSystemColor(SWT.COLOR_BLACK)); 
		section.setTitleBarGradientBackground(
				new Color(Display.getCurrent(), 255, 244, 163)); 
		section.setTitleBarBackground(
				new Color(Display.getCurrent(), 255, 185, 49)); 
		section.setTitleBarBorderColor(
				new Color(Display.getCurrent(), 255, 168, 0)); 
	}
}
