package GetCalls;
import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetAllManagers extends BaseClass {
//    String baseUrl = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
//    String tokenByAdmin;
    int responseTime;
    public static Logger logger = Logger.getLogger(GetAllManagers.class);
    @BeforeTest
    public void handshake() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    public boolean responseTimeVAlidation(int responseTime)
    {
        if(responseTime<1500)
        {
            logger.info("response time is less than 1500");
            return true;
        }
        else
            logger.info("response time is greater than 1500");
        return false;

    }

    @Test(priority = 1)
    public void getAllManagers() {
        Response response2 =
                given()
                        .baseUri(url)
                        .header("Authorization", "Bearer " + token)
                        .header("Content-type", "application/json")
                        .when()
                        .get("managers")
                        .then()
                        .header("Server",equalTo("Google Frontend"))
                        .assertThat().statusCode(200)
                        .extract().response();
        logger.info("In getAllManagers :Status code is as expected in response that is 200");
        JSONArray jsonObject = new JSONArray(response2.asString());
        System.out.println(jsonObject);
        logger.info("succcessfully retrieved all the managers");
        responseTime= (int) response2.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));

    }

    @Test(priority = 2)
    public void getSpecificManager() {
        Response response3 =
                given()
                        .baseUri(url)
                        .header("Authorization", "Bearer " + token)
                        .header("Content-type", "application/json")
                        .when().get("managers/41")
                        .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .body(matchesJsonSchemaInClasspath("specificManagerschema.json"))
                        .header("Server",equalTo("Google Frontend"))
                        .extract().response();
        logger.info("In getSpecificManager :Status code is as expected in response that is 200");
        JSONObject jsonObject = new JSONObject(response3.asString());
        assertThat(jsonObject.get("name"), equalTo("manager1"));
        logger.info("name is as expected in response");
        assertThat(jsonObject.get("username"), equalTo("Manager1"));
        logger.info("username is as expected in response");
        assertThat(jsonObject.get("email"), equalTo("manager1@deloitte.com"));
        logger.info("email is as expected in response");
        assertThat(jsonObject.get("band"), equalTo(""));
        logger.info("band is as expected in response");
        responseTime= (int) response3.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));


    }

    @Test(priority = 3)
    public void getAllProjects() {
        Response response4 =
                given()
                        .baseUri(url)
                        .header("Authorization", "Bearer " + token)
                        .header("Content-type", "application/json")
                        .when().get("projects")
                        .then()
                        .body(matchesJsonSchemaInClasspath("getAllProjects.json"))
                        .header("Server",equalTo("Google Frontend"))
                        .assertThat().statusCode(200)
                        .extract().response();
        logger.info("In getAllProjects :statuscode is as expected in response");
        responseTime= (int) response4.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));

    }
}
