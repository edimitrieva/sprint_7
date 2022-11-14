import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CourierClient;
import org.example.generator.CourierGenerate;
import org.example.model.Courier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.example.constants.ConstantsError.ERROR_NOT_DATA_FOR_CREATE_ORDER;

@RunWith(Parameterized.class)
public class CourierCreateErrorTest {
    CourierClient courierClient;
    Courier courier;
    int expectedStatusCode;
    String expectedErrorMessage;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    public CourierCreateErrorTest(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters(name = "Тестовые данные: date for create courier = {0} expectedStatusCode = {1} expectedErrorMessage = {2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierGenerate.getWithoutLogin(), SC_BAD_REQUEST, ERROR_NOT_DATA_FOR_CREATE_ORDER},
                {CourierGenerate.getWithoutPassword(), SC_BAD_REQUEST, ERROR_NOT_DATA_FOR_CREATE_ORDER},
                {CourierGenerate.getWithEmptyPassword(), SC_BAD_REQUEST, ERROR_NOT_DATA_FOR_CREATE_ORDER},
                {CourierGenerate.getWithEmptyLogin(), SC_BAD_REQUEST, ERROR_NOT_DATA_FOR_CREATE_ORDER}
        };
    }

    @Test
    @DisplayName("Create courier: errors")
    @Description("Create courier without login or password")
    public void courierErrorCreate() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCodeCreate = responseCreate.extract().statusCode();
        String answer = responseCreate.extract().path("message");

        Assert.assertEquals(expectedStatusCode, statusCodeCreate);
        Assert.assertEquals(expectedErrorMessage, answer);
    }
}
