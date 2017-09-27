//  UI Automation 

// Created by Dan Fujimura on 8/26/2017.

package mainSrc;

import java.io.BufferedWriter;
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
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class mainCodes {
		
	public final String CONFIGPATH = "src/config/";
	public final String PATH2RECORDS = CONFIGPATH + "records/";
	public final String PATHJSONRECORDS =  CONFIGPATH + "urlRecords.json";
	public final String LOGLOCATION = CONFIGPATH + "logs/";
	public static WebDriver driver = null;

	
	logger log = new logger();
	IOfile ioFile = new IOfile();
	
	
	
	//// webdriver /////////////////////////////////////////////////////////////////
	public void connect2webdriver() {
		System.setProperty("webdriver.chrome.driver", "/Users/" + username + "/eclipse/jee-oxygen/eclipse/dropins/chromedriver.exe");		
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com/");
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
		public String getDataFromJsonFile(String pathName, String navigationByKeys, String findMatch) throws FileNotFoundException, IOException, ParseException {
			// Purpose: Retrieve data from a json file. It recognize and deals with both Json Objects and Json Arrays.
			// 	If a value then the result is returned as a string
			// 	Parameters: 
			//		pathName is the path and json file location
			//		navigationByKeys: a listing of keys that leads to where the value needed is located. semi-colon is the delimiter for the path needed. While > is used to identify the key that stores the value 
			//			example: "landings;pagename>link"
			
			// Verifies that all required data are provided with some data
			if (navigationByKeys.trim().isEmpty() || findMatch.trim().isEmpty()) {
				log.error("getDataFromJsonFile", "Json file is missing some information, unable to complete this task");
				return "420";
			}
			
			String result = null;
			
			jsonFile json = new jsonFile();
			
			Object ListingOfPossibleWebpageNames = new JSONParser().parse(new FileReader("src/config/nameVariations.json"));
			Object jsonFile = new JSONParser().parse(new FileReader(pathName));

			findMatch = (String) json.getJsonObject(ListingOfPossibleWebpageNames, findMatch);
			
			String[] keys = navigationByKeys.split(">");
			String[] path = keys[0].split(";");
			
			// Verify the key used to get the value is identified after the '>'"
			if (keys.length <= 1) {
				log.error("getDataFromJsonFile", "Key used to identify which value to retrieve is missing");
				return "420";
			}
			String key = keys[1];
			
			
			// get the jsonObject and jsonArray
			for(String pointer : path) {
				if (jsonFile instanceof JSONObject) {
					//System.out.println("This is an JSON object");
					jsonFile = json.getJsonObject(jsonFile, pointer);
					//System.out.println("OBJECT >>>  " + jsonFile);				
				} else {
					//System.out.println("This is a JSON Array");
					jsonFile = json.getJsonArray(jsonFile, pointer, findMatch);
					//System.out.println("ARRAY >>>>  " + jsonFile);
				}
			}
			
			// Verify that the return value is not a JSON array
			if ((jsonFile instanceof JSONArray)) {
				log.error("getDataFromJsonFile", "This is a json array and it failed to get the value for '" + key + "'");
				//return "420";
				result = "420";
			} else if(String.valueOf(jsonFile).equals("420")) {
				log.error("getDataFromJsonFile", "Error 420");
				//return "420";
				result = "420";
			} else {
				result = (String) json.getJsonObject(jsonFile, key);
			}
			
			//return (String) json.getJsonObject(jsonFile, key);
			return result;
		}
		
		////////////////////////////////////////////////////////////////////////
		public Object getJsonObject(Object obj, String key) {
			//System.out.println("JSONObject retrieve the value based from the provided key");
			
			
			//System.out.println("Object >>  " + obj.getClass());
			JSONObject tempJO = (JSONObject) obj;
			if ((tempJO.containsKey(key.toLowerCase()) || (!tempJO.isEmpty()))) {
				obj = (Object) tempJO.get(key.toLowerCase());
			} else {
				log.info("getJsonObject", key + " could not find a match");
				return (Integer) 420;
			}

			return obj;
		}
		
		/////////////////////////////////////////////////////////////////////////
		public Object getJsonArray(Object jArray, String findKey, String matchValue) {
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
						//log.warning("getJsonArray", "No matching Value found for <" + matchValue + "> in the object >>>> " + obj.toString();
						message = "No matching value found for <" + matchValue + "> in the object >>>> " + obj.toString();
					}	
				} else {
					message = "The key " + findKey + " could not be found";					
					//log.warning("getJsonArray", "key is missing");
				}
				
			}			
			
			log.warning("getJsonArray", message);
			return (Integer) 420;		
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
				System.out.println("logger >>>> creating file for >>>> " + filePath.toString());
				filePath.getParent().toFile().mkdirs();
			} 
			if(!ioFile.putSingleLineIntoFile(filePath, timestamp + " " + messageEntry)) System.err.println("logger >>> Could not enter text file");
			
		}
	}
	
	
//// IO Files ///////////////////////////////////////////////////////
	public class IOfile {
		
		//// MS Excel //////////////////////////////////////////////
		
		
		
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




