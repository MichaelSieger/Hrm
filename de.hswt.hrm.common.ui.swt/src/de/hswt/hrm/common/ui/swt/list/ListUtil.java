package de.hswt.hrm.common.ui.swt.list;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.List;

public final class ListUtil {

	private ListUtil() {

	}

	public static void sortList(ListViewer lv) {

		List list = lv.getList();
		String[] items = list.getItems();
		java.util.Arrays.sort(items);
		list.setItems(items);

	}

}
