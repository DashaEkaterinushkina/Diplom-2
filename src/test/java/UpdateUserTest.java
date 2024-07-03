import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class UpdateUserTest extends BaseTest {
    @Test
    @DisplayName("Проверка изменения пароля пользователя с авторизацией")
    public void checkPasswordUserWithAuthorizeTest(){
        new User().createUser(json)
                .statusCode(200);

        accessToken = users.login(json)
                .extract().body().path("accessToken");

        accessToken = accessToken.replace (delete, "");

        //Меняем поле пароля
        json = "{\"email\": \"" + email + "\", \"password\": \"praktikum150\", \"name\": \"praktikum\" }";

        new User().update(accessToken, json)
                .statusCode(200)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Проверка изменения email пользователя с авторизацией")
    public void checkEmailUserWithAuthorizeTest(){
        new User().createUser(json)
                .statusCode(200);

        accessToken = users.login(json)
                .extract().body().path("accessToken");

        accessToken = accessToken.replace (delete, "");

        //Меняем поле email
        email = "praktikum" + random.nextInt(10000000) + "@yandex.ru";
        json = "{\"email\": \"" + email + "\", \"password\": \"praktikum100\", \"name\": \"praktikum\" }";

        new User().update(accessToken, json)
                .statusCode(200)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Проверка изменения email пользователя без авторизации")
    public void checkUpdateEmailWithoutAuthorize(){
        users.createUser(json)
                .extract().body().path("accessToken");

        accessToken = "";

        //Меняем поле email
        email = "praktikum" + random.nextInt(10000000) + "@yandex.ru";
        json = "{\"email\": \"" + email + "\", \"password\": \"praktikum100\", \"name\": \"praktikum\" }";

        new User().update(accessToken, json)
                .statusCode(401)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Проверка изменения пароля пользователя без авторизации")
    public void checkUpdatePasswordWithoutAuthorize(){
        users.createUser(json)
                .extract().body().path("accessToken");

        accessToken = "";

        //Меняем поле password
        json = "{\"email\": \"" + email + "\", \"password\": \"praktikum25\", \"name\": \"praktikum\" }";

        new User().update(accessToken, json)
                .statusCode(401)
                .and()
                .body("success", is(false));
    }
}
