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

import static org.apache.http.HttpStatus.SC_OK;

public class AcceptOrdersTest {
    OrderClient orderClient;
    Orders orders;
    CourierClient courierClient;
    Courier courier;
    int orderId;
    int courierId;
    int track;

    @Before
    public void setup(){
        orders = OrderGenerate.getDefaultWithColorBlack();
        orderClient = new OrderClient();
        ValidatableResponse responseOrderCreate = orderClient.create(orders);
        track = responseOrderCreate.extract().path("track");
        orderId = orderClient.getOrderByNumber(track).getOrder().getId();

        courier = CourierGenerate.getRandom();
        courierClient = new CourierClient();
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        courierId = responseLogin.extract().path("id");
    }

    @After
    public void cleanUp(){
        orderClient.cancel(track);
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Order creation")
    @Description("Basic tests for /api/v1/orders/accept/:id")
    public void courierOrderCreate(){
        ValidatableResponse responseAcceptOrder = orderClient.acceptOrder(orderId, courierId);
        int statusCode = responseAcceptOrder.extract().statusCode();

        Assert.assertEquals(SC_OK, statusCode);
        if (statusCode == SC_OK){
            boolean answer = responseAcceptOrder.extract().path("ok");
            Assert.assertTrue(answer);
        }

    }
}
