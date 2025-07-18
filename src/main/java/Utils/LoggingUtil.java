package Utils;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.qameta.allure.Allure;
import io.restassured.response.Response;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoggingUtil {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void logRequest(ExtentTest test, String method, String endpoint, Object requestBody) throws JsonProcessingException {

        Map<String,Object> logMap = new HashMap<>();
        logMap.put("Method", method);
        logMap.put("Endpoint", endpoint);
        if (requestBody==null){
            logMap.put("Request Body","This method does not have a request body");
        }
        else {
            logMap.put("Request Body", requestBody);
        }

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap);
        test.info("<pre>"+json+"</pre>");
        Allure.addAttachment("Request Details:","application/json",new ByteArrayInputStream(json.getBytes()),".json");
    }

    public static void logResponse(ExtentTest test, Response response) throws JsonProcessingException {

        Map<String,Object> logMap = new HashMap<>();
        logMap.put("Status Code",response.getStatusCode());
        logMap.put("Content Type",response.getContentType());
        logMap.put("Headers",response.getHeaders());
        logMap.put("Execution Time",response.getTimeIn(TimeUnit.SECONDS)+"seconds");

        Object responseBody;
        try {
            responseBody = response.getBody().as(Map.class);
        }
        catch (Exception e){
            responseBody = response.getBody().asString();
        }

        logMap.put("Response Body",responseBody);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap);
        test.info("<pre>"+json+"</pre>");
        Allure.addAttachment("Response Details:","application/json",new ByteArrayInputStream(json.getBytes()),".json");
    }
}