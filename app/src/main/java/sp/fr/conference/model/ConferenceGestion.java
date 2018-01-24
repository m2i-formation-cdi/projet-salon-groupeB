package sp.fr.conference.model;

/**
 * Created by Formation on 24/01/2018.
 */

public class ConferenceGestion {

    private String startHour;
    private String endHour;
    private String Location;
    private String date;

    public ConferenceGestion(String startHour, String endHour, String location, String date) {
        this.startHour = startHour;
        this.endHour = endHour;
        Location = location;
        this.date = date;
    }

    public String getStartHour() {
        return startHour;
    }

    public ConferenceGestion setStartHour(String startHour) {
        this.startHour = startHour;
        return this;
    }

    public String getEndHour() {
        return endHour;
    }

    public ConferenceGestion setEndHour(String endHour) {
        this.endHour = endHour;
        return this;
    }

    public String getLocation() {
        return Location;
    }

    public ConferenceGestion setLocation(String location) {
        Location = location;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ConferenceGestion setDate(String date) {
        this.date = date;
        return this;
    }
}
