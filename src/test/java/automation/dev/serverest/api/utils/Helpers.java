package automation.dev.serverest.api.utils;

import automation.dev.serverest.api.base.BaseTest;
import automation.dev.serverest.api.models.NewUsersModel;
import com.github.javafaker.Faker;

import static org.apache.http.HttpStatus.SC_OK;

public class Helpers extends BaseTest {
    private static final Faker faker = new Faker();

    public static NewUsersModel getRandomUser() {
        logger.info("Gerando usuário aleatório");
        try {
            NewUsersModel newUser = new NewUsersModel();

            newUser.setNome(faker.name().firstName());
            newUser.setEmail(faker.internet().emailAddress());
            newUser.setPassword(faker.internet().password());
            newUser.setAdministrador(Boolean.toString(true));

            return newUser;
        } catch (Exception e) {
            logger.error("Falha ao gerar usuário aleatório: {}", e.getMessage());
            throw new RuntimeException("Erro na geração de usuário aleatório: " + e.getMessage(), e);
        }
    }

    public static void deletUser(String userId) {
        logger.info("Iniciando exclusão do usuário com ID: {}", userId);
        try {
            if (userId != null) {
                deleteUser(userId)
                        .then()
                        .statusCode(SC_OK);
            }
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário com ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Falha ao deletar usuário com id" + userId + ": " + e.getMessage(), e);

        }
    }

    public static String getUserId(NewUsersModel user) {
        logger.info("Obtendo ID do usuário: {}", user.getEmail());
        try {
            return getUser(user)
                    .then()
                    .extract()
                    .response()
                    .path("usuarios[0]._id")
                    .toString();
        } catch (Exception e) {
            logger.error("Erro ao obter ID do usuário {}: {}", user.getEmail(), e.getMessage());
            throw new RuntimeException("Falha ao obter ID do usuário " + user.getEmail() + ": " + e.getMessage(), e);
        }
    }
}
