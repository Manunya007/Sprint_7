package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Создание заказа")
    public ValidatableResponse courierCreate(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(Endpoints.POST_CREATE_COURIER)
                .then();
    }

    @Step("Получение логина курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(Endpoints.POST_LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(Courier courier) {
        return given()
                .pathParams("id", courier.getId())
                .when()
                .delete(Endpoints.DELETE_COURIER)
                .then();
    }
}
