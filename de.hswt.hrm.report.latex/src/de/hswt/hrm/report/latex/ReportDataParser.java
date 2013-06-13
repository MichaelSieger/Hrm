package de.hswt.hrm.report.latex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.inspection.model.Inspection;

public class ReportDataParser {

    private Properties prop = new Properties();
    private StringBuffer buffer = new StringBuffer();

    /*
     * Keywords used in file reportdata.properties
     */

    private final String CUSTOMER_NAME = ":customerName:";
    private final String CUSTOMER_STREET = ":customerStreet:";
    private final String CUSTOMER_CITY = ":customerCity:";

    private final String CONTRACTOR_NAME = ":contractorName:";
    private final String CONTRACTOR_STREET = ":contractorStreet:";
    private final String CONTRACTOR_CITY = ":contractorCity:";

    private final String CONTROLLER_NAME = ":controllerName:";
    private final String CONTROLLER_STREET = ":controllerStreet:";
    private final String CONTROLLER_CITY = ":controllerCity:";

    private final String OBJECT_TITLE = ":objectTitle:";
    private final String OBJECT_NAME = ":objectName:";
    private final String OBJECT_STREET = ":objectStreet:";
    private final String OBJECT_CITY = ":objectCity:";
    private final String OBJECT_IMAGE = ":objectImage:";

    private final String INSPECTION_DATE = ":inspectionDate:";
    private final String REPORT_DATE = ":reportDate:";

    private final String PLANT = ":plant:";
    private final String PLANT_PLACE = ":plantPlace:";
    private final String PLANT_SERVICE_AREA = ":plantServiceArea:";
    private final String PLANT_IMAGE = ":plantImage:";
    private final String PLANT_MANUFACTURER = ":plantManufacturer:";
    private final String PLANT_TYPE = ":plantType:";
    private final String PLANT_YEAR = ":plantYear:";
    private final String PLANT_AIR_POWER = ":plantAirPower:";
    private final String PLANT_ENGINE_POWER = ":plantEnginePower:";
    private final String PLANT_RPM = ":plantEngineRPM:";
    private final String PLANT_CURRENT = ":plantCurrent:";
    private final String PLANT_VOLTAGE = ":plantVoltage:";

    /*
     * @return String , ready to be written to file reportdata.tex
     */
    public String parse(String pathDir, Contact contactCustomer, Contact contactContractor,
            Contact conctactController, Plant plant, Place place, Inspection inspection)
            throws FileNotFoundException, IOException {

        prop.load(Files.newInputStream(Paths.get(pathDir, "templates", "reportdata.properties")));

        buffer.append(prop.getProperty("reportdata.customer.name").replace(CUSTOMER_NAME,
                contactCustomer.getName() + " " + contactCustomer.getName()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.customer.street").replace(CUSTOMER_STREET,
                contactCustomer.getStreet() + " " + contactCustomer.getStreetNo()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.customer.city").replace(CUSTOMER_CITY,
                contactCustomer.getPostCode() + " " + contactCustomer.getCity()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.contractor.name").replace(CONTRACTOR_NAME,
                contactContractor.getName() + " " + contactContractor.getName()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.contractor.street").replace(CONTRACTOR_STREET,
                contactContractor.getStreet() + " " + contactContractor.getStreetNo()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.contractor.city").replace(CONTRACTOR_CITY,
                contactContractor.getPostCode() + " " + contactContractor.getCity()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.controller.name").replace(CONTROLLER_NAME,
                conctactController.getName() + " " + conctactController.getName()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.controller.street").replace(CONTROLLER_STREET,
                conctactController.getStreet() + " " + conctactController.getName()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.controller.city").replace(CONTROLLER_CITY,
                conctactController.getPostCode() + " " + conctactController.getCity()));

        appendNewLine();

        // TODO
        buffer.append(prop.getProperty("reportdata.object.title").replace(OBJECT_TITLE,
                "report.getTitle()"));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.object.name").replace(OBJECT_NAME,
                place.getPlaceName()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.object.street").replace(OBJECT_STREET,
                place.getStreet() + " " + place.getStreetNo()));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.object.city").replace(OBJECT_CITY,
                place.getPostCode() + " " + place.getCity()));

        appendNewLine();
        // TODO
        buffer.append(prop.getProperty("reportdata.object.image").replace(OBJECT_IMAGE,
                "place.getImage"));

        appendNewLine();

        // TODO
        buffer.append(prop.getProperty("reportdata.date.inspection").replace(INSPECTION_DATE,
                "report.getInspectionDate()"));

        appendNewLine();

        buffer.append(prop.getProperty("reportdata.data.report").replace(REPORT_DATE,
                "report.getReportDate()"));

        appendNewLine();

        // TODO
        buffer.append(prop.getProperty("reportdata.plant").replace(PLANT, "plant.getPlantName()"));

        appendNewLine();

        // TODO Location
        buffer.append(prop.getProperty("reportdata.plant.place").replace(PLANT_PLACE,
                "plant.getLocation"));

        appendNewLine();

        // TODO
        buffer.append(prop.getProperty("reportdata.plant.servicearea").replace(PLANT_SERVICE_AREA,
                "plant.getArea"));

        appendNewLine();

        // TODO
        buffer.append(prop.getProperty("reportdata.plant.image").replace(PLANT_IMAGE,
                "Photo.getPlantImage"));

        appendNewLine();

        if (plant.getManufactor().isPresent()) {
            buffer.append(prop.getProperty("reportdata.plant.manufacturer").replace(
                    PLANT_MANUFACTURER, plant.getManufactor().get()));

            appendNewLine();
        }

        if (plant.getType().isPresent()) {
            buffer.append(prop.getProperty("reportdata.plant.type").replace(PLANT_TYPE,
                    plant.getType().get()));

            appendNewLine();
        }

        buffer.append(prop.getProperty("reportdata.plant.year").replace(PLANT_YEAR,
                plant.getConstructionYear().toString()));

        appendNewLine();

        if (plant.getAirPerformance().isPresent()) {
            buffer.append(prop.getProperty("reportdata.plant.power").replace(PLANT_AIR_POWER,
                    plant.getAirPerformance().get()));

            appendNewLine();
        }
        if (plant.getMotorPower().isPresent()) {

            buffer.append(prop.getProperty("reportdata.plant.engine.power").replace(
                    PLANT_ENGINE_POWER, plant.getMotorPower().get()));

            appendNewLine();
        }
        if (plant.getMotorRpm().isPresent()) {
            buffer.append(prop.getProperty("reportdata.plant.engine.rpm").replace(PLANT_RPM,
                    plant.getMotorRpm().get()));

            appendNewLine();
        }
        if (plant.getCurrent().isPresent()) {
            buffer.append(prop.getProperty("reportdata.plant.current").replace(PLANT_CURRENT,
                    plant.getCurrent().get()));

            appendNewLine();
        }
        if (plant.getVoltage().isPresent())
            buffer.append(prop.getProperty("reportdata.plant.voltage").replace(PLANT_VOLTAGE,
                    plant.getVoltage().get()));

        appendNewLine();

        return buffer.toString();
    }

    private void appendNewLine() {
        buffer.append("\n");
    }

}
