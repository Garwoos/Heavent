package com.Heavent.Heavent.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import java.util.Date;

@Entity
@Table(name = "usersheavent")
@Getter
@Setter
@NoArgsConstructor
public  class usersHeavent {
    @Id
    private String email;
    @Column(length = 250)
    private String username;
    @Column(length = 250)
    private String password;
    @Column(name = "is_Admin", length = 250)
    private Boolean isAdmin;
}