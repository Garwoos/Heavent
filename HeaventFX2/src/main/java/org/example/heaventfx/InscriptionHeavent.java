package org.example.heaventfx;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InscriptionHeavent {
    @JsonProperty("inscriptionId")
    private long inscriptionId;

    @JsonProperty("user")
    private String userEmail;

    @JsonProperty("event")
    private Event event;

    @JsonProperty("id")
    private long id;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public long getInscriptionId() { // Et cette méthode
        return inscriptionId;
    }

    public void setInscriptionId(long inscriptionId) { // Et cette méthode
        this.inscriptionId = inscriptionId;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}

