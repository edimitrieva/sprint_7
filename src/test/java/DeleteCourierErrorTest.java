import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CourierClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.example.constants.ConstantsError.ERROR_COURIER_ID_NOT_FOUND;
import static org.example.constants.ConstantsError.ERROR_NOT_DATA_FOR_DELETE_COURIER;

@RunWith(Parameterized.class)
public class DeleteCourierErrorTest {
    CourierClient courierClient;
    Integer id;
    int expectedStatusCode;
    String expectedErrorMessage;

    @Before
    public void setup(){
        courierClient = new CourierClient();
    }

    public DeleteCourierErrorTest(Integer id, int expectedStatusCode, String expectedErrorMessage) {
        this.id = id;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {null, SC_BAD_REQUEST, ERROR_NOT_DATA_FOR_DELETE_COURIER},
                {-1, SC_NOT_FOUND, ERROR_COURIER_ID_NOT_FOUND}
        };
    }


    @Test
    @DisplayName("Delete courier: errors")
    @Description("Delete courier without id or unknown number")
    public void courierErrorDelete(){
        ValidatableResponse responseDelete = courierClient.delete(id);
        int statusCodeCreate = responseDelete.extract().statusCode();
        String answer = responseDelete.extract().path("message");

        Assert.assertEquals(expectedStatusCode, statusCodeCreate);
        Assert.assertEquals(expectedErrorMessage, answer);

    }
}
