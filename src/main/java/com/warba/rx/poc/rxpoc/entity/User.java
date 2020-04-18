package com.warba.rx.poc.rxpoc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue
    @Column(name = "cif")
    private Long cif;

    @Column(name = "civil_id")
    private String civilId;

    @Column(name = "name")
    private String name;
}

