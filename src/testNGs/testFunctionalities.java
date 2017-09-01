package testNGs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import mainSrc.mainCodes;





public class testFunctionalities {
	mainCodes mc = new mainCodes();
	mainCodes.logger logCat = mc.new logger();    // declaring an inner class 
	mainCodes.jsonFile jsonLog = mc.new jsonFile();

	
	
	@Test(enabled = true, priority = 0, description = "get navigation point", groups = {"all", "navigation"})
	public void navigationMap() {
		
	}
	
	
	
	
	
	@Test(enabled = false, priority = 2, description = "No matching Key is found in a Json array", groups = {"all", "json"})
	public void noMatchFound4Key() throws FileNotFoundException, IOException, ParseException {
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));
		
		Object result = jsonLog.getJsonArray(jo, "Dummy", "Golf");
		assertEquals(result.toString(), "");	
	}
	
	@Test(enabled = false, priority = 1, description = "No matching Value is found in a Json array", groups = {"all", "json"})
	public void noMatchFoundinJSONArray() throws FileNotFoundException, IOException, ParseException {
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));
		
		Object result = jsonLog.getJsonArray(jo, "A1", "dummy");
		System.out.println(result);
		assertEquals(result.toString(), "");	
	}
	
	@Test(enabled = false, priority = 0, description = "return a value from a JSON array", groups = {"all", "json"})
	public void getJsonArrayAndReturnaValue() throws FileNotFoundException, IOException, ParseException {
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));
		
		Object result = jsonLog.getJsonArray(jo, "A1", "Golf");
		assertEquals(result.toString(), "{\"A1\":\"Golf\",\"A2\":\"India\",\"A3\":\"Juliet\"}");	
	}
	
	
	@Test(enabled = false, priority = 0, description="figure out how toget a value from a JSON array", groups= {"all" , "json"})
	public void getJsonArray() throws FileNotFoundException, IOException, ParseException {
		
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));

		if (jo instanceof JSONArray) {
			
			System.out.println("this is a jsonArray");
			int size = ((JSONArray) jo).size();
			System.out.println("SIZE >>> " + size);
			System.out.println(jo);
			
			for(Object obj : (JSONArray) jo) {
				
			
				JSONObject temp = (JSONObject) obj;
				Object value = temp.get("A1");
				if (value.toString().equals("Golf")) {
					System.out.println("match found   " + value.toString());
					System.out.println(obj);
				} else {
					System.err.println("No match    " + value.toString());
				}
					
			}
			
			
		} else {
			System.out.println("This is a json object");
		}
		

	}
	
	
	@Test(enabled = false, priority = 0, description = "find a match for Facebook from the json object", groups = {"all", "json"})
	public void getJsonObject() throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("src/config/TC_navigation.json"));
		assertEquals(jsonLog.getJsonObject(obj, "facebook"), "Facebook");
	}
	
	@Test(enabled = false, priority = 1, description = "return blank when no match is found", groups = {"all", "json"})
	public void findNoMatchFromJsonObject() throws FileNotFoundException, IOException, ParseException {
			Object obj =new JSONParser().parse(new FileReader("src/config/TC_navigation.json"));
			assertEquals(jsonLog.getJsonObject(obj, "dummy"), "");
	}
	
	
	
	@Test(enabled = false, priority = 0, description = "add information to a log", groups = {"all", "logging"}) 
	public void putInfoIntoLog() {
		logCat.debug("testHeader", "warning warning");
		logCat.error("Error Methods", "error error");
	}
	
	
	@Test(enabled = false, priority = 2, description ="adding an array of strings into a file", groups = {"all", "IOFile"})
	public void putArrayStringIntoFile() {
		String[] StrArray = {"Cheese", "yogurt", "pudding", "butter"};		
		assertTrue(mc.putStringArrayIntoFile(Paths.get("src/config/testArrayList.txt"), StrArray));
	
	}
	
	@Test(enabled = false, priority = 1, description = "put an arraylist into a file", groups = {"all", "IOFile"}) 
		public void putArrayListIntoFile() {
			ArrayList<String> obj = new ArrayList<String>();
			obj.add("ArrayList: This is a new file");
			obj.add("ArrayList: adding a second line");
			obj.add("ArrayList: The cow jumped over the moon");
			obj.add("");
			obj.add("ArrayList: there's a space above here");
			obj.add("ArrayList: end of line");
						
			assertTrue(mc.putArrayListIntoFile(Paths.get("src/config/testArrayList.txt"), obj));

		}
		
		
	@Test(enabled = false, priority = 0, description= "put text into file", groups = {"all", "IOFile"})
	public void putTextIntoFile() {
		//putDataIntoFile

			assertTrue(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), "Hello World"));
			assertTrue(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), "New line"));
			assertTrue(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), ""));
			assertTrue(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), "Third and final line")); 
					
	}
		
	

	
	@Test(enabled = false, priority = 0, description = "get file using Java 8" , groups = {"all", "getFile"})
	public void getFile() {
		System.out.println(mc.getDataFromFile(Paths.get("src/config/test1File.txt")));
	}
	
	@Test(enabled = false, priority = 1, description = "get JSON file using Java 8", groups = {"all", "getFile"})
	public void getJSONFile() {
		System.out.println(mc.getDataFromFile(Paths.get("src/config/tc_navigation.json")));
	}
	
	@Test(enabled = false, priority = 2, description = "failed to get a file using Java 8", groups = {"all", "getFile"})
	public void Fail2getFile() {
		System.out.println(mc.getDataFromFile(Paths.get("test1File.txt")));
	}	
	

	@Test(enabled = false, priority = 3, description= "assign getFile to an array and display information", groups = {"all", "getFile"})
	public void getFileThenAssign2Array() {
		List<String> lines = mc.getDataFromFile(Paths.get("src/config/test1File.txt"));
		//System.out.println("SIZE >> " + lines.size());
		for (String line : lines) {
			System.out.println(">> " + line);
		}		
	}
	
	@Test (enabled = false, priority = 0, description = "file does exist and return true", groups = {"all", "getFile"})	
	public void FileReturnTrue() {

		assertTrue(mc.DoesFileExist(Paths.get("src/config/tc_navigation.json")));
		
	}
	
	@Test (enabled = false, priority = 1, description = "file does not exist and return false", groups = {"all", "getFile"})
	public void FileReturnFalse() {
		assertFalse(mc.DoesFileExist(Paths.get("src/config/dummy.txt")));
	}
	
	@Test (enabled = false, priority = 2, description = "file exist but in a different location and return false", groups = {"all", "getFile"})
	public void DifferentDirectory() {
		assertFalse(mc.DoesFileExist(Paths.get("src/dummy/tc_navigation.json")));
	}	


	
	@AfterMethod
	public void endTest() {
		System.out.println(System.lineSeparator());
	}

}
