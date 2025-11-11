package automation.dev.serverest.api.runners;

import automation.dev.serverest.api.tests.EditUserTest;
import automation.dev.serverest.api.tests.GetUserTest;
import automation.dev.serverest.api.tests.LoginUserTest;
import automation.dev.serverest.api.tests.RegisterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeTags("regressao") 
@DisplayName("Automação de Serviços - DevServe")
@SelectPackages("src.test.java.automation.dev.serverest.api.tests")
@SelectClasses({
        LoginUserTest.class,
        RegisterTest.class,
        EditUserTest.class,
        GetUserTest.class
})
public class TestRunner { }