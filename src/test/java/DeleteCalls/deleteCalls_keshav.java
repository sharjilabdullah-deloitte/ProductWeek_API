package DeleteCalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class deleteCalls_keshav {
    String baseUrl = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
    String tokenByAdmin;
    int responseTime;
    String tokenByManager;
    public static Logger logger = Logger.getLogger(deleteCalls_keshav.class);
    @BeforeTest
    public void handshake() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    public boolean responseTimeVAlidation(int responseTime)
    {
        if(responseTime<1000)
        {
            logger.info("response time is less than 1000");
            return true;
        }
        else
            logger.info("response time is greater than 1000");
        return false;

    }

        @Test(priority = 1)
    public void testingAdminLoginApi() throws IOException {
        Path fileNameForAdminLogin
                = Path.of("src/main/java/utils/jsonBody/AdminLogin.json");
        String postAdminJsonData = Files.readString(fileNameForAdminLogin);

        String response =
                given()
                        .header("Content-type", "application/json")
                        .baseUri(baseUrl)
                        .body(postAdminJsonData)
                        .when().post("api/auth/signin")
                        .then().assertThat().statusCode(200)
                        .body(matchesJsonSchemaInClasspath("adminLoginSchema.json"))
                        .header("Server",equalTo("Google Frontend"))
                        .extract().response().asString();
        logger.info("In testingAdminLoginApi :Status code is 200");
        JsonPath jsonPath = new JsonPath(response);
        tokenByAdmin = jsonPath.getString("accessToken");
        System.out.println(tokenByAdmin);
        JSONObject jsonObject = new JSONObject(response);
        System.out.println(jsonObject);
        assertThat(jsonObject.get("username"), equalTo("admin"));
        logger.info("username is as expected in response");
        assertThat(jsonObject.get("email"), equalTo("admin@admin"));
        logger.info("email is as expected in response");
    }
    @Test(priority = 2)
    public void deletingManager() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + tokenByAdmin)
                        .header("Context-type", "application/json")
                        .when().delete("managers/462")
                        .then()
                        .assertThat()
                        .header("Server",equalTo("Google Frontend"))
                        .statusCode(204)
                        .extract().response();
        logger.info("In deletingManager :Manager is deleted successfully");
        responseTime= (int) response.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));
        String bodyOfResponse=response.asString();
        Assert.assertEquals(true,bodyOfResponse.isEmpty());
    }

    @Test(priority = 3)
    public void deletingVacancyByUser() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + tokenByAdmin)
                        .header("Context-type", "application/json")
                        .when().delete("users/457/vacancies-delete/625")
                        .then()
                        .assertThat()
                        .header("Server",equalTo("Google Frontend"))
                        .statusCode(204)
                        .extract().response();
        logger.info("In deletingManager :vacancy by user is deleted successfully");
        responseTime= (int) response.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));
        String bodyOfResponse=response.asString();
        Assert.assertEquals(true,bodyOfResponse.isEmpty());
    }

}
