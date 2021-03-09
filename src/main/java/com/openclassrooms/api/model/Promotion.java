package com.openclassrooms.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String codePromo;

    private String detail;
}
