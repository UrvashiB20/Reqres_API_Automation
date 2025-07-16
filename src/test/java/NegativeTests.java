import Core.BaseTest;
import Utils.Routes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Pojo.UserRequestData;
import Enum.StatusCode;

    public class NegativeTests extends BaseTest {

        @Test(description = "Test to fetch the user that does not exist")
        public void testGetInvalidUser(){
            Response response =
                    requestSpecification
                            .pathParam("id",23)
                            .when()
                            .get(Routes.USERS_ID)
                            .then()
                            .extract()
                            .response();
            softAssert.assertEquals(response.getStatusCode(), StatusCode.NOT_FOUND.code,"The expected status code was 404 but actual status code is "+response.getStatusCode());
            String responseBody =response.getBody().asString().trim();
            softAssert.assertEquals(responseBody,"{}","The expected and actual result does not match");
            System.out.println("testGetInvalidUser passed successfully");
        }
//-------------------------------------------------Not Supported On Reqres.in API---------------------------------------------------------------
//    @Test(description = "Test to create user with empty payload/json")
//    public void testCreateUserWithEmptyPayload(){
//        Response response =
//                requestSpecification
//                        .when()
//                        .post(Routes.USERS)
//                        .then()
//                        .extract()
//                        .response();
//        softAssert.assertEquals(response.getStatusCode(),StatusCode.BAD_REQUEST.code,"The expected status code was 400 but actual status code is "+response.getStatusCode());
//        System.out.println("testCreateUserWithEmptyPayload passed successfully");
//    }

// @Test(description = "Test to update non-existent user")
//    public void testUpdateInvalidUser(){
//        UserRequestData userRequestData = new UserRequestData();
//        userRequestData.setName("Jenna");
//        userRequestData.setJob("Quality Analyst");
//        Response response =
//                requestSpecification
//                        .pathParam("id",25)
//                        .body(userRequestData)
//                        .when()
//                        .patch(Routes.USERS_ID)
//                        .then()
//                        .extract()
//                        .response();
//        softAssert.assertEquals(response.getStatusCode(),StatusCode.NOT_FOUND.code,"The expected status code was 404 but actual status code is "+response.getStatusCode());
//        System.out.println("testUpdateInvalidUser passed successfully");
//    }

//    @Test(description = "Test to delete non-existent user")
//    public void testDeleteInvalidUser(){
//        Response response =
//                requestSpecification
//                        .pathParam("id",10)
//                        .when()
//                        .delete(Routes.USERS_ID)
//                        .then()
//                        .extract()
//                        .response();
//        softAssert.assertEquals(response.getStatusCode(),StatusCode.NOT_FOUND.code,"The expected status code was 404 but actual status code is "+response.getStatusCode());
//        System.out.println("testDeleteInvalidUser passed successfully");
//            }
}