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
import org.junit.jupiter.api.BeforeEach;

import static automation.dev.serverest.api.utils.Helpers.*;
import static automation.dev.serverest.api.utils.Helpers.getUserId;
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
    private NewUsersModel dynamicUser_;
    private String id_;

    @BeforeEach
    public void initSetup() {
        dynamicUser_ = getRandomUser();
        id_ = createAndGetRandomUserId(dynamicUser_);
    }

    @AfterEach
    public void endsetup() {
        deletUser(getUserId(id_));
    }

    @Test
    @Order(1)
    @Tag("registerSuccess")
    @DisplayName("Cenario 01: Deve realizar cadastro com sucesso")
    public void registrationSuccessful() {
        dynamicUser_ = getRandomUser();
        response = registerUser(dynamicUser_);
        response.then()
                .statusCode(SC_CREATED)
                .body(is(notNullValue()))
                .body("_id", is(notNullValue()))
                .body("message", equalTo("Cadastro realizado com sucesso"));


        // To delete user after test to not dirty the database
        deletUser(getUserId(id_));
    }

    @Test
    @Order(2)
    @Tag("registerFailure")
    @DisplayName("Cenario 02: Deve falhar ao realizar cadastro com e-mail inválido")
    public void registrationWithInvalidEmail() {
        dynamicUser_.setEmail("invalid_email");
        response = registerUser(dynamicUser_);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    @Order(3)
    @Tag("registerFailure")
    @DisplayName("Cenario 03: Deve falhar ao realizar cadastro com nome em branco")
    public void registrationWithEmptyName() {
        dynamicUser_.setNome("");
        response = registerUser(dynamicUser_);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("nome", equalTo("nome não pode ficar em branco"));
    }

    @Test
    @Order(4)
    @Tag("registerFailure")
    @DisplayName("Cenario 04: Deve falhar ao realizar cadastro com email em branco")
    public void registrationWithEmptyEmail() {
        dynamicUser_.setEmail("");
        response = registerUser(dynamicUser_);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    @Order(5)
    @Tag("registerFailure")
    @DisplayName("Cenario 05: Deve falhar ao realizar cadastro com senha em branco")
    public void registrationWithEmptyPassword() {
        dynamicUser_.setPassword("");
        response = registerUser(dynamicUser_);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    @Order(6)
    @Tag("registerSuccessContractValidation")
    @DisplayName("Cenario 06: Deve validar o contrato de resposta ao realizar cadastro com sucesso")
    public void validateRegistrationSuccessContract() {
        dynamicUser_ = getRandomUser();
        response = registerUser(dynamicUser_);
        response.then()
                .statusCode(SC_CREATED)
                .body(matchesJsonSchemaInClasspath("contracts/registerSuccessSchema.json"));

        String idUser = response.jsonPath().getString("id");
        deletUser(getUserId(id_));
    }
}