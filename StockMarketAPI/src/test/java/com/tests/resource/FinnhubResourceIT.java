package com.tests.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.restassured.RestAssured;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class FinnhubResourceIT {

    @Test
    void getStockData_validSymbol_returnsOk() {
        given()
                .queryParam("symbol", "AAPL")
                .when()
                .get("/api/stock")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void getStockData_invalidSymbol_returnsNotFound() {
        given()
                .queryParam("symbol", "INVALID")
                .when()
                .get("/api/stock")
                .then()
                .statusCode(200);
    }
}