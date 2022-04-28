package GetCalls;

import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetManagerWithProjectId extends BaseClass {
    Response response;
    String responseBody;

    @Test(priority = 1)
    public void validateSC() {
        response = given().headers("Authorization", "Bearer " + token, "Content-Type",
                        ContentType.JSON)
                .baseUri(url)
                .when().get("projects-managers/149").then().extract().response();
        int statusCode = response.statusCode();
        String contentType = response.getContentType();

        logger.info("The Response is : ");
        responseBody = response.getBody().asString();
        System.out.println("Response is " + responseBody);
        logger.info("The Status code is 200 : VERIFIED ");
        Assert.assertEquals(statusCode,200);
        logger.info("The Content Type is : JSON ");
        Assert.assertEquals(contentType,"application/json");
    }

    @Test(priority = 2)
    public void validateEmail(){
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String email = jsonObject.getString("email");
        System.out.println("The email of the manager associated is "+ email);
        logger.info("Asserting whether email field is not null");
        assertThat(email,is(notNullValue()));
    }
    @Test(priority = 3)
    public void validateId(){
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        int id = jsonObject.getInt("id");
        System.out.println("The id of the manager associated is "+ id);
        logger.info("Asserting whether id field is not null");
        assertThat(id,is(notNullValue()));
    }
}
