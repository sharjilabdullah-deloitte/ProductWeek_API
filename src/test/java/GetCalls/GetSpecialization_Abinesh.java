package GetCalls;

import PostCalls.PostProfilePicUpdate;
import baseClass.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetSpecialization_Abinesh extends BaseClass {
    public static Logger logger = Logger.getLogger(PostProfilePicUpdate.class);
    String specializationName = "php15";
    @Test(priority = 3)
    public void getSpecialization() {
        Response response = given().
                baseUri("https://hashedin-backend-test-urtjok3rza-wl.a.run.app").
                header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token).
                when().
                get("drop-down/specialization").
                then().extract().response();
        int statuscode = response.getStatusCode();
        logger.info("Status Code :" + statuscode);
        Assert.assertEquals(statuscode, 200, "Correct status code returned");
        JSONArray array = new JSONArray(response.asString());
        for (int i = 0; i < array.length(); i++) {
            int id = (int) array.getJSONObject(i).get("specializationId");
            if (id == 76) {
                logger.info("id is present in the list of specialization");
                String technologyName = (String) array.getJSONObject(i).get("specialization");
                if (technologyName.equals(specializationName)) {
                    logger.info(specializationName + " is present in the list of Specialization");
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
