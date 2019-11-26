package RESTFramework;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.ResusableMethods;
import files.payLoad;
import files.resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class AddDeletePlaceXmlTest {
	private static final Logger log = LogManager.getLogger(AddDeletePlaceXmlTest.class.getName());
	Properties prop = new Properties();

	@BeforeTest
	public void getEnvironmentValues() throws IOException {
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\dinav\\RestAssuredProject\\src\\main\\java\\files\\env.properties");
		prop.load(fis);
	}

	@Test
	public void addPlacePost() throws IOException {
		String postxml = GenerateStringFromResource("C:\\Users\\dinav\\Documents\\AddPlacexml.xml");
		
		
		
		RestAssured.baseURI = prop.getProperty("HOST");
		Response res = given().
				queryParam("key", prop.getProperty("KEY")).
				body(postxml).
				when().post(resources.placePostXml()).
				then()
				.assertThat().statusCode(200).and().contentType(ContentType.XML)
				.extract().response();

		// Grab the place id
		String responseString = res.asString();
	log.debug(responseString);
		XmlPath x  = new XmlPath(responseString);
		String placeid = x.get("response.place_id");
		log.debug("placeid is " + placeid);
		
		// place this place id in delete place api
		
		//String deletexml = GenerateStringFromResource("C:\\Users\\dinav\\Documents\\deletePlaceXml.xml");

				RestAssured.baseURI = prop.getProperty("HOST");
				Response res1 = given().
						queryParam("key", prop.getProperty("KEY"))
						.body("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
								"<root>\r\n" + 
								"    <place_id>"+placeid+"</place_id>\r\n" + 
								"</root>\r\n" + 
								"").

						when().post(resources.deletePostXml()).
						then().assertThat().statusCode(200).and()
						.contentType(ContentType.XML).and().body("response.status", equalTo("OK")).
						extract().response();

				String responseString1 = res1.asString();
				log.debug(responseString1);

	}
	
	//it formats the xml to String
	public static String GenerateStringFromResource(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	
	
	
	
}
