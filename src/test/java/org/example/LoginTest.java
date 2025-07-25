package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseTest {

    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;

    @Before
    public void setUp() {
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12));
        new CourierSteps()
                .courierCreate(courier);
    }

    @DisplayName("Получение логина курьера")
    @Test
    public void LoginCourierTestReturn200() {

        new CourierSteps()
                .loginCourier(courier)
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @DisplayName("Получение логина курьера при неправильном вводе логина")
    @Test
    public void LoginCourierTestIfLoginIncorrectReturn404() {

        Courier invalidCourier = new Courier()
                .setLogin("nonexistent_login_" + RandomStringUtils.randomAlphabetic(5))
                .setPassword(courier.getPassword());

        courierSteps.loginCourier(invalidCourier)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Получение логина курьера при неправильном вводе пароля")
    @Test
    public void LoginCourierTestIfPasswordIncorrectReturn404() {

        Courier invalidCourier = new Courier()
                .setLogin(courier.getLogin())
                .setPassword("nonexistent_" + RandomStringUtils.randomAlphabetic(5));

        courierSteps.loginCourier(invalidCourier)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Получение логина курьера без ввода логина")
    @Test
    public void LoginCourierTestIfLoginNullReturn400() {

        Courier invalidCourier = new Courier()
                .setLogin(null)
                .setPassword(courier.getPassword());

        courierSteps.loginCourier(invalidCourier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Получение логина курьера без ввода пароля")
    @Test
    public void LoginCourierTestIfPasswordNullReturn400() {

        Courier invalidCourier = new Courier()
                .setLogin(courier.getLogin())
                .setPassword(null);

        courierSteps.loginCourier(invalidCourier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(courier)
                .extract().body().path("id");
        courier.setId(id);
        courierSteps.deleteCourier(courier);
    }
}
