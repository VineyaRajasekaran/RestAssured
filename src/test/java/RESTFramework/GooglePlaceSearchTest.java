package RESTFramework;

import org.testng.annotations.Test;

import files.ResusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GooglePlaceSearchTest {
	
	private static final Logger log = LogManager.getLogger(GooglePlaceSearchTest.class.getName());
	
	@Test
	public void testGet() {
		
		RestAssured.baseURI="https://maps.googleapis.com";
		Response res =given().
		
		param("location","-33.8670522,151.1957362").
		param("radius","1500").
		param("type","restaurant").
		param("keyword","cruise").
		param("key","AIzaSyCywFpR8kh23zsIF2-N0oYAFb0C820tBpE").
		when().
		get("/maps/api/place/nearbysearch/json").
		then().
		assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		String responseString = res.asString();
		log.debug(responseString);
		JsonPath js = new JsonPath(responseString);
		
		int count = js.get("results.size()");
		log.debug(count);
		
		for(int i=0;i<count; i++) {
			String name = js.get("results["+i+"].name");
			log.debug(name);
			
		}
	}

}
