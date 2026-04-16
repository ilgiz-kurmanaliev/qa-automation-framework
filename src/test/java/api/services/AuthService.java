package api.services;

import api.client.BaseApiClient;
import io.restassured.response.Response;

public class AuthService extends BaseApiClient {

    public Response login(String email, String password) {
        String requestBody = """
                {
                  "email": "%s",
                  "password": "%s"
                }
                """.formatted(email, password);

        attachRequestBody(requestBody);

        Response response = request()
                .body(requestBody)
                .post("/login");

        attachResponse(response);
        return response;
    }
}