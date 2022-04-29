package DeleteCalls;

import baseClass.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class DeleteTechnology extends BaseClass {
    static String technologyName;
    @Test(priority = 7)
    public void deleteTechonolgyDropdown() throws IOException {
        Path dropdowntechnology
                = Path.of("src/main/java/utils/jsonBody/dropDownTechnology.json");
        technologyName = Files.readString(dropdowntechnology);
        logger.info("Deleting Technology Dropdown");
        Response response = given().
                baseUri("https://hashedin-backend-test-urtjok3rza-wl.a.run.app").
                header("Content-Type","application/json").
                header("Authorization","Bearer "+ token).
                body(technologyName).
                when().
                delete("/drop-down/technology/129").
                then().extract().response();
        logger.info("Deleted a technology in dropdown");
        Assert.assertEquals(response.statusCode(),204);
    }
}
