import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.UserAPI;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest {
    UserAPI userAPI = new UserAPI();
    String email = (RandomStringUtils.randomAlphabetic(9) + "@yandex.ru").toLowerCase();
    String password = RandomStringUtils.randomAlphabetic(7);
    String name = RandomStringUtils.randomAlphabetic(11);
    String newEmail = (RandomStringUtils.randomAlphabetic(10) + "@yandex.ru").toLowerCase();
    String newName = RandomStringUtils.randomAlphabetic(10);

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией работает?")
    @Description("Проверка возможности изменять данные пользователя. Изменение данных в полях name и email")
    public void doesChangingUserDataWithAuthorizationWork() {

        CreateUserSuccessfulResponse response1 = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        String accessToken = response1.getAccessToken().replace("Bearer ","");
        userAPI.changeUserData(newEmail, newName, accessToken).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(newEmail))
                .and()
                .body("user.name", equalTo(newName));
        userAPI.deleteUser(accessToken);
    }
    @Test
    @DisplayName("Изменение данных пользователя с авторизацией позволяет менять имя пользователя?")
    @Description("Проверка возможности изменять данные пользователя. Изменение данных только в поле name")
    public void doesChangingUserDataWithAuthorizationAllowsChangeName() {
        CreateUserSuccessfulResponse response1 = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        String accessToken = response1.getAccessToken().replace("Bearer ","");
        userAPI.changeUserData(email, newName, accessToken).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.name", equalTo(newName));
        userAPI.deleteUser(accessToken);
    }
    @Test
    @DisplayName("Изменение данных пользователя с авторизацией позволяет менять email пользователя?")
    @Description("Проверка возможности изменять данные пользователя. Изменение данных только в поле email")
    public void doesChangingUserDataWithAuthorizationAllowsChangeEmail() {
        CreateUserSuccessfulResponse response1 = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        String accessToken = response1.getAccessToken().replace("Bearer ","");
        userAPI.changeUserData(newEmail, name, accessToken).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(newEmail));
        userAPI.deleteUser(accessToken);
    }
    @Test
    @DisplayName("Изменение данных пользователя без авторизации невозможно?")
    @Description("Проверка невозможности изменять данные пользователя без авторизации")
    public void doesChangingUserDataWithoutAuthorizationImpossible() {
        userAPI.changeUserData(newEmail, newName, "").then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }






}
