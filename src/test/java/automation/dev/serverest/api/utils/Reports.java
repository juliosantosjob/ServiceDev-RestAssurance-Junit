package automation.dev.serverest.api.utils;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class Reports {

    public static void attachmentsAllure(Response response) {
        try {
            if (response != null) {
                String responseDetails =
                                "Status Code: " + response.getStatusCode() + "\n" +
                                "Headers: " + response.getHeaders().toString() + "\n" +
                                "Body: " + response.getBody().asString();

                Allure.addAttachment("API Response", "text/plain", responseDetails);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to attach response to Allure report: " + e.getMessage(), e);
        }
    }
}