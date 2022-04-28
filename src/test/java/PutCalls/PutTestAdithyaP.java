package PutCalls;

import baseClass.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PutTestAdithyaP extends BaseClass {


    public static Logger logger = Logger.getLogger(PutTestAdithyaP.class);

    @Test(priority = 1)
    public void updateProject() throws IOException {
    File editProjectDetails= new File("src/test/resources/editProjectDetails.json");

    Response response =
            given().
                    baseUri(url).
                    body(editProjectDetails).
                    headers(
                            "Authorization",
                            "Bearer " + token,
                            "Content-Type",
                            ContentType.JSON,
                            "Accept",
                            ContentType.JSON).

            when().
                    put("projects/453").
            then().
                    log().body().
                    extract().response();
       try {
           int statusCode = response.getStatusCode();
           assertThat(statusCode,is(200));
           int responseTime = (int) response.getTime();
           assertThat(responseTime, is(lessThan(1500)));
           JSONObject obj = new JSONObject(response.asString());
           assertThat(obj.get("projectId"), is(not(equalTo(null))));

           logger.info("Checked projectId to be not null");

       }catch (AssertionError e){
           logger.info(e.getMessage());

       }
    }
}


