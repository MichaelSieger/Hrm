package de.hswt.hrm.place.ui.part;

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
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.ui.wizard.PlaceWizard;

public final class PlacePartUtil {

    public PlacePartUtil() {

    }

    public static Optional<Place> showWizard(IEclipseContext context, Shell shell,
            Optional<Place> place) {

        // TODO: partly move to extra plugin

        // Create wizard with injection support
        PlaceWizard wizard = new PlaceWizard(place);
        ContextInjectionFactory.inject(wizard, context);

        // Show wizard
        WizardDialog wd = WizardCreator.createWizardDialog(shell, wizard);
        wd.open();
        return wizard.getPlace();
    }

    public static List<ColumnDescription<Place>> getColumns() {
        List<ColumnDescription<Place>> columns = new ArrayList<>();
        columns.add(getPlaceColumn());
        columns.add(getPostCodeColumn());
        columns.add(getCityColumn());
        columns.add(getStreetColumn());
        columns.add(getStreetNoColumn());

        return columns;
    }

    private static ColumnDescription<Place> getPlaceColumn() {
        return new ColumnDescription<Place>("Place Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Place p = (Place) element;
                return p.getPlaceName();
            }
        }, new Comparator<Place>() {

            @Override
            public int compare(Place o1, Place o2) {
                return o1.getPlaceName().compareTo(o2.getPlaceName());
            }

        });
    }

    private static ColumnDescription<Place> getPostCodeColumn() {
        return new ColumnDescription<>("Postcode", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Place p = (Place) element;
                return p.getPostCode();
            }
        }, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return o1.getPostCode().compareTo(o2.getPostCode());
            }
        });
    }

    private static ColumnDescription<Place> getCityColumn() {
        return new ColumnDescription<>("City", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Place p = (Place) element;
                return p.getCity();
            }
        }, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return o1.getCity().compareTo(o2.getCity());
            }
        });
    }

    private static ColumnDescription<Place> getStreetColumn() {
        return new ColumnDescription<>("Street", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Place p = (Place) element;
                return p.getStreet();
            }
        }, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return o1.getStreet().compareTo(o2.getStreet());
            }
        });
    }

    private static ColumnDescription<Place> getStreetNoColumn() {
        return new ColumnDescription<>("Street Number", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Place p = (Place) element;
                return p.getStreetNo();
            }
        }, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return o1.getStreetNo().compareTo(o2.getStreetNo());
            }
        });
    }

}
