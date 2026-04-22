package framework.utils;

import com.github.javafaker.Faker;

public class RandomDataUtils {

    private static final Faker FAKER = new Faker();

    private RandomDataUtils() {
    }

    public static String getFirstName() {
        return FAKER.name().firstName();
    }

    public static String getLastName() {
        return FAKER.name().lastName();
    }

    public static String getEmail() {
        return FAKER.internet().emailAddress();
    }

    public static String getPostalCode() {
        return "12345";
    }

    public static String getUsername() {
        return FAKER.name().username();
    }

    public static String getPassword() {
        return FAKER.internet().password(8, 12, true, true, true);
    }

    public static String getJobTitle() {
        return FAKER.job().title();
    }
}