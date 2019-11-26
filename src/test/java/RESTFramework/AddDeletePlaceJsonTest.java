package RESTFramework;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import files.resources;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

import files.ResusableMethods;
import files.payLoad;

public class AddDeletePlaceJsonTest {
	private static final Logger log = LogManager.getLogger(AddDeletePlaceJsonTest.class.getName());
	Properties prop = new Properties();

	@BeforeTest
	public void getEnvironmentValues() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir")+"\\src\\main\\java\\files\\env.properties");
		prop.load(fis);
	}

	@Test
	public void addPlacePost() {
		
		RestAssured.baseURI = prop.getProperty("HOST");
		Response res = given().
				queryParam("key", prop.getProperty("KEY")).
				body(payLoad.placePostBody()).
				when().post(resources.placePostData()).
				then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("status", equalTo("OK"))
				.extract().response();

		// Grab the place id
		ResusableMethods rs = new ResusableMethods();
		JsonPath js = rs.rawToJson(res);
		String placeid = js.get("place_id");
		log.debug("placeid is " + placeid);

		// place this place id in delete place api

		RestAssured.baseURI = prop.getProperty("HOST");
		Response res1 = given().
				queryParam("key", prop.getProperty("KEY"))
				.body("{\r\n" + "    \"place_id\":\"" + placeid + "\"" + "}\r\n").

				when().post(resources.placePostDeleteData()).
				then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK")).extract().response();

		String responseString1 = res1.asString();
		log.debug(responseString1);

	}

}
