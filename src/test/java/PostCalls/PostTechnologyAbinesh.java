package PostCalls;

import baseClass.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class PostTechnologyAbinesh extends BaseClass {
    public static Logger logger = Logger.getLogger(PostProfilePicUpdate.class);
    @Test(priority = 6)
    public void postTechnology(){
        JSONObject object = new JSONObject();
        String technologyName = "Java" + ThreadLocalRandom.current().nextInt();
        object.put("technology",technologyName);
        Response response = given().
                baseUri(url).
                header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token).
                body(object.toString()).
                when().
                post("drop-down/technology").
                then().statusCode(201).extract().response();
        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode, 201, "Correct status code returned");
        System.out.println(statuscode);
        int time= (int) response.getTime();
        if(time<=1000){
            logger.info("Response time is less than 1000");
        }
        else logger.info("Response time is greater than 1000");
        String checkEmpty=response.asString();
        Assert.assertEquals(false,checkEmpty.isEmpty());
        if((object.get("technology") instanceof String)){
            logger.info("Data types Validated");
        }
    }
}
