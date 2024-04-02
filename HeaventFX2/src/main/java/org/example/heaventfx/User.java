package org.example.heaventfx;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {

    private String email;
    private String username;

    private String password;

    @JsonProperty("isAdmin")
    private boolean isAdmin;

    private List<Event> participatingEvents;

    public User() {

    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Event> getParticipatingEvents() {
        return participatingEvents;
    }
}