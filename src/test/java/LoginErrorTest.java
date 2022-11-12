import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CourierClient;
import org.example.generator.CourierGenerate;
import org.example.model.Courier;
import org.example.model.Credential;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.example.constants.ConstantsError.ERROR_COURIER_LOGIN_NOT_FOUND;
import static org.example.constants.ConstantsError.ERROR_NOT_DATA_FOR_LOGIN;

@RunWith(Parameterized.class)
public class LoginErrorTest {

    CourierClient courierClient;
    Courier courier;
    int expectedStatusCode;
    String expectedErrorMessage;

    @Before
    public void setup(){
        courierClient = new CourierClient();
    }

    public LoginErrorTest(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {CourierGenerate.getWithoutLogin(), SC_BAD_REQUEST, ERROR_NOT_DATA_FOR_LOGIN},
                {CourierGenerate.getWithEmptyPassword(), SC_BAD_REQUEST, ERROR_NOT_DATA_FOR_LOGIN},
                {CourierGenerate.getRandom(),SC_NOT_FOUND,ERROR_COURIER_LOGIN_NOT_FOUND}
        };
    }

    @Test
    @DisplayName("Login: errors")
    @Description("Authorization without login or password. And authorization with unknown login")
    public void courierErrorLogin(){
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        int statusCodeCreate = responseLogin.extract().statusCode();
        String answer = responseLogin.extract().path("message");

        Assert.assertEquals(expectedStatusCode, statusCodeCreate);
        Assert.assertEquals(expectedErrorMessage, answer);

    }
}
