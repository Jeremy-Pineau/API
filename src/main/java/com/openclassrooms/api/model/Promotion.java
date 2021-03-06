package com.openclassrooms.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    private int id;

    private String codePromo;

    private String detail;
}
