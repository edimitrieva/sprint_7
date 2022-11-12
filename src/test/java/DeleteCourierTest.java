import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.generator.CourierGenerate;
import org.example.metods.CourierClient;
import org.example.model.Courier;
import org.example.model.Credential;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class DeleteCourierTest {
    CourierClient courierClient;
    Courier courier;
    int id;

    @Before
    public void setup(){
        courier = CourierGenerate.getRandom();
        courierClient = new CourierClient();
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        id = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Delete courier")
    @Description("Basic test for /api/v1/courier/:id")
    public void courierDeleteSuccess(){
        ValidatableResponse responseDelete = courierClient.delete(id);
        int statusCode = responseDelete.extract().statusCode();

        Assert.assertEquals("Expected 200", SC_OK, statusCode);
        if (statusCode == SC_OK){
            boolean answer = responseDelete.extract().path("ok");
            Assert.assertTrue(answer);
        }

    }
}
