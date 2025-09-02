package com.tests.repository;

import com.example.model.entity.Company;
import com.example.model.entity.Stock;
import com.example.repository.StockRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@QuarkusTest
public class StockRepositoryIT {

    @Inject
    EntityManager em;

    @Inject
    StockRepository repository;

    private Company company;
    private Stock stock1;
    private Stock stock2;

    @BeforeEach
    @Transactional
    void setUp() {
        company = new Company();
        company.setName("Test Company");
        company.setSymbol("TEST");
        company.setCountry("US");
        company.setWebsite("https://test.com");
        company.setEmail("test@test.com");
        em.persist(company);

        stock1 = new Stock();
        stock1.setCompany(company);
        stock1.setMarketCapitalization(new BigDecimal("1000.00"));
        stock1.setShareOutstanding(new BigDecimal("100.00"));
        stock1.setCreatedAt(LocalDateTime.now().minusHours(1));
        em.persist(stock1);
        em.flush();

        stock2 = new Stock();
        stock2.setCompany(company);
        stock2.setMarketCapitalization(new BigDecimal("1100.00"));
        stock2.setShareOutstanding(new BigDecimal("110.00"));
        stock2.setCreatedAt(LocalDateTime.now());
        em.persist(stock2);
        em.flush();
    }

    @Test
    @Transactional
    void findFirstByCompanyOrderByCreatedAtDesc_returnsLatestStock() {
        Stock found = repository.findFirstByCompanyOrderByCreatedAtDesc(company);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(stock2.getId());
        assertThat(found.getMarketCapitalization())
                .isEqualByComparingTo(stock2.getMarketCapitalization());
        assertThat(found.getShareOutstanding())
                .isEqualByComparingTo(stock2.getShareOutstanding());
        assertThat(found.getCreatedAt())
                .isCloseTo(stock2.getCreatedAt(), within(1, ChronoUnit.MICROS));
    }
}