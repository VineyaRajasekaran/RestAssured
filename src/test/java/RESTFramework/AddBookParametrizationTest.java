package RESTFramework;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import files.ResusableMethods;
import files.payLoad;
import files.resources;
import groovy.util.logging.Log;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddBookParametrizationTest {
	
	private static final Logger log = LogManager.getLogger(AddBookParametrizationTest.class.getName());
	

	private static final String getData = null;
	Properties prop = new Properties();

	@BeforeTest
	public void getEnvironmentValues() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\files\\env.properties");
		prop.load(fis);
	}

	/**@param  aisle 
	 * @Test
	public void addPlacePost() {
		
		RestAssured.baseURI = prop.getProperty("LibraryBase");
	 given().
				header("Content-Type","application/json").
				body(payLoad.addBook()).
				when().post(resources.addBookResource()).
				then().log().all()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();**/
		


	@Test(dataProvider="getData")
	public void addPlacePost(String isbn, String aisle) {
		
				RestAssured.baseURI = prop.getProperty("LibraryBase");
				Response res =given().
				header("Content-Type","application/json").
				body(payLoad.addBook( isbn , aisle)).
				when().post(resources.addBookResource()).
				then().log().all()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
				 
				String response =res.asString();
				JsonPath js = new JsonPath(response);
				String id = js.get("ID");
			log.debug("Id is : "+id);

				RestAssured.baseURI = prop.getProperty("LibraryBase");
				given().
				header("Content-Type","application/json").
				body(payLoad.deleteBook(id)).
				when().post(resources.deleteBookResource()).
				then().log().all()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
				log.debug("I am in AddBookParametrization : isbn : " +isbn + " aisle: " +aisle );
	
		}
	
	@DataProvider(name = "getData")
	public Object[][] bookData()
	{
		Object[][] data = new Object[][] {{"Aqxtc","11323"},{"Auuatxd","36494"},{"Aghtjhj","3284434"}};
		return data;
	}
	
	
}