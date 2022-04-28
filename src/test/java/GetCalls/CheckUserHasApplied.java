package GetCalls;

import baseClass.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CheckUserHasApplied extends BaseClass {

    String adminToken = null;
    String baseUrl ="https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
    public static Logger logger = Logger.getLogger(CheckUserHasApplied.class);
    @Test(priority = 1)
    public void checkUserHasApplied(){
        Response response =
                given().
                        baseUri(baseUrl).
                        headers(
                                "Authorization",
                                "Bearer " + token,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON).

                        when().
                        get("users/8/projects-vacancies/23").
                        then().
                        log().body().extract().response();
        try {
            int statusCode = response.getStatusCode();
            assertThat(statusCode,is(200));
            int responseTime = (int) response.getTime();
            assertThat(responseTime, is(lessThan(2000)));
            assertThat(response.asString(), containsString("vacancyId"));
            assertThat(response.asString(), containsString("band"));
            assertThat(response.asString(), containsString("skill"));
            //checking whether user 8 has applied for project 23
            logger.info("checking whether user 8 has applied for project 23");

            JSONArray jsonArray = new JSONArray(response.asString());
            Object obj = jsonArray.getJSONObject(0).get("filled");
            assertThat(obj, is(true));
        }catch (AssertionError e){
            System.out.println(e.getMessage());

        }
    }
}
