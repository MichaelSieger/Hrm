package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.contact.model.Contact;

import java.util.Date;

public class Inspection {
    private final int id;
    private Layout layout;
    private Plant plant;
    private Contact requester;
    private Contact contractor;
    private Contact checker;
    private Date jobDate;
    private Date reportDate;
    private Date nextDate;
    private int temperature;
    private int humidity;
    private String summary;
    private String titel;
    private String temperatureRating;
    private String temperatureQuantifier;
    private String humidityRating;
    private String humidityQuantifier;

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public Inspection(int id, Date jobDate, Date reportDate, Date nextDate, int temperature,
            int humidity, String summary, String titel, String temperatureRating,
            String temperatureQuantifier, String humidityRating, String humidityQuantifier) {

        this.id = id;

        setJobDate(jobDate);
        setReportDate(reportDate);
        setNextDate(nextDate);
        setTemperature(temperature);
        setHumidity(humidity);
        setSummary(summary);
        setTitel(titel);
        setTemperatureRating(temperatureRating);
        setHumidityRating(humidityRating);
        setTemperatureQuantifier(temperatureQuantifier);
        setHumidityQuantifier(humidityQuantifier);

    }

    public Inspection(Date jobDate, Date reportDate, Date nextDate, int temperature, int humidity,
            String summary, String titel, String temperatureRating, String temperatureQuantifier,
            String humidityRating, String humidityQuantifier) {
        this(-1, jobDate, reportDate, nextDate, temperature, humidity, summary, titel,
                temperatureRating, temperatureQuantifier, humidityRating, humidityQuantifier);
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

    public Contact getRequester() {
        return requester;
    }

    public void setRequester(Contact requester) {
        checkNotNull(requester);
        this.requester = requester;
    }

    public Contact getContractor() {
        return contractor;
    }

    public void setContractor(Contact contractor) {
        checkNotNull(contractor);
        this.contractor = contractor;
    }

    public Contact getChecker() {
        return checker;
    }

    public void setChecker(Contact checker) {
        checkNotNull(checker);
        this.checker = checker;
    }

    public Date getJobDate() {
        return jobDate;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        checkArgument(temperature > 0, INVALID_NUMBER, temperature);
        this.temperature = temperature;

    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        checkArgument(humidity > 0, INVALID_NUMBER, humidity);
        this.humidity = humidity;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        checkArgument(!isNullOrEmpty(summary), IS_MANDATORY);
        this.summary = summary;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        checkArgument(!isNullOrEmpty(titel), IS_MANDATORY);
        this.titel = titel;
    }

    public String getTemperatureRating() {
        return temperatureRating;
    }

    public void setTemperatureRating(String temperatureRating) {
        this.temperatureRating = temperatureRating;
    }

    public String getTemperatureQuantifier() {
        return temperatureQuantifier;
    }

    public void setTemperatureQuantifier(String temperatureQuantifier) {
        this.temperatureQuantifier = temperatureQuantifier;
    }

    public String getHumidityRating() {
        return humidityRating;
    }

    public void setHumidityRating(String humidityRating) {
        this.humidityRating = humidityRating;
    }

    public String getHumidityQuantifier() {
        return humidityQuantifier;
    }

    public void setHumidityQuantifier(String humidityQuantifier) {
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
        result = prime * result
                + ((humidityQuantifier == null) ? 0 : humidityQuantifier.hashCode());
        result = prime * result + ((humidityRating == null) ? 0 : humidityRating.hashCode());
        result = prime * result + id;
        result = prime * result + ((jobDate == null) ? 0 : jobDate.hashCode());
        result = prime * result + ((layout == null) ? 0 : layout.hashCode());
        result = prime * result + ((nextDate == null) ? 0 : nextDate.hashCode());
        result = prime * result + ((plant == null) ? 0 : plant.hashCode());
        result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
        result = prime * result + ((requester == null) ? 0 : requester.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + temperature;
        result = prime * result
                + ((temperatureQuantifier == null) ? 0 : temperatureQuantifier.hashCode());
        result = prime * result + ((temperatureRating == null) ? 0 : temperatureRating.hashCode());
        result = prime * result + ((titel == null) ? 0 : titel.hashCode());
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
        if (humidityQuantifier == null) {
            if (other.humidityQuantifier != null) {
                return false;
            }
        }
        else if (!humidityQuantifier.equals(other.humidityQuantifier)) {
            return false;
        }
        if (humidityRating == null) {
            if (other.humidityRating != null) {
                return false;
            }
        }
        else if (!humidityRating.equals(other.humidityRating)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (jobDate == null) {
            if (other.jobDate != null) {
                return false;
            }
        }
        else if (!jobDate.equals(other.jobDate)) {
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
        if (nextDate == null) {
            if (other.nextDate != null) {
                return false;
            }
        }
        else if (!nextDate.equals(other.nextDate)) {
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
        if (temperatureQuantifier == null) {
            if (other.temperatureQuantifier != null) {
                return false;
            }
        }
        else if (!temperatureQuantifier.equals(other.temperatureQuantifier)) {
            return false;
        }
        if (temperatureRating == null) {
            if (other.temperatureRating != null) {
                return false;
            }
        }
        else if (!temperatureRating.equals(other.temperatureRating)) {
            return false;
        }
        if (titel == null) {
            if (other.titel != null) {
                return false;
            }
        }
        else if (!titel.equals(other.titel)) {
            return false;
        }
        return true;
    }

}
