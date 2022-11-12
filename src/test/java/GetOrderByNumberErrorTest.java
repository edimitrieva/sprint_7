import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.OrderClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.example.constants.ConstantsError.ERROR_BAD_REQUEST;
import static org.example.constants.ConstantsError.ERROR_ORDER_NOT_FOUND;

@RunWith(Parameterized.class)
public class GetOrderByNumberErrorTest {
    Integer orderId;
    int expectedStatusCode;
    String expectedErrorMessage;

    OrderClient orderClient;

    @Before
    public void setup() {

        orderClient = new OrderClient();
    }

    public GetOrderByNumberErrorTest(Integer orderId, int expectedStatusCode, String expectedErrorMessage) {
        this.orderId = orderId;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {null, SC_BAD_REQUEST, ERROR_BAD_REQUEST},
                {10000000, SC_NOT_FOUND, ERROR_ORDER_NOT_FOUND}
        };
    }


    @Test
    @DisplayName("Get order by number: errors")
    @Description("Get order by numbe without track or unknown track")
    public void courierErrorGetOrderByNumber(){
        System.out.println("orderId "+orderId);
        ValidatableResponse responseGetOrder = orderClient.getResponseOrderByNumber(orderId);
        int statusCodeCreate = responseGetOrder.extract().statusCode();
        String answer = responseGetOrder.extract().path("message");

        Assert.assertEquals(expectedStatusCode, statusCodeCreate);
        Assert.assertEquals(expectedErrorMessage, answer);

    }
}
