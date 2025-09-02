package com.tests.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.restassured.RestAssured;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@QuarkusTest
@EnabledIfEnvironmentVariable(named = "FINNHUB_TOKEN", matches = ".+")
class FinnhubResourceIT {

    @Test
    void getStockData_validSymbol_returnsOk() {
        given()
                .queryParam("symbol", "AAPL")
                .when()
                .get("/api/stock")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("ticker", anyOf(equalTo("AAPL"), startsWith("AAPL")));
    }

    @Test
    void getStockData_invalidSymbol_returnsEmptyOrError() {
        given()
                .queryParam("symbol", "INVALID")
                .when()
                .get("/api/stock")
                .then()
                .statusCode(anyOf(is(200), is(400), is(404)))
                .body("$", anyOf(
                        anEmptyMap(),
                        not(hasKey("ticker")),
                        hasKey("error")
                ));
    }
}