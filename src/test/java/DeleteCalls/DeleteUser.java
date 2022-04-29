package DeleteCalls;

import baseClass.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteUser extends BaseClass {
    Response response;
    public static Logger logger = Logger.getLogger(DeleteUser.class);

    @Test(priority = 1)
    public void deleteUser() {

        logger.info("Deleting the user");
        response = given()
                .baseUri(url)
                .headers("Authorization", "Bearer " + token, "Content-Type",
                        ContentType.JSON)
                .when()
                .delete("users/456")
                .then()
                .assertThat().statusCode(204).extract().response();
    }
}
