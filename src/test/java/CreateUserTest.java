import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    UserAPI userAPI = new UserAPI();
    String email = (RandomStringUtils.randomAlphabetic(9) + "@yandex.ru").toLowerCase();
    String password = RandomStringUtils.randomAlphabetic(7);
    String name = RandomStringUtils.randomAlphabetic(11);
    String userAssesToken;
    @Test
    @DisplayName("Создание пользователя работает?")
    @Description("Проверка возможности создания юзера")
    public void doesCreatingNewUserWork(){
        userAPI.createUser(email,password,name).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName("Создание пользователя, который уже существует невозможно?")
    @Description("Проверка невозможности создать пользователя, который уже зарегистрирован")
    public void IsCreatingUserWhoAlreadyRegisteredImpossible(){
        userAPI.createUser(email,password,name);
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без email невозможно?")
    @Description("Проверка невозможности создать пользователя без email")
    public void IsCreatingUserWithoutEmailImpossible(){
        email = null;
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без пароля невозможно?")
    @Description("Проверка невозможности создать пользователя без пароля")
    public void IsCreatingUserWithoutPasswordImpossible(){
        password = null;
        userAPI.createUser(email,password,name).then().statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без имени невозможно?")
    @Description("Проверка невозможности создать пользователя без имени")
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
