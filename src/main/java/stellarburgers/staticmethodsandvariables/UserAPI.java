package stellarburgers.staticmethodsandvariables;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellarburgers.dto.*;

import static io.restassured.RestAssured.given;

public class UserAPI extends StellarBurgersAPI {
    private static final String handleForCreateUser = "api/auth/register";
    private static final String handleForDeleteAndPatchUser = "api/auth/user";
    private static final String handleForLoginUser = "api/auth/login";

    @Step("Создание пользователя")
    public Response createUser(String givenEmail, String givenPassword, String givenName) {
        CreateUserRequest newUser = new CreateUserRequest(givenEmail, givenPassword, givenName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newUser)
                .when()
                .post(handleForCreateUser);
        return response;
    }
    @Step("Авторизация пользователя")
    public Response loginUser(String givenEmail, String givenPassword) {
        LoginUserRequest newUser = new LoginUserRequest(givenEmail, givenPassword);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newUser)
                .when()
                .post(handleForLoginUser);
        return response;
    }
    @Step("Удаление пользователя")
    public Response deleteUser (String accessToken) {
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .when()
                .delete(handleForDeleteAndPatchUser);
        return response;
    }
    @Step("Изменение данных пользователя")
    public Response changeUserData (String givenEmail, String givenName, String accessToken) {
        ChangeUserDataRequest newUser = new ChangeUserDataRequest(givenEmail, givenName);
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(newUser)
                .when()
                .patch(handleForDeleteAndPatchUser);
        return response;
    }

}
