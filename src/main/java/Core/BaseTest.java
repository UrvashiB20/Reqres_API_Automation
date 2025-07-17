package Core;

import Utils.LoggingFilter;
import Utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import java.io.IOException;

public class BaseTest {

    public RequestSpecification requestSpecification;
    public SoftAssert softAssert;

    @BeforeMethod
    public void setUp() throws IOException {
        requestSpecification =
                RestAssured
                        .given()
                        .filter(new LoggingFilter())
                        .baseUri(PropertyReader.readProperty("serverAddress"))
                        .header("x-api-key", "reqres-free-v1")
                        .header("Content-Type", "application/json");

        softAssert = new SoftAssert();
    }
}