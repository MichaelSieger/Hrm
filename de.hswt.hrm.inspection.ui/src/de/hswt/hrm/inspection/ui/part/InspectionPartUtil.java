package de.hswt.hrm.inspection.ui.part;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.wizards.WizardCreator;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.ui.wizard.ReportCreationWizard;

public class InspectionPartUtil {

	private static final Color MORE_THEN_3 = Display.getCurrent()
			.getSystemColor(SWT.COLOR_GREEN);
	private static final Color LESS_THEN_3 = Display.getCurrent()
			.getSystemColor(SWT.COLOR_YELLOW);
	private static final Color EXPIRED = Display.getCurrent().getSystemColor(
			SWT.COLOR_RED);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");
	
	private static final I18n I18N = I18nFactory.getI18n(InspectionPartUtil.class);

	public static Optional<Inspection> showInspectionCreateWizard(
			IEclipseContext context, Shell shell) {
		// Create wizard with injection support
		ReportCreationWizard wizard = new ReportCreationWizard();
		ContextInjectionFactory.inject(wizard, context);

		// Show wizard
		WizardDialog wd = WizardCreator.createWizardDialog(shell, wizard);
		wd.open();
		return wizard.getInspection();
	}

	public static List<ColumnDescription<Inspection>> getColumns() {
		List<ColumnDescription<Inspection>> columns = new ArrayList<>();
		columns.add(getColorColumn());
		columns.add(getTitleColumn());
		columns.add(getPlantClumn());
		columns.add(getReportDateColumn());
		columns.add(getInspectionDateColumn());
		columns.add(getNextInspectionDateColumn());
		columns.add(getStyleColumn());
		return columns;
	}

	private static ColumnDescription<Inspection> getColorColumn() {
		return new ColumnDescription<>("", new OwnerDrawLabelProvider() {
			
			@Override
			protected void paint(Event event, Object element) {
				Inspection inspection = (Inspection) element;

				Calendar cal = Calendar.getInstance();
				int current = cal.get(Calendar.MONTH);
				int currentYear = cal.get(Calendar.YEAR);
				int next = inspection.getNextInspectionDate().get(
						Calendar.MONTH);
				int nextYear = inspection.getNextInspectionDate().get(
						Calendar.YEAR);

				int diffYears = (nextYear - currentYear) * 12;
				int diff = next - current + diffYears;

				if (diff < 0) {
					event.gc.setBackground(EXPIRED);
				} else if (diff <= 3) {
					event.gc.setBackground(LESS_THEN_3);
				} else {
					event.gc.setBackground(MORE_THEN_3);
				}
				
				// Get correct width
				Rectangle cellBounds = null;
				if (event.item instanceof TableItem){
					cellBounds = ((TableItem) event.item).getBounds(event.index);					
				}
				
				// event.gc.fillOval(event.x + 10, event.y + 10, 10, 10);
				int height = event.getBounds().height;
				int width = event.getBounds().width;
				if (cellBounds != null) {
					height = cellBounds.height;
					width = cellBounds.width;
				}
				
				// calculate circle diameter
				int diameter = height > width ? width : height;
				if (diameter > 12) {
					diameter = 12;
				}
				
				// calculate upper left corner (coordinate system start at upper left)
				int x = event.x + width / 2 - diameter / 2;
				int y = event.y + height / 2 - diameter/ 2;

				// event.gc.fillOval(event.x, event.y, event.y/2, event.y/2);
				event.gc.fillOval(x, y, diameter, diameter);
			}

			@Override
			protected void measure(Event event, Object element) {

			}
		}, new Comparator<Inspection>() {
			@Override
			public int compare(Inspection i1, Inspection i2) {
				return i1.getNextInspectionDate().compareTo(
						i2.getNextInspectionDate());
			}
		}, 30);
	}

	private static ColumnDescription<Inspection> getTitleColumn() {
		return new ColumnDescription<>(I18N.tr("Title"), new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inspection i = (Inspection) element;
				return i.getTitle();
			}
		}, new Comparator<Inspection>() {
			@Override
			public int compare(Inspection i1, Inspection i2) {
				return i1.getTitle().compareToIgnoreCase(i2.getTitle());
			}
		});
	}

	private static ColumnDescription<Inspection> getPlantClumn() {
		return new ColumnDescription<>(I18N.tr("Plant Description"),
				new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						Inspection i = (Inspection) element;
						return i.getPlant().getDescription();
					}
				}, new Comparator<Inspection>() {
					@Override
					public int compare(Inspection i1, Inspection i2) {
						return i1
								.getPlant()
								.getDescription()
								.compareToIgnoreCase(
										i2.getPlant().getDescription());
					}
				});
	}

	private static ColumnDescription<Inspection> getReportDateColumn() {
		return new ColumnDescription<>(I18N.tr("Report Date"),
				new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						Inspection i = (Inspection) element;
						return dateFormat.format(i.getReportDate().getTime());
					}
				}, new Comparator<Inspection>() {
					@Override
					public int compare(Inspection i1, Inspection i2) {
						return dateFormat.format(i1.getReportDate().getTime())
								.compareToIgnoreCase(
										dateFormat.format(i2.getReportDate()
												.getTime()));
					}
				});
	}

	private static ColumnDescription<Inspection> getInspectionDateColumn() {
		return new ColumnDescription<>(I18N.tr("Inspection Date"),
				new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						Inspection i = (Inspection) element;
						return dateFormat.format(i.getReportDate().getTime());
					}
				}, new Comparator<Inspection>() {
					@Override
					public int compare(Inspection i1, Inspection i2) {
						return dateFormat.format(i1.getReportDate().getTime())
								.compareToIgnoreCase(
										dateFormat.format(i2.getReportDate()
												.getTime()));
					}
				});
	}

	private static ColumnDescription<Inspection> getNextInspectionDateColumn() {
		return new ColumnDescription<>(I18N.tr("Next Inspection"),
				new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						Inspection i = (Inspection) element;
						return dateFormat.format(i.getNextInspectionDate()
								.getTime());
					}
				}, new Comparator<Inspection>() {
					@Override
					public int compare(Inspection i1, Inspection i2) {
						return dateFormat.format(
								i1.getNextInspectionDate().getTime())
								.compareToIgnoreCase(
										dateFormat.format(i2
												.getNextInspectionDate()
												.getTime()));
					}
				});
	}

	private static ColumnDescription<Inspection> getStyleColumn() {
		return new ColumnDescription<>(I18N.tr("Layout"), new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Inspection i = (Inspection) element;
				return i.getLayout().getName();
			}
		}, new Comparator<Inspection>() {
			@Override
			public int compare(Inspection i1, Inspection i2) {
				return i1.getLayout().getName()
						.compareToIgnoreCase(i2.getLayout().getName());
			}
		});

	}

}
