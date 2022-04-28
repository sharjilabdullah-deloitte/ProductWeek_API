package DeleteCalls;
import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class DeleteBand extends BaseClass {
    public static Logger logger = Logger.getLogger(DeleteBand.class);

    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(priority = 1)
    public void DeleteBnd(){
        logger.info("Delete band from dropdown");
        Response response = given().baseUri(url).
                headers("Authorization", "Bearer " + token,
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON).
                delete("drop-down/band/126");
        System.out.println("Time taken :" + response.getTime());
        int statuscode = response.getStatusCode();
        System.out.println("Status Code :" + response.getStatusCode());
        Assert.assertEquals(statuscode, 204);
        int responseTime =(int)response.getTime();
        assertThat(responseTime,is(lessThan(1500)));
        String str=response.asString();
        Assert.assertEquals(true,str.isEmpty());
    }
}

