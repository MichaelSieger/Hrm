package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.photo.model.Photo;

import java.util.Calendar;

import com.google.common.base.Optional;

public class Inspection {
    private final int id;
    private Layout layout;
    private Plant plant;
    private Contact requester;
    private Contact contractor;
    private Contact checker;
    private Calendar inspectionDate;
    private Calendar reportDate;
    private Calendar nextInspectionDate;
    private int temperature;
    private int humidity;
    private String summary;
    private String title;
    private int temperatureRating;
    private int temperatureQuantifier;
    private int humidityRating;
    private int humidityQuantifier;
    private Photo frontpicture;
    private Photo plantpicture;

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public Inspection(int id, Calendar reportDate, Calendar inspectionDate,
            Calendar nextInspection, String title, Layout layout, Plant plant) {
        this.id = id;
        setReportDate(reportDate);
        setInspectionDate(inspectionDate);
        setNextInspectionDate(nextInspection);
        setTitle(title);
        setPlant(plant);
        setLayout(layout);
    }

    public Inspection(Calendar reportDate, Calendar inspectionDate, Calendar nextInspection,
            String title, Layout layout, Plant plant) {
        this(-1, reportDate, inspectionDate, nextInspection, title, layout, plant);
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        checkNotNull(layout);
        this.layout = layout;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        checkNotNull(plant);
        this.plant = plant;
    }

    public Optional<Contact> getRequester() {
        return Optional.fromNullable(requester);
    }

    public void setRequester(Contact requester) {
        checkNotNull(requester);
        this.requester = requester;
    }

    public Optional<Contact> getContractor() {
        return Optional.fromNullable(contractor);
    }

    public void setContractor(Contact contractor) {
        checkNotNull(contractor);
        this.contractor = contractor;
    }

    public Optional<Contact> getChecker() {
        return Optional.fromNullable(checker);
    }

    public void setChecker(Contact checker) {
        checkNotNull(checker);
        this.checker = checker;
    }

    public Calendar getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Calendar inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Calendar getReportDate() {
        return reportDate;
    }

    public void setReportDate(Calendar reportDate) {
        this.reportDate = reportDate;
    }

    public Calendar getNextInspectionDate() {
        return nextInspectionDate;
    }

    public void setNextInspectionDate(Calendar nextInspectionDate) {
        this.nextInspectionDate = nextInspectionDate;
    }

    public Optional<Integer> getTemperature() {
        return Optional.fromNullable(temperature);
    }

    public void setTemperature(int temperature) {
        checkArgument(temperature > 0, INVALID_NUMBER, temperature);
        this.temperature = temperature;
    }

    public Optional<Integer> getHumidity() {
        return Optional.fromNullable(humidity);
    }

    public void setHumidity(int humidity) {
        checkArgument(humidity > 0, INVALID_NUMBER, humidity);
        this.humidity = humidity;
    }

    public Optional<String> getSummary() {
        return Optional.fromNullable(summary);
    }

    public void setSummary(String summary) {
        checkArgument(!isNullOrEmpty(summary), IS_MANDATORY);
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titel) {
        checkArgument(!isNullOrEmpty(titel), IS_MANDATORY);
        this.title = titel;
    }

    public Optional<Integer> getTemperatureRating() {
        return Optional.fromNullable(temperatureRating);
    }

    public void setTemperatureRating(int temperatureRating) {
        this.temperatureRating = temperatureRating;
    }

    public Optional<Integer> getTemperatureQuantifier() {
        return Optional.fromNullable(temperatureQuantifier);
    }

    public void setTemperatureQuantifier(int temperatureQuantifier) {
        this.temperatureQuantifier = temperatureQuantifier;
    }

    public Optional<Integer> getHumidityRating() {
        return Optional.fromNullable(humidityRating);
    }

    public void setHumidityRating(int humidityRating) {
        this.humidityRating = humidityRating;
    }

    public Optional<Integer> getHumidityQuantifier() {
        return Optional.fromNullable(humidityQuantifier);
    }

    public void setHumidityQuantifier(int humidityQuantifier) {
        this.humidityQuantifier = humidityQuantifier;
    }

    public int getId() {
        return id;
    }

    public Optional<Photo> getFrontpicture() {
        return Optional.fromNullable(frontpicture);
    }

    public void setFrontpicture(Photo frontpicture) {
        this.frontpicture = frontpicture;
    }

    public Optional<Photo> getPlantpicture() {
        return Optional.fromNullable(plantpicture);
    }

    public void setPlantpicture(Photo plantpicture) {
        this.plantpicture = plantpicture;
    }

}
