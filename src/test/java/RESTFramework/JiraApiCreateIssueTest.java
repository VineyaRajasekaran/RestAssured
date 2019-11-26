package RESTFramework;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.ResusableMethods;
import files.payLoad;
import files.resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JiraApiCreateIssueTest {

	Properties prop = new Properties();

	@BeforeTest
	public void getEnvironmentValues() throws IOException {
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\dinav\\RestAssuredProject\\src\\main\\java\\files\\env.properties");
		prop.load(fis);
	}

	@Test
	public void createIssue() {
		RestAssured.baseURI = prop.getProperty("JIRSHOST");
		Response res = given().
				header("cookie","JSESSIONID="+ResusableMethods.getSessionId()).
				header("Content-Type","application/json").
				body(payLoad.getCreateIssueBody()).
				when().post("/rest/api/2/issue").
				then().
				assertThat().statusCode(201).and().contentType(ContentType.JSON).
				extract().response();
		ResusableMethods rs = new ResusableMethods(); 
		JsonPath js = rs.rawToJson(res);
		String id = js.get("id");
		System.out.println("id is "+id);
		

		
	}


}
