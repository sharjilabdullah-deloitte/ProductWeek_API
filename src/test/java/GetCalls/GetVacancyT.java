package GetCalls;

import baseClass.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetVacancyT extends BaseClass {

    public static Logger logger = Logger.getLogger(GetVacancyT.class);


    @Test(priority = 1)
    public void getAllVacancy(){
        Response response =
                given().
                        baseUri(url).
                        headers(
                                "Authorization",
                                "Bearer " + token,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON).

                when().
                        get("vacancies").
                then().

                        extract().response();
        try {
            int statusCode = response.getStatusCode();
            assertThat(statusCode,is(200));
            int responseTime = (int) response.getTime();
            assertThat(responseTime, is(lessThan(1500)));
            System.out.println("Checking body contains vacancyId,band,skill");
            assertThat(response.asString(), containsString("vacancyId"));
            assertThat(response.asString(), containsString("band"));
            assertThat(response.asString(), containsString("skill"));
        }catch (AssertionError e){
            logger.info(e.getMessage());
        }
    }

    @Test(priority = 2)
    public void getAVacancy(){

        Response response =
                given().
                        baseUri(url).
                        headers(
                                "Authorization",
                                "Bearer " + token,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON).
                when().
                        get("vacancies/8").
                then().
                        log().body().extract().response();


        try {
            int statusCode = response.getStatusCode();
            assertThat(statusCode,is(200));
            int  responseTime = (int)response.getTime();
            assertThat(responseTime,is(lessThan(2000)));
            assertThat(response.asString(), containsString("vacancyId"));
            assertThat(response.asString(), containsString("band"));
            assertThat(response.asString(), containsString("skill"));

            JSONObject obj = new JSONObject(response.asString());
            if ((obj.get("vacancyId") instanceof Integer) && (obj.get("band") instanceof String) &&
                    (obj.get("skill") instanceof String) && (obj.get("technology") instanceof String)
                    && (obj.get("filled") instanceof Boolean) && (obj.get("createdAt") instanceof String)
                    && (obj.get("applied") instanceof Boolean)) {

                logger.info("Data Types validated");
            } else {
                logger.info("Data Types not proper");
            }
        }catch (AssertionError e){
            logger.info(e.getMessage());
        }
    }
    @Test(priority =3)
    public void checkUserHasApplied(){

        Response response =
                given().
                        baseUri(url).
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
            logger.info(e.getMessage());

        }
    }


}
