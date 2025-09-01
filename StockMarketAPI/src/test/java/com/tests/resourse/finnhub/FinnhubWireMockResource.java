package com.tests.resourse.finnhub;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class FinnhubWireMockResource implements QuarkusTestResourceLifecycleManager {
    private WireMockServer wm;

    @Override
    public Map<String, String> start() {
        wm = new WireMockServer(8089);
        wm.start();

        // Success: AMD
        wm.stubFor(get(urlPathEqualTo("/stock/profile2"))
                .withQueryParam("symbol", equalTo("AMD"))
                .withQueryParam("token", matching(".*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
          {
            "ticker": "AMD",
            "name": "Advanced Micro Devices Inc",
            "country": "US",
            "marketCapitalization": 263923.08192960377,
            "shareOutstanding": 1622.84
          }
        """)
                        .withStatus(200)));

// Error: ERR
        wm.stubFor(get(urlPathEqualTo("/stock/profile2"))
                .withQueryParam("symbol", equalTo("ERR"))
                .withQueryParam("token", matching(".*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"Too many requests\"}")
                        .withStatus(429)));


        return Map.of(
                "quarkus.rest-client.finnhub.url", "http://localhost:8089",
                "quarkus.rest-client.\"com.example.rest_client.interfaces.FinnhubService\".url", "http://localhost:8089",
                "finhub.api.key", "dummy"
        );
    }

    @Override
    public void stop() {
        if (wm != null) wm.stop();
    }
}
