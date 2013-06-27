package de.hswt.hrm.scheme.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.component.model.Attribute;

public final class SchemeDialogUtil {

	private SchemeDialogUtil() {

	}

	public static List<ColumnDescription<Attribute>> getColumns(
			Map<Attribute, String> assignedValues) {
		List<ColumnDescription<Attribute>> columns = new ArrayList<>();
		columns.add(getNameColumn());
		columns.add(getValueColumn(assignedValues));
		return columns;
	}

	private static ColumnDescription<Attribute> getNameColumn() {
		return new ColumnDescription<Attribute>("Name",
				new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						Attribute a = (Attribute) element;
						return a.getName();
					}
				}, new Comparator<Attribute>() {

					@Override
					public int compare(Attribute a1, Attribute a2) {

						return a1.getName().compareToIgnoreCase(a2.getName());
					}

				});
	}

	private static ColumnDescription<Attribute> getValueColumn(
			final Map<Attribute, String> assignedValues) {
		return new ColumnDescription<Attribute>("Value",
				new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {

						Attribute a = (Attribute) element;
						Collection<Attribute> c = assignedValues.keySet();

						for (Attribute att : c) {
							if (a.getName().equalsIgnoreCase(att.getName())) {
								return assignedValues.get(att);
							}
						}

						return "";

					}
				}, new Comparator<Attribute>() {

					@Override
					public int compare(Attribute a1, Attribute a2) {

						return a1.getName().compareToIgnoreCase(a2.getName());
					}

				});
	}
}
