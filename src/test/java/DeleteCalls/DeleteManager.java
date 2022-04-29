package DeleteCalls;

import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteManager extends BaseClass {

    int responseTime;

    public static Logger logger = Logger.getLogger(DeleteManager.class);
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
                        .baseUri(url)
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
                        .baseUri(url)
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
