package com.tests.service;


import com.example.model.dto.StockDto;
import com.example.model.entity.Company;
import com.example.model.entity.Stock;
import com.example.repository.StockRepository;
import com.example.rest_client.FinnhubResource;
import com.example.service.implementations.StockServiceImpl;
import com.example.service.interfaces.CompanyService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Verifies StockServiceImpl builds a DTO like:
 * {
 *   "companyId": 9,
 *   "country": "US",
 *   "createdAt": "...",
 *   "email": "investor.relations@amd.com",
 *   "marketCapitalization": 263923.08192960377,
 *   "name": "Advanced Micro Devices, Inc.",
 *   "shareOutstanding": 1622.84,
 *   "symbol": "AMD",
 *   "website": "https://www.amd.com"
 * }
 */
@ExtendWith(MockitoExtension.class)
class StockServiceImplUnitTest {

    @Mock CompanyService companyService;
    @Mock FinnhubResource finnhubResource;
    @Mock StockRepository stockRepository;

    @InjectMocks StockServiceImpl service;

    private Company amd;

    @BeforeEach
    void setUp() {
        amd = new Company();
        amd.setId(9L);
        amd.setName("Advanced Micro Devices, Inc.");
        amd.setCountry("US");
        amd.setSymbol("AMD");
        amd.setWebsite("https://www.amd.com");
        amd.setEmail("investor.relations@amd.com");
    }

    @Test
    void getStock_buildsDto_likeSample_whenNoExisting() {
        when(companyService.getCompany(9L)).thenReturn(amd);
        when(stockRepository.findFirstByCompanyOrderByCreatedAtDesc(amd)).thenReturn(null);

        Map<String, Object> finnhubPayload = Map.of(
                "marketCapitalization", "263923.08192960377",
                "shareOutstanding",     "1622.84"
        );
        Response finnhubResp = mock(Response.class);
        when(finnhubResp.readEntity(Object.class)).thenReturn(finnhubPayload);
        when(finnhubResource.getStockData("AMD")).thenReturn(finnhubResp);

        ArgumentCaptor<Stock> saved = ArgumentCaptor.forClass(Stock.class);
        doAnswer(inv -> null).when(stockRepository).save(saved.capture());

        StockDto out = service.getStock(9L);

        assertThat(out.getCompanyId()).isEqualTo(9L);
        assertThat(out.getCountry()).isEqualTo("US");
        assertThat(out.getEmail()).isEqualTo("investor.relations@amd.com");
        assertThat(out.getName()).isEqualTo("Advanced Micro Devices, Inc.");
        assertThat(out.getSymbol()).isEqualTo("AMD");
        assertThat(out.getWebsite()).isEqualTo("https://www.amd.com");
        assertThat(out.getMarketCapitalization())
                .isEqualByComparingTo(new BigDecimal("263923.08192960377"));
        assertThat(out.getShareOutstanding())
                .isEqualByComparingTo(new BigDecimal("1622.84"));
        assertThat(out.getCreatedAt())
                .isNotNull()
                .isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));

        verify(finnhubResource).getStockData("AMD");
        verify(stockRepository).save(any(Stock.class));
        Stock persisted = saved.getValue();
        assertThat(persisted.getCompany()).isSameAs(amd);
        assertThat(persisted.getMarketCapitalization())
                .isEqualByComparingTo("263923.08192960377");
        assertThat(persisted.getShareOutstanding())
                .isEqualByComparingTo("1622.84");
        assertThat(persisted.getCreatedAt())
                .isNotNull()
                .isCloseTo(out.getCreatedAt(), within(1, ChronoUnit.SECONDS));
    }
}