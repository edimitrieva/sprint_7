import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.OrderClient;
import org.example.model.OrdersList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class OrdersListTest {
    OrderClient orderClient;

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Order List")
    @Description("Basic test for /api/v1/orders")
    public void checkOrderList() {
        ValidatableResponse responseListOrders = orderClient.getListOrdersResponse();
        OrdersList listOrders = orderClient.getListOrders();
        int statusCode = responseListOrders.extract().statusCode();

        Assert.assertEquals(SC_OK, statusCode);
        Assert.assertNotNull(listOrders.getOrders());
        Assert.assertNotNull(listOrders.getPageInfo());
        Assert.assertNotNull(listOrders.getAvailableStations());
    }
}
