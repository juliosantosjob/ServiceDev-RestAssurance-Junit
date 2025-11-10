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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static automation.dev.serverest.api.utils.Helpers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@Tag("regression")
@Tag("editUserRegression")
@DisplayName("Feature: Testes de Edição de Usuário")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EditUserTest extends BaseTest {

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
        deletUser(id_);
        id_ = null;
        dynamicUser_ = null;
    }

    @Test
    @Order(1)
    @Tag("editUserSuccess")
    @DisplayName("Cenario 01: Deve realizar edição com sucesso")
    public void editUserSuccessful() {
        response = editUser(dynamicUser_, id_);
        response.then()
                .statusCode(SC_OK)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    @Order(2)
    @Tag("editUserInvalidData")
    @DisplayName("Cenario 02: Deve falhar ao realizar edição com todos os dados em branco")
    public void editUserWithInvalidData() {
        dynamicUser_.setNome("");
        dynamicUser_.setEmail("");
        dynamicUser_.setPassword("");
        dynamicUser_.setAdministrador("");

        response = editUser(dynamicUser_, id_);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("nome", equalTo("nome não pode ficar em branco"));
    }

    @Test
    @Order(3)
    @Tag("editUserNonExistent")
    @DisplayName("Cenario 03: Deve criar um novo usuário ao tentar editar um usuário inexistente")
    public void editNonExistentUser() {
        NewUsersModel newUser = getRandomUser();

        response = editUser(newUser, "non_existent_id");
        response.then()
                .statusCode(SC_CREATED)
                .body("message", equalTo("Cadastro realizado com sucesso"));

        id_ = getUserId(newUser);
        deletUser(id_);
    }

    @Test
    @Order(4)
    @Tag("editUserNullFields")
    @DisplayName("Cenario 04: Deve falhar ao realizar edição com campos nulos")
    public void editUserWithNullFields() {
        dynamicUser_.setNome(null);
        dynamicUser_.setEmail(null);
        dynamicUser_.setPassword(null);
        dynamicUser_.setAdministrador(null);

        response = editUser(dynamicUser_, id_);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("nome", equalTo("nome deve ser uma string"))
                .body("email", equalTo("email deve ser uma string"))
                .body("password", equalTo("password deve ser uma string"))
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"));
    }

    @Test
    @Order(5)
    @Tag("editUserContractValidation")
    @DisplayName("Cenario 05: Deve validar o contrato de resposta ao editar usuário com sucesso")
    public void validateEditUserContract() {
        response = editUser(dynamicUser_, id_);
        response.then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("contracts/editUserSchema.json"));
    }
}
