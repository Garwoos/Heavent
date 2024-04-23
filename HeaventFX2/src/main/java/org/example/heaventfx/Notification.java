package org.example.heaventfx;

import java.util.Date;

public class Notification {
    private int id;
    private Event event; // Ajoutez ce champ
    private String notificationType;
    private Date date;

    public Notification() {
    }

    public Notification(int id, Event event, String notificationType, Date date) {
        this.id = id;
        this.event = event;
        this.notificationType = notificationType;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
