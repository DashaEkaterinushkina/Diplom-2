import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.List;

public class Order extends BaseClient {

    @Step("Создание заказа")
    public ValidatableResponse createOrder(OrderRequest orderRequest, String token) {
        return RequestSpecification()
                        .auth().oauth2(token)
                        .body(orderRequest)
                        .when()
                        .post(Config.ORDER_CREATE)
                        .then();
    }
    @Step("Получение заказа")
    public ValidatableResponse getOrder(String token) {
        return RequestSpecification()
                        .auth().oauth2(token)
                        .get(Config.ORDER_GET)
                        .then();
    }

    @Step("Получение ингредиентов")
    public List<String> getIngredient(){
        return RequestSpecification()
                .when()
                .get(Config.INGREDIENT_GET)
                .then()
                .statusCode(200)
                .extract()
                .path("data._id");
    }

}
