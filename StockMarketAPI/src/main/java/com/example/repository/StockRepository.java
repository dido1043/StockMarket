package com.example.repository;

import com.example.model.entity.Company;
import com.example.model.entity.Stock;
import org.springframework.data.jpa.repository.*;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findFirstByCompanyOrderByCreatedAtDesc(Company company);
}
