package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.common.observer.Observable;
import de.hswt.hrm.common.observer.Observer;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.photo.model.Photo;

import java.util.Calendar;

import com.google.common.base.Optional;

public class Inspection {
    private final int id;
    private Layout layout;
    private final Observable<Plant> plant = new Observable<>();
    private Contact requester;
    private Contact contractor;
    private Contact checker;
    private Calendar inspectionDate;
    private Calendar reportDate;
    private Calendar nextInspectionDate;
    private float temperature;
    private float humidity;
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
        return plant.get();
    }

    public void setPlant(Plant plant) {
        checkNotNull(plant);
        this.plant.set(plant);
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

    public Optional<Float> getTemperature() {
        return Optional.fromNullable(temperature);
    }

    public void setTemperature(float temperature) {
        checkArgument(temperature > 0, INVALID_NUMBER, temperature);
        this.temperature = temperature;
    }

    public Optional<Float> getHumidity() {
        return Optional.fromNullable(humidity);
    }

    public void setHumidity(float humidity) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((checker == null) ? 0 : checker.hashCode());
        result = prime * result + ((contractor == null) ? 0 : contractor.hashCode());
        result = prime * result + ((frontpicture == null) ? 0 : frontpicture.hashCode());
        result = prime * result + Float.floatToIntBits(humidity);
        result = prime * result + humidityQuantifier;
        result = prime * result + humidityRating;
        result = prime * result + id;
        result = prime * result + ((inspectionDate == null) ? 0 : inspectionDate.hashCode());
        result = prime * result + ((layout == null) ? 0 : layout.hashCode());
        result = prime * result
                + ((nextInspectionDate == null) ? 0 : nextInspectionDate.hashCode());
        result = prime * result + ((plant == null) ? 0 : plant.hashCode());
        result = prime * result + ((plantpicture == null) ? 0 : plantpicture.hashCode());
        result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
        result = prime * result + ((requester == null) ? 0 : requester.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + Float.floatToIntBits(temperature);
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
        if (frontpicture == null) {
            if (other.frontpicture != null) {
                return false;
            }
        }
        else if (!frontpicture.equals(other.frontpicture)) {
            return false;
        }
        if (Float.floatToIntBits(humidity) != Float.floatToIntBits(other.humidity)) {
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
        if (plantpicture == null) {
            if (other.plantpicture != null) {
                return false;
            }
        }
        else if (!plantpicture.equals(other.plantpicture)) {
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
        if (Float.floatToIntBits(temperature) != Float.floatToIntBits(other.temperature)) {
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
    
    public void addPlantObserver(Observer<Plant> o){
        plant.addObserver(o);
    }

}
