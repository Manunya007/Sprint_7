package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest extends BaseTest {
    private OrderSteps orderSteps = new OrderSteps();

    @DisplayName("Получение списка заказа")
    @Test
    public void getOrderslistTest() {
        orderSteps.ordersList()
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }
}
