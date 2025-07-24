package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание заказа")
    public ValidatableResponse ordersCreate(OrderData orderData) {
        return given()
                .header("Content-Type", "application/json")
                .body(orderData)
                .when()
                .post(Endpoints.POST_ORDER_CREATE)
                .then();
    }
@Step("Отмена заказа")
    public ValidatableResponse cancelOrder(OrderData order) {
        return given()
                .header("Content-type", "application/json")
                .queryParam("track", order.getTrack())
                .when()
                .put(Endpoints.PUT_CANCEL_ORDER)
                .then();
    }
}
