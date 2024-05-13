import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.UserAPI;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    UserAPI userAPI = new UserAPI();
    String email = (RandomStringUtils.randomAlphabetic(9) + "@yandex.ru").toLowerCase();
    String password = RandomStringUtils.randomAlphabetic(7);
    String name = RandomStringUtils.randomAlphabetic(11);
    String wrongEmail = (RandomStringUtils.randomAlphabetic(8) + "@yandex.ru").toLowerCase();
    String wrongPassword = RandomStringUtils.randomAlphabetic(6);

    String userAssesToken;
    @Before
    public void setup() {
        userAPI.createUser(email,password,name);
    }
    @Test
    @DisplayName("Авторизация пользователя работает?")
    @Description("Проверка авторизации под существующим пользователем")
    public void doesLoginUserWork(){

        userAPI.loginUser(email,password).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация пользователя c неправильным email невозможна?")
    @Description("Проверка невозможности авторизации c неправильным email")
    public void doesLoginUserWithWrongEmailImpossible(){
        userAPI.loginUser(wrongEmail,password).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Авторизация пользователя c неправильным паролем невозможна?")
    @Description("Проверка невозможности авторизации c неправильным паролем")
    public void doesLoginUserWithWrongPasswordNotWork(){
        userAPI.loginUser(email,wrongPassword).then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Авторизация пользователя c неправильным email и паролем невозможна?")
    @Description("Проверка невозможности авторизации c неправильным email и паролем")
    public void doesLoginUserWithWrongEmailAndPasswordNotWork(){
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
