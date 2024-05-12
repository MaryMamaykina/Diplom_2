import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    UserAPI userAPI = new UserAPI();
    @Test
    public void doesCreatingNewUserWork(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        Assert.assertTrue(response.isSuccess());

        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }
    @Test
    public void IsCreatingUserWhoAlreadyRegisteredImpossible(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        Response response = userAPI.createUser(email,password,name);
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
        userAPI.deleteUser(response.as(CreateUserSuccessfulResponse.class).getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void IsCreatingUserWithoutEmailImpossible(){
        String email = null;
        String password = "ffhhhj";
        String name = "Ivan";
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    public void IsCreatingUserWithoutPasswordImpossible(){
        String email = "sdhfhds@yandex.ru";
        String password = null;
        String name = "Ivan";
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    public void IsCreatingUserWithoutNameImpossible(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = null;
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

}
