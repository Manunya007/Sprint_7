package org.example;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
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
@DisplayName("Создание курьера")
    @Test
    public void courierCreateReturn201() {

courierSteps
        .courierCreate(courier)
        .statusCode(SC_CREATED)
                .body("ok", is(true));
    }
    @DisplayName("Создание дубликата курьера")
    @Test
    public void duplicateCourierCreateReturn409(){

        courierSteps
                .courierCreate(courier);
     courierSteps
                .courierCreate(courier)
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));

    }
    @DisplayName("Создание курьера без пароля")
    @Test
    public void courierCreateNoPasswordReturn400(){
        Courier courierWithoutPassword = new Courier()
                    .setLogin(RandomStringUtils.randomAlphabetic(12));
        courierSteps
                    .courierCreate(courierWithoutPassword)
                    .statusCode(SC_BAD_REQUEST)
                    .body("message", is("Недостаточно данных для создания учетной записи"));
        }
@DisplayName("Создание курьера без логина")
    @Test
    public void courierCreateNoLoginReturn400(){
        Courier courierWithoutLogin = new Courier()
                .setPassword(RandomStringUtils.randomAlphabetic(12));
        courierSteps
                .courierCreate(courierWithoutLogin)
                .statusCode(SC_BAD_REQUEST)
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
                                .statusCode(SC_CREATED);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
