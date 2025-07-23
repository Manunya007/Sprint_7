package org.example;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersTest extends BaseTest {

    private OrderSteps orderSteps = new OrderSteps();
    private OrderData order;

    String[] color;

    public OrdersTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorOrders() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Before
    public void setUp() {
        order = new OrderData(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color
        );
    }
@Step("Создание заказа, код 201")
    @Test
    public void ordersCreateTestReturn201() {

        orderSteps
                .ordersCreate(order)
                .statusCode(201)
                .body("track", notNullValue());

    }
@Step("Получение списка заказа")
    @Test
    public void getOrderslist() {
        given()
                .get("/api/v1/orders")
                .then().statusCode(200)
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }

    @After
    public void tearDown() {
        Integer track = orderSteps.ordersCreate(order).extract().path("track");
        order.setTrack(track);
        orderSteps.cancelOrder(order);
    }

}
