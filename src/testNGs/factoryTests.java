package testNGs;


import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import mainSrc.mainCodes;


public class factoryTests {
	mainCodes mc = new mainCodes();
	mainCodes.jsonFile jf = mc.new jsonFile();
	mainCodes.logger logCat = mc.new logger();    // declaring an inner class 
	mainCodes.jsonFile jsonLog = mc.new jsonFile();
	String[] keys = {"findMatch", "code", "description", "jsonPath", "pathname", "expectedResult"};
	
	@DataProvider(name = "validJsonFile")
	public Object[][] validJsonData() throws FileNotFoundException, IOException, ParseException {		
		JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader("src/config/dataTesting/jsonTestData.json"));
		JSONArray ja = (JSONArray) jo.get("getGoodData");
				
		int index = 0;	

		String[][] arrayJson = new String[ja.size()][keys.length];

		for (Object line :ja) {	
			JSONObject jData  = (JSONObject) line;
				int cnt = 0;
				for(String key : keys) {
					arrayJson[index][cnt++] = (String) jData.get(key);
				}
				index++;
		}
		
		return arrayJson;
		
	}

	@DataProvider(name = "invalidJsonFile")
	public Object[][] invalidJsonData() throws IOException,ParseException {
		
		JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader("src/config/dataTesting/jsonTestData.json"));
		JSONArray ja = (JSONArray) jo.get("getBadData");
						
		int index = 0;
		//int totalCount = 0;		

		String[][] arrayJson = new String[ja.size()][keys.length];

		for (Object line :ja) {	
			JSONObject jData  = (JSONObject) line;
				int cnt = 0;
				for(String key : keys) {
					arrayJson[index][cnt++] = (String) jData.get(key);
				}
				index++;
				//totalCount++;
		}
		//System.out.println("Total Count >> " + totalCount);
		return arrayJson;		
	}
		
	@Test(enabled = true, priority= 1, dataProvider = "validJsonFile", description="valid information being passed from json file")
	public void getValidDataFromJsonFile(String[] testData) {


			System.out.println("DESC: >> " + testData[2]);			// display the description
			try {
				assertEquals(jf.getDataFromJsonFile(testData[4], testData[3], testData[0]), testData[5]);
				System.out.println("Pause");
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	@Test (enabled = false, priority= 2, dataProvider = "invalidJsonFile", description="throw exception errors for invalid parameters sent from a json file", expectedExceptions = {ParseException.class, IOException.class})
	public void getDataFromJsonFile(String[] testData) throws FileNotFoundException, IOException, ParseException {		
		
		System.out.println("DESC: >> " + testData[2]);		// display the description
		System.out.println(jf.getDataFromJsonFile(testData[4], testData[3], testData[0]));		
		
		
	} 
	
}
	

