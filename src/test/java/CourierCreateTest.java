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

import static org.apache.http.HttpStatus.*;
import static org.example.constants.ConstantsError.ERROR_DUPLICATE_LOGIN;

public class CourierCreateTest {
    CourierClient courierClient;
    Courier courier;
    int id;

    @Before
    public void setup(){
        courier = CourierGenerate.getRandom();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp(){
        ValidatableResponse responseDelete = courierClient.delete(id);
        System.out.println("id "+id+" "+responseDelete.extract().path("ok"));

    }

    @Test
    @DisplayName("Create courier")
    @Description("Basic test for /api/v1/courier")
    public void courierCanBeCreated(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        System.out.println(courier.getLogin());
        id = responseLogin.extract().path("id");
        int statusCode = responseCreate.extract().statusCode();

        Assert.assertEquals("Expected 201", SC_CREATED, statusCode);
        if (statusCode == SC_OK){
            boolean answer = responseCreate.extract().path("ok");
            Assert.assertTrue(answer);
        }

    }

    @Test
    @DisplayName("Create duplicate courier")
    @Description("Check statusCode and error message if we create a duplicate courier")
    public void courierCreateDuplicate(){
        courierClient.create(courier);
        ValidatableResponse responseCreateDuplicate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        id = responseLogin.extract().path("id");
        int statusCode = responseCreateDuplicate.extract().statusCode();
        String answer = responseCreateDuplicate.extract().path("message");

        Assert.assertEquals("Expected 409", SC_CONFLICT, statusCode);
        Assert.assertEquals("Expected error message",ERROR_DUPLICATE_LOGIN,answer);
    }
}
