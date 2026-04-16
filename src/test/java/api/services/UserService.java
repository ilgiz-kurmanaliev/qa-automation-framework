package api.services;

import api.client.BaseApiClient;
import api.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class UserService extends BaseApiClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Response createUser(User user) {
        try {
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            attachRequestBody(requestBody);

            Response response = request()
                    .body(requestBody)
                    .post("/users");

            attachResponse(response);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize user request body", e);
        }
    }

    public Response getUser(int id) {
        Response response = request()
                .get("/users/" + id);

        attachResponse(response);
        return response;
    }
}