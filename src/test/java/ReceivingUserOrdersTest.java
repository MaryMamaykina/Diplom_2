import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.OrdersAPI;
import stellarburgers.staticmethodsandvariables.UserAPI;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class ReceivingUserOrdersTest {
    UserAPI userAPI = new UserAPI();
    OrdersAPI ordersAPI = new OrdersAPI();
    String email = "sdhfhuqdy@yandex.ru";
    String password = "ffhhhj";
    String name = "Ivan";
    String userAssesToken;

    @Test
    public void doesReceivingUserOrdersWithAuthorizationWork() {

        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        String accessToken = response.getAccessToken().replace("Bearer ","");
        ordersAPI.receivingUserOrders(accessToken).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
        userAPI.deleteUser(accessToken);
    }
    @Test
    public void doesNotReceivingUserOrdersWithoutAuthorizationWork() {
        String accessToken = "";
        ordersAPI.receivingUserOrders(accessToken).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
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
