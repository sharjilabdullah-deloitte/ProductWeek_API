package PostCalls;

import api.HashedinProjects;
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

public class postCalls_Keshav {
    String baseUrl = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
    int responseTime;
    String tokenByAdmin;
    public static Logger logger = Logger.getLogger(HashedinProjects.class);
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
                = Path.of("src/main/java/jsonbody/adminLogin.json");
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
    public void createVacancy() throws IOException {
        Path fileNameForAdminLogin
                = Path.of("src/main/java/jsonbody/createVacancy.json");
        String postVacancyJsonData = Files.readString(fileNameForAdminLogin);
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + tokenByAdmin)
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
                = Path.of("src/main/java/jsonbody/createNewBand.json");
        String newBandJsonData = Files.readString(fileNameForAdminLogin);
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + tokenByAdmin)
                        .header("Content-type", "application/json")
                        .body(newBandJsonData)
                        .when().post("drop-down/band")
                        .then()
                        .assertThat()
                        .header("Server",equalTo("Google Frontend"))
                        .statusCode(201).extract().response();
        logger.info("In createNewBand :status code is as expected in response");
        JSONObject jsonObject = new JSONObject(response.asString());
        assertThat(jsonObject.get("bandName"), equalTo("B990"));
        responseTime= (int) response.getTime();
        Assert.assertTrue(responseTimeVAlidation(responseTime));

    }
    @Test(priority = 4)
    public void signup() throws IOException {
        Path fileNameForAdminLogin
                = Path.of("src/main/java/jsonbody/signup.json");
        String signupJsonData = Files.readString(fileNameForAdminLogin);
        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Authorization", "Bearer " + tokenByAdmin)
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
