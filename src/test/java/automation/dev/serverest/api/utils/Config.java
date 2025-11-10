package automation.dev.serverest.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static String PATH_PROP = "/src/test/resources/config.properties";
    private static String PATH_FULL = System.getProperty("user.dir") + PATH_PROP;
    private static Properties properties = new Properties();

    private static Properties loadProp() {
        try {
            properties.load(new FileInputStream(PATH_FULL));
        } catch (IOException ex) {
            throw new RuntimeException("Arquivo 'config.properties' não encontrado em src/test/resources/. " +
                    "Por favor, crie o arquivo baseado em 'config.properties.example' antes de rodar o projeto.", ex);

        }
        return properties;
    }

    public static String getSecret(String key) {
        return loadProp().getProperty(key);
    }
}