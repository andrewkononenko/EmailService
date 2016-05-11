package inc.softserve.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Andrew on 10.05.2016.
 */
public class User {
    private String username;
    private String name;
    private String surname;

    public User(String username, String name, String surname) {
        this.username = username;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return surname != null ? surname.equals(user.surname) : user.surname == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
