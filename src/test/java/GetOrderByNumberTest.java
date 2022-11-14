import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.generator.OrderGenerate;
import org.example.metods.OrderClient;
import org.example.model.Order;
import org.example.model.Orders;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class GetOrderByNumberTest {
    OrderClient orderClient;
    Orders orders;
    int track;

    @Before
    public void setup() {
        orders = OrderGenerate.getDefaultWithColorBlack();
        orderClient = new OrderClient();
        ValidatableResponse responseOrderCreate = orderClient.create(orders);
        track = responseOrderCreate.extract().path("track");
    }

    @After
    public void cleanUp() {
        orderClient.cancel(track);
    }

    @Test
    @DisplayName("Get order by number")
    @Description("Basic tests for /api/v1/orders/track")
    public void checkGetOrderByNumber() {
        System.out.println(track);
        ValidatableResponse responseAcceptOrder = orderClient.getResponseOrderByNumber(track);
        int statusCode = responseAcceptOrder.extract().statusCode();
        Order order = orderClient.getOrderByNumber(track);
        int trackFromOder = order.getOrder().getTrack();

        Assert.assertEquals(SC_OK, statusCode);
        Assert.assertNotNull(order);
        Assert.assertEquals(track, trackFromOder);
    }
}

