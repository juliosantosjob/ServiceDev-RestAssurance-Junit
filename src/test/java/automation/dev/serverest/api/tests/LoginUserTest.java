package automation.dev.serverest.api.tests;

import automation.dev.serverest.api.base.BaseTest;
import automation.dev.serverest.api.models.LoginModel;
import automation.dev.serverest.api.models.NewUsersModel;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;

import static automation.dev.serverest.api.utils.Helpers.*;
import static automation.dev.serverest.api.utils.Helpers.getUserId;
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
    private NewUsersModel dynamicUser_;
    private LoginModel credentials;
    private String id_;

    @BeforeEach
    public void initsetup() {
        dynamicUser_ = getRandomUser();
        credentials = getUserCredentials(dynamicUser_);
        id_ = createAndGetRandomUserId(dynamicUser_);
    }

    @AfterEach
    public void endsetup() {
        deletUser(getUserId(id_));
    }

    @Test
    @Order(1)
    @Tag("loginSuccess")
    @DisplayName("Cenario 01: Deve realizar login com sucesso")
    public void loginSuccessful() {
        response = loginUser(credentials);
        response.then()
                .statusCode(SC_OK)
                .body(is(notNullValue()))
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue())
                .body("authorization", startsWith("Bearer "));
    }

    @Test
    @Order(2)
    @Tag("loginInvalidEmail")
    @DisplayName("Cenario 02: Não deve realizar login com email invalido")
    public void loginWithInvalidEmail() {
        credentials.setEmail("invalid_email");
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
        credentials.setEmail("");
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
        credentials.setPassword("invalid_password");
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
        credentials.setPassword("");
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
        credentials.setEmail("");
        credentials.setPassword("");
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
        credentials.setEmail(" name@example.com ");
        credentials.setPassword(" senha123 ");
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
        credentials.setEmail(null);
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
        credentials.setPassword(null);
        response = loginUser(credentials);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("password", equalTo("password deve ser uma string"));
    }

    @Test
    @Order(10)
    @Tag("loginSuccessContractValidation")
    @DisplayName("Cenario 10: Deve validar o contrato de resposta ao realizar login com sucesso")
    public void validateLoginSuccessContract() {
        response = loginUser(credentials);
        response.then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("contracts/loginSuccessSchema.json"));
    }
}
