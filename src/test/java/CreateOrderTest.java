import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.hamcrest.Matchers.is;

public class CreateOrderTest {
    String email;
    String json;
    User users = new User();
    String accessToken = "";
    String delete = "Bearer ";
    Random random = new Random();
    List<String> ingredients;
    OrderRequest orderRequest;

    @Before
    public void setUp() {
        email = "praktikum" + random.nextInt(10000000) + "@yandex.ru";
        json = "{\"email\": \"" + email + "\", \"password\": \"praktikum100\", \"name\": \"praktikum\" }";
        ingredients = new Order().getIngredient();
        orderRequest = new OrderRequest(List.of(ingredients.get(0), ingredients.get(1)));
    }

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

        String delete = "Bearer ";
        accessToken = accessToken.replace (delete, "");
        users.delete(accessToken,json);
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
