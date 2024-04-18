package com.Heavent.Heavent.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "inscriptionheavent")
@Getter
@Setter
@NoArgsConstructor
public class inscriptionHeavent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inscriptionId;
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    @JsonIgnore
    private usersHeavent user;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private eventsHeavent event;

    public Long getId() {
        return this.inscriptionId;
    }
}