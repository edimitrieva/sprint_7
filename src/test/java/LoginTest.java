import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CourierClient;
import org.example.generator.CourierGenerate;
import org.example.model.Courier;
import org.example.model.Credential;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class LoginTest {
    CourierClient courierClient;
    Courier courier;
    int id;

    @Before
    public void setup() {
        courier = CourierGenerate.getRandom();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Login success")
    @Description("Basic test for /api/v1/courier/login")
    public void courierAuthSuccess() {
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        System.out.println(courier.getLogin());
        id = responseLogin.extract().path("id");
        int statusCode = responseLogin.extract().statusCode();

        Assert.assertEquals("Expected 200", SC_OK, statusCode);
        Assert.assertNotNull(id);
    }
}
