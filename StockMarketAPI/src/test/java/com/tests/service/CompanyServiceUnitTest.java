package com.tests.service;

import com.example.model.entity.Company;
import com.example.repository.CompanyRepository;
import com.example.service.implementations.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import com.example.model.dto.CompanyDto;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static org.assertj.core.api.Assertions.within;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CompanyServiceUnitTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyServiceImpl service;


    @Test
    void postCompany_returnsDtoLikeSample() {
        CompanyDto input = new CompanyDto();
        input.setName("Advanced Micro Devices, Inc.");
        input.setCountry("US");
        input.setSymbol("AMD");
        input.setWebsite("https://www.amd.com");
        input.setEmail("investor.relations@amd.com");

        when(companyRepository.save(any(Company.class))).thenAnswer(inv -> {
            Company e = inv.getArgument(0);
            if (e.getCreatedAt() == null) {
                e.setCreatedAt(LocalDateTime.now());
            }
            return e;
        });


        CompanyDto result = service.postCompany(input);


        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Advanced Micro Devices, Inc.");
        assertThat(result.getCountry()).isEqualTo("US");
        assertThat(result.getSymbol()).isEqualTo("AMD");
        assertThat(result.getWebsite()).isEqualTo("https://www.amd.com");
        assertThat(result.getEmail()).isEqualTo("investor.relations@amd.com");


        assertThat(result.getCreatedAt())
                .isNotNull()
                .isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));


        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository, times(1)).save(captor.capture());
        verifyNoMoreInteractions(companyRepository);


        Company persisted = captor.getValue();
        assertThat(persisted.getName()).isEqualTo("Advanced Micro Devices, Inc.");
        assertThat(persisted.getCountry()).isEqualTo("US");
        assertThat(persisted.getSymbol()).isEqualTo("AMD");
        assertThat(persisted.getWebsite()).isEqualTo("https://www.amd.com");
        assertThat(persisted.getEmail()).isEqualTo("investor.relations@amd.com");
        assertThat(persisted.getCreatedAt())
                .isNotNull()
                .isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));
    }
    @Test
    void getAllCompanies_returnsDtoLikeSample() {
        Company company = new Company();
       // company.setId(9L);
        company.setName("Advanced Micro Devices, Inc.");
        company.setCountry("US");
        company.setSymbol("AMD");
        company.setWebsite("https://www.amd.com");
        company.setEmail("investor.relations@amd.com");
        company.setCreatedAt(LocalDateTime.now());

        when(companyRepository.findAll()).thenReturn(java.util.List.of(company));

        List<CompanyDto> result = service.getAllCompanies();

        assertThat(result).hasSize(1);
        CompanyDto dto = result.get(0);
        //assertThat(dto.getId()).isEqualTo(9L);
        assertThat(dto.getName()).isEqualTo("Advanced Micro Devices, Inc.");
        assertThat(dto.getCountry()).isEqualTo("US");
        assertThat(dto.getSymbol()).isEqualTo("AMD");
        assertThat(dto.getWebsite()).isEqualTo("https://www.amd.com");
        assertThat(dto.getEmail()).isEqualTo("investor.relations@amd.com");
        assertThat(dto.getCreatedAt())
                .isNotNull()
                .isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));

        verify(companyRepository).findAll();
        verifyNoMoreInteractions(companyRepository);
    }
}