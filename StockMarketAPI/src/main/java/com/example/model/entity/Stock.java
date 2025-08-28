package com.example.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock  extends PanacheEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "companyId", nullable = false)
    public Company company;



}
