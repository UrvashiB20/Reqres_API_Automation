import Core.BaseTest;
import Utils.JsonReader;
import Utils.RetryAnalyzer;
import Utils.Routes;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import Enum.StatusCode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthTests extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to login with valid credentials")
    public void testLoginWithValidCredentials() throws IOException, ParseException {
        Map<String,String> userLoginDetails = new HashMap<>();
        userLoginDetails.put("email",JsonReader.readJsonData("email"));
        userLoginDetails.put("password",JsonReader.readJsonData("password"));
        Response response =
                requestSpecification
                        .body(userLoginDetails)
                        .when()
                        .post(Routes.LOGIN)
                        .then()
                        .extract()
                        .response();

        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code,"The expected status code was 200 but actual status code is "+response.getStatusCode());
        softAssert.assertTrue(!response.getBody().asString().isEmpty(),"Expected response was not empty but the actual response is empty");
        Map responseBody = response.as(Map.class);
        softAssert.assertTrue(responseBody.containsKey("token"),"Login Failed");
        softAssert.assertAll();
        System.out.println("testLoginWithValidCredentials passed successfully");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to login with invalid credentials")
    public void testLoginWithInvalidCredentials() throws IOException, ParseException {
        Map<String,String> userLoginDetails = new HashMap<>();
        userLoginDetails.put("email",JsonReader.readJsonData("email"));
        Response response =
                requestSpecification
                        .body(userLoginDetails)
                        .when()
                        .post(Routes.LOGIN)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.BAD_REQUEST.code,"The expected status code was 400 but actual status code is "+response.getStatusCode());
        softAssert.assertTrue(!response.getBody().asString().isEmpty(),"Expected response was not empty but the actual response is empty");
        Map responseBody = response.as(Map.class);
        softAssert.assertTrue(responseBody.containsKey("error"),"Login Successfully");
        softAssert.assertAll();
        System.out.println("testLoginWithInvalidCredentials passed successfully");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to register user with valid credentials")
    public void testRegisterUserWithValidCredentials() throws IOException, ParseException {
        Map<String,String> userRegisterDetails = new HashMap<>();
        userRegisterDetails.put("email", JsonReader.readJsonData("email"));
        userRegisterDetails.put("password",JsonReader.readJsonData("password"));
        Response response=
                requestSpecification
                        .body(userRegisterDetails)
                        .when()
                        .post(Routes.REGISTER)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code,"The expected status code was 200 but actual status code is "+response.getStatusCode());
        softAssert.assertTrue(!response.getBody().asString().isEmpty(),"Expected response was not empty but the actual response is empty");
        Map responseRegisterBody = response.as(Map.class);
        softAssert.assertTrue(responseRegisterBody.containsKey("id") && responseRegisterBody.containsKey("token"),"Register failed. Fill the required details");
        softAssert.assertAll();
        System.out.println("testRegisterUserWithValidCredentials passed successfully");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to register user with invalid credentials")
    public void testRegisterUserWithInvalidCredentials() throws IOException, ParseException {
        Map<String,String> userRegisterDetails = new HashMap<>();
        userRegisterDetails.put("email", JsonReader.readJsonData("email"));
        Response response=
                requestSpecification
                        .body(userRegisterDetails)
                        .when()
                        .post(Routes.REGISTER)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(), StatusCode.BAD_REQUEST.code,"The expected status code was 400 but actual status code is "+response.getStatusCode());
        softAssert.assertTrue(!response.getBody().asString().isEmpty(),"Expected response was not empty but the actual response is empty");
        Map responseRegisterBody = response.as(Map.class);
        softAssert.assertTrue(responseRegisterBody.containsKey("error"),"Successfully Registered a user");
        softAssert.assertAll();
        System.out.println("testRegisterUserWithInvalidCredentials passed successfully");
    }
}