package automation.dev.serverest.api.tests;

import automation.dev.serverest.api.base.BaseTest;
import automation.dev.serverest.api.models.LoginModel;

import automation.dev.serverest.api.models.NewUsersModel;
import org.junit.jupiter.api.*;

import static automation.dev.serverest.api.utils.Helpers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

@Tag("regression")
@Tag("loginUserRegression")
@DisplayName("Feature: Teste de Login de Usuário")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginUserTest extends BaseTest {

    private String id_;
    private NewUsersModel dynamicUser_;

    @BeforeEach
    public void setup() {
        dynamicUser_ = getRandomUser();
        registerUser(dynamicUser_);
        id_ = getUserId(dynamicUser_);
    }

    @AfterEach
    public void cleanUp() {
        deleteUser(id_);
        id_ = null;
        dynamicUser_ = null;
    }

    @Test
    @Order(1)
    @Tag("loginSuccess")
    @DisplayName("Cenario 01: Deve realizar login com sucesso")
    public void loginSuccessful() {
        LoginModel credentials = new LoginModel(dynamicUser_.getEmail(), dynamicUser_.getPassword());

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_OK)
                .body(is(notNullValue()))
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue())
                .body("authorization", startsWith("Bearer "))
                .body(matchesJsonSchemaInClasspath("contracts/loginSuccessSchema.json"));
    }

    @Test
    @Order(2)
    @Tag("loginInvalidEmail")
    @DisplayName("Cenario 02: Não deve realizar login com email invalido")
    public void loginWithInvalidEmail() {
        LoginModel credentials = new LoginModel("Invalid_email", dynamicUser_.getPassword());

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    @Order(3)
    @Tag("loginEmptyEmail")
    @DisplayName("Cenario 03: Não deve realizar login com email vazio")
    public void loginWithEmptyEmail() {
        LoginModel credentials = new LoginModel("", dynamicUser_.getPassword());

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    @Order(4)
    @Tag("loginInvalidPassword")
    @DisplayName("Cenario 04: Não deve realizar login com senha inválida")
    public void loginWithInvalidPassword() {
        LoginModel credentials = new LoginModel(dynamicUser_.getEmail(), "invalid_password");

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    @Order(5)
    @Tag("loginEmptyPassword")
    @DisplayName("Cenario 05: Não deve realizar login com senha vazia")
    public void loginWithEmptyPassword() {
        LoginModel credentials = new LoginModel(dynamicUser_.getEmail(), "");

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    @Order(6)
    @Tag("loginEmptyCredentials")
    @DisplayName("Cenario 06: Não deve realizar login com email e senha vazios")
    public void loginWithEmptyCredentials() {
        LoginModel credentials = new LoginModel("", "");

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email não pode ficar em branco"))
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    @Order(7)
    @Tag("loginSpacesInCredentials")
    @DisplayName("Cenario 07: Não deve realizar login com email e senha com espaços em branco")
    public void loginWithSpacesInCredentials() {
        LoginModel credentials = new LoginModel(" name@example.com ", " senha123 ");

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    @Order(8)
    @Tag("loginNullEmail")
    @DisplayName("Cenario 08: Não deve realizar login com email nulo")
    public void loginWithNullEmail() {
        LoginModel credentials = new LoginModel(null, dynamicUser_.getPassword());

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email deve ser uma string"));
    }

    @Test
    @Order(9)
    @Tag("loginNullPassword")
    @DisplayName("Cenario 09: Não deve realizar login com senha nula")
    public void loginWithNullPassword() {
        LoginModel credentials = new LoginModel(dynamicUser_.getEmail(), null);

        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("password", equalTo("password deve ser uma string"));
    }

}