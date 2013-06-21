package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.contact.model.Contact;

import java.util.GregorianCalendar;

import com.google.common.base.Optional;

public class Inspection {
    private final int id;
    private Layout layout;
    private Plant plant;
    private Contact requester;
    private Contact contractor;
    private Contact checker;
    private GregorianCalendar inspectionDate;
    private GregorianCalendar reportDate;
    private GregorianCalendar nextInspectionDate;
    private int temperature;
    private int humidity;
    private String summary;
    private String title;
    private int temperatureRating;
    private int temperatureQuantifier;
    private int humidityRating;
    private int humidityQuantifier;

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public Inspection(int id, GregorianCalendar reportDate, GregorianCalendar inspectionDate,
            GregorianCalendar nextInspection, String title, Layout layout, Plant plant) {
        this.id = id;
        setReportDate(reportDate);
        setInspectionDate(inspectionDate);
        setNextInspectionDate(nextInspection);
        setTitle(title);
        setPlant(plant);
        setLayout(layout);
    }

    public Inspection(GregorianCalendar reportDate, GregorianCalendar inspectionDate,
            GregorianCalendar nextInspection, String title, Layout layout, Plant plant) {
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

    public GregorianCalendar getJobDate() {
        return inspectionDate;
    }

    public void setInspectionDate(GregorianCalendar inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public GregorianCalendar getReportDate() {
        return reportDate;
    }

    public void setReportDate(GregorianCalendar reportDate) {
        this.reportDate = reportDate;
    }

    public GregorianCalendar getNextInspectionDate() {
        return nextInspectionDate;
    }

    public void setNextInspectionDate(GregorianCalendar nextInspectionDate) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((checker == null) ? 0 : checker.hashCode());
        result = prime * result + ((contractor == null) ? 0 : contractor.hashCode());
        result = prime * result + humidity;
        result = prime * result + humidityQuantifier;
        result = prime * result + humidityRating;
        result = prime * result + id;
        result = prime * result + ((inspectionDate == null) ? 0 : inspectionDate.hashCode());
        result = prime * result + ((layout == null) ? 0 : layout.hashCode());
        result = prime * result
                + ((nextInspectionDate == null) ? 0 : nextInspectionDate.hashCode());
        result = prime * result + ((plant == null) ? 0 : plant.hashCode());
        result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
        result = prime * result + ((requester == null) ? 0 : requester.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + temperature;
        result = prime * result + temperatureQuantifier;
        result = prime * result + temperatureRating;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Inspection other = (Inspection) obj;
        if (checker == null) {
            if (other.checker != null) {
                return false;
            }
        }
        else if (!checker.equals(other.checker)) {
            return false;
        }
        if (contractor == null) {
            if (other.contractor != null) {
                return false;
            }
        }
        else if (!contractor.equals(other.contractor)) {
            return false;
        }
        if (humidity != other.humidity) {
            return false;
        }
        if (humidityQuantifier != other.humidityQuantifier) {
            return false;
        }
        if (humidityRating != other.humidityRating) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (inspectionDate == null) {
            if (other.inspectionDate != null) {
                return false;
            }
        }
        else if (!inspectionDate.equals(other.inspectionDate)) {
            return false;
        }
        if (layout == null) {
            if (other.layout != null) {
                return false;
            }
        }
        else if (!layout.equals(other.layout)) {
            return false;
        }
        if (nextInspectionDate == null) {
            if (other.nextInspectionDate != null) {
                return false;
            }
        }
        else if (!nextInspectionDate.equals(other.nextInspectionDate)) {
            return false;
        }
        if (plant == null) {
            if (other.plant != null) {
                return false;
            }
        }
        else if (!plant.equals(other.plant)) {
            return false;
        }
        if (reportDate == null) {
            if (other.reportDate != null) {
                return false;
            }
        }
        else if (!reportDate.equals(other.reportDate)) {
            return false;
        }
        if (requester == null) {
            if (other.requester != null) {
                return false;
            }
        }
        else if (!requester.equals(other.requester)) {
            return false;
        }
        if (summary == null) {
            if (other.summary != null) {
                return false;
            }
        }
        else if (!summary.equals(other.summary)) {
            return false;
        }
        if (temperature != other.temperature) {
            return false;
        }
        if (temperatureQuantifier != other.temperatureQuantifier) {
            return false;
        }
        if (temperatureRating != other.temperatureRating) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        }
        else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }

}
