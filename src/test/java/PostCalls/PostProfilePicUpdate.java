package PostCalls;

import baseClass.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PostProfilePicUpdate extends BaseClass {

    Response response;
    public static Logger logger = Logger.getLogger(PostProfilePicUpdate.class);

    @Test(priority = 1)
    public void postProfilePic(){

        File uploadProfilePic = new File("src/main/java/utils/pictures/updateProfilePic.PNG");
        response = given()
                .baseUri(url)
                .headers("Authorization", "Bearer " + token)
                .multiPart("profile", uploadProfilePic)                         //key = profile
                .when().                                                           //437
                post("users/69/profile-picture").then().extract().response();
        logger.info("Uploading profile picture");
        System.out.println("The Status code is :" + response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
    }
}
