import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class User {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(String json) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Config.URL_CONST)
                .body(json)
                .post(Config.USER_CREATE)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse login(String json) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Config.URL_CONST)
                .body(json)
                .post(Config.USER_LOGIN)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String token,String json) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .baseUri(Config.URL_CONST)
                .body(json)
                .delete(Config.USER_DELETE)
                .then();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse update(String token,String json) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Config.URL_CONST)
                .auth().oauth2(token)
                .body(json)
                .patch(Config.USER_UPDATE)
                .then();
    }
}
