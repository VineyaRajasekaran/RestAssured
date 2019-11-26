package RESTFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.ResusableMethods;
import files.payLoad;
import files.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
public class JSONFromHashMapTest extends  ResusableMethods{
	private static final Logger log = LogManager.getLogger(JSONFromHashMapTest.class.getName());
	Properties prop = new Properties();
	
	@BeforeTest
	public void getEnvironmentValues() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\files\\env.properties");
		prop.load(fis);
	}
	
	@Test
	public void JsonFromHashMap() throws IOException
	{
	
		
		HashMap <String,Object> hm= new HashMap <String,Object>();
		hm.put("name", "FirstBook");
		hm.put("isbn", "2345");
		hm.put("aisle", "hgd");
		hm.put("author", "Paul");
		
		RestAssured.baseURI=prop.getProperty("LibraryBase");
		String res = given().
		body(hm).
		when().
		post(resources.addBookResource()).
		then().log().all().
		extract().asString();
		
		JsonPath js = new JsonPath(res);
		String id = js.get("ID");
		log.debug("Id is : "+id);
		
		RestAssured.baseURI = prop.getProperty("LibraryBase");
		given().
		header("Content-Type","application/json").
		body(payLoad.deleteBook(id)).
		when().post(resources.deleteBookResource()).
		then().log().all()
		
		.extract().response();
		
		
	}

}
