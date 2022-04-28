package PostCalls;

import GetCalls.GetProject;
import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.hamcrest.core.IsEqual;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

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
