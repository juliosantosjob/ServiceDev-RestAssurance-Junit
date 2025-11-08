package automation.dev.serverest.api.utils;

import automation.dev.serverest.api.base.BaseTest;
import automation.dev.serverest.api.models.NewUsersModel;
import com.github.javafaker.Faker;

import static org.apache.http.HttpStatus.SC_OK;

public class Helpers extends BaseTest {
    private static final Faker faker = new Faker();

    public static NewUsersModel getRandomUser() {
        try {
            NewUsersModel newUser = new NewUsersModel();

            newUser.setNome(faker.name().firstName());
            newUser.setEmail(faker.internet().emailAddress());
            newUser.setPassword(faker.internet().password());
            newUser.setAdministrador(Boolean.toString(true));

            return newUser;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate random user: " + e.getMessage(), e);
        }
    }

    public static void deletUser(String userId) {
        try {
            if (userId != null) {
                deleteUser(userId)
                        .then()
                        .statusCode(SC_OK);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    public static String getUserId(NewUsersModel user) {
        try {
            return getUser(user)
                    .then()
                    .extract()
                    .response()
                    .path("usuarios[0]._id")
                    .toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user ID: " + e.getMessage(), e);
        }
    }
}
