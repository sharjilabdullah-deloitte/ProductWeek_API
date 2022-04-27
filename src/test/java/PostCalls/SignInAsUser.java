package PostCalls;

import GetCalls.GetProject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class SignInAsUser {
    Response response;
    String responseBody;
    static String token;
    static String postUserJsonData;
    static Response postUserLoginResponse;

    public static Logger logger = Logger.getLogger(GetProject.class);

    @Test(priority = 1)
    public static void setupUserLogin() throws IOException {
        RestAssured.baseURI = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";
        Path fileNameForUserLogin
                = Path.of("src/main/java/resources/jsonData/UserLogin.json");
        postUserJsonData = Files.readString(fileNameForUserLogin);
        postUserLoginResponse = given().headers("Content-Type", ContentType.JSON).
                body(postUserJsonData).
                when().
                post("api/auth/signin").
                then().extract().response();
        String userResponse = postUserLoginResponse.prettyPrint();
        JSONObject jsonObject = new JSONObject(userResponse);
        String email = jsonObject.get("email").toString();
        System.out.println("Email of the user is " + email);
        Assert.assertEquals(postUserLoginResponse.statusCode(),200);
        Assert.assertEquals(email,"abc@deloitte.com");
    }
}
