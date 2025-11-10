package automation.dev.serverest.api.utils;

public class Environment {
    private static String HOM_URL = System.getenv("HOM_URL");

    public static String getBaseUrl() {
        if (HOM_URL == null) {
            return Config.getEnv("HOM_URL");
        }
        return HOM_URL;
    }
}