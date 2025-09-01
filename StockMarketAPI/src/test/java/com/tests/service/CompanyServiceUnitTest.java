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
import static org.mockito.ArgumentMatchers.any;
/**
 * Unit test for CompanyServiceImpl.create/post flow based on the example response:
 * {
 *   "country": "US",
 *   "createdAt": "2025-09-01T19:24:35.4844771",
 *   "email": "investor.relations@amd.com",
 *   "name": "Advanced Micro Devices, Inc.",
 *   "symbol": "AMD",
 *   "website": "https://www.amd.com"
 * }
 */
@ExtendWith(MockitoExtension.class)
class CompanyServiceUnitTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyServiceImpl service;

    @Test
    void postCompany_returnsDtoLikeSample() {
        // Arrange: input DTO (what your controller would pass to the service)
        CompanyDto input = new CompanyDto();
        input.setName("Advanced Micro Devices, Inc.");
        input.setCountry("US");
        input.setSymbol("AMD");
        input.setWebsite("https://www.amd.com");
        input.setEmail("investor.relations@amd.com");

        // Stub repo.save(...) to simulate JPA persist filling createdAt on the entity.
        // If your repo.save returns the entity:
        when(companyRepository.save(any(Company.class))).thenAnswer(inv -> {
            Company e = inv.getArgument(0);
            // emulate DB-generated timestamps if your service doesn't set them before save
            if (e.getCreatedAt() == null) {
                e.setCreatedAt(LocalDateTime.now());
            }
            return e;
        });

        // If your repo.save is void, use this instead and remove the when(...) above:
        // doAnswer(inv -> {
        //     Company e = inv.getArgument(0);
        //     if (e.getCreatedAt() == null) e.setCreatedAt(LocalDateTime.now());
        //     return null;
        // }).when(companyRepository).save(any(Company.class));

        // Act
        CompanyDto result = service.postCompany(input);

        // Assert: fields match the sample payload
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Advanced Micro Devices, Inc.");
        assertThat(result.getCountry()).isEqualTo("US");
        assertThat(result.getSymbol()).isEqualTo("AMD");
        assertThat(result.getWebsite()).isEqualTo("https://www.amd.com");
        assertThat(result.getEmail()).isEqualTo("investor.relations@amd.com");

        // createdAt should be set to "now" (allow a few seconds tolerance)
        assertThat(result.getCreatedAt())
                .isNotNull()
                .isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));

        // Verify persistence happened
        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository, times(1)).save(captor.capture());
        verifyNoMoreInteractions(companyRepository);

        // Also assert what we actually persisted
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
}