package DeleteCalls;

import baseClass.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteTests_adithyaP extends BaseClass {
    public static Logger logger = Logger.getLogger(DeleteTests_adithyaP.class);

    @Test
    public void deleteAlreadyDeletedVacancy(){

        Response response =
                given().
                        baseUri(url).
                        headers(
                                "Authorization",
                                "Bearer " + token,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON).
                delete("vacancies/40").
                then().
                        statusCode(HttpStatus.SC_NOT_FOUND).extract().response();
                logger.info("Vacancy 40 is not found since it is already deleted");

    }
}
