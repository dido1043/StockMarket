package com.example.repository;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.Company;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findById(Long id);
}
