import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class GetOrdersTest extends BaseTest {
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
    }
}
