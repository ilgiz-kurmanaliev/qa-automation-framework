package api.tests;

import api.assertions.ApiAssertions;
import api.services.AuthService;
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
@Feature("Login API")
public class LoginApiTests extends BaseApiTest {

    private final AuthService authService = new AuthService();

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ilgiz")
    public void validLoginTest() {
        Response response = authService.login(
                "eve.holt@reqres.in",
                "cityslicka"
        );

        ApiAssertions.assertStatusCode(response, 200);
        ApiAssertions.assertResponseContains(response, "token");
        ApiAssertions.assertResponseTimeLessThan(response, 5000);
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Ilgiz")
    public void invalidLoginTest() {
        Response response = authService.login(
                "peter@klaven",
                ""
        );

        ApiAssertions.assertStatusCode(response, 400);
        ApiAssertions.assertResponseContains(response, "error");
        ApiAssertions.assertResponseTimeLessThan(response, 5000);
    }
}