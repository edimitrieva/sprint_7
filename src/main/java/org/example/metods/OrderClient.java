package org.example.metods;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import org.example.model.Order;
import org.example.model.Orders;
import org.example.model.OrdersList;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH_CREATE = "/api/v1/orders";
    private static final String PATH_CANCEL = "/api/v1/orders/cancel";
    private static final String PATH_ACCEPT = "/api/v1/orders/accept/";
    private static final String PATH_GET_ORDER = "/api/v1/orders/track";

    @Step("Create order")
    public ValidatableResponse create(Orders orders) {
        return given()
                .spec(getSpec())
                .body(orders)
                .when()
                .post(PATH_CREATE)
                .then();
    }

    @Step("Cancel order")
    public ValidatableResponse cancel(int track) {
        return given()
                .spec(getSpec())
                .when()
                .put(String.format("%s/?track=%d", PATH_CANCEL, track))
                .then();
    }

    @Step("Get response from /api/v1/orders with list orders")
    public ValidatableResponse getListOrdersResponse() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_CREATE)
                .then();
    }

    @Step("Get list orders")
    public OrdersList getListOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_CREATE)
                .body().as(OrdersList.class);
    }

    @Step("Accept order")
    public ValidatableResponse acceptOrder(Integer orderId, Integer courierId) {
        return given()
                .spec(getSpec())
                .when()
                .queryParam("courierId", courierId)
                .put(PATH_ACCEPT + orderId)
                .then();
    }

    @Step("Get order by it's number")
    public Order getOrderByNumber(int track) {
        return given()
                .spec(getSpec())
                .when()
                .queryParam("t", track)
                .get(PATH_GET_ORDER)
                .body().as(Order.class);

    }

    @Step("Get order by it's number")
    public ValidatableResponse getResponseOrderByNumber(Integer track) {
        return given()
                .spec(getSpec())
                .when()
                .queryParam("t", track)
                .get(PATH_GET_ORDER)
                .then();
    }
}
