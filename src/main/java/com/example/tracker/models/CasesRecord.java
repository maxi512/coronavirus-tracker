package com.example.tracker.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data

@Entity
public class CasesRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String country;
    private String province;
    private Long confirmed;
    private Long newCases;
}
