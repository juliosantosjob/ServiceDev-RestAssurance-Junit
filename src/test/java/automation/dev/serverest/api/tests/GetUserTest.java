package automation.dev.serverest.api.tests;

import automation.dev.serverest.api.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static automation.dev.serverest.api.utils.Helpers.getUserList;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;

@Tag("regression")
@Tag("getUserRegression")
@DisplayName("Feature: Teste de Obtenção de Usuário")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetUserTest extends BaseTest {

    @Test
    @Order(1)
    @Tag("getUser")
    @DisplayName("Cenario 01: Deve obter um usuario na lista")
    public void getAUserInTheList() {
        response = getUserList();
        List<Map<String, Object>> usuarios = response.jsonPath().getList("usuarios");

        int randomIndex = new Random().nextInt(usuarios.size());
        Map<String, Object> selectedUser = usuarios.get(randomIndex);

        Assertions.assertNotNull(selectedUser.get("nome"));
        Assertions.assertNotNull(selectedUser.get("email"));
        Assertions.assertNotNull(selectedUser.get("password"));
        Assertions.assertNotNull(selectedUser.get("administrador"));
    }

    @Test
    @Order(2)
    @Tag("getUserContractValidation")
    @DisplayName("Cenario 02: Deve validar o contrato de resposta ao obter um usuário na lista")
    public void validateGetUserContract() {
        response = getUserList();
        response.then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("contracts/getUserSchema.json"));
    }

}