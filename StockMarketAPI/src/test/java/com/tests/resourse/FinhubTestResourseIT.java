package com.tests.resourse;

import com.tests.resourse.finnhub.FinnhubWireMockResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@QuarkusTestResource(FinnhubWireMockResource.class)
class FinhubTestResourseIT {

    @Test
    void get_api_stock_AMD_returns200_withExpectedBody() {
        given()
                .queryParam("symbol", "AMD")
                .when()
                .get("/api/stock")
                .then()
                .statusCode(200)
                .body("ticker", equalTo("AMD"))
                .body("name", equalTo("Advanced Micro Devices Inc"))
                .body("country", equalTo("US"))
                .body("marketCapitalization", notNullValue())
                .body("shareOutstanding", notNullValue());
    }

    @Test
    void get_api_stock_ERR_propagatesError() {
        given()
                .queryParam("symbol", "ERR")
                .when()
                .get("/api/stock")
                .then()
                .statusCode(anyOf(is(429), is(500)));
    }
}

