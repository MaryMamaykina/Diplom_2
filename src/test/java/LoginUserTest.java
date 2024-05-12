import org.junit.Assert;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.dto.ErrorMessageResponse;
import stellarburgers.staticmethodsandvariables.UserAPI;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    UserAPI userAPI = new UserAPI();
    @Test
    public void doesLoginUserWork(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        userAPI.loginUser(email,password).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void doesLoginUserWithWrongEmailNotWork(){
        String email = "sdhfhds@yandex.ru";
        String wrongEmail = "addfjgfjk@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        userAPI.loginUser(wrongEmail,password).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void doesLoginUserWithWrongPasswordNotWork(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String wrongPassword = "adfgsrh";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        userAPI.loginUser(email,wrongPassword).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void doesLoginUserWithWrongEmailAndPasswordNotWork(){
        String email = "sdhfhds@yandex.ru";
        String wrongEmail = "addfjgfjk@yandex.ru";
        String password = "ffhhhj";
        String wrongPassword = "adfgsrh";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        userAPI.loginUser(wrongEmail,wrongPassword).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }


}
