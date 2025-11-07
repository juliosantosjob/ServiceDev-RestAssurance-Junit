package automation.dev.serverest.api.utils;

import automation.dev.serverest.api.base.BaseTest;
import automation.dev.serverest.api.models.LoginModel;
import automation.dev.serverest.api.models.NewUsersModel;
import com.github.javafaker.Faker;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Helpers extends BaseTest {
    private static final Faker faker = new Faker();

    public static NewUsersModel getRandomUser() {
        NewUsersModel newUser = new NewUsersModel();

        newUser.setNome(faker.name().firstName());
        newUser.setEmail(faker.internet().emailAddress());
        newUser.setPassword(faker.internet().password());
        newUser.setAdministrador(Boolean.toString(true));

        return newUser;
    }

    public static LoginModel getUserCredentials(NewUsersModel newUser) {
        LoginModel login = new LoginModel(
                newUser.getEmail(),
                newUser.getPassword());

        return login;
    }

    public static String createAndGetRandomUserId(NewUsersModel newUsers) {
        return registerUser(newUsers)
                .then()
                .statusCode(SC_CREATED)
                .body(is(notNullValue()))
                .body("_id", notNullValue())
                .extract()
                .path("_id")
                .toString();
    }

    public static Response getUserList() {
        return getUser()
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();
    }

    public static String getUserId(String userID) {
        return getUserById(userID)
                .then()
                .extract()
                .path("_id")
                .toString();
    }

    public static void deletUser(String userId) {
        if (userId != null) {
            deleteUser(userId)
                    .then()
                    .statusCode(SC_OK);
        }
    }
}
