package com.tests.repository;

import com.example.model.entity.Company;
import com.example.repository.CompanyRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest

public class CompanyRepositoryIT {

    @Inject
    private CompanyRepository companyRepository;
    
    @Test
    void saveToDB(){
        Company company = new Company();
        company.setName("Advanced Micro Devices, Inc.");
        company.setCountry("US");
        company.setSymbol("AMD");
        company.setWebsite("https://www.amd.com");
        company.setEmail("investor.relations@amd.com");
        Company saved = companyRepository.save(company);

        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getName()).isEqualTo(company.getName());
        Assertions.assertThat(saved.getCountry()).isEqualTo(company.getCountry());
        Assertions.assertThat(saved.getSymbol()).isEqualTo(company.getSymbol());
        Assertions.assertThat(saved.getWebsite()).isEqualTo(company.getWebsite());
        Assertions.assertThat(saved.getEmail()).isEqualTo(company.getEmail());

    }



}
