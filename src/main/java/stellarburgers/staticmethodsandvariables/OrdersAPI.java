package stellarburgers.staticmethodsandvariables;

import io.restassured.response.Response;
import stellarburgers.dto.CreateOrderRequest;


import static io.restassured.RestAssured.given;

public class OrdersAPI extends StellarBurgersAPI{
    private static final String handleForCreateOrder = "api/orders";
    public Response createOrder(String[] ingredients) {
        CreateOrderRequest newOrder = new CreateOrderRequest(ingredients);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newOrder)
                .when()
                .post(handleForCreateOrder);
        return response;
    }
}
