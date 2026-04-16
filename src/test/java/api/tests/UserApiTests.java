package api.tests;

import api.assertions.ApiAssertions;
import api.models.User;
import api.services.UserService;
import io.qameta.allure.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic("API Tests")
@Feature("User API")
public class UserApiTests extends BaseApiTest {

    private final UserService userService = new UserService();

    @Test
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void createUserTest() {
        User user = new User("Ilgiz", "QA Engineer");

        Response response = userService.createUser(user);

        ApiAssertions.assertStatusCode(response, 201);
        ApiAssertions.assertResponseContains(response, "Ilgiz");
        ApiAssertions.assertResponseTimeLessThan(response, 5000);
    }

    @Test
    @Story("Get User")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void getUserTest() {
        Response response = userService.getUser(2);

        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertResponseContains(response, "email");
        ApiAssertions.assertResponseTimeLessThan(response, 5000);
    }
}