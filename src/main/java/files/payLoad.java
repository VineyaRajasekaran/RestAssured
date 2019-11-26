package files;

public class payLoad {
	
	public static String placePostBody()
	{
		String b = "{" + "\"location\":{" + "\"lat\" : -38.383494," + "\"lng\" : 33.427362" + "}," + "\"accuracy\":50,"
				+ "\"name\":\"Frontline house\"," + "\"phone_number\":\"(+91) 983 893 3937\","
				+ "\"address\" : \"29, side layout, cohen 09\"," + "\"types\": [\"shoe park\",\"shop\"],"
				+ "\"website\" : \"http://google.com\"," + "\"language\" : \"French-IN\"" + "}";
		return b;

	}
	
	public static String getCreateIssueBody()
	{
		String b ="{\r\n" + 
				"    \"fields\": {\r\n" + 
				"       \"project\":\r\n" + 
				"       {\r\n" + 
				"         \"key\": \"FIR\"\r\n" + 
				"       },\r\n" + 
				"       \"summary\": \"Eclipse 8rd Defect today 11/20/2019\",\r\n" + 
				"       \"description\": \"I created from my automation program defect 3\",\r\n" + 
				"      \"issuetype\": {\r\n" + 
				"          \"name\": \"Bug\"\r\n" + 
				"       }\r\n" + 
				"   }\r\n" + 
				"	\r\n" + 
				"}";
		return b;

	}
	
	public static String getAddCommentBody()
	{
		String b ="{\r\n" + 
				"  \"body\": \"I am adding a comment from postman 11/20/2019\",\r\n" + 
				"      \"visibility\": {\r\n" + 
				"    \"type\": \"role\",\r\n" + 
				"    \"value\": \"Administrators\"\r\n" + 
				"  }\r\n" + 
				"}";
		return b;

	}
	
	public static String addBook(String isbn,String aisle) {
		String s= "{\r\n" + 
				"\r\n" + 
				"\"name\":\"Learn Appium Automation with Java\",\r\n" + 
				"\"isbn\":\""+isbn+"\",\r\n" + 
				"\"aisle\":\""+aisle+"\",\r\n" + 
				"\"author\":\"John foe\"\r\n" + 
				"}\r\n" + 
				"";
		return s;
		
	}
	 public static String deleteBook(String id) {
		 String s= "{\r\n" + 
		 		" \r\n" + 
		 		"\"ID\" : \""+id+"\"\r\n" + 
		 		" \r\n" + 
		 		"} \r\n" + 
		 		"";
		 return s;
	 }
	 
	 public static String createSessionJIRA() {
		 String s="{ \"username\": \"vineya\", \"password\": \"ourchat75\" }";
		 return s;
	 }
	 
	 public static String getUpdateCommentBody()
		{
			String b ="{\r\n" + 
					"    \"body\": \"My updated comment that 11/20/2019 is displayed but not checkbox 2\",\r\n" + 
					"    \"visibility\": {\r\n" + 
					"        \"type\": \"role\",\r\n" + 
					"        \"value\": \"Administrators\"\r\n" + 
					"    }\r\n" + 
					"}";
			return b;

		}
}


