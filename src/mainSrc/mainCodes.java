//  UI Automation 

// Created by Dan Fujimura on 8/26/2017.

package mainSrc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class mainCodes {
		
	public final String CONFIGPATH = "src/config/";
	public final String PATH2RECORDS = CONFIGPATH + "records/";
	public final String PATHJSONRECORDS =  CONFIGPATH + "urlRecords.json";
	public final String LOGLOCATION = CONFIGPATH + "logs/";
	public static WebDriver driver = null;

	
	logger log = new logger();
	IOfile ioFile = new IOfile();
	
	
	
	//// webdriver /////////////////////////////////////////////////////////////////
	public void connect2webdriver(String browsertype) {
		
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
			case "firefox":
			case "ff":
				FirefoxDriver driver = new FirefoxDriver();
				break;
			default:
				log.warning("connect2webdriver", "Browser '" + browsertype + "' is currently not supported" );
		}
	}
	
	
	//// selenium //////////////////////////////////////////////////////////////////
	
	public void openWebPage(String browser, String webpageName) throws FileNotFoundException, IOException, ParseException {
		jsonFile json = new jsonFile();
		
		Object webpage = new JSONParser().parse(new FileReader("src/config/TC_navigation.json"));
		System.out.println("webpage completed >>>>> " + webpage);
		System.out.println("FIND >>>> " + webpageName);
		
		Object pageName = json.getJsonObject(webpage, webpageName);
		
		System.out.println("pageName completed  >>>> " + pageName.toString());
		
		
		String name = pageName.toString();
		System.out.println("name completed");
		
			
		Object fileName = new JSONParser().parse(new FileReader(PATHJSONRECORDS));
		System.out.println("fileName completed");
		
		Object data  = json.getJsonObject(fileName, "landing");
		System.out.println("data completed");
		
		data = json.getJsonArray(data, "application", name);
		System.out.println("RESULT >>>  " + data.toString());
	}
	
	

	///////////////////////////////////////////////////////////////////////////////////	
	public void navigationMap(String map) {
		// Purpose: navigation flow
		List<String> navigation = new ArrayList<String>(Arrays.asList(map.split(";")));
		
		for(String point : navigation) {
			System.out.println(">>  " + point);	
			switch (point) {
			case "home":
				break;
			case "detail":
				break;
			case "product":
				break;
			case "shoppingcart":
				break;
			default:
			}
		}	
	}
	
	
	

	
////JSON /////////////////////////////////////////////////////////////	

	
	////////////////////////////////////////////////////////////////////////////////////
	public class jsonFile {	
		
		
		////////////////////////////////////////////////////////////////////////////////////
		public String returnDataFromJsonFile(String fileLocation, String JsonPath, String findMatch )  throws  IOException, ParseException, JsonException {
		// Purpose: Retrieve data from a json file. It recognize and deals with both Json Objects and Json Arrays.
		// 	If a value then the result is returned as a string
		// 	Parameters: 
		//		fileLocation is the path and json file location
		//		JsonPath: a listing of keys that leads to where the value needed is located. semi-colon is the delimiter for the path needed. While > is used to identify the key that stores the value 
		//			example: "landings;pagename>link"
		//		findMatch: Key name used to return it's value	
			
			
			if (JsonPath.trim().isEmpty() || findMatch.trim().isEmpty() || (fileLocation.trim().isEmpty())) { 
				throw new JsonException("getDataFromJsonFile: There are data missing from the Json file"); 
			}
			
			String result = null;			
			jsonFile json = new jsonFile();
			Object ListingOfPossibleWebpageNames = null;
			Object jsonFile = null;			
			
			ListingOfPossibleWebpageNames = new JSONParser().parse(new FileReader("src/config/nameVariations.json"));
			jsonFile = new JSONParser().parse(new FileReader(fileLocation));
			
			
			
			
			return "Testing";
		}
		
		
		////////////////////////////////////////////////////////////////////////////////////
		public String getDataFromJsonFile(String pathName, String navigationByKeys, String findMatch) throws  IOException, ParseException, JsonException {
			// Purpose: Retrieve data from a json file. It recognize and deals with both Json Objects and Json Arrays.
			// 	If a value then the result is returned as a string
			// 	Parameters: 
			//		pathName is the path and json file location
			//		navigationByKeys: a listing of keys that leads to where the value needed is located. semi-colon is the delimiter for the path needed. While > is used to identify the key that stores the value 
			//			example: "landings;pagename>link"
			
			// Verifies that all required data are provided with some data
			if (navigationByKeys.trim().isEmpty() || findMatch.trim().isEmpty()) { 
				throw new JsonException("getDataFromJsonFile: There are data missing from the Json file"); 
			}
			
			String result = null;			
			jsonFile json = new jsonFile();
			
			
			Object ListingOfPossibleWebpageNames = new JSONParser().parse(new FileReader("src/config/nameVariations.json"));
			Object jsonFile = new JSONParser().parse(new FileReader(pathName));

			// Return a standardized name for one of the recognized companies
			findMatch = (String) json.getJsonObject(ListingOfPossibleWebpageNames, findMatch);
	
			String[] keys = navigationByKeys.split(">");
			String[] path = keys[0].split(";");
			
			// Verify the key used to get the value is identified after the '>'"
			if (keys.length <= 1) {
				throw new JsonException("getDataFromJsonFile: Key used to identify which value to retrieve is missing. The key is usually preceeded by '>' ");
			}
			String key = keys[1];
			
			
			// get the jsonObject and jsonArray
			for(String pointer : path) {
				if (jsonFile instanceof JSONObject) {
					//System.out.println("This is an JSON object");
					jsonFile = json.getJsonObject(jsonFile, pointer);				
				} else {
					//System.out.println("This is a JSON Array");
					jsonFile = json.getJsonArray(jsonFile, pointer, findMatch);
				}
			}
			
			// Verify that the return value is not a JSON array
			if ((jsonFile instanceof JSONArray)) {
				throw new JsonException("getDataFromJsonFile: This is a json array and it failed to get the value for '" + key + "'");				
			} else {
				result = (String) json.getJsonObject(jsonFile, key);
			}

			return result;
		}
		
		///////////////////////////////////////////////////////////////////////
		// my exception class
		@SuppressWarnings("serial")
		public class JsonException extends Exception {
			String msg;
			
			JsonException (String errorMsg) {
				msg = errorMsg;
			}
			
			public String toString() {
				return("JsonException> " + msg);
			}
			
		}
		
		

		////////////////////////////////////////////////////////////////////////
		public Object getJsonObject(Object obj, String key) throws JsonException {
			//System.out.println("JSONObject retrieve the value based from the provided key");
			
			
			JSONObject tempJO = (JSONObject) obj;
			obj = (Object) tempJO.get(key.toLowerCase());
			if (obj != null) {				
				log.info("getJsonObject", "When searching for key '" + key + "', a match was found");
				return obj;
			} else {
				String message = "getJsonObject: When searching for key '" + key + "', a match could not be found" ;
				throw new JsonException(message);
			}
		}

		/////////////////////////////////////////////////////////////////////////
		public Object getJsonArray(Object jArray, String findKey, String matchValue) throws JsonException {
			// Purpose: return the jsonarray when it finds its first matching value
			//String objArray = null;
			String message = null;
			for(Object obj : (JSONArray) jArray)  {
		
				JSONObject jObject = (JSONObject) obj;
				Object value = jObject.get(findKey);
				if (value != null) {
					if (value.toString().equals(matchValue)) {
						log.info("getJsonArray", "match found for <" + matchValue + "> in the object >>>> " + obj.toString());						
						return obj;
					} else {
						message = "getJsonArray: No matching value found for <" + matchValue + "> in the object >>>> " + obj.toString();
						
					}	
				} else {
					message = "getJsonArray: The key '" + findKey + "' could not be found";	
				}
			}
			throw new JsonException(message);
		}
	}
	
	
//// LOGGER //////////////////////////////////////////////////////////
	public class logger {
		// Purpose: log data into a log file
		// The original methods include debug, step, warning, info, error
		// Parameters:
		//    	logType = different available types of message like debug, step, warning, info, error
		//		header = could be like a method or class name
		// 		message = information that are to be added into the log
		// 		messageEntry = concatenated string of header + " " + logType + " " + message
		// 	Date: 8/29/2017
		
		String logType = null;
		String header = null;
		String message = null;
		String messageEntry = null;
		
		public void debug(String header, String message) {
			this.logType = "DEBUG:";
			this.header = header;
			this.message = message;
			printLog();
		}
		
		public void info(String header, String message) {
			this.logType = "INFO:";
			this.header = header;
			this.message = message;
			printLog();
		}
		
		public void step(String header, String message) {
			this.logType = "STEP:";
			this.header = header;
			this.message = message;		
			printLog();
		}
		
		public void warning(String header, String message) {
			this.logType = "WARNING";
			this.header = header;
			this.message = message;		
			printLog();
		}
		
		public void error(String header, String message) {
			this.logType = "ERROR";
			this.header = header;
			this.message = message;		
			printLog();
		}
		
		
		public void printLog() {
			//System.out.println("Testing printlog");
			messageEntry = (header + " " + logType + " " + message);
			switch(logType) {
			case "INFO:":
			case "DEBUG:":
				System.out.println(messageEntry);
				break;
			default:
				System.err.println(messageEntry);
			}
			
			//System.out.println("LOG: >>> " + LOGLOCATION);
			
			// enter data into a log file
			LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
			String datestamp = currentDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmSS");
			String timestamp = currentDateTime.format(formatter) + "z ";
			Path filePath = Paths.get(LOGLOCATION + "LOG_" + datestamp + ".log");
			
			if(!filePath.toFile().exists()) {
				//System.out.println("logger >>>> creating file for >>>> " + filePath.toString());
				filePath.getParent().toFile().mkdirs();
			} 
			if(!putSingleLineIntoFile(filePath, timestamp + " " + messageEntry)) System.err.println("logger >>> Could not enter text file");
			
		}
	}
	
	
//// IO Files ///////////////////////////////////////////////////////
	public class IOfile {
		
		//// MS Excel //////////////////////////////////////////////
		@SuppressWarnings("deprecation")
		public void readSpreadSheet(String fileLocation, String sheetName) throws IOException {
			
			FileInputStream inputStream = new FileInputStream(new File(fileLocation));
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = wb.getSheetAt(0);
			
			Iterator<Row> rows = sheet.iterator();
			
			while(rows.hasNext()) {
				Row row = rows.next();
				
				Iterator<Cell> cells = row.cellIterator();
				while(cells.hasNext()) {
					Cell cell = cells.next();
						switch(cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							System.out.println(cell.getStringCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_NUMERIC:
							System.out.println(cell.getNumericCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							System.out.println(cell.getBooleanCellValue() + "\t");
							break;
						default:
						}
					}
				}
				System.out.println("");
			}

		
		
		//// text file //////////////////////////////////////////////
		public List<String> getDataFromFile(Path pathName) {
			// Purpose: Read a file and return it's findings
			// file not found should have been verified in method DoesFileExist()
			// Using Java 8 to write this: http://javarevisited.blogspot.com/2015/07/3-ways-to-read-file-line-by-line-in.html
			
			List<String> line = new ArrayList<String>();
			try {
				Files.lines(pathName)
				.map(s -> s.trim())
				.filter(s -> !s.isEmpty())
				.forEach(line::add);
			} catch (IOException e) {
				System.err.println("ERROR: getDataFromFile not file found >>>  " + pathName);
			}
			
			return line;
			
		}  //  List<String> getDataFromFile(Path pathName)
		

		//////////////////////////////////////////////////////////////////////////////////////////////////
		public Boolean putStringArrayIntoFile(Path pathName, String[] data) {
			for(String line : data) {
				if(!putSingleLineIntoFile(pathName, line)) return false;
			}
			return true;
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		public Boolean putArrayListIntoFile(Path pathName, ArrayList<String> arrCollection) { 
			for(String line : arrCollection) {
				if (!putSingleLineIntoFile(pathName, line)) return false;
			}
			return true;
		}
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Boolean putSingleLineIntoFile(Path pathName, String txtFile) {
			try (BufferedWriter writer = Files.newBufferedWriter(pathName, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
				writer.write(txtFile);
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} 
			return true;
		}
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		public Boolean DoesFileExist(Path pathName) {
			// Purpose: If file exist then return true else return false
			
			if (Files.exists(pathName)) {
				if (Files.isRegularFile(pathName) & Files.isReadable(pathName) & Files.isExecutable(pathName)) {
					System.out.println(">> found -- >>  " + pathName);
					return true;
				} 
			}
			System.err.println("ERROR: File not found >>> " + pathName);
			return false;
		}  // DoesFileExist(Path pathName)
	}
	

	

	
}	// maidCodes}




