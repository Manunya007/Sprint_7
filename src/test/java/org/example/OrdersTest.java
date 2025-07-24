package org.example;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersTest extends BaseTest {

    private OrderSteps orderSteps = new OrderSteps();
    private OrderData order;

    String[] color;

    public OrdersTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвета: {0}, {1}")
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
@DisplayName("Создание заказа")
    @Test
    public void ordersCreateTestReturn201() {

        orderSteps
                .ordersCreate(order)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

    }

    @After
    public void tearDown() {
        Integer track = orderSteps.ordersCreate(order).extract().path("track");
        order.setTrack(track);
        orderSteps.cancelOrder(order);
    }

}
