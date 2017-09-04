package testNGs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Factory;
import mainSrc.mainCodes;


public class testUsingFactory {
	private mainCodes mc = new mainCodes();
	private mainCodes.jsonFile json = mc.new jsonFile();
	
	
	
	@Factory
	public Object[] createInstance() {
		Object[] result = new Object[10];
		
		try {

			Object testData = new JSONParser().parse(new FileReader("src/config/dataTesting/testData.json"));
			//Object data = json.getJsonObject(testData, "getDataFromJsonFile");			

			System.out.println("DATA >>>>  " +  data);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("FileNotFoundException");
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("IOException");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("ParseException");
			e.printStackTrace();
		}
		
		
		return result;
	}
}
