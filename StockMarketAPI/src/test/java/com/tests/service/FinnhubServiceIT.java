package com.tests.service;

import com.example.rest_client.interfaces.FinnhubService;
//import com.example.test.FinnhubWireMockResource;
import com.tests.resourse.finnhub.FinnhubWireMockResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(FinnhubWireMockResource.class)
class FinnhubServiceIT {

    @Inject @RestClient
    FinnhubService finnhubService;

    @Test
    void getStockProfile_AMD_returnsWireMockStub() {
        Object resp = finnhubService.getStockProfile("AMD", "dummy");
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) resp;

        assertThat(body.get("ticker")).isEqualTo("AMD");
        assertThat(body.get("name")).isEqualTo("Advanced Micro Devices Inc");
        assertThat(body.get("country")).isEqualTo("US");
        assertThat(new BigDecimal(body.get("marketCapitalization").toString()))
                .isEqualByComparingTo("263923.08192960377");
        assertThat(new BigDecimal(body.get("shareOutstanding").toString()))
                .isEqualByComparingTo("1622.84");
    }

    @Test
    void getStockProfile_ERR_returns429_orThrows() {
        try {
            finnhubService.getStockProfile("ERR", "dummy");
            fail("Expected MP REST client to throw for non-2xx, or return a type that signals error");
        } catch (Exception e) {
            // Resteasy Reactive MP client usually throws WebApplicationException/ClientWebApplicationException
            assertThat(e.getClass().getName())
                    .contains("WebApplicationException");
        }
    }
}