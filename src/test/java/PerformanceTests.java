import Core.BaseTest;
import Utils.Routes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Enum.StatusCode;

public class PerformanceTests extends BaseTest {

    @Test(description = "Test to validate delayed response returns correct data")
    public void testValidateDelayedResponse(){
        Response response =
                requestSpecification
                        .queryParam("delay",5)
                        .when()
                        .get(Routes.USERS)
                        .then()
                        .extract()
                        .response();
        softAssert.assertEquals(response.getStatusCode(),StatusCode.SUCCESS.code,"The expected status code was 200 but actual status code is "+response.getStatusCode());
        softAssert.assertTrue(!response.jsonPath().getList("data").isEmpty(),"The user list is empty");
    }
}