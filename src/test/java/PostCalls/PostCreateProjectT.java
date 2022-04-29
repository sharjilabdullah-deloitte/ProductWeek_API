package PostCalls;

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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class PostCreateProjectT extends BaseClass {


    public static Logger logger = Logger.getLogger(PostCreateProjectT.class);

    @Test
    public void managerCreatesId(){
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
                        post("managers/453/projects/4").
                then().
                        log().body().
                        extract().response();
      try {
          int statusCode = response.getStatusCode();
          assertThat(statusCode,is(200));
          int  responseTime = (int)response.getTime();
         assertThat(responseTime,is(lessThan(1500)));
      }catch (AssertionError e){
          logger.info(e.getMessage());
      }

    }
    @Test
    public void createProject() throws IOException {

//        ExcelData excelData = new ExcelData();
//        //taking project details from excel
//        String projectName = excelData.getProjectDetails(0,1,0);
//        String status = excelData.getProjectDetails(0,1,1);
//        String description = excelData.getProjectDetails(0,1,2);
//        String startDate = excelData.getProjectDetails(0,1,3);
//        String endDate = excelData.getProjectDetails(0,1,4);
//        String tech1 = excelData.getProjectDetails(0,1,5);
//        String tech2 = excelData.getProjectDetails(0,1,6);
//        String updatedAt = excelData.getProjectDetails(0,1,7);

        // Data projectDetails = new Data(projectName,status,description,startDate,endDate,tech1,tech2,updatedAt);
        File projectDetails = new File("src/test/resources/projectDetails.json");

        Response response =
                given().
                        baseUri(url).
                        body(projectDetails).
                        headers(
                                "Authorization",
                                "Bearer " + token,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON).

                when().
                        post("projects").
                then().
                        log().body().extract().response();
        try {
            int statusCode = response.getStatusCode();
            assertThat(statusCode,is(201));
            int responseTime = (int) response.getTime();
            assertThat(responseTime, is(lessThan(1500)));
            JSONObject obj = new JSONObject(response.asString());
            if ((obj.get("projectId") instanceof Integer) && (obj.get("name") instanceof String) &&
                    (obj.get("status") instanceof String) && (obj.get("description") instanceof String)) {
                logger.info("Data Types validated");

            } else {
                logger.info("Data Types not proper");
            }
        }catch (AssertionError e){
            logger.info(e.getMessage());
        }
    }

}
