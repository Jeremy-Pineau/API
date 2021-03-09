package com.openclassrooms.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    private String mail;

    private String nom;

    private String prenom;

    private String adresse;

    private String mdp;

    @ManyToMany
    @JoinTable(name = "historique",
            joinColumns = { @JoinColumn(name = "mail") },
            inverseJoinColumns = { @JoinColumn(name = "idPromo") })
    private List<Promotion> historique;
}
