package GetCalls;

import baseClass.BaseClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetTest extends BaseClass {
    @BeforeSuite
    public void nothing() {
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(priority = 2)
    public void GetAllVacancyAssociatedWithProjectID(){
        //System.out.println(tokenstr);
        logger.info("Getting all vacancy associated with project ID");
        Response response = given().
                header("Authorization","Bearer "+ token).
                when().
                get("https://hashedin-backend-test-urtjok3rza-wl.a.run.app/projects-vacancies/23").
                then().extract().response();
        response.prettyPrint();
        String responsebody = response.asPrettyString();
        JSONArray jsonArray = new JSONArray(responsebody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        int vacnacyId = jsonObject.getInt("vacancyId");
        logger.info("Got vacancy Associated with the project ID");
        Assert.assertEquals(vacnacyId,39);
    }
    @Test(priority = 3)
    public void getAllUser(){
        Response response = given().
                header("Authorization","Bearer "+ token).
                when().
                get("https://hashedin-backend-test-urtjok3rza-wl.a.run.app/users").
                then().
                body(matchesJsonSchemaInClasspath("getAlluserSchema.json")).
                extract().response();

        Assert.assertEquals(response.statusCode(),200);
        logger.info("Got the user Associated with the project ID");

    }
    @Test(priority = 4)
    public void getaUser(){
        Response response = given().
                header("Authorization","Bearer "+ token).
                when().
                get("https://hashedin-backend-test-urtjok3rza-wl.a.run.app/users/69").
                then().
                body(matchesJsonSchemaInClasspath("getaUserSchema.json")).
                extract().response();
        Assert.assertEquals(200,response.statusCode());
        logger.info("Got a user Assoiated with user id");

    }

}
