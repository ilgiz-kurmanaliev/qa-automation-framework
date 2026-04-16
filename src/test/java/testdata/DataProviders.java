package testdata;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"wrong_user", "wrong_pass", false},
                {"locked_out_user", "secret_sauce", false}
        };
    }
}