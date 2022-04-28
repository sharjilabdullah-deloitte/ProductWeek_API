package PostCalls;


import baseClass.BaseClass;
import io.restassured.RestAssured;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostMethodKeshav extends BaseClass {
    String baseUrl = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
    int responseTime;
    public static Logger logger = Logger.getLogger(PostMethodKeshav.class);
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
    @Test(priority = 2)
    public void createVacancy() throws IOException {
        Path fileNameForAdminLogin
                = Path.of("src/main/java/utils/jsonBody/createVacancy.json");
        String postVacancyJsonData = Files.readString(fileNameForAdminLogin);
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + token)
                        .header("Content-type", "application/json")
                        .body(postVacancyJsonData)
                        .when().post("vacancies/138")
                        .then().assertThat()
                        .header("Server",equalTo("Google Frontend"))
                        .statusCode(201)
                        .extract().response();
        logger.info("In createVacancy :status code  is as expected in response");
        JSONObject jsonObject = new JSONObject(response.asString());
        assertThat(jsonObject.get("band"), equalTo("B6L"));
        logger.info("band is as expected in response");
        assertThat(jsonObject.get("skill"), equalTo("QA"));
        logger.info("skill is as expected in response");
        assertThat(jsonObject.get("technology"), equalTo("Angular19"));
        logger.info("technology is as expected in response");
        responseTime= (int) response.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));

    }

    @Test(priority = 3)
    public void createNewBand() throws IOException {
        Path fileNameForAdminLogin
                = Path.of("src/main/java/utils/jsonBody/createNewBand.json");
        String newBandJsonData = Files.readString(fileNameForAdminLogin);
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + token)
                        .header("Content-type", "application/json")
                        .body(newBandJsonData)
                        .when().post("drop-down/band")
                        .then()
                        .assertThat()
                        .header("Server",equalTo("Google Frontend"))
                        .statusCode(201).extract().response();
        logger.info("In createNewBand :status code is as expected in response");
        JSONObject jsonObject = new JSONObject(response.asString());
        assertThat(jsonObject.get("bandName"), equalTo("C990"));
        responseTime= (int) response.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));

    }
    @Test(priority = 4)
    public void signup() throws IOException {
        Path fileNameForAdminLogin
                = Path.of("src/main/java/utils/jsonBody/signup.json");
        String signupJsonData = Files.readString(fileNameForAdminLogin);
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + token)
                        .header("Content-type", "application/json")
                        .body(signupJsonData.toString())
                        .when().post("api/auth/signup")
                        .then()
                        .log().all()
                        .assertThat()
                        .header("Server",equalTo("Google Frontend"))
                        .extract().response();
        logger.info("In signup :statuscode is as expected in response");
        JSONObject jsonObject=new JSONObject(response.asString());
        assertThat(jsonObject.get("message"),equalTo("User registered successfully!"));
        logger.info("user is registered successfully");
        responseTime= (int) response.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));

    }

}
