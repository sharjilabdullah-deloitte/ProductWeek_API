package PostCalls;

import baseClass.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class PostSpecialization extends BaseClass {
    public static Logger logger = Logger.getLogger(PostProfilePicUpdate.class);
    @Test(priority = 5)
    public void postSpecialization(){
        JSONObject object = new JSONObject();
        String specName= "Php" + ThreadLocalRandom.current().nextInt();
        object.put("specialization",specName);
        Response response = given().
                baseUri(url).
                header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token).
                body(object.toString()).
                when().
                post("drop-down/specialization").
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

        if((object.get("specialization") instanceof String)){
            logger.info("Data types Validated");
        }
    }

}
