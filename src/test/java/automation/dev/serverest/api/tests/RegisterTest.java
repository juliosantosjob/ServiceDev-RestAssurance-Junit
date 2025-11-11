package automation.dev.serverest.api.tests;

import automation.dev.serverest.api.base.BaseTest;
import automation.dev.serverest.api.models.NewUsersModel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static automation.dev.serverest.api.utils.Helpers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Tag("regressao")
@DisplayName("Feature: Teste de Cadastro de Usuário")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest extends BaseTest {

    @Test
    @Order(1)
    @Tag("cadastro_cad_sucesso")
    @DisplayName("Deve realizar cadastro com sucesso")
    public void registrationSuccessful() {
        NewUsersModel newUser = getRandomUser();

        response = registerUser(newUser);
        response.then()
                .statusCode(SC_CREATED)
                .body(is(notNullValue()))
                .body("_id", is(notNullValue()))
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body(matchesJsonSchemaInClasspath("contracts/registerSuccessSchema.json"));

        String id_ = getUserId(newUser);
        deleteUser(id_);
    }

    @Test
    @Order(2)
    @Tag("cadastro_email_invalido")
    @DisplayName("Deve falhar ao realizar cadastro com e-mail inválido")
    public void registrationWithInvalidEmail() {
        NewUsersModel newUser = getRandomUser();
        newUser.setEmail("invalid_email.com");

        response = registerUser(newUser);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email deve ser um email válido"));
    }

    @Test
    @Order(3)
    @Tag("cadastro_nome_vazio")
    @DisplayName("Deve falhar ao realizar cadastro com nome em branco")
    public void registrationWithEmptyName() {
        NewUsersModel newUser = getRandomUser();
        newUser.setNome("");

        response = registerUser(newUser);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("nome", equalTo("nome não pode ficar em branco"));
    }

    @Test
    @Order(4)
    @Tag("cadastro_email_vazio")
    @DisplayName("Deve falhar ao realizar cadastro com email em branco")
    public void registrationWithEmptyEmail() {
        NewUsersModel newUser = getRandomUser();
        newUser.setEmail("");

        response = registerUser(newUser);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    @Order(5)
    @Tag("cadastro_senha_vazia")
    @DisplayName("Deve falhar ao realizar cadastro com senha em branco")
    public void registrationWithEmptyPassword() {
        NewUsersModel newUser = getRandomUser();
        newUser.setPassword("");

        response = registerUser(newUser);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    @Order(6)
    @Tag("cadastro_email_duplicado")
    @DisplayName("Deve falhar ao realizar cadastro com e-mail duplicado")
    public void registrationWithDuplicateEmail() {
        NewUsersModel newUser = getRandomUser();
        NewUsersModel duplicateUser = new NewUsersModel();

        registerUser(newUser);

        duplicateUser.setNome(newUser.getNome());
        duplicateUser.setEmail(newUser.getEmail());
        duplicateUser.setPassword(newUser.getPassword());
        duplicateUser.setAdministrador(newUser.getAdministrador());

        response = registerUser(duplicateUser);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("message", equalTo("Este email já está sendo usado"));

        String id_ = getUserId(newUser);
        deleteUser(id_);
    }

    @Test
    @Order(7)
    @Tag("cadastro_admin_invalido")
    @DisplayName("Deve falhar ao realizar cadastro com administrador inválido")
    public void registrationWithInvalidAdministrador() {
        NewUsersModel newUser = getRandomUser();
        newUser.setAdministrador(null);

        response = registerUser(newUser);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(is(notNullValue()))
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    @Order(8)
    @Tag("cadastro_campos_obrig")
    @DisplayName("Deve falhar ao realizar cadastro sem preencher campos obrigatórios")
    public void registrationWithMissingFields() {
        NewUsersModel newUser = new NewUsersModel();
        newUser.setNome("");
        newUser.setEmail("");
        newUser.setPassword("");
        newUser.setAdministrador("false");

        response = registerUser(newUser);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("nome", equalTo("nome não pode ficar em branco"))
                .body("email", equalTo("email não pode ficar em branco"))
                .body("password", equalTo("password não pode ficar em branco"));
    }

}