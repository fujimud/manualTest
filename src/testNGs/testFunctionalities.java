package testNGs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import junit.framework.Assert;
import mainSrc.mainCodes;
import mainSrc.mainCodes.jsonFile.JsonException;
import mainSrc.practiceJavaHere;
import mainSrc.practiceJavaHere.Websites;
import mainSrc.tMobile_GlobalFunction;



public class testFunctionalities {
	mainCodes mc = new mainCodes();
	mainCodes.logger logCat = mc.new logger();    // declaring an inner class 
	mainCodes.jsonFile jsonLog = mc.new jsonFile();
	mainCodes.IOfile ioFile = mc.new IOfile();
	mainSrc.tMobile_GlobalFunction tgf = new mainSrc.tMobile_GlobalFunction(); 
	
	String findMatch = null;
	String jsonPath = null;
	String pathname = null;
	String description = null;
	String code = null;
	
	public static WebDriver driver = null;

	//public static WebDriver driver = null;
	String username = System.getProperty("user.name");
	
/*	
	@BeforeTest
	public void gotoYahooUsingChrome() {
		String browsertype = "chrome";
		
		switch(browsertype.toLowerCase().trim()) {
		case "chrome": 
			System.setProperty("webdriver.chrome.driver", "\\webdriver\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "internetexplorer":
		case "ie":
			driver = new InternetExplorerDriver();
			System.setProperty("webdriver.ie.driver", "\\webdriver\\IEDriverServer.exe");
			break;
//		case "firefox":
//		case "ff":
//			FirefoxDriver driver = new FirefoxDriver();
//			break;
		default:
			logCat.warning("connect2webdriver", "Browser '" + browsertype + "' is currently not supported" );
		}

		driver.get("https://www.google.com/");
	}
*/
	
	@Test(enabled = true, description = "navigate through the spreadsheet")
	public void readXSSF() {
		//String file = "/TestCases/1_Login_AD.xlsx";
		String file = "/TestCases/tcTemplate.xlsx";
		try {
			ioFile.readFromTC_SpreadSheet(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@Test (enabled = false, description="navigate to t-mobile")
	public void openTMobile() {
		
		try {
			tgf.route("ie", "go2Login");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test(enabled = false, description = "get to the product page")
	public void get2ProductPage() {
		mc.router("product");
	}
	
	@Test(enabled = false, description = "practice using testException")
	public void throwTestException() {
		practiceJavaHere te = new practiceJavaHere(Websites.EBAY);
		te.throwJsonException();
	}
	
	@Test(enabled =false, description= "practice using Enum") 
	public void getEBayfromEnum() { 
		practiceJavaHere ebay = new practiceJavaHere(Websites.EBAY);
		ebay.companyDetails();
	}

	@Test(enabled = false, priority = 4, description = "match error message when a bad file name is passed", groups = "all, json")
	public void displayErrorMessageWhenBadFileNameIsPassed() {
		try {
			jsonLog.returnDataFromJsonFile("src/bad/urlRecords.json", "landings;pagename>link", "Facebook");
		} catch (JsonException actualResult) {
				actualResult.printStackTrace();
				CharSequence expectedResult = "There is an error with the json file at the following location >> src/bad/urlRecords.json";
				assertTrue(actualResult.toString().contains(expectedResult));	
		}
	}
	
	@Test(enabled = false, priority = 3, description = "exception is thrown when a a bad file name or it's location is passed", groups = "all, json", expectedExceptions = {JsonException.class})
	public void badFileNameOrLocationThrowsJsonExceptionError() throws JsonException, FileNotFoundException, IOException, ParseException {
		jsonLog.returnDataFromJsonFile("src/bad/urlRecords.json", "landings;pagename>link", "Facebook");
	}
	
	@Test(enabled= false, priority = 3, description = "not exception is thrown when a valid file name is provided", groups = "all, json")
	public void validFileNameNoException() throws JsonException, FileNotFoundException, IOException, ParseException {
		jsonLog.returnDataFromJsonFile("src/config/urlRecords.json", "landings;pagename>link", "Facebook");
	}
	
	
	@Test(enabled = false, priority = 2, description = "throw a JsonException when an invalid url or file is provided", groups = "all, json", expectedExceptions = {JsonException.class})
	public void invaldFileprovided() throws JsonException, FileNotFoundException, IOException, ParseException {
		jsonLog.returnDataFromJsonFile("src/bad/location/file.json", "landings;pagename>link", "Facebook");
	}
	
	@Test(enabled = false, priority =2, description = "no exception is throws from ListingOfPossibleWebpageNames()")
	public void ListingOfPossibleWebpageNamesWorks()  throws JsonException,FileNotFoundException, IOException, ParseException {
		jsonLog.returnDataFromJsonFile("src/config/urlRecords.json", "landings;pagename>link", "Facebook");
	}
	
	
	@Test(enabled = false, priority = 1, description = "No exception is thrown when all parameters has data", groups = "all, json")
	public void noExceptionMessageIsThrownWhenEachParamterHasAValidData() throws JsonException,FileNotFoundException, IOException, ParseException  {
		jsonLog.returnDataFromJsonFile("src/config/urlRecords.json", "landings;pagename>link", "Facebook");
	}

	@Test(enabled = false, priority = 1, description = "Confirm Exception message", groups = "all, json")
	public void confirmExceptionErrorMessage() throws FileNotFoundException, IOException, ParseException  {
		try {
			jsonLog.returnDataFromJsonFile("src/config/urlRecords.json", "", "Facebook");
		} catch (JsonException actualResult) {
			actualResult.printStackTrace();
			CharSequence expectedResult = "getDataFromJsonFile: Each parameter requires data, please provide the data";
			assertTrue(actualResult.toString().contains(expectedResult));
		}
	}	
	
	@Test(enabled = false, priority = 1, description = "throw and exception when all the parameters are empty", groups = "all, json", expectedExceptions= {JsonException.class})
	public void throwJsonExceptionWhenAllParametersIsEmpty() throws JsonException,FileNotFoundException, IOException, ParseException  {
		jsonLog.returnDataFromJsonFile("", "", "");
	}
	
	@Test(enabled = false, priority = 1, description = "throw and exception when findMatch is empty", groups = "all, json", expectedExceptions= {JsonException.class})
	public void throwJsonExceptionWhenfindMatchIsEmpty() throws JsonException,FileNotFoundException, IOException, ParseException  {
		jsonLog.returnDataFromJsonFile("src/config/urlRecords.json", "landings;pagename>link", "");
	}

	@Test(enabled = false, priority = 1, description = "throw and exception when jsonPath is empty", groups = "all, json", expectedExceptions= {JsonException.class})
	public void throwJsonExceptionWhenJsonPathIsEmpty() throws JsonException,FileNotFoundException, IOException, ParseException  {
		jsonLog.returnDataFromJsonFile("src/config/urlRecords.json", "", "facebook");
	}
	
	@Test(enabled = false, priority = 1, description = "throw and exception when pathName is empty", groups = "all, json", expectedExceptions= {JsonException.class})
	public void throwJsonExceptionWhenPathNameIsEmpty() throws JsonException,FileNotFoundException, IOException, ParseException  {
		jsonLog.returnDataFromJsonFile("", "landings;pagename>link", "facebook");
				
	}
	
	@Test(enabled = false, priority = 4, description = "throw JsonException error when json file is in a bad format", groups = "all, json", expectedExceptions = {JsonException.class})
	public void triggerJsonException() throws JsonException,FileNotFoundException, IOException {
		jsonLog.throwJsonException("src/config/dataTesting/CorruptJsonFile.json");
	}
	
	
	@Test(enabled = false, priority = 3, description = "display JsonException error message", groups = "all, json")
	public void displayJsonExceptionMessage() throws  JsonException, FileNotFoundException, IOException {
		
		try {
			jsonLog.throwJsonException("src/config/dataTesting/CorruptJsonFile.json");
		} catch (JsonException actualResult) {
			CharSequence expectedResult = "There a problem with the json file when trying to parse it's data in the following file 'src/config/dataTesting/CorruptJsonFile.json'";
			assertTrue(actualResult.toString().contains(expectedResult));
		}
	}
	
	@Test(enabled = false, priority = 2, description = "display FileNotFoundException message", groups = "all, json")
	public void displayFileNotFoundExceptionMessage() throws JsonException, FileNotFoundException, IOException { 
		try {
			jsonLog.throwJsonException("");
		} catch (JsonException actualResult) {
			CharSequence expectedResult = "No file could be found at the provided location ''";
			assertTrue(actualResult.toString().contains(expectedResult));
		}
}
	
	@Test(enabled = false, priority = 1, description = "throw fileNotFoundException when a correct path is not provided", groups = "all, json" , expectedExceptions = {JsonException.class})
	public void throwFileNotFoundException() throws JsonException, FileNotFoundException, IOException { 
			jsonLog.throwJsonException("");	
	}
	
	
	@Test(enabled= false, priority = 1, description = "read MS excel spreadsheet", groups="all, excell")
	public void readMSExcell() {
		String file = "/TestCases/1_Login_AD.xlsx";
		String sheetName = "";
		try {
			ioFile.readSpreadSheet(file, sheetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test(enabled = false, priority = 1, description="initializing IE webdriver")
	public void openIEwebpage() {
		WebDriver driver = new InternetExplorerDriver();
		System.setProperty("webdriver.ie.driver", "\\webdriver\\IEDriverServer.exe");
		driver.get("https://www.yahoo.com/");
	}
	
	@Test (enabled = false, priority = 1, description = "Initializing firefox" )
	public void openFireDrier() {
		FirefoxDriver driver = new FirefoxDriver();
		driver.get("https://www.yahoo.com/");
	}
	
	
	@Test (enabled = false, priority = 1, description="initite Chrome driver", groups="all, driver")
	public void openChromeDriver() {		

		System.setProperty("webdriver.chrome.driver", "\\webdriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.yahoo.com/");
	}
	
	
	@Test(enabled = false, priority= 1, groups ="MultipleJson")
	public void runMultipleJsonTests() throws FileNotFoundException, IOException, ParseException {
		System.out.println("Desc >> " + description);
		jsonLog.getDataFromJsonFile(pathname, jsonPath, findMatch);		
		
	}
	
	@Test (enabled = false, priority=4, description = "get a JsonArray", groups = {"all", "navigation"})
	public void getJsonArrayWithObjects() {
		
			int number_of_pages_being_tested  = 0;
			Object fileName = new JSONParser().parse(new FileReader("testCases/testCases.json"));
			JSONObject tempJO = (JSONObject) fileName;
			Object getDirections = (Object) tempJO.get("get2Plans");
			JSONArray tempJA = (JSONArray) getDirections;
			number_of_pages_being_tested = tempJA.size();
			System.out.println(number_of_pages_being_tested);
			//obj = (Object) tempJO.get(key.toLowerCase());
			//Object getDirections = jsonLog.getJsonObject(fileName, "get2ProductDetailpage");
			//Object testFile = jsonLog.returnDataFromJsonFile(fileName.toString(), "get2ProductDetailpage" , "get2ProductDetailpage");
			//JSONObject temp = (JSONObject) testFile;
			System.out.println(getDirections.toString());
			//Object getJson = temp.get(arg0);
			//JSONArray ja = (JSONArray) getJson
			

		
	}
	
	
	
	
	@Test (enabled = false, priority=3, description="test an array of data and retrieve exception errors", groups = {"all", "navigation"})
	public void runArrayOfData() throws FileNotFoundException, IOException, ParseException   {
//  expectedExceptions = {ClassCastException.class, ParseException.class, IOException.class}		
		Object obj = new JSONParser().parse(new FileReader("src/config/dataTesting/testData.json"));
		System.out.println("obj > " + obj);
		JSONObject tempJO = (JSONObject) obj;		
		Object getJson = tempJO.get("getDataFromJsonFile");
		//System.out.println(">> " + getJson);
		JSONArray ja = (JSONArray) getJson;
		
		for(Object line : ja) {
			System.out.println(">> " + line.toString());
			JSONObject jo = (JSONObject) line;
			System.out.println(">> " + jo.get("findMatch"));
			System.out.println(">> " + jo.get("jsonPath"));
			System.out.println(">> " + jo.get("pathname"));
			
		}
	}
	
	
	@Test (enabled = false, priority = 2, description = "get data from json while when path is complete", groups = {"all", "navigation"}, expectedExceptions = {ClassCastException.class, ParseException.class, IOException.class})
	public void throwExceptionErrorWhenJSONpathIsInvalid() throws IOException, ParseException  {		
		System.out.println("INVALID >>>>> " + jsonLog.getDataFromJsonFile("src/config/urlRecords.json", "landings>link", "facebook2"));	
	}
	
	
	@Test (enabled = false, priority = 1, description = "get data using Invalid URL to json file", groups = {"all", "navigation"}, expectedExceptions = {IOException.class})
	public void getDataWithInvalidFileURL() throws IOException, ParseException  {		
		System.out.println("INVALID >>>>> " + jsonLog.getDataFromJsonFile("src/config/dummpy.json", "landings;pagename>link", "facebook"));	
	}
	
	
	@Test (enabled = false, priority = 0, description = "get the link for Facebook found in a json file", groups = {"all", "navigation"})
	public void getFileFromJson() throws FileNotFoundException, IOException, ParseException {
		assertEquals(jsonLog.getDataFromJsonFile("src/config/urlRecords.json", "landings;pagename>link", "facebook"), "http://www.facebook.com/");		
	}
	
	
	@Test(enabled = false, priority = 2, description = "open browser using Chrome", groups = {"all", "navigation"})
	public void openChrome() throws FileNotFoundException, IOException, ParseException {	
		mc.openWebPage("Chrome", "facebook");
	}
	
	
	
	
	
	@Test(enabled = false, priority = 2, description = "No matching Key is found in a Json array", groups = {"all", "json"})
	public void noMatchFound4Key() throws FileNotFoundException, IOException, ParseException {
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));
		
		Object result;
		try {
			result = jsonLog.getJsonArray(jo, "Dummy", "Golf");
			assertEquals(result.toString(), "");
		} catch (JsonException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logCat.warning("getJsonObject", sw.toString());
		}
			
	}
	
	@Test(enabled = false, priority = 1, description = "Dispaly JsonException message", groups = {"all", "json"})
	public void displayExceptionMessageForJsonArray() throws FileNotFoundException, IOException, ParseException {
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));
		
		Object result;
		try {
			result = jsonLog.getJsonArray(jo, "A1", "dummy");
			System.out.println(result);
			assertEquals(result.toString(), "");	
		} catch (JsonException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logCat.warning("getJsonObject", sw.toString());
		}
		
	}
	
	@Test(enabled = false, priority = 1, description = "No matching Value is found in a Json array", groups = {"all", "json"}, expectedExceptions = JsonException.class)
	public void noMatchFoundinJSONArray() throws FileNotFoundException, IOException, ParseException, JsonException {
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));
		
		Object result = jsonLog.getJsonArray(jo, "A1", "dummy");
		System.out.println(result);
		assertEquals(result.toString(), "");	
	}
	
	@Test(enabled = false, priority = 0, description = "return a value from a JSON array", groups = {"all", "json"})
	public void getJsonArrayAndReturnaValue() throws FileNotFoundException, IOException, ParseException {
		Object jo = new JSONParser().parse(new FileReader("src/config/TC_jsonArrayFile.json"));
		
		Object result;
		try {
			result = jsonLog.getJsonArray(jo, "A1", "Golf");
			assertEquals(result.toString(), "{\"A1\":\"Golf\",\"A2\":\"India\",\"A3\":\"Juliet\"}");
		} catch (JsonException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logCat.warning("getJsonObject", sw.toString());
		}
			
	}
	
	
	@Test(enabled = false, priority = 0, description="figure out how to get a value from a JSON array", groups= {"all" , "json"})
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
	public void displayExceptionErrorForJsonObject() throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("src/config/TC_navigation.json"));
		String name = "sdfsd";
		
		try {
			assertEquals(jsonLog.getJsonObject(obj, name), "Facebook");
		} catch (JsonException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logCat.warning("getJsonObject", sw.toString());
		}
	}
	
	
	@Test(enabled = false, priority = 0, description = "find a match for Facebook from the json object", groups = {"all", "json"})
	public void getJsonObject() throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("src/config/TC_navigation.json"));
		String name = "facebook";
		
		try {
			assertEquals(jsonLog.getJsonObject(obj, name), "Facebook");
		} catch (JsonException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logCat.warning("getJsonObject", sw.toString());
		}
	}
	
	@Test(enabled = false, priority = 1, description = "return blank when no match is found", groups = {"all", "json"}, expectedExceptions = {FileNotFoundException.class, JsonException.class, ParseException.class, IOException.class})
	public void findNoMatchFromJsonObject() throws FileNotFoundException, IOException, ParseException, JsonException {
			Object obj =new JSONParser().parse(new FileReader("src/config/TC_navigation.json"));
			@SuppressWarnings("unused")
			Object result = jsonLog.getJsonObject(obj, "roadrunner");
	}
	
	
	
	@Test(enabled = false, priority = 0, description = "add information to a log", groups = {"all", "logging"}) 
	public void putInfoIntoLog() {
		logCat.debug("testHeader", "warning warning");
		logCat.error("Error Methods", "error error");
	}
	
	
	@Test(enabled = false, priority = 2, description ="adding an array of strings into a file", groups = {"all", "IOFile"})
	public void putArrayStringIntoFile() {
		String[] StrArray = {"Cheese", "yogurt", "pudding", "butter"};		
		//assertTrue(mc.putStringArrayIntoFile(Paths.get("src/config/testArrayList.txt"), StrArray));
	
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

			//assertEquals(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), "Hello World"));
			//assertEquals(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), "New line"));
			//assertEquals(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), ""));
			//assertEquals(mc.putSingleLineIntoFile(Paths.get("src/config/testSingleLineIntoFile.txt"), "Third and final line")); 
					
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


