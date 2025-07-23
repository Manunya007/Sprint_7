package org.example;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseTest{

    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;

    @Before
    public void setUp() {
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(12))
        .setPassword(RandomStringUtils.randomAlphabetic(12));
    }
@Step("Получение логина курьера, код 200")
@Test
    public void LoginCourierTestReturn200(){

    new CourierSteps()
            .courierCreate(courier);

        new CourierSteps()
                .loginCourier(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }
@Step("Получение логина курьера при неправильном вводе логина, ошибка 404")
    @Test
    public void LoginCourierTestIfLoginIncorrectReturn404(){

        courierSteps.courierCreate(courier);

        Courier invalidCourier = new Courier()
                    .setLogin("nonexistent_login_" + RandomStringUtils.randomAlphabetic(5))
                    .setPassword(courier.getPassword());

            courierSteps.loginCourier(invalidCourier)
                    .statusCode(404)
                    .body("message", is("Учетная запись не найдена"));
        }
        @Step("Получение логина курьера при неправильном вводе пароля, ошибка 404")
    @Test
    public void LoginCourierTestIfPasswordIncorrectReturn404(){

        courierSteps.courierCreate(courier);

        Courier invalidCourier = new Courier()
                .setLogin(courier.getLogin())
                .setPassword("nonexistent_" + RandomStringUtils.randomAlphabetic(5));

        courierSteps.loginCourier(invalidCourier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }
@Step("Получение логина курьера без ввода логина, ошибка 400")
    @Test
    public void LoginCourierTestIfLoginNullReturn400(){
        courierSteps
                .courierCreate(courier);

        Courier invalidCourier = new Courier()
                .setLogin(null)
                .setPassword(courier.getPassword());

        courierSteps.loginCourier(invalidCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }
    @Step("Получение логина курьера без ввода пароля, ошибка 400")
    @Test
    public void LoginCourierTestIfPasswordNullReturn400(){
        courierSteps
                .courierCreate(courier);

        Courier invalidCourier = new Courier()
                .setLogin(courier.getLogin())
                .setPassword(null);

        courierSteps.loginCourier(invalidCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }
    @After
    public void tearDown(){
        Integer id = courierSteps.loginCourier(courier)
                .extract().body().path("id");
        courier.setId(id);
        courierSteps.deleteCourier(courier);
    }
}
