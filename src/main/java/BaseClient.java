import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    public static final String URL_CONST = "https://stellarburgers.nomoreparties.site/";

    public RequestSpecification RequestSpecification(){
        return given()
                .header("Content-type", "application/json")
                .baseUri(URL_CONST);
    }
}
