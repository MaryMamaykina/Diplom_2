import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.UserAPI;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    UserAPI userAPI = new UserAPI();
    String email = "sdhfhdqwes@yandex.ru";
    String password= "ffhhhj";
    String name = "Ivan";
    String userAssesToken;
    @Before
    public void setup() {
        userAPI.createUser(email,password,name);
    }
    @Test
    public void doesLoginUserWork(){

        userAPI.loginUser(email,password).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    public void doesLoginUserWithWrongEmailNotWork(){
        String wrongEmail = "addfjgfjk@yandex.ru";
        userAPI.loginUser(wrongEmail,password).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    public void doesLoginUserWithWrongPasswordNotWork(){
        String wrongPassword = "adfgsrh";
        userAPI.loginUser(email,wrongPassword).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    public void doesLoginUserWithWrongEmailAndPasswordNotWork(){
        String wrongEmail = "addfjgfjk@yandex.ru";
        String wrongPassword = "adfgsrh";
        userAPI.loginUser(wrongEmail,wrongPassword).then().statusCode(SC_UNAUTHORIZED)
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
