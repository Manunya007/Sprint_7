package org.example;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest extends BaseTest{

    @DisplayName("Получение списка заказа")
    @Test
    public void getOrderslistTest() {
        given()
                .get("/api/v1/orders")
                .then().statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }
}
