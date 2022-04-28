package GetCalls;
import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetVacanyUsers extends BaseClass {
    public static Logger logger = Logger.getLogger(GetVacanyUsers.class);
    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(priority = 1)
    void UserVacancy() {
        logger.info("Getting list of users in a particular Vacancy");

        Response response = given().baseUri(url).
                headers("Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON).
                when().
                get("vacancies-users/39").
                then().
                statusCode(HttpStatus.SC_OK).extract().response();
        System.out.println("Status Code :" + response.getStatusCode());
        System.out.println("Body :" + response.getBody().asString());
        System.out.println("Time taken :" + response.getTime());
        System.out.println("Header :" + response.getHeader("content-type"));
        assertThat(response.asString(), containsString("band"));
        assertThat(response.asString(), containsString("technology"));
        assertThat(response.asString(), containsString("skill"));
        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 200);
        int responseTime =(int)response.getTime();
        assertThat(responseTime,is(lessThan(1500)));

    }
}