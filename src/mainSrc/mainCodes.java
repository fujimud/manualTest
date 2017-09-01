//  UI Automation 

// Created by Dan Fujimura on 8/26/2017.

package mainSrc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
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

public class mainCodes {
		
	public final String CONFIGPATH = "src/config/";
	public final String PATH2RECORDS = CONFIGPATH + "records/";
	public final String PATHJSONRECORDS =  CONFIGPATH + "urlRecords.json";
	public final String LOGLOCATION = CONFIGPATH + "logs/";
//	public static WebDriver driver = null;

	
	logger log = new logger();
	
	
	
//// JSON /////////////////////////////////////////////////////////////	
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
	
	
	
	////////////////////////////////////////////////////////////////////////////////////
	public class jsonFile {	
		public Object getJsonObject(Object obj, String findMatch) {
			System.out.println("JSONObject retrieve the value based from the provided key");
			
			JSONObject tempJO = (JSONObject) obj;
			obj = (Object) tempJO.get(findMatch.toLowerCase());
		
			if (obj == null) {
				log.info("getJsonObject", "Object is empty");
				return "";
			}
			return obj;
		}
		
		/////////////////////////////////////////////////////////////////////////
		public Object getJsonArray(Object jArray, String findKey, String matchValue) {
			// Purpose: return the jsonarray when it finds its first matching value
			Object result = null;
		
			for(Object obj : (JSONArray) jArray)  {
		
				JSONObject jObject = (JSONObject) obj;
				Object value = jObject.get(findKey);
				if (value != null) {
					if (value.toString().equals(matchValue)) {
						log.info("getJsonArray", "match found for <" + matchValue + "> in the object >>>> " + obj.toString());
						result = obj;
						break;
					} else {
						log.warning("getJsonArray", "No matching Value found for <" + matchValue + "> in the object >>>> " + obj.toString());
						result = "";
					}	
				} else {
					log.warning("getJsonArray", "No matching Key was found <" + findKey + "> in the object >>>> " + obj.toString());
					result = "";
				}
				
			}			
			return result;
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
			System.out.println("Testing printlog");
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
			if(!putSingleLineIntoFile(filePath, timestamp + " " + messageEntry)) System.err.println("logger >>> Could not enter text file");
			
		}
	}
	
	
//// IO Files ///////////////////////////////////////////////////////
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
	

	
}	// maidCodes}




