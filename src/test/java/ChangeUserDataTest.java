import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.staticmethodsandvariables.UserAPI;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest {
    UserAPI userAPI = new UserAPI();
    @Test
    public void doesChangingUserDataWithAuthorizationWork() {
        String email = "sdhfhud@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        String newEmail = "like@yandex.ru";
        String newName = "cuteIvan";
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
    public void doesChangingUserDataWithAuthorizationAllowsChangeName() {
        String email = "sdhfhud@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        String newName = "cuteIvan";
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
    public void doesChangingUserDataWithAuthorizationAllowsChangeEmail() {
        String email = "sdhfhud@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        String newEmail = "like@yandex.ru";
        CreateUserSuccessfulResponse response1 = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        String accessToken = response1.getAccessToken().replace("Bearer ","");
        userAPI.changeUserData(name, newEmail, accessToken).then().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.name", equalTo(newEmail));
        userAPI.deleteUser(accessToken);
    }
    @Test
    public void doesNotChangingUserDataWithoutAuthorizationWork() {
        String newEmail = "like@yandex.ru";
        String newName = "cuteIvan";

        userAPI.changeUserData(newEmail, newName, "").then().statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }






}
