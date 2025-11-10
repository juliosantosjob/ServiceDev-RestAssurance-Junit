package automation.dev.serverest.api.utils;

import static automation.dev.serverest.api.utils.Environment.getBaseUrl;

public interface Routes {
    String APP_BASE_URL = getBaseUrl();
    String USERS = "/usuarios/";
    String LOGIN = "/login/";
}