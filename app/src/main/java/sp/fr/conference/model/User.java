package sp.fr.conference.model;

/**
 * Created by Formation on 23/01/2018.
 */

class User {

    private String firstname;
    private String name;
    private String mail;

    public User() {
    }

    public User(String firstname, String name, String mail) {
        this.firstname = firstname;
        this.name = name;
        this.mail = mail;
    }

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
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
}



