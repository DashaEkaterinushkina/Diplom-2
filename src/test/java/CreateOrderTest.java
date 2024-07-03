import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import java.util.*;
import static org.hamcrest.Matchers.is;

public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("Проверка создания заказа с авторизацией и с ингредиентами")
    public void checkCreateOrderWithAuthorize(){
        new User().createUser(json)
                .statusCode(200);

        accessToken = users.login(json)
                .extract().body().path("accessToken");

        accessToken = accessToken.replace (delete, "");

        new Order().createOrder(orderRequest, accessToken)
                .statusCode(200)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Проверка создания заказа без авторизации и с ингредиентами")
    public void checkCreateOrderWithoutAuthorize() {
        new Order().createOrder(orderRequest, accessToken)    //accessToken - пустой
                .statusCode(200)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Проверка создания заказа без авторизации и без ингредиентов")
    public void checkCreateWithoutIngredients(){
        orderRequest = new OrderRequest(List.of()); //Обнуление ингредиентов
        new Order().createOrder(orderRequest, accessToken)    //ingredients и accessToken - пустые
                .statusCode(400)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Проверка создания заказа с неверным хешем ингредиентов")
    public void checkWithWrongIngredientHashTest(){
        orderRequest = new OrderRequest(List.of("{\"ingredients\": \"61c0c5a71d1g82001bdaaa73\" }"));
        new Order().createOrder(orderRequest, accessToken)    // accessToken - пустой
                .statusCode(500);   //Internal Server Error
    }
}
