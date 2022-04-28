package GetCalls;
import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Bandrpdwn extends BaseClass {
    public static Logger logger = Logger.getLogger(Bandrpdwn.class);
    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(priority = 1)
    void DropdownBnd() {
        logger.info("Getting  Dropdown");
        Response response = given().baseUri(url).
                headers("Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON).
                when().
                get("drop-down/band").
                then().
                statusCode(HttpStatus.SC_OK).extract().response();
        System.out.println("Status Code :" + response.getStatusCode());
        logger.info("Getting status code");
        System.out.println("Body :" + response.getBody().asString());
        System.out.println("Time taken :" + response.getTime());
        logger.info("Getting response time");
        System.out.println("Header :" + response.getHeader("content-type"));
        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 200);
        int responseTime =(int)response.getTime();
        assertThat(responseTime,is(lessThan(1500)));
        assertThat(response.asString(),containsString("bandName"));//bandName
        assertThat(response.asString(),containsString("bandId"));//bandId
        JSONArray array = new JSONArray(response.asString());
        for (int i = 0; i < array.length(); i++) {
            int id = (int) array.getJSONObject(i).get("bandId");
            if (id == 6) {
                String BandName = (String) array.getJSONObject(i).get("bandName");
                if (BandName.equals("B7L")) {
                    System.out.println("B7l is present in the list of Technology");
                }
            }
        }
    }

}
