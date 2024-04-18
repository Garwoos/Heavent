package com.Heavent.Heavent.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "eventsheavent")
@Getter
@Setter
@NoArgsConstructor
public  class eventsHeavent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;
    @JsonProperty("name")
    @Column(length = 250)
    private String name; // Ensure this field exists in your database table

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    @Column(length = 250)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date date;

    @JsonProperty("location")
    @Column(length = 250)
    private String location;

    @JsonProperty("places")
    @Column(length = 250)
    private int places;

    @JsonProperty("User")
    @Column(length = 250)
    @JoinColumn(name = "user", nullable = false)
    private String user;

    @JsonProperty("prix")
    @Column(length = 250)
    private double prix;
}