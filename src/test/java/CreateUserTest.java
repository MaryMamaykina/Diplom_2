import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    UserAPI userAPI = new UserAPI();
    String email= "sdhfhds@yandex.ru";
    String password = "ffhhhj";
    String name = "Ivan";
    String userAssesToken;
    @Test
    public void doesCreatingNewUserWork(){
        userAPI.createUser(email,password,name).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }
    @Test
    public void IsCreatingUserWhoAlreadyRegisteredImpossible(){
        userAPI.createUser(email,password,name);
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    public void IsCreatingUserWithoutEmailImpossible(){
        email = null;
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    public void IsCreatingUserWithoutPasswordImpossible(){
        password = null;
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    public void IsCreatingUserWithoutNameImpossible(){
        name = null;
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
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
