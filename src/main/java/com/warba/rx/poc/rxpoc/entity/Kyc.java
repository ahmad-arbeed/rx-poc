package com.warba.rx.poc.rxpoc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "kyc")
public class Kyc {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "civil_id")
    private String civilId;

    @Column(name = "salary")
    private BigDecimal salary;
}
