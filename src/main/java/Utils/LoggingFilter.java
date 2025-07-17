package Utils;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class LoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {

        ExtentTest test = ExtentReportListener.getTest();
        String method = filterableRequestSpecification.getMethod();
        String endpoint = filterableRequestSpecification.getURI();
        Object requestBody = filterableRequestSpecification.getBody();
        try {
            LoggingUtil.logRequest(test,method,endpoint,requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = filterContext.next(filterableRequestSpecification,filterableResponseSpecification);
        try {
            LoggingUtil.logResponse(test,response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}