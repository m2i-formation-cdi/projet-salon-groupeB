package sp.fr.conference.model;

/**
 * Created by Formation on 23/01/2018.
 */

public class Conference {

    private String title;
    private ThemesConference theme;
    private String description;
    private User user;
    private String id;
    private String statut;

    public Conference(String title, ThemesConference theme, String description, User user, String id, String statut) {
        this.title = title;
        this.theme = theme;
        this.description = description;
        this.user = user;
        this.id = id;
        this.statut = statut;
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

    public User getUser() {
        return user;
    }

    public Conference setUser(User user) {
        this.user = user;
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
}
