package com.example.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
public class Stock{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @Column(name = "market_capitalization", precision = 20, scale = 2, nullable = false)
    private BigDecimal marketCapitalization;
    @Column(name = "share_outstanding", precision = 20, scale = 2, nullable = false)
    private BigDecimal shareOutstanding;
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BigDecimal getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(BigDecimal marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public BigDecimal getShareOutstanding() {
        return shareOutstanding;
    }

    public void setShareOutstanding(BigDecimal shareOutstanding) {
        this.shareOutstanding = shareOutstanding;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
