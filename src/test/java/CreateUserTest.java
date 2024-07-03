import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class CreateUserTest extends BaseTest {

    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void checkCreateUniqueUserTest(){
        new User().createUser(json)
                .statusCode(200)
                .and()
                .body("success", is(true));

        accessToken = users.login(json)
                .extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Проверка создания пользователя, который уже зарегистрирован")
    public void checkCreateDoubleUserTest() {
        new User().createUser(json)
                .statusCode(200);

        accessToken = users.login(json)
                .extract().body().path("accessToken");

        new User().createUser(json)
                .assertThat()
                .body("success", is(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Проверка создания пользователя без одного из обязательных полей")
    public void checkEmptyFieldCreateUserTest(){
        json = "{ \"password\": \"praktikum\", \"name\": \"praktikum\" }";

        new User().createUser(json)
                .statusCode(403)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Проверка входа под существующим пользователем")
    public void checkLoginExistingUserTest(){
        new User().createUser(json)
                .statusCode(200);

        accessToken = users.login(json)
                .extract().body().path("accessToken");

        new User().login(json)
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка входа с неверным логином и паролем")
    public void checkAuthorizeFailTest(){
        json = "{\"email\": \"praktikum665@yandex.ru\", \"password\": \"praktikum100\", \"name\": \"praktikum\" }";

        new User().login(json)
                .statusCode(401)
                .and()
                .body("success", is(false));
    }
}
