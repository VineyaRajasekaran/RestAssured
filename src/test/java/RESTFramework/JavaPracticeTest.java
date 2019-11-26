package RESTFramework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.resources;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.AddPlaceQAClick;
import pojo.Location;

public class JavaPracticeTest {
	private static final Logger log = LogManager.getLogger(JavaPracticeTest.class.getName());
	Properties prop = new Properties();
	String placeid ;
	@BeforeTest
	public void getEnvironmentValues() throws IOException {

		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir")+"\\src\\main\\java\\files\\env.properties");
		
		prop.load(fis);
	}


	String b= "{\r\n" + 
			"    \"location\":{\r\n" + 
			"        \"lat\" : -38.383494,\r\n" + 
			"        \"lng\" : 33.427362\r\n" + 
			"    },\r\n" + 
			"    \"accuracy\":50,\r\n" + 
			"    \"name\":\"Frontline house\",\r\n" + 
			"    \"phone_number\":\"(+91) 983 893 3937\",\r\n" + 
			"    \"address\" : \"29, side layout, cohen 09\",\r\n" + 
			"    \"types\": [\"shoe park\",\"shop\"],\r\n" + 
			"    \"website\" : \"http://google.com\",\r\n" + 
			"    \"language\" : \"French-IN\"\r\n" + 
			"}\r\n" + 
			"";
	
	
	
	@Test
	public void Sample()
	{
		AddPlaceQAClick ap = new AddPlaceQAClick();
		
		Location l = new Location();
		l.setLat("-38.383494");
		l.setLng("33.427362");
		ap.setLocation(l);
		ap.setAccuracy("50");
		ap.setName("Frontline house");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setAddress("29, side layout, cohen 09");
		ap.setTypes("[\"shoe park\",\"shop\"]");
		ap.setWebsite("http://google.com");
		ap.setLanguage("French-IN");
		
		RestAssured.baseURI="http://216.10.245.166";
		Response res = given().
		queryParam("key","qaclick123").log().all().
		body(ap).
		when().
		post("/maps/api/place/add/json").
		then().
		assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		String Response1 = res.asString();
	log.debug(Response1);
		JsonPath js = new JsonPath(Response1);
		placeid=js.get("place_id");
		log.debug(placeid);
		
		RestAssured.baseURI=prop.getProperty("HOST");
		log.debug("delete");
		Response res1 = given().
		queryParam(prop.getProperty("KEY")).
		body("{\r\n" + 
				"    \"place_id\":\""+placeid+"\"          \r\n" + 
				"}").
		when().
		post(resources.placePostDeleteData()).
		then().log().all().
		assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		log.debug(res1.asString());
		
		
		
		
		
	}

}
