import org.junit.After;
import org.junit.Before;

import java.util.List;
import java.util.Random;

public class BaseTest {
    String email;
    String json;
    Random random = new Random();
    List<String> ingredients;
    OrderRequest orderRequest;
    String accessToken = "";
    User users = new User();
    String delete = "Bearer ";

    @Before
    public void setUp() {
        email = "praktikum" + random.nextInt(10000000) + "@yandex.ru";
        json = "{\"email\": \"" + email + "\", \"password\": \"praktikum100\", \"name\": \"praktikum\" }";
        ingredients = new Order().getIngredient();
        orderRequest = new OrderRequest(List.of(ingredients.get(0), ingredients.get(1)));
    }

    @After
    public void deleteUser(){
        if(!accessToken.isEmpty()){
            String delete = "Bearer ";
            accessToken = accessToken.replace (delete, "");
            users.delete(accessToken,json);
        }
    }
}
