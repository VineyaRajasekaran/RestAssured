package files;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ResusableMethods {
	
	public JsonPath rawToJson(Response res)
	{
		String responseString = res.asString();
		JsonPath js = new JsonPath(responseString);
		return js;
	}
	
	public XmlPath rawToXml(Response res)
	{
		String responseString = res.asString();
		XmlPath x  = new XmlPath(responseString);
		return x;
	}
	
	public static String getSessionId()
	{
		RestAssured.baseURI = "http://localhost:8080";
		Response res = given().
				header("Content-Type","application/json").
				body("{ \"username\": \"vineya\", \"password\": \"ourchat75\" }").
				when().post("/rest/auth/1/session").
				then()
				.assertThat().statusCode(200)
				.extract().response();

		// Grab the place id
		ResusableMethods rs = new ResusableMethods();
		JsonPath js = rs.rawToJson(res);
		String sessionid = js.get("session.value");
		
		return sessionid;
		
	}
	public ArrayList<String> ExcelDriven(String SheetName, String Testcasename) throws IOException {
		ArrayList<String> data = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\ExcelDriven.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int NumOfSheet = workbook.getNumberOfSheets();
		for (int i = 0; i < NumOfSheet; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(SheetName)) {
				XSSFSheet Sheet = workbook.getSheetAt(i);
				Iterator row = Sheet.iterator();
				Row firstrow = (Row) row.next();
				Iterator<Cell> ce = firstrow.cellIterator();
				int k = 0;
				int column = 0;
				while (ce.hasNext()) {
					if (ce.next().getStringCellValue().equalsIgnoreCase("Selenium")) {
						column = k;
					}
					k++;
				}

				System.out.println("column" + column);

				while (row.hasNext()) {
					Row r = (Row) row.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(Testcasename)) {

						Iterator cv = r.cellIterator();
						while (cv.hasNext()) {
							
				  data.add(( (Cell) cv.next()).getStringCellValue());
							            
							}
							 
							/**if(((Cell) cv.next()).getCellType() ==CellType.STRING)
							{
								data.add(( (Cell) cv.next()).getStringCellValue());
							}
							else {
								data.add(NumberToTextConverter.toText(((Cell) cv.next()).getNumericCellValue()));
														
							}
						}**/
							
						}
					}

				}

			}
		return data;
		}

		
	
}
