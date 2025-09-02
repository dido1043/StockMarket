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

        Assertions.assertThat(saved.getId() != null);
        Assertions.assertThat(saved.getName()).isEqualTo(company.getName());
        Assertions.assertThat(saved.getCountry()).isEqualTo(company.getCountry());
        Assertions.assertThat(saved.getSymbol()).isEqualTo(company.getSymbol());
        Assertions.assertThat(saved.getWebsite()).isEqualTo(company.getWebsite());
        Assertions.assertThat(saved.getEmail()).isEqualTo(company.getEmail());

    }

    @Test
    void findById_test() {
        Company company = new Company();
        company.setName("Advanced Micro Devices, Inc.");
        company.setCountry("US");
        company.setSymbol("AMD");
        company.setWebsite("https://www.amd.com");
        company.setEmail("investor.relations@amd.com");
        Company saved = companyRepository.save(company);


        Company fetched = companyRepository.findById(saved.getId()).orElse(null);

        Assertions.assertThat(fetched).isNotNull();
        Assertions.assertThat(fetched.getId()).isEqualTo(saved.getId());
        Assertions.assertThat(fetched.getName()).isEqualTo(saved.getName());
        Assertions.assertThat(fetched.getCountry()).isEqualTo(saved.getCountry());
        Assertions.assertThat(fetched.getSymbol()).isEqualTo(saved.getSymbol());
        Assertions.assertThat(fetched.getWebsite()).isEqualTo(saved.getWebsite());
        Assertions.assertThat(fetched.getEmail()).isEqualTo(saved.getEmail());
    }

}
