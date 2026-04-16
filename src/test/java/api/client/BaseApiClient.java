package api.client;

import framework.config.ConfigManager;
import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseApiClient {

    static {
        RestAssured.baseURI = ConfigManager.get("base.api.url");
    }

    protected RequestSpecification request() {
        String apiKey = System.getProperty("reqres.api.key");

        if (apiKey == null || apiKey.isBlank()) {
            apiKey = ConfigManager.get("reqres.api.key");
        }

        return RestAssured.given()
                .header("x-api-key", apiKey)
                .header("X-Reqres-Env", ConfigManager.get("reqres.env"))
                .contentType("application/json")
                .accept("application/json")
                .log().ifValidationFails(LogDetail.ALL);
    }

    @Attachment(value = "API Response", type = "application/json")
    protected String attachResponse(Response response) {
        return response.asPrettyString();
    }

    @Attachment(value = "API Request Body", type = "application/json")
    protected String attachRequestBody(String requestBody) {
        return requestBody;
    }
}