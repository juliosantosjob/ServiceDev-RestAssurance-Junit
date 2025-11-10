package automation.dev.serverest.api.base;

import automation.dev.serverest.api.models.LoginModel;
import automation.dev.serverest.api.models.NewUsersModel;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mozilla.javascript.tools.shell.Environment;

import static automation.dev.serverest.api.utils.Config.getSecret;
import static automation.dev.serverest.api.utils.Reports.attachmentsAllure;

public class BaseTest extends Environment implements Constants {
    protected String BASE_URL = System.getenv("BASE_URL") == null ? getSecret("BASE_URL") : System.getenv("BASE_URL");
    protected String email = System.getenv("USER_EMAIL") == null ? getSecret("USER_EMAIL") : System.getenv("USER_EMAIL");
    protected String password = System.getenv("USER_PASSWORD") == null ? getSecret("USER_PASSWORD") : System.getenv("USER_PASSWORD");
    protected Response response;

    @BeforeEach
    public void setupRestAssured() {
        ResponseSpecBuilder responseSpec = new ResponseSpecBuilder();

        if (BASE_URL == null) {
            System.err.println("*** Please, create the .config.properties file " +
                    "and set \"app.base.url.hom\" environment variable ***");
        }

        responseSpec.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        RestAssured.baseURI = BASE_URL;
        RestAssured.responseSpecification = responseSpec.build();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", CONTENT_TYPE).build();

        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @AfterEach
    public void endSetup() {
        attachmentsAllure(response);
    }

    /**
     * Mwtodo para obter todos os usuários
     *
     * @return Response da chamada
     */

    public static Response getListUsers() {
        try {
            return RestAssured
                    .given()
                    .get(USERS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users: " + e.getMessage());
        }
    }

    /**
     * Mwtodo para obter um usuário por email e senha
     *
     * @param user Objeto contendo email e senha do usuário
     * @return Response da chamada
     */

    public static Response getUser(NewUsersModel user) {
        try {
            return RestAssured
                    .given()
                    .queryParam("nome", user.getNome())
                    .queryParam("email", user.getEmail())
                    .queryParam("password", user.getPassword())
                    .queryParam("administrador", user.getAdministrador())
                    .when()
                    .get(USERS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para realizar login de um usuário
     *
     * @param credentials Objeto contendo as credenciais de login (email e senha)
     * @return Response da chamada
     */

    public static Response loginUser(LoginModel credentials) {
        try {
            return RestAssured
                    .given()
                    .body(credentials)
                    .when()
                    .post(LOGIN);
        } catch (Exception e) {
            throw new RuntimeException("Error logging in: " + e.getMessage());
        }
    }

    /**
     * Metodo para editar usuarios
     *
     * @param newUser Objeto java com os dados do usuario
     * @param idUser  Id da conta do usuario para realizar a edição
     * @return Response da chamada
     */

    public static Response editUser(NewUsersModel newUser, String idUser) {
        try {
            return RestAssured
                    .given()
                    .body(newUser)
                    .when()
                    .put(USERS.concat(idUser));
        } catch (Exception e) {
            throw new RuntimeException("Failed to edit user" + e.getMessage());
        }
    }

    /**
     * Metodo para registrar um novo usuário
     *
     * @param newUser Objeto contendo os dados do novo usuário
     * @return Response da chamada
     */

    public static Response registerUser(NewUsersModel newUser) {
        try {
            return RestAssured
                    .given()
                    .body(newUser)
                    .when()
                    .post(USERS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user: " + e.getMessage());
        }
    }

    /**
     * Metodo para deletar usuarios
     *
     * @param idUser Id da conta que será deletada
     * @return Response da chamada
     */

    public static Response deleteUser(String idUser) {
        try {
            return RestAssured
                    .given()
                    .when()
                    .delete(USERS.concat(idUser));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user" + e.getMessage());
        }
    }

}