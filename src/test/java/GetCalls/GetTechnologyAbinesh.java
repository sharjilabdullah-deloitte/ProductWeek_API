package GetCalls;

import PostCalls.PostProfilePicUpdate;
import baseClass.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetTechnologyAbinesh extends BaseClass {
    public static Logger logger = Logger.getLogger(PostProfilePicUpdate.class);
    String techName = "React JS";
    @Test(priority = 1)
    public void getTechnology() {
        Response response = given().
                baseUri(url).
                header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token).
                when().
                get("drop-down/technology").
                then().extract().response();
        int statuscode = response.getStatusCode();
        logger.info("Status Code :" + statuscode);
        Assert.assertEquals(statuscode, 200, "Correct status code returned");
        JSONArray array = new JSONArray(response.asString());
        for (int i = 0; i < array.length(); i++) {
            int id = (int) array.getJSONObject(i).get("technologyId");
            if (id == 9) {
                logger.info("id is present in the list of technology");
                String technologyName = (String) array.getJSONObject(i).get("technology");
                if (technologyName.equals(techName)) {
                    logger.info(techName + " is present in the list of Technology");
                }
            }
        }
        int time= (int) response.getTime();
        if(time<=1500){
            logger.info("Response time is less than 1500");
        }
        else logger.info("Response time is greater than 1500");
    }

}
