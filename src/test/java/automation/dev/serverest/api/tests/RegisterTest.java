package automation.dev.serverest.api.tests;

import automation.dev.serverest.api.base.BaseTest;
import automation.dev.serverest.api.models.NewUsersModel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestMethodOrder;

import static automation.dev.serverest.api.utils.Helpers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Tag("regression")
@Tag("registerRegression")
@DisplayName("Feature: Teste de Cadastro de Usuário")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest extends BaseTest {
    private String id_;

    @AfterEach
    public void endsetup() {
        deletUser(id_);
    }

    @Test
    @Order(1)
    @Tag("registerFailure")
    @DisplayName("Deve falhar ao realizar cadastro com e-mail inválido")
    public void registrationWithInvalidEmail() {
        NewUsersModel user = getRandomUser();
        user.setEmail("invalid_email");

        response = registerUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    @Order(2)
    @Tag("registerFailure")
    @DisplayName("Deve falhar ao realizar cadastro com nome em branco")
    public void registrationWithEmptyName() {
        NewUsersModel user = getRandomUser();
        user.setNome("");

        response = registerUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("nome", equalTo("nome não pode ficar em branco"));
    }

    @Test
    @Order(3)
    @Tag("registerFailure")
    @DisplayName("Deve falhar ao realizar cadastro com email em branco")
    public void registrationWithEmptyEmail() {
        NewUsersModel user = getRandomUser();
        user.setEmail("");

        response = registerUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    @Order(4)
    @Tag("registerFailure")
    @DisplayName("Deve falhar ao realizar cadastro com senha em branco")
    public void registrationWithEmptyPassword() {
        NewUsersModel user = getRandomUser();
        user.setPassword("");

        response = registerUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    @Order(5)
    @Tag("registerSuccess")
    @DisplayName("Deve realizar cadastro com sucesso")
    public void registrationSuccessful() {
        NewUsersModel user = getRandomUser();

        response = registerUser(user);
        response.then()
                .statusCode(SC_CREATED)
                .body(is(notNullValue()))
                .body("_id", is(notNullValue()))
                .body("message", equalTo("Cadastro realizado com sucesso"));

        id_ = getUserId(user);
    }

    @Test
    @Order(6)
    @Tag("registerSuccessContractValidation")
    @DisplayName("Deve validar o contrato de resposta ao realizar cadastro com sucesso")
    public void validateRegistrationSuccessContract() {
        NewUsersModel user = getRandomUser();

        response = registerUser(user);
        response.then()
                .statusCode(SC_CREATED)
                .body(matchesJsonSchemaInClasspath("contracts/registerSuccessSchema.json"));

        id_ = getUserId(user);
    }
}
