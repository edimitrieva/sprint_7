import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.generator.CourierGenerate;
import org.example.generator.OrderGenerate;
import org.example.metods.CourierClient;
import org.example.metods.OrderClient;
import org.example.model.Courier;
import org.example.model.Credential;
import org.example.model.Orders;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.example.constants.ConstantsError.*;

@RunWith(Parameterized.class)
public class AcceptOrdersErrorTest {

    CourierClient courierClient;
    Integer courierId;
    Integer orderId;
    int expectedStatusCode;
    String expectedErrorMessage;
    OrderClient orderClient;
    Orders orders;
    Courier courier;
    int flagDeleteCourier = 0;
    int flagDeleteOrder = 0;
    int track;

    @Before
    public void setup() {
        orderClient = new OrderClient();
        if (orderId.equals(0)) {
            orders = OrderGenerate.getDefaultWithColorBlack();
            ValidatableResponse responseOrderCreate = orderClient.create(orders);
            track = responseOrderCreate.extract().path("track");
            orderId = orderClient.getOrderByNumber(track).getOrder().getId();
            flagDeleteOrder = 1;
        } else if (orderId.equals(-1))
            orderId = null;

        if (courierId.equals(0)) {
            courier = CourierGenerate.getRandom();
            courierClient = new CourierClient();
            courierClient.create(courier);
            ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
            courierId = responseLogin.extract().path("id");
            flagDeleteCourier = 1;
        } else if (courierId.equals(-1))
            courierId = null;
    }

    @After
    public void cleanUp() {
        if (flagDeleteOrder == 1) orderClient.cancel(track);
        if (flagDeleteCourier == 1) courierClient.delete(courierId);
    }

    public AcceptOrdersErrorTest(Integer orderId, Integer courierId, int expectedStatusCode, String expectedErrorMessage) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    //0 - создаем заказ/курьера и присваивам реальный id
    //-1 - присваиваем переменной null
    //любое число - передача параметра
    @Parameterized.Parameters(name = "Тестовые данные: orderID = {0} courierId = {1} expectedStatusCode = {2} " +
            "expectedErrorMessage = {3}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {-1, 0, SC_BAD_REQUEST, ERROR_BAD_REQUEST},
                {10000000, 0, SC_NOT_FOUND, ERROR_ORDER_NOT_EXIST},
                {0, 1, SC_NOT_FOUND, ERROR_COURIER_NOT_FOUND},
                {0, -1, SC_BAD_REQUEST, ERROR_BAD_REQUEST}
        };
    }

    @Test
    @DisplayName("Accept order: errors")
    @Description("Accept order without courirId/orderId or unknown courirId/orderId")
    public void courierErrorAcceptOrder() {
        System.out.println("courierId " + courierId + " orderId " + orderId);
        ValidatableResponse responseAccept = orderClient.acceptOrder(orderId, courierId);
        int statusCodeCreate = responseAccept.extract().statusCode();
        String answer = responseAccept.extract().path("message");

        Assert.assertEquals(expectedStatusCode, statusCodeCreate);
        Assert.assertEquals(expectedErrorMessage, answer);
    }
}
