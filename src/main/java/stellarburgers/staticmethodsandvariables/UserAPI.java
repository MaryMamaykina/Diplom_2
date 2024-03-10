package stellarburgers.staticmethodsandvariables;

import io.restassured.response.Response;
import stellarburgers.dto.CreateUserRequest;
import stellarburgers.dto.LoginUserRequest;

import static io.restassured.RestAssured.given;

public class UserAPI extends StellarBurgersAPI {
    private static final String handleForCreateUser = "api/auth/register";
    private static final String handleForDeleteUser = "api/auth/user";
    private static final String handleForLoginUser = "api/auth/login";
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

    public Response deleteUser (String accessToken) {
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .when()
                .delete(handleForDeleteUser);
        return response;
    }

}
