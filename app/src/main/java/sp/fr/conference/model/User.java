package sp.fr.conference.model;

/**
 * Created by Formation on 23/01/2018.
 */

public class User {

    private String name;
    private String mail;
    private String id;

    public User() {
    }

    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }


    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }
}
