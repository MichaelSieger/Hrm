package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.plant.model.Plant;

public class ComponentsParser {

    private final String COMMENT = "%";

    private final String FILE_DIR = "templates";
    private final String FILE_NAME_TABLE = "componentstable.tex";
    private final String FILE_NAME_ROW = "componentsrow.tex";

    private final String TABLE_COMPONENT_NAME = ":componentName:";
    private final String TABLE_ROW = ":row:";

    private final String PLANT_OVERVIEW = "Anlagenübersicht";

    private final String ROW_FEATURE = ":componentFeature:";
    private final String ROW_VALUE = ":componentValue:";

    private final String MANUFACTURER = "Hersteller";
    private final String TYPE = "Typ";
    private final String YEAR_OF_CONSTRUCTION = "Baujahr";
    private final String AIRPERFORMANCE = "Lufleistung";
    private final String MOTORPOWER = "Motorleistung";
    private final String MOTOR_RPM = "Motordrehzahl";
    private final String CURRENT = "Nennstrom";
    private final String VOLTAGE = "Spannung";
    private final String AREA = "Versorgter Bereich";

    private StringBuffer rowsOverview;
    private StringBuffer bufferTable;
    private BufferedReader reader;
    private String line;
    private String targetTable;

    private Inspection inspection;
    private Plant plant;
    private String path;
    private final Collection<Map<Attribute, String>> componentAttributes;

    private String targetRowOverview;
    private StringBuffer endTarget;
    private StringBuffer bufferRow;

    private String innerRow;
    private StringBuffer innerRowTarget;

    private String outerTable;
    private StringBuffer outerTableTarget;

    public ComponentsParser(String path, Inspection inspection,
            Collection<Map<Attribute, String>> componentAttributes) {

        this.path = path;
        this.inspection = inspection;
        this.plant = this.inspection.getPlant();
        this.componentAttributes = componentAttributes;
    }

    public String parse() throws IOException {
        Path pathTable = FileSystems.getDefault().getPath(this.path, FILE_DIR, FILE_NAME_TABLE);
        bufferTable.setLength(0);
        reader = null;
        reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        line = null;
        while ((line = reader.readLine()) != null) {
            // do not print comments
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                bufferTable.append(line);
                bufferTable.append("\n");
            }
        }
        this.targetTable = bufferTable.toString();

        Path pathRow = FileSystems.getDefault().getPath(this.path, FILE_DIR, FILE_NAME_ROW);
        bufferRow.setLength(0);
        reader = null;
        reader = Files.newBufferedReader(pathRow, Charset.defaultCharset());
        line = null;
        while ((line = reader.readLine()) != null) {
            // do not print comments
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                bufferRow.append(line);
                bufferRow.append("\n");
            }
        }
        this.targetRowOverview = bufferRow.toString();

        parseRowOverview();
        parseRow();

        targetTable = targetTable.replace(TABLE_COMPONENT_NAME, PLANT_OVERVIEW);
        targetTable = targetTable.replace(TABLE_ROW, rowsOverview.toString());
        endTarget.append(targetTable);
        endTarget.append("\n");
        endTarget.append(outerTableTarget.toString());

        return endTarget.toString();

    }

    private void parseRow() {
        for (Map<Attribute, String> attributes : componentAttributes) {
            // for each scheme component
            if (!attributes.isEmpty()) {
                this.outerTable = bufferTable.toString();
                outerTable = outerTable.replace(TABLE_COMPONENT_NAME, attributes.keySet().iterator()
                        .next().getComponent().getName());
            }

            for (Attribute attr : attributes.keySet()) {
                // for each attribute
                this.innerRow = bufferRow.toString();
                innerRow = innerRow.replace(ROW_FEATURE, attr.getName());
                innerRow = innerRow.replace(ROW_VALUE, attributes.get(attr));
                this.innerRowTarget.append(innerRow);
                this.innerRowTarget.append("\n");
            }
            outerTable = outerTable.replace(TABLE_ROW, innerRowTarget.toString());
            outerTableTarget.append(outerTable);
            outerTableTarget.append("\n");

        }

    }

    private void parseRowOverview() {

        if (plant.getManufactor().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, MANUFACTURER).replace(
                    ROW_VALUE, plant.getManufactor().orNull()));
            appendNewLineOverview();
        }
        if (plant.getType().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, TYPE).replace(
                    ROW_VALUE, plant.getType().orNull()));
            appendNewLineOverview();
        }
        if (plant.getConstructionYear().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, YEAR_OF_CONSTRUCTION)
                    .replace(ROW_VALUE, String.valueOf(plant.getConstructionYear().orNull())));
            appendNewLineOverview();
        }
        if (plant.getAirPerformance().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, AIRPERFORMANCE)
                    .replace(ROW_VALUE, plant.getAirPerformance().orNull()));
            appendNewLineOverview();
        }
        if (plant.getMotorPower().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, MOTORPOWER).replace(
                    ROW_VALUE, plant.getMotorPower().orNull()));
            appendNewLineOverview();
        }
        if (plant.getMotorRpm().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, MOTOR_RPM).replace(
                    ROW_VALUE, plant.getMotorRpm().orNull()));
            appendNewLineOverview();
        }
        if (plant.getCurrent().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, CURRENT).replace(
                    ROW_VALUE, plant.getCurrent().orNull()));
            appendNewLineOverview();
        }
        if (plant.getVoltage().isPresent()) {
            rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, VOLTAGE).replace(
                    ROW_VALUE, plant.getVoltage().orNull()));
            appendNewLineOverview();
        }
        rowsOverview.append(this.targetRowOverview.replace(ROW_FEATURE, AREA).replace(ROW_VALUE,
                plant.getArea()));
        appendNewLineOverview();

    }

    private void appendNewLineOverview() {
        rowsOverview.append("\n");
    }

}
