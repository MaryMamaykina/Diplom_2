import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.OrdersAPI;
import stellarburgers.staticmethodsandvariables.UserAPI;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {
    OrdersAPI ordersAPI = new OrdersAPI();
    UserAPI userAPI = new UserAPI();
    String email = (RandomStringUtils.randomAlphabetic(9) + "@yandex.ru").toLowerCase();
    String password = RandomStringUtils.randomAlphabetic(7);
    String name = RandomStringUtils.randomAlphabetic(11);
    String userAssesToken;
    ArrayList<String> ingredientsFromAPI;
    @Before
    public void setup() {
        ingredientsFromAPI =ordersAPI.getListOfIngredients().extract().path("data[0,1,2]._id");
        System.out.println(ingredientsFromAPI);
    }

    @Test
    public void doesCreateOrderWithAuthorizationWork() {
        String[] ingredients = ingredientsFromAPI.toArray(new String[]{});
        userAPI.createUser(email,password,name);
        ordersAPI.createOrder(ingredients).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }
    @Test
    public void doesCreateOrderWithoutAuthorizationWork() {
        String[] ingredients = ingredientsFromAPI.toArray(new String[]{});
        ordersAPI.createOrder(ingredients).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }
    @Test
    public void doesNotCreateOrderWithoutAuthorizationAndIngredientsWork() {
        String[] ingredients = null;
        ordersAPI.createOrder(ingredients).then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", equalTo(false));
    }
    @Test
    public void doesNotCreateOrderWithoutAuthorizationAndWithWrongIngredientsWork() {
        String[] ingredients= new String[]{"123"};
        ordersAPI.createOrder(ingredients).then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
    @After
    public void teardown() {
        Response response = userAPI.loginUser(email,password);
        int statusCode = response.getStatusCode();
        if(statusCode==200){
            userAssesToken = response.as(CreateUserSuccessfulResponse.class).getAccessToken().replace("Bearer ", "");
            userAPI.deleteUser(userAssesToken);}
    }


}
