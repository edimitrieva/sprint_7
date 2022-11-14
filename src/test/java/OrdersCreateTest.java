import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.generator.OrderGenerate;
import org.example.metods.OrderClient;
import org.example.model.Orders;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class OrdersCreateTest {
    OrderClient orderClient;
    Orders orders;
    int track;

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp() {
        ValidatableResponse responseOrder = orderClient.cancel(track);
        System.out.println("track " + track + " " + responseOrder.extract().path("ok"));
    }

    public OrdersCreateTest(Orders orders) {
        this.orders = orders;
    }

    @Parameterized.Parameters(name = "Тестовые данные: date for create order = {0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderGenerate.getDefaultWithColorBlack()},
                {OrderGenerate.getDefaultWithColorGrey()},
                {OrderGenerate.getDefaultWithColorGreyAndBlack()},
                {OrderGenerate.getDefaultWithoutColor()}
        };
    }

    @Test
    @DisplayName("Order creation")
    @Description("Basic tests for /api/v1/orders")
    public void courierOrderCreate() {
        ValidatableResponse responseOrderCreate = orderClient.create(orders);
        int statusCodeCreate = responseOrderCreate.extract().statusCode();
        track = responseOrderCreate.extract().path("track");

        Assert.assertEquals(SC_CREATED, statusCodeCreate);
        Assert.assertNotNull(track);
        System.out.println(track);
    }
}
