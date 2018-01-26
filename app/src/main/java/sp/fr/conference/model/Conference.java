package sp.fr.conference.model;

/**
 * Created by Formation on 23/01/2018.
 */

public class Conference {

    private String title;
    private ThemesConference theme;
    private String description;
    private User attendents;
    private String id;
    private String statut;
    private String day;
    private String startHour;
    private String endHour;
    private String location;
    private String latitude;
    private String longitude;
    private User speaker;
    private Comments comments;

    public Conference(String title, ThemesConference theme, String description, User attendents, String id, String statut, String day, String startHour, String endHour, String location, String latitude, String longitude, User speaker, Comments comments) {
        this.title = title;
        this.theme = theme;
        this.description = description;
        this.attendents = attendents;
        this.id = id;
        this.statut = statut;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speaker = speaker;
        this.comments = comments;
    }

    public Conference() {
    }

    public String getTitle() {
        return title;
    }

    public Conference setTitle(String title) {
        this.title = title;
        return this;
    }

    public ThemesConference getTheme() {
        return theme;
    }

    public Conference setTheme(ThemesConference theme) {
        this.theme = theme;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Conference setDescription(String description) {
        this.description = description;
        return this;
    }

    public User getAttendents() {
        return attendents;
    }

    public Conference setAttendents(User attendents) {
        this.attendents = attendents;
        return this;
    }

    public String getId() {
        return id;
    }

    public Conference setId(String id) {
        this.id = id;
        return this;
    }

    public String getStatut() {
        return statut;
    }

    public Conference setStatut(String statut) {
        this.statut = statut;
        return this;
    }

    public String getDay() {
        return day;
    }

    public Conference setDay(String day) {
        this.day = day;
        return this;
    }

    public String getStartHour() {
        return startHour;
    }

    public Conference setStartHour(String startHour) {
        this.startHour = startHour;
        return this;
    }

    public String getEndHour() {
        return endHour;
    }

    public Conference setEndHour(String endHour) {
        this.endHour = endHour;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Conference setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public Conference setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public Conference setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public User getSpeaker() {
        return speaker;
    }

    public Conference setSpeaker(User speaker) {
        this.speaker = speaker;
        return this;
    }

    public Comments getComments() {
        return comments;
    }

    public Conference setComments(Comments comments) {
        this.comments = comments;
        return this;
    }
}