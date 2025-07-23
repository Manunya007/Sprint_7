package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierTest extends BaseTest{

    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;

    @Before
            public void setUp() {
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(12))
        .setPassword(RandomStringUtils.randomAlphabetic(12));
    }
@Step("Создание курьера, возвращается код 201")
    @Test
    public void courierCreateReturn201() {

courierSteps
        .courierCreate(courier)
        .statusCode(201)
                .body("ok", is(true));
    }
    @Step("Создание дубликата курьера и возвращение ошибки 409")
    @Test
    public void duplicateCourierCreateReturn409(){

        courierSteps
                .courierCreate(courier);
     courierSteps
                .courierCreate(courier)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));

    }
    @Step("Создание курьера без пароля и возвращение ошибки 400")
    @Test
    public void courierCreateNoPasswordReturn400(){
        Courier courierWithoutPassword = new Courier()
                    .setLogin(RandomStringUtils.randomAlphabetic(12));
        courierSteps
                    .courierCreate(courierWithoutPassword)
                    .statusCode(400)
                    .body("message", is("Недостаточно данных для создания учетной записи"));
        }
@Step("Создание курьера без логина и возвращение ошибки 400")
    @Test
    public void courierCreateNoLoginReturn400(){
        Courier courierWithoutLogin = new Courier()
                .setPassword(RandomStringUtils.randomAlphabetic(12));
        courierSteps
                .courierCreate(courierWithoutLogin)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
    @After
    public void tearDown() {
        if (courier != null) {
            try {
                ValidatableResponse response = courierSteps.loginCourier(courier);
                if (response.extract().statusCode() == 201) {
                    Integer id = response.extract().body().path("id");
                    if (id != null) {
                        courierSteps.deleteCourier(new Courier().setId(id))
                                .statusCode(201);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
