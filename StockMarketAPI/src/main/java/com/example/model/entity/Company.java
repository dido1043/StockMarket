package com.example.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "company")
public class Company extends PanacheEntity {
    @Column(nullable = false, name = "company_name")
    @NotBlank(message = "Company name cannot be empty")
    public String name;

    @Column(nullable = false, name = "country", length = 2)
    @NotBlank(message = "Country cannot be empty")
    public String country;

    @Column(nullable = false, name = "symbol")
    @NotBlank(message = "Symbol cannot be empty")
    public String symbol;

    @Column(nullable = false, name = "website")
    @NotBlank(message = "Website cannot be empty")
    public String website;

    @Column(nullable = false, name = "email")
    @NotBlank(message = "Email cannot be empty")
    public String email;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public LocalDate createdAt;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

