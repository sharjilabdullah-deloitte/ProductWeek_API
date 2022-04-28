package PostCalls;

import GetCalls.GetProject;
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

public class PostProfilePicUpdate  {

    Response response;
    String responseBody;
    static String token;
    static String postAdminJsonData;
    static Response postAdminLoginResponse;
    static String url = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";

    public static Logger logger = Logger.getLogger(GetProject.class);

    @Test(priority = 1)
    public static void setupAdminLogin() throws IOException {

        Path fileNameForAdminLogin
                = Path.of("src/main/java/utils/jsonBody/AdminLogin.json");
        postAdminJsonData = Files.readString(fileNameForAdminLogin);
        logger.info("Signing as admin");
        postAdminLoginResponse = given().headers("Content-Type", ContentType.JSON).
                baseUri(url).
                body(postAdminJsonData).
                when().
                post("api/auth/signin").
                then().extract().response();
        String adminResponse = postAdminLoginResponse.prettyPrint();
        JSONObject jsonObject = new JSONObject(adminResponse);
        token = jsonObject.get("accessToken").toString();
        System.out.println("Token is " + token);
    }

    @Test(priority = 2)
    public void postProfilePic(){

        File uploadProfilePic = new File("src/main/java/utils/pictures/updateProfilePic.PNG");
        response = given()
                .baseUri(url)
                .headers("Authorization", "Bearer " + token)
                .multiPart("profile", uploadProfilePic)                         //key = profile
                .when().
                post("users/437/profile-picture").then().extract().response();
        logger.info("Uploading profile picture");
        System.out.println("The Status code is :" + response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
    }
}
