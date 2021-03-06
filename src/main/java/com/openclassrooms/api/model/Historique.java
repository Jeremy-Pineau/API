package com.openclassrooms.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "historique")
public class Historique {

    @Id
    private int id;

    @ManyToMany
    private int idPromo;

    @Column(name = "mail")
    @ManyToMany
    private String idUser;
}
