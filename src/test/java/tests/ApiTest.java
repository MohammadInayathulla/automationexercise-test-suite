package tests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ConfigReader;

public class ApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.get("base.url");
    }

    // This site returns HTTP 200 for everything and puts the real status
    // in the JSON body as "responseCode", and the content type is text/html,
    // so we parse the body ourselves instead of using .body("...") matchers.
    private JsonPath json(Response response) {
        return new JsonPath(response.asString());
    }

    @Test
    public void getProductsListReturnsProducts() {
        Response response = given().when().get("/api/productsList");
        JsonPath body = json(response);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(body.getInt("responseCode"), 200);
        Assert.assertFalse(body.getList("products").isEmpty(), "Products list is empty");
    }

    @Test
    public void getBrandsListReturnsBrands() {
        Response response = given().when().get("/api/brandsList");
        JsonPath body = json(response);

        Assert.assertEquals(body.getInt("responseCode"), 200);
        Assert.assertFalse(body.getList("brands").isEmpty(), "Brands list is empty");
    }

    @Test
    public void postToProductsListIsNotSupported() {
        Response response = given().when().post("/api/productsList");
        JsonPath body = json(response);

        Assert.assertEquals(body.getInt("responseCode"), 405);
        Assert.assertEquals(body.getString("message"), "This request method is not supported.");
    }

    @Test
    public void searchProductReturnsMatches() {
        Response response = given()
                .formParam("search_product", "top")
                .when().post("/api/searchProduct");
        JsonPath body = json(response);

        Assert.assertEquals(body.getInt("responseCode"), 200);
        Assert.assertFalse(body.getList("products").isEmpty(), "No products found for 'top'");
    }

    @Test
    public void searchProductWithoutParamReturns400() {
        Response response = given().when().post("/api/searchProduct");
        JsonPath body = json(response);

        Assert.assertEquals(body.getInt("responseCode"), 400);
    }

    @Test
    public void verifyLoginWithValidCredentials() {
        Response response = given()
                .formParam("email", ConfigReader.get("user.email"))
                .formParam("password", ConfigReader.get("user.password"))
                .when().post("/api/verifyLogin");
        JsonPath body = json(response);

        Assert.assertEquals(body.getInt("responseCode"), 200);
        Assert.assertEquals(body.getString("message"), "User exists!");
    }

    @Test
    public void verifyLoginWithInvalidCredentials() {
        Response response = given()
                .formParam("email", "no_such_user@example.com")
                .formParam("password", "wrongPassword123")
                .when().post("/api/verifyLogin");
        JsonPath body = json(response);

        Assert.assertEquals(body.getInt("responseCode"), 404);
        Assert.assertEquals(body.getString("message"), "User not found!");
    }
}