package automation.dev.serverest.api.tests;

import automation.dev.serverest.api.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

@Tag("regression")
@Tag("getUserRegression")
@DisplayName("Feature: Teste de Obtenção de Usuário")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetUserTest extends BaseTest {

    @Test
    @Order(1)
    @Tag("getUser")
    @DisplayName("Cenário 01: Deve obter um usuário aleatório na lista e validar os dados")
    public void getRandomUserAndValidateFields() {
        response = getListUsers();

        List<Map<String, Object>> usuarios = response.jsonPath().getList("usuarios");
        int randNumb = new Random().nextInt(usuarios.size());

        response.then()
                .statusCode(SC_OK)
                .body("usuarios[" + randNumb + "].nome", notNullValue())
                .body("usuarios[" + randNumb + "].email", notNullValue())
                .body("usuarios[" + randNumb + "].password", notNullValue())
                .body("usuarios[" + randNumb + "].administrador", notNullValue())
                .body(matchesJsonSchemaInClasspath("contracts/getUserSchema.json"));
    }
}