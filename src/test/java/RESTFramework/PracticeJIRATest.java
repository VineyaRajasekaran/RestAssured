package RESTFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import files.payLoad;
import files.resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class PracticeJIRATest {
	Properties prop = new Properties();
	private static final Logger log = LogManager.getLogger(PracticeJIRATest.class.getName());
	
	@BeforeTest
	public void getEnvironmentValues() throws IOException {
				FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir")+"\\src\\main\\java\\files\\env.properties");
		
		prop.load(fis);
	}
	

	public String GetSessionId()
	{
		RestAssured.baseURI=prop.getProperty("JIRSHOST");
		Response res = given().
		header("Content-Type","application/json").
		body(payLoad.createSessionJIRA()).
		when().
		post(resources.getSessionIdJIRA()).
		then().log().all().
		assertThat().contentType(ContentType.JSON).
		extract().response();
		
		String response = res.asString();
		JsonPath js = new JsonPath(response);
		String sessionid = js.get("session.value");
		log.debug("sessionid " +sessionid);
		return sessionid;
	}
	
	@Test
	public void CreateJiraIssue() {
		RestAssured.baseURI=prop.getProperty("JIRSHOST");
		Response res = given().
		header("Content-Type","application/json").
		header("cookie","JSESSIONID="+GetSessionId()).log().all().
		body(payLoad.getCreateIssueBody()).
		when().
		post(resources.getCreateIssueJira()).
		then().log().all().
		assertThat().contentType(ContentType.JSON).
		extract().response();
		
		String response = res.asString();
		JsonPath js = new JsonPath(response);
		String bugid = js.get("id");
		log.debug("bugid " +bugid);
		
	//Adding the Comment
		RestAssured.baseURI=prop.getProperty("JIRSHOST");
		Response res1 = given().
		header("Content-Type","application/json").
		header("cookie","JSESSIONID="+GetSessionId()).log().all().
		body(payLoad.getAddCommentBody()).
		when().
		post("/rest/api/2/issue/"+bugid+"/comment").
		then().log().all().
		assertThat().contentType(ContentType.JSON).
		extract().response();
		
		String response1 = res1.asString();
		JsonPath js1 = new JsonPath(response1);
		 String commentId = js1.get("id");
			log.debug("commentId " +commentId);
		
	
	//Updating the Comment
	
		RestAssured.baseURI=prop.getProperty("JIRSHOST");
		Response res2 = given().
		header("Content-Type","application/json").
		header("cookie","JSESSIONID="+GetSessionId()).
		body(payLoad.getUpdateCommentBody()).log().all().
		when().
		put("/rest/api/2/issue/"+bugid+"/comment/"+commentId+"").
		then().log().all().
		assertThat().contentType(ContentType.JSON).
		extract().response();
		
		String response2 = res2.asString();
		JsonPath js2 = new JsonPath(response2);
		String updatecommentId = js2.get("id");
		log.debug("commentId " +commentId);
		Assert.assertEquals(commentId, updatecommentId);
		
		//Delete the comment
		RestAssured.baseURI=prop.getProperty("JIRSHOST");
		given().
		header("cookie","JSESSIONID="+GetSessionId()).
		log().all().
		when().
		delete("/rest/api/2/issue/"+bugid+"").
		then().log().all();
		
		
		
	}
	
	
	

	//it formats the xml to String
		public static String GenerateStringFromResource(String path) throws IOException
		{
			return new String(Files.readAllBytes(Paths.get(path)));
		}
}
