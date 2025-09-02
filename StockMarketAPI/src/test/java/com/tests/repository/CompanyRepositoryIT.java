package com.tests.repository;

import com.example.model.entity.Company;
import com.example.repository.CompanyRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CompanyRepositoryIT {

    @Inject
    CompanyRepository repository;

    private Company amd;

    @BeforeEach
    void setUp() {
        amd = new Company();
        amd.setName("Advanced Micro Devices, Inc.");
        amd.setCountry("US");
        amd.setSymbol("AMD");
        amd.setWebsite("https://www.amd.com");
        amd.setEmail("investor.relations@amd.com");
    }

    @Test
    void saveAndRetrieveCompany() {
        repository.save(amd);
        assertThat(amd.getId()).isNotNull();

        Company found = repository.findById(amd.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Advanced Micro Devices, Inc.");
        assertThat(found.getCountry()).isEqualTo("US");
        assertThat(found.getSymbol()).isEqualTo("AMD");
        assertThat(found.getWebsite()).isEqualTo("https://www.amd.com");
        assertThat(found.getEmail()).isEqualTo("investor.relations@amd.com");
    }
}
