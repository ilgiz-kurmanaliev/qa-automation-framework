package api.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

public class ApiAssertions {

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Status code mismatch. Actual response body:\n" + response.asPrettyString()
        );
    }

    public static void assertResponseContains(Response response, String expectedText) {
        Assert.assertTrue(
                response.asString().contains(expectedText),
                "Response does not contain expected text: " + expectedText
                        + "\nActual response body:\n" + response.asPrettyString()
        );
    }

    public static void assertResponseTimeLessThan(Response response, long maxMilliseconds) {
        Assert.assertTrue(
                response.time() < maxMilliseconds,
                "Response time is too high: " + response.time() + " ms"
        );
    }
}