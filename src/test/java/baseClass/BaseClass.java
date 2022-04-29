package baseClass;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class BaseClass {

    public static String token= null;
    static String postAdminJsonData;
    static Response postAdminLoginResponse;
    public static String url = "https://hashedin-backend-test-urtjok3rza-wl.a.run.app/";

    public static Logger logger = Logger.getLogger(BaseClass.class);

    @BeforeClass
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
}