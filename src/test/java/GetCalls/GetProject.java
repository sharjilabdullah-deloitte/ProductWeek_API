package GetCalls;

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

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProject {

    Response response;
    String responseBody;
    static String token;
    JSONObject jsonObject;
    static String postAdminJsonData;
    static Response postAdminLoginResponse;

    public static Logger logger = Logger.getLogger(GetProject.class);
    static String baseUrl = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";

    @Test(priority = 1)
    public static void setupAdminLogin() throws IOException {

        Path fileNameForAdminLogin
                = Path.of("src/main/java/utils/jsonBody/AdminLogin.json");
        postAdminJsonData = Files.readString(fileNameForAdminLogin);
        postAdminLoginResponse = given().baseUri(baseUrl).
                headers("Content-Type",ContentType.JSON).
                body(postAdminJsonData).
                when().
                post("api/auth/signin").
                then().extract().response();
        String adminResponse = postAdminLoginResponse.prettyPrint();
        JSONObject jsonObject = new JSONObject(adminResponse);
        token = jsonObject.get("accessToken").toString();
        System.out.println("Token is " + token);
    }

    @Test(priority = 2)
    public void validateSC() {

        response = given().baseUri(baseUrl).
                headers("Authorization", "Bearer " + token, "Content-Type",
                        ContentType.JSON)
                .when().get("projects/3").then().extract().response();
        int statusCode = response.statusCode();
        String contentType = response.getContentType();

        logger.info("The Response is : ");
        responseBody = response.asString();
        System.out.println("Response is " + responseBody);
        logger.info("The Status code is 200 : VERIFIED ");
        Assert.assertEquals(statusCode,200);
        logger.info("The Content Type is : JSON ");
        Assert.assertEquals(contentType,"application/json");

    }

    @Test(priority = 3)
    public void checkProjectID(){
        jsonObject = new JSONObject(responseBody);
        String projectID = jsonObject.get("projectId").toString();
        logger.info("Checking Product ID of the response :");
        assertThat(projectID,equalTo("3"));
    }

    @Test(priority = 4)
    public void checkProjectName(){
        jsonObject = new JSONObject(responseBody);
        String projectName = jsonObject.get("name").toString();
        logger.info("Checking the name of the project");
        assertThat(projectName,equalTo("Test Project"));
    }

    @Test(priority = 5)
    public  void checkProjectStatus(){
        jsonObject = new JSONObject(responseBody);
        String projectStatus = jsonObject.get("status").toString();
        logger.info("Checking the status of the project");
        assertThat(projectStatus,equalTo("open"));
    }
}
