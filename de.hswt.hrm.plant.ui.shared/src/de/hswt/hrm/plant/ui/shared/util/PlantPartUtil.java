package de.hswt.hrm.plant.ui.shared.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.wizards.WizardCreator;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.ui.shared.ui.wizzard.PlantWizard;


public class PlantPartUtil {
    
    private final static I18n I18N = I18nFactory.getI18n(PlantPartUtil.class);

    public static Optional<Plant> showWizard(IEclipseContext context, Shell activeShell,
            Optional<Plant> plant) {

        PlantWizard pw = new PlantWizard(context, plant);
        ContextInjectionFactory.inject(pw, context);

        WizardDialog wd = WizardCreator.createWizardDialog(activeShell, pw);
        wd.open();
        return pw.getPlant();
    }

    public static List<ColumnDescription<Plant>> getColumns() {

        List<ColumnDescription<Plant>> columns = new ArrayList<>();
        columns.add(getDescription());
        columns.add(getNumberOfElements());
        columns.add(getPlace());
        columns.add(getConstructionYear());
        columns.add(getManufactor());
        columns.add(getType());
        columns.add(getAirPerformance());
        columns.add(getMotorPower());
        columns.add(getMotorRPM());
        columns.add(getVentilatorPerformance());
        columns.add(getCurrent());
        columns.add(getVoltage());
        columns.add(getNote());

        return columns;
    }

    private static ColumnDescription<Plant> getNumberOfElements() {
        return new ColumnDescription<Plant>(I18N.tr("Number of Elements"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return String.valueOf(p.getNumberOfElements());

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getNumberOfElements() - o2.getNumberOfElements();
            }

        });
    }

    private static ColumnDescription<Plant> getDescription() {
        return new ColumnDescription<Plant>(I18N.tr("Description"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getDescription();
            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getDescription().compareToIgnoreCase(o2.getDescription());
            }

        });
    }

    private static ColumnDescription<Plant> getPlace() {
        return new ColumnDescription<Plant>(I18N.tr("Place"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                if (p.getPlace().isPresent()) {
                    return p.getPlace().get().getPlaceName();
                }
                return "";

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getPlace().get().getPlaceName()
                        .compareToIgnoreCase(o2.getPlace().get().getPlaceName());
            }

        });
    }

    private static ColumnDescription<Plant> getConstructionYear() {
        return new ColumnDescription<Plant>(I18N.tr("Construction Year"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getConstructionYear().get().toString();

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getConstructionYear().get().compareTo(o2.getConstructionYear().get());
            }

        });
    }

    private static ColumnDescription<Plant> getManufactor() {
        return new ColumnDescription<Plant>(I18N.tr("Manufactor"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getManufactor().get();

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getManufactor().get().compareToIgnoreCase(o2.getManufactor().get());
            }

        });
    }

    private static ColumnDescription<Plant> getType() {
        return new ColumnDescription<Plant>(I18N.tr("Type"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getConstructionYear().get().toString();

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getConstructionYear().get().compareTo(o2.getConstructionYear().get());
            }

        });
    }

    private static ColumnDescription<Plant> getAirPerformance() {
        return new ColumnDescription<Plant>(I18N.tr("Air Performance"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getAirPerformance().orNull();

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getAirPerformance().get()
                        .compareToIgnoreCase(o2.getAirPerformance().get());
            }

        });
    }

    private static ColumnDescription<Plant> getMotorPower() {
        return new ColumnDescription<Plant>(I18N.tr("Motor Power"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getMotorPower().orNull();

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getMotorPower().get().compareToIgnoreCase(o2.getMotorPower().get());
            }

        });
    }

    private static ColumnDescription<Plant> getMotorRPM() {
        return new ColumnDescription<Plant>(I18N.tr("Motor RPM"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getMotorRpm().orNull();

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getMotorRpm().get().compareToIgnoreCase(o2.getMotorRpm().get());
            }

        });
    }

    private static ColumnDescription<Plant> getVentilatorPerformance() {
        return new ColumnDescription<Plant>(I18N.tr("Ventilator Performance"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getVentilatorPerformance().orNull();

            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getVentilatorPerformance().get()
                        .compareToIgnoreCase(o2.getVentilatorPerformance().get());
            }

        });
    }

    private static ColumnDescription<Plant> getCurrent() {
        return new ColumnDescription<Plant>(I18N.tr("Current"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getCurrent().orNull();
            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getCurrent().get().compareToIgnoreCase(o2.getCurrent().get());
            }

        });
    }

    private static ColumnDescription<Plant> getVoltage() {
        return new ColumnDescription<Plant>(I18N.tr("Voltage"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getVoltage().orNull();
            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getVoltage().get().compareToIgnoreCase(o2.getVoltage().get());
            }

        });
    }

    private static ColumnDescription<Plant> getNote() {
        return new ColumnDescription<Plant>(I18N.tr("Note"), new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Plant p = (Plant) element;
                return p.getNote().orNull();
            }
        }, new Comparator<Plant>() {

            @Override
            public int compare(Plant o1, Plant o2) {
                return o1.getNote().get().compareTo(o2.getNote().get());
            }

        });
    }

}
