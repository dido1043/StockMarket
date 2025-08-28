package com.example.model.dto;

import com.example.model.entity.Company;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockDto {
    private Long companyId;
    private BigDecimal marketCapitalization;
    private BigDecimal shareOutstanding;
    private LocalDateTime createdAt;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
