package PutCalls;

import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class PutUpdateVacancy extends BaseClass {

    @BeforeSuite
    public void nothing() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    static String updatevacancyString;

    @Test(priority = 2)
    public void updateVacancy() throws IOException {
        Path updatevacancyPath
                = Path.of("src/main/java/utils/jsonBody/updatevacancy.json");
        updatevacancyString = Files.readString(updatevacancyPath);
        Response response = given().
                baseUri("https://hashedin-backend-test-urtjok3rza-wl.a.run.app/").
                header("Content-Type","application/json").
                header("Authorization","Bearer "+ token).
                body(updatevacancyString).
                when().
                put("vacancies/150").
                then().extract().response();
        System.out.println(token);
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),201);
        logger.info("Updated Vacancy");
    }
}
