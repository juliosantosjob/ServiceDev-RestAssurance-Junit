package automation.dev.serverest.api.utils;

import automation.dev.serverest.api.base.BaseTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config extends BaseTest {
    private static final String PATH_PROP = "/src/test/resources/config.properties";
    private static final String PATH_FULL = System.getProperty("user.dir") + PATH_PROP;
    private static final Properties properties = new Properties();

    private static Properties loadProp() {
        try {
            properties.load(new FileInputStream(PATH_FULL));
        } catch (IOException ex) {
            logger.error("Arquivo \"config.properties\" não encontrado em src/test/resources/. " +
                    "Por favor, crie o arquivo baseado em \"config.properties.example\" antes de rodar o projeto.");;
            throw new RuntimeException("Arquivo \"config.properties\" não encontrado", ex);
        }
        return properties;
    }

    public static String getSecret(String key) {
        return loadProp().getProperty(key);
    }
}