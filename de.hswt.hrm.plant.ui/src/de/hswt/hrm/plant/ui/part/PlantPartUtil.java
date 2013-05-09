//package de.hswt.hrm.plant.ui.part;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//
//import org.eclipse.jface.viewers.ColumnLabelProvider;
//import org.eclipse.swt.widgets.Shell;
//
//import com.google.common.base.Optional;
//
//import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
//import de.hswt.hrm.plant.model.Plant;
//
//public class PlantPartUtil {
//
//    public static Optional<Plant> showWizard(Shell activeShell, Optional<Plant> fromNullable) {
//
//        // TODO
//
//        return null;
//
//    }
//
//    public static List<ColumnDescription<Plant>> getColumns() {
//
//        List<ColumnDescription<Plant>> columns = new ArrayList<>();
//        columns.add(getInspectionInterval());
//        columns.add(getDescription());
//        columns.add(getPlace());
//        return null;
//    }
//
//    private static ColumnDescription<Plant> getInspectionInterval() {
//        return new ColumnDescription<Plant>("Inspection Interval", new ColumnLabelProvider() {
//            @Override
//            public String getText(Object element) {
//                Plant p = (Plant) element;
//                return String.valueOf(p.getInspectionInterval());
//            }
//        }, new Comparator<Plant>() {
//
//            @Override
//            public int compare(Plant o1, Plant o2) {
//                return o1.getInspectionInterval() - o2.getInspectionInterval();
//            }
//
//        });
//    }
//
//    private static ColumnDescription<Plant> getDescription() {
//        return new ColumnDescription<Plant>("Description", new ColumnLabelProvider() {
//            @Override
//            public String getText(Object element) {
//                Plant p = (Plant) element;
//                return p.getDescription();
//            }
//        }, new Comparator<Plant>() {
//
//            @Override
//            public int compare(Plant o1, Plant o2) {
//                return o1.getDescription().compareToIgnoreCase(o2.getDescription());
//            }
//
//        });
//    }
//
//    private static ColumnDescription<Plant> getNumberOfElements() {
//        return new ColumnDescription<Plant>("Place Name", new ColumnLabelProvider() {
//            @Override
//            public String getText(Object element) {
//                Plant p = (Plant) element;
//                return String.valueOf(p.getNumberOfElements());
//
//            }
//        }, new Comparator<Plant>() {
//
//            @Override
//            public int compare(Plant o1, Plant o2) {
//                return o1.getInspectionInterval() - o2.getInspectionInterval();
//            }
//
//        });
//    }
//
//    private static ColumnDescription<Plant> getPlace() {
//        return new ColumnDescription<Plant>("Place Name", new ColumnLabelProvider() {
//            @Override
//            public String getText(Object element) {
//                Plant p = (Plant) element;
//                return p.getPlace().get().toString();
//
//            }
//        }, new Comparator<Plant>() {
//
//            @Override
//            public int compare(Plant o1, Plant o2) {
//                return o1.getInspectionInterval() - o2.getInspectionInterval();
//            }
//
//        });
//    }
//
//    private static ColumnDescription<Plant> getPlace() {
//        return new ColumnDescription<Plant>("Place Name", new ColumnLabelProvider() {
//            @Override
//            public String getText(Object element) {
//                Plant p = (Plant) element;
//                return p.getPlace().get().toString();
//
//            }
//        }, new Comparator<Plant>() {
//
//            @Override
//            public int compare(Plant o1, Plant o2) {
//                return o1.getInspectionInterval() - o2.getInspectionInterval();
//            }
//
//        });
//    }
//
//}
