import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.OrdersAPI;
import stellarburgers.staticmethodsandvariables.UserAPI;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {
    OrdersAPI ordersAPI = new OrdersAPI();
    UserAPI userAPI = new UserAPI();
    String email = "sdhfhuqdy@yandex.ru";
    String password = "ffhhhj";
    String name = "Ivan";
    String userAssesToken;

    @Test
    public void doesCreateOrderWithAuthorizationWork() {
        String[] ingredients = new String[] { "61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f" };
        userAPI.createUser(email,password,name);
        ordersAPI.createOrder(ingredients).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }
    @Test
    public void doesCreateOrderWithoutAuthorizationWork() {
        String[] ingredients = new String[] {"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f"};
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
