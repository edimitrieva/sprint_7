package org.example.metods;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;
import org.example.model.Courier;
import org.example.model.Credential;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String PATH_CREATE_COURIER = "/api/v1/courier";
    private static final String PATH_LOGIN_COURIER = "/api/v1/courier/login";
    private static final String PATH_DELETE ="/api/v1/courier/";

    @Step("Create courier")
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH_CREATE_COURIER)
                .then();
    }

    @Step("Authorization")
    public ValidatableResponse login(Credential credential){
        return given()
                .spec(getSpec())
                .body(credential)
                .when()
                .post(PATH_LOGIN_COURIER)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse delete(Integer id){
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH_DELETE+id)
                .then();
    }
}
