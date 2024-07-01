import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Order {

    @Step("Создание заказа")
    public ValidatableResponse createOrder(OrderRequest orderRequest, String token) {
        return
                given()
                        //  .header("Content-type", "application/json")
                        .contentType(ContentType.JSON)
                        .baseUri(Config.URL_CONST)
                        .auth().oauth2(token)
                        .body(orderRequest)
                        .when()
                        .post(Config.ORDER_CREATE)
                        .then();
    }
    @Step("Получение заказа")
    public ValidatableResponse getOrder(String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .baseUri(Config.URL_CONST)
                        .auth().oauth2(token)
                        .get(Config.ORDER_GET)
                        .then();
    }

    @Step("Получение ингредиентов")
    public List<String> getIngredient(){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Config.URL_CONST)
                .when()
                .get(Config.INGREDIENT_GET)
                .then()
                .statusCode(200)
                .extract()
                .path("data._id");
    }

}
