package com.Heavent.Heavent.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @Column(length = 250)
    private String name; // Ensure this field exists in your database table
    private String description;
    @Column(length = 250)
    private Date date;
    @Column(length = 250)
    private String location;
    @Column(length = 250)
    private int places;
    @Column(length = 250)
    private int user;
    @Column(length = 250)
    private double prix;
}