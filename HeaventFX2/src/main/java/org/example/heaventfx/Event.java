package org.example.heaventfx;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event {
    private String id;
    private String nomCreateur;
    private String nomProjet;

    private List<User> participants;
    private String dateEvent;
    private String typeEvent;
    private String descriptionEvent;
    private int siegeDispo;

    public Event() {
        this.participants = new ArrayList<>();
    }
    public Event( String nomCreateur, String nomProjet, String dateEvent, String typeEvent, String descriptionEvent, int siegeDispo) {
        this.id = UUID.randomUUID().toString();
        this.nomCreateur = nomCreateur;
        this.nomProjet = nomProjet;
        this.dateEvent = dateEvent;
        this.typeEvent = typeEvent;
        this.descriptionEvent = descriptionEvent;
        this.siegeDispo = siegeDispo;
        this.participants = new ArrayList<>();
    }

    // Getters et Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   public String getNomCreateur() {
        return nomCreateur;
    }

    public void setNomCreateur(String nomCreateur) {
        this.nomCreateur = nomCreateur;
    }
    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public int getSiegeDispo() {
        return siegeDispo;
    }

    public void setSiegeDispo(int siegeDispo) {
        this.siegeDispo = siegeDispo;
    }

    @Override
    public String toString() {
        return this.getNomProjet();
    }

}
