package testNGs;


import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import mainSrc.mainCodes;
import mainSrc.mainCodes.jsonFile.JsonException;


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
		

	
	@Test(enabled = false, priority= 1, dataProvider = "validJsonFile", description="valid information being passed from json file")
	//public void getValidDataFromJsonFile(String[] testData) {
	public void getValidDataFromJsonFile(String findMatch, String status, String description, String fileLocation, String jsonUrl, String expectedResult) { 
	
			try {
				assertEquals( jf.getDataFromJsonFile(jsonUrl, fileLocation, findMatch), expectedResult);
			} catch (JsonException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logCat.warning("getJsonObject", sw.toString());	
			} catch (IOException | ParseException e1) {
				StringWriter sw = new StringWriter();
				e1.printStackTrace(new PrintWriter(sw));
				logCat.warning("getJsonObject", sw.toString());
			} 
	}
	
	
	@Test (enabled = false, priority= 2, dataProvider = "invalidJsonFile", description="throw FileNotFoundException exception errors for invalid parameters sent from a json file", expectedExceptions = {FileNotFoundException.class, JsonException.class})	
	public void getDataFromJsonFile(String findMatch, String status, String description, String jsonPath, String fileLocation, String expectedResult)  throws FileNotFoundException, JsonException{	

		System.out.println(jf.returnDataFromJsonFile(fileLocation, jsonPath, findMatch));
		

	}
	
	@Test(enabled = false, priority = 3, dataProvider = "invalidJsonFile", description="throws JsonException errors" )
	public void triggerJsonExceptionError(String findMatch, String status, String description, String fileLocation, String jsonUrl, String expectedResult) {
		//System.out.println("findMatch > " +url);
		//System.out.println("code > " + data1);
		//System.out.println("description: " +data2);
		//System.out.println("jsonPath: " + data3);
		//System.out.println("jsonUrl: " + data4);
		//System.out.println("expectedResult: " + data5);
		
		
		try {
			Assert.assertEquals( jf.returnDataFromJsonFile( fileLocation, jsonUrl, findMatch), expectedResult);
			//System.out.println(jf.returnDataFromJsonFile(fileLocation, jsonUrl, findMatch));
		} catch (IOException | ParseException | JsonException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logCat.warning("getJsonObject", sw.toString());
		}

		
		
		
		
	}
}


	

