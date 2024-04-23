package org.example.heaventfx;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.List;

public class Event {

    private double prix;

    @JsonProperty("User")
    private String mailUser;

    private String User;

    private int id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @JsonDeserialize(using = DateUtils.class)
    private Date date;
    private String location;
    private String description;
    private int places;

    public Event() {
    }


    public Event( String mailUser, String name, Date date, String location, String description, int places,double prix) {
        this.mailUser = mailUser;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.places = places;
        this.prix = prix;

    }

    // Getters et Setters

   public String getMailUser() {
        return mailUser;
    }


    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getId() {
        return this.id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
