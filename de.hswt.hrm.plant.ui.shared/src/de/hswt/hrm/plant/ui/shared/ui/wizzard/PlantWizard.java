package de.hswt.hrm.plant.ui.shared.ui.wizzard;

import java.util.HashMap;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.service.PlantService;

public class PlantWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(PlantWizard.class);
    private static final I18n I18N = I18nFactory.getI18n(PlantWizard.class);

    @Inject
    private PlantService plantService;

    private PlantWizardPageOne first;
    private PlantWizardPageTwo second;
    private Optional<Plant> plant;

    public PlantWizard(IEclipseContext context, Optional<Plant> plant) {
        this.plant = plant;
        first = new PlantWizardPageOne("First page", plant);
        second = new PlantWizardPageTwo("Second page", plant);
        ContextInjectionFactory.inject(second, context);

        if (plant.isPresent()) {
            setWindowTitle(I18N.tr("Edit plant"));
        }
        else {
            setWindowTitle(I18N.tr("Add plant"));
        }
    }

    @Override
    public void addPages() {
        addPage(first);
        addPage(second);
    }

    @Override
    public boolean canFinish() {
        return first.isPageComplete() && second.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (plant.isPresent()) {
            return editExistingPlant();
        }
        else {
            return insertNewPlant();
        }
    }

    private boolean editExistingPlant() {
        Plant p = this.plant.get();
        try {
            // Update plant from DB
            p = plantService.findById(p.getId());
            // Set values to fields from WizardPage
            p = setValues(plant);
            // Update plant in DB
            plantService.update(p);
            plant = Optional.of(p);
        }
        catch (DatabaseException e) {
            LOG.error("An error occured", e);
        }
        return true;
    }

    private boolean insertNewPlant() {
        Plant p = setValues(Optional.<Plant> absent());
        try {
            plant = Optional.of(plantService.insert(p));
        }
        catch (SaveException e) {
            LOG.error("Could not save Element: " + plant + "into Database", e);
        }
        return true;
    }

    private Plant setValues(Optional<Plant> p) {
        HashMap<String, Text> mandatoryWidgets = first.getMandatoryWidgets();
        String description = mandatoryWidgets.get("description").getText();
        String area = mandatoryWidgets.get("area").getText();
        String location = mandatoryWidgets.get("location").getText();

        HashMap<String, Text> optionalWidgets = first.getOptionalWidgets();
        String manufactor = optionalWidgets.get("manufactor").getText();
        String type = optionalWidgets.get("type").getText();
        String airPerformance = optionalWidgets.get("airPerformance").getText();
        String motorPower = optionalWidgets.get("motorPower").getText();
        String ventilatorPerformance = optionalWidgets.get("ventilatorPerformance").getText();
        String motorRPM = optionalWidgets.get("motorRPM").getText();
        String current = optionalWidgets.get("current").getText();
        String voltage = optionalWidgets.get("voltage").getText();
        String note = optionalWidgets.get("note").getText();

        HashMap<String, Combo> optionalCombos = first.getOptionalCombos();
        Combo constYear = optionalCombos.get("constructionYear");
        String constructionYear = constYear.getItem(constYear.getSelectionIndex());

        Plant plant;
        if (p.isPresent()) {
            plant = p.get();
            plant.setDescription(description);
            plant.setArea(area);
            plant.setLocation(location);
        }
        else {
            plant = new Plant(description, area, location);
        }
        plant.setManufactor(manufactor);
        if (!constructionYear.equals("")) {
            plant.setConstructionYear(Integer.parseInt(constructionYear));
        }
        plant.setType(type);
        plant.setAirPerformance(airPerformance);
        plant.setMotorPower(motorPower);
        plant.setVentilatorPerformance(ventilatorPerformance);
        plant.setMotorRpm(motorRPM);
        plant.setCurrent(current);
        plant.setVoltage(voltage);
        plant.setNote(note);

        plant.setPlace(second.getPlace());

        return plant;
    }

    public Optional<Plant> getPlant() {
        return plant;
    }

}
