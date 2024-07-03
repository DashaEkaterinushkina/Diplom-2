import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class User extends BaseClient {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(String json) {
        return RequestSpecification()
                .body(json)
                .post(Config.USER_CREATE)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse login(String json) {
        return RequestSpecification()
                .body(json)
                .post(Config.USER_LOGIN)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String token,String json) {
        return RequestSpecification()
                .auth().oauth2(token)
                .body(json)
                .delete(Config.USER_DELETE)
                .then();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse update(String token,String json) {
        return RequestSpecification()
                .auth().oauth2(token)
                .body(json)
                .patch(Config.USER_UPDATE)
                .then();
    }
}
