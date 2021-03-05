package com.openclassrooms.api.model;

import lombok.Data;

import javax.persistence.*;

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


}
