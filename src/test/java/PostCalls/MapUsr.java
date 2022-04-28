package PostCalls;
import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class MapUsr extends BaseClass {
    public static Logger logger = Logger.getLogger(MapUsr.class);
    Response response;

    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();

    }
    @Test(priority = 1)
    public void MapUserId() {
        logger.info("Mapping UserId");

        response = given().
                baseUri(url).
                headers("Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON).

                when().
                post("users/69/vacancies/39").
                then().
                log().body().statusCode(HttpStatus.SC_OK).extract().response();
        System.out.println("Time taken :" + response.getTime());
        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 200);
        System.out.println("Status Code :" + response.getStatusCode());
        int responseTime = (int) response.getTime();
        assertThat(responseTime, is(lessThan(2000)));
    }
}