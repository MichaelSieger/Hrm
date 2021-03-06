package de.hswt.hrm.plant.ui.shared.ui.wizzard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.ui.part.PlaceComposite;
import de.hswt.hrm.plant.model.Plant;

public class PlantWizardPageTwo extends WizardPage {

    private final static I18n I18N = I18nFactory.getI18n(PlantWizardPageTwo.class);

    private Place selectedPlace;
    private Optional<Plant> plant;

    private PlaceComposite placeComposite;

    @Inject
    private IEclipseContext context;

    private FormToolkit toolkit;

    public PlantWizardPageTwo(String title, Optional<Plant> plant) {
        super(title);
        this.plant = plant;
        if (plant.isPresent()) {
            if (plant.get().getPlace().isPresent()) {
                selectedPlace = plant.get().getPlace().get();
            }
        }
        setTitle(I18N.tr("Plant Wizard"));
        setDescription(createDescription());
    }

    private String createDescription() {
        if (plant.isPresent()) {
            return I18N.tr("Select place for existing plant.");
        }
        return I18N.tr("Select place for new plant.");
    }

    @Override
    public void createControl(Composite parent) {
        parent.setBackgroundMode(SWT.INHERIT_FORCE);

        toolkit = FormUtil.createToolkit();

        Section headerSection = toolkit.createSection(parent, Section.TITLE_BAR);
        toolkit.paintBordersFor(headerSection);
        headerSection.setBackgroundMode(SWT.INHERIT_FORCE);
        headerSection.setExpanded(true);
        FormUtil.initSectionColors(headerSection);

        Composite composite = toolkit.createComposite(headerSection);

        composite.setLayout(LayoutUtil.createGridLayout(2, false, true, true));
        toolkit.paintBordersFor(composite);

        placeComposite = new PlaceComposite(composite, SWT.NONE);
        ContextInjectionFactory.inject(placeComposite, context);
        placeComposite.setAllowEditing(false);
        placeComposite.setLayoutData(LayoutUtil.createFillData());
        placeComposite.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {

                selectedPlace = (Place) ((IStructuredSelection) event.getSelection())
                        .getFirstElement();
                getWizard().getContainer().updateButtons();
            }
        });

        Button addButton = toolkit.createButton(composite, I18N.tr("Add"), SWT.NONE);
        addButton.setLayoutData(LayoutUtil.createRightGridData());
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                placeComposite.addPlace();
            }
        });

        headerSection.setClient(composite);
        setControl(headerSection);

        if (selectedPlace != null) {
            placeComposite.setSelection(selectedPlace);
        }
    }

    public Place getPlace() {
        return selectedPlace;
    }

    @Override
    public boolean isPageComplete() {

        return selectedPlace != null;
    }

    @Override
    public void dispose() {
        toolkit.dispose();
        super.dispose();
    }
}
