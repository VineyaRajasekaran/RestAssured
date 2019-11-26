package files;

public class resources {
	
	

	public static String placePostData() {
		String res ="/maps/api/place/add/json";
		return res;
	}
	
	
	
	public static String placePostDeleteData() {
		String res ="/maps/api/place/delete/json";
		return res;
	}
	
	public static String placePostXml() {
		String res ="/maps/api/place/add/xml";
		return res;
	}
	

	
	public static String deletePostXml() {
		String res ="	/maps/api/place/delete/xml";
		return res;
	}
	
	public static String addBookResource() {
		String res="/Library/Addbook.php";
		return res;
		
	}
	
	
	public static String deleteBookResource() {
		String res="/Library/DeleteBook.php";
		return res;
		
	}
	
	public static String getSessionIdJIRA() {
		String res="/rest/auth/1/session";
		return res;
		
	}
	

	public static String getCreateIssueJira() {
		String res="/rest/api/2/issue";
		return res;
		
	}
	
	
	
}





