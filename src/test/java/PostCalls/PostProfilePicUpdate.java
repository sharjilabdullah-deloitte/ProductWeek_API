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

    public static Logger logger = Logger.getLogger(GetProject.class);

    @Test(priority = 1)
    public static void setupAdminLogin() throws IOException {
        RestAssured.baseURI = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
        Path fileNameForAdminLogin
                = Path.of("src/main/java/resources/jsonData/AdminLogin.json");
        postAdminJsonData = Files.readString(fileNameForAdminLogin);
        postAdminLoginResponse = given().headers("Content-Type", ContentType.JSON).
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

        File uploadProfilePic = new File("src/main/java/resources/pictures/updateProfilePic.PNG");
        response = given().headers("Authorization", "Bearer " + token)
                .multiPart("profile", uploadProfilePic)                         //key = profile
                .when().
                post("users/437/profile-picture").then().extract().response();
        System.out.println("The Status code is :" + response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
    }
}
