package com.example.model.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class CompanyDto {

    public String name;
    public String country;
    public String symbol;
    public String website;
    public String email;
    public LocalDate createdAt;


}
