package ru.job4j;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiValidationTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String SWAGGER_PATH = "swagger.yaml";
    
    private static final OpenApiValidationFilter validationFilter = 
            new OpenApiValidationFilter(SWAGGER_PATH);

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testSummariseEndpoint() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("first", 5);
        requestBody.put("second", 3);

        RestAssured.given()
                .filter(validationFilter)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/calc/summarise")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetLogsEndpoint() {
        RestAssured.given()
                .filter(validationFilter)
                .when()
                .get("/calc/")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
} 