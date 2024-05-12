import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.OrdersAPI;
import stellarburgers.staticmethodsandvariables.UserAPI;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {
    OrdersAPI ordersAPI = new OrdersAPI();
    UserAPI userAPI = new UserAPI();
    @Test
    public void doesCreateOrderWithAuthorizationWork() {
        String email = "sdhfhuqdy@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        String[] ingredients = new String[] { "61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f" };
        CreateUserSuccessfulResponse response1 = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        ordersAPI.createOrder(ingredients).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));

        userAPI.deleteUser(response1.getAccessToken().replace("Bearer ",""));
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


}
