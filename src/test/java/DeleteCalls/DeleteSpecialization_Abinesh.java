package DeleteCalls;

import baseClass.BaseClass;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteSpecialization_Abinesh extends BaseClass {
    @Test(priority = 1)
    public void deleteSpecialization() {
        JSONObject object = new JSONObject();
        Response response = given().
                baseUri(url).
                header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token).
                body(object.toString()).
                when().
                delete("drop-down/specialization/44").
                then().extract().response();
        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 204, "Correct status code returned");
        logger.info(statuscode);
        int time= (int) response.getTime();
        if(time<=1500){
            logger.info("Response time is less than 1500");
        }
        else logger.info("Response time is greater than 1500");
        String checkEmpty = response.asString();
        Assert.assertEquals(true, checkEmpty.isEmpty());
    }
}
