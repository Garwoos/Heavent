package com.Heavent.Heavent.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notificationsheavent")
@Getter
@Setter
@NoArgsConstructor

public class notificationsHeavent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private eventsHeavent event;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "date")
    private Date date;
}