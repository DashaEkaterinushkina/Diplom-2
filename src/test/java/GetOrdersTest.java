import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import static org.hamcrest.Matchers.is;

public class GetOrdersTest {
    String email;
    String json;
    User users = new User();
    String accessToken = "";
    String delete = "Bearer ";
    Random random = new Random();

    @Before
    public void setUp() {
        email = "praktikum" + random.nextInt(10000000) + "@yandex.ru";
        json = "{\"email\": \"" + email + "\", \"password\": \"praktikum100\", \"name\": \"praktikum\" }";
    }

    @Test
    @DisplayName("Проверка получания заказа без входа")
    public void checkGetOrdersWithoutAuthorizeTest(){
        new Order().getOrder(accessToken)
                .statusCode(401)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Проверка получания заказа после входа")
    public void checkGetOrdersWithAuthorizeTest(){
        new User().createUser(json)
                .statusCode(200);

        accessToken = users.login(json)
                .extract().body().path("accessToken");

        new User().login(json)
                .statusCode(200);

        accessToken = accessToken.replace (delete, "");

        new Order().getOrder(accessToken)
                .statusCode(200)
                .and()
                .body("success", is(true));

        //Удаление созданного пользователя
        String delete = "Bearer ";
        accessToken = accessToken.replace (delete, "");
        users.delete(accessToken,json);
    }
}
