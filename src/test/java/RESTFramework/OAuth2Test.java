package RESTFramework;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.GetCources;
import pojo.WebAutomation;

public class OAuth2Test 
{
	private static final Logger log = LogManager.getLogger(OAuth2Test.class.getName());
	Properties prop = new Properties();
	WebDriver driver;
	String[] actual= {"Selenium Webdriver Java","Cypress","Protractor"};
	ArrayList<String> expected= new ArrayList<String>();
	
	@BeforeTest
	public void getEnvironmentValues() throws IOException {
	log.debug(System.getProperty("user.dir")+"\\src\\main\\java\\files\\env.properties");
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir")+"\\src\\main\\java\\files\\env.properties");
		
		prop.load(fis);
	}



	@Test
	public void QAuthTest() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\dinav\\Downloads\\chromedriver_win32_Version2.35\\chromedriver.exe");

		 driver = new ChromeDriver();

		driver.get(
				"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("vineya@gmail.com");
		driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
		Thread.sleep(4000L);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(prop.getProperty("pwd"));
		driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
		Thread.sleep(4000L);
		// to get authorization code
		
		String url = driver.getCurrentUrl();
		
		log.debug("currenturl" +url);
		String authcode = url.split("&scope")[0];
		log.debug("authcode" +authcode);
		String url1 = authcode.split("code=")[1];
		log.debug("url1" +url1);
	
		
		
		// to get access code
		String res1 = given().
				urlEncodingEnabled(false). // to not allow the % symbol in url1 to change otherwise it formats the access code
				queryParam("code", url1).log().all()
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").
				when().post("https://www.googleapis.com/oauth2/v4/token")
				.then().log().all().extract().asString();
		log.debug(res1);
		JsonPath js = new JsonPath(res1);
		
		String accesstoken = js.getString("access_token");
		log.debug("accesstoken" + accesstoken);

		// actual request
		GetCources gc  = given().
				queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON).
				when()
				.get("https://rahulshettyacademy.com/getCourse.php").
				then().extract().as(GetCources.class);
		
		log.debug(gc.getInstructor());
		log.debug(gc.getLinkedIn());
		
		
		 for(int i=0;i<gc.getCourses().getWebAutomation().size(); i++)
		 {
			 expected.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
		 }
		 
		List<String> actualArray= Arrays.asList(actual);
		 
		 Assert.assertTrue(actualArray.equals(expected));
		
		 for(int i=0;i<gc.getCourses().getApi().size(); i++)
		 {
			if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				log.debug(gc.getCourses().getApi().get(i).getPrice());
			}
		 }
		


	}

	
	@AfterTest
	public void closeBrowser() {
		driver.close();
	}
}
