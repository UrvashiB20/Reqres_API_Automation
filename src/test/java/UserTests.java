import Core.BaseTest;
import Pojo.UserRequestData;
import Pojo.UserResponseData;
import Utils.DataProviders;
import Utils.RetryAnalyzer;
import Utils.Routes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Enum.StatusCode;
import java.util.List;
import java.util.Map;

public class UserTests extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to retrieve the list of users")
    public void testGetUsers(){
        Response response =
                requestSpecification
                        .when()
                        .get(Routes.USERS)
                        .then()
                        .extract()
                        .response();

        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"The expected status code was 200 but actual status code is "+response.getStatusCode());
        List<Object> responseUserList = response.jsonPath().getList("data");
        softAssert.assertTrue(!responseUserList.isEmpty(),"The user list is empty");
        softAssert.assertAll();
        System.out.println("testGetUsers passed successfully");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to retrieve the single user")
    public void testGetSingleUser(){
        Response response =
                requestSpecification
                        .pathParam("id",2)
                        .when()
                        .get(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"The expected status code was 200 but actual status code is "+response.getStatusCode());
        Map<String,Object> responseUserMap = response.jsonPath().getMap("data");
        softAssert.assertTrue(!responseUserMap.isEmpty(),"The user data is empty");
        softAssert.assertEquals(response.jsonPath().getInt("data.id"),2,"The expected and actual id does not match");
        softAssert.assertEquals(response.jsonPath().getString("data.email"),"janet.weaver@reqres.in","The expected and actual email does not match");
        softAssert.assertAll();
        System.out.println("testGetSingleUser passed successfully");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "createUserData", dataProviderClass = DataProviders.class, description = "Test to create a user")
    public void testCreateUser(String name, String job){
        UserRequestData user = new UserRequestData();
        user.setName(name);
        user.setJob(job);
        Response response=
                requestSpecification
                        .body(user)
                        .when()
                        .post(Routes.USERS)
                        .then()
                        .extract()
                        .response();
        UserResponseData userResponseBody = response.as(UserResponseData.class);
        softAssert.assertEquals(response.getStatusCode(),StatusCode.CREATED.code,"The expected status code was 201 but actual status code is "+response.getStatusCode());
        softAssert.assertEquals(response.jsonPath().getString("name"),userResponseBody.getName(),"Name mismatched between expected and actual response");
        softAssert.assertEquals(response.jsonPath().getString("job"),userResponseBody.getJob(),"Job mismatched between expected and actual response");
        softAssert.assertAll();
        System.out.println("testCreateUser passed successfully and User details are Name: "+name+" | Job: "+job);

    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to update a user")
    public void testUpdateUser(){
        UserRequestData user = new UserRequestData();
        user.setName("morpheus");
        user.setJob("Developer");
        Response response=
                requestSpecification
                        .body(user)
                        .pathParam("id",2)
                        .when()
                        .put(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        UserResponseData userResponseBody = response.as(UserResponseData.class);
        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"The expected status code was 200 but actual status code is "+response.getStatusCode());
        softAssert.assertEquals(response.jsonPath().getString("name"),userResponseBody.getName(),"Name mismatched between expected and actual response");
        softAssert.assertEquals(response.jsonPath().getString("job"),userResponseBody.getJob(),"Job mismatched between expected and actual response");
        Map responseMap = response.as(Map.class);
        softAssert.assertTrue(responseMap.containsKey("updatedAt"),"UpdatedAt field is not present");
        softAssert.assertTrue(!response.jsonPath().getString("updatedAt").isEmpty(), "Timestamp is empty");
        softAssert.assertAll();
        System.out.println("testUpdateUser passed successfully");

    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to update a user partially")
    public void testUpdateUserPartially(){
        UserRequestData user = new UserRequestData();
        user.setName("Anna");
        Response response=
                requestSpecification
                        .body(user)
                        .pathParam("id",4)
                        .when()
                        .patch(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        UserResponseData userResponseBody = response.as(UserResponseData.class);
        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"The expected status code was 200 but actual status code is "+response.getStatusCode());
        softAssert.assertEquals(response.jsonPath().getString("name"),userResponseBody.getName(),"Name mismatched between expected and actual response");
        Map responseMap = response.as(Map.class);
        softAssert.assertTrue(responseMap.containsKey("updatedAt"),"UpdatedAt field is not present");
        softAssert.assertTrue(!response.jsonPath().getString("updatedAt").isEmpty(), "Timestamp is empty");
        softAssert.assertAll();
        System.out.println("testUpdateUserPartially passed successfully");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to delete a user")
    public void testDeleteUser(){
        Response response =
                requestSpecification
                        .pathParam("id", 3)
                        .when()
                        .delete(Routes.USERS_ID)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(),StatusCode.NO_CONTENT.code, "The expected status code was 204 but actual status code is "+response.getStatusCode());
        softAssert.assertTrue(response.getBody().asString().trim().isEmpty(), "Expected empty response but actual response is "+response.getBody().asString());
        softAssert.assertAll();
        System.out.println("testDeleteUser passed successfully");
    }
}