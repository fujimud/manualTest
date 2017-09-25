import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class factoryTestCases {
	
	private int param;
	
	@Factory(dataProvider = "dataMethod") 
	public factoryTestCases(int param) {
		this.param = param;
	}

	@DataProvider
	public static Object[][] dataMethod() {
		return new Object[][] {{0}, {1}};
	}
	
	@Test
	public void testMethodOne() {
		int opValue = param + 1;
		System.out.println("Test method one output " + opValue );
	}
	
	@Test
	public void testMethodTwo() {
		int opValue = param + 2;
		System.out.println("Test method two output " + opValue );
		
		
		for(Object line : ja) {
			JSONObject jo = (JSONObject) line;
			this.code = jo.get("code").toString();
			this.description = jo.get("description").toString();
			this.findMatch = jo.get("findMatch").toString();
			this.jsonPath = jo.get("jsonPath").toString();
			this.landings = jo.get("landings").toString();
			this.pathname = jo.get("pathnmae").toString();
			
		}
		
	} 
}
