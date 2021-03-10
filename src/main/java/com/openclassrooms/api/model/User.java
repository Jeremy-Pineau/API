package com.openclassrooms.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mail;

    private String nom;

    private String prenom;

    private String adresse;

    private String mdp;

    @ManyToMany
    @JoinTable(name = "historique",
            joinColumns = {@JoinColumn(name = "idUser", referencedColumnName = "id", table = "users")},
            inverseJoinColumns = @JoinColumn(name = "idPromo", referencedColumnName = "id", table = "promotions"))
    private List<Promotion> promotions;
}
