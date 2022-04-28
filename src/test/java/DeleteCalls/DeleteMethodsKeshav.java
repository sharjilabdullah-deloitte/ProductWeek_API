package DeleteCalls;

import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteMethodsKeshav extends BaseClass {
    String baseUrl = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
    String tokenByAdmin;
    int responseTime;
    String tokenByManager;
    public static Logger logger = Logger.getLogger(DeleteMethodsKeshav.class);
    @BeforeTest
    public void handshake() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    public boolean responseTimeVAlidation(int responseTime)
    {
        if(responseTime<1000)
        {
            logger.info("response time is less than 1500");
            return true;
        }
        else
            logger.info("response time is greater than 1500");
        return false;

    }
    @Test(priority = 1)
    public void deletingManager() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + token)
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

    @Test(priority = 2)
    public void deletingVacancyByUser() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + token)
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
