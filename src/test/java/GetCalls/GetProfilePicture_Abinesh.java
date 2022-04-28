package GetCalls;

import baseClass.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetProfilePicture_Abinesh extends BaseClass {
    @Test(priority = 4)
    public void getprofilePicture() {
        Response response = given().
                baseUri("https://hashedin-backend-test-urtjok3rza-wl.a.run.app").
                header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token).
                when().
                get("users/1/profile-picture").
                then().extract().response();
        int statuscode = response.getStatusCode();
        logger.info("Status Code :" + statuscode);
        Assert.assertEquals(statuscode, 200, "Correct status code returned");
        int time= (int) response.getTime();
        if(time<=1500){
            logger.info("Response time is less than 1500");
        }
        else logger.info("Response time is greater than 1500");
    }

}
