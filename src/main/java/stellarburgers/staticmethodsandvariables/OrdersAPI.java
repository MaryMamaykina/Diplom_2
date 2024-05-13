package stellarburgers.staticmethodsandvariables;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import stellarburgers.dto.CreateOrderRequest;


import static io.restassured.RestAssured.given;

public class OrdersAPI extends StellarBurgersAPI{
    private static final String handleForOrders = "api/orders";
    private static final String handleForIngredients = "api/ingredients";
    public Response createOrder(String[] ingredients) {
        CreateOrderRequest newOrder = new CreateOrderRequest(ingredients);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newOrder)
                .when()
                .post(handleForOrders);
        return response;
    }
    public Response receivingUserOrders(String accessToken) {
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .when()
                .get(handleForOrders);
        return response;
    }
    public ValidatableResponse getListOfIngredients() {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .when()
                .get(handleForIngredients).then();
        return response;
    }
}
