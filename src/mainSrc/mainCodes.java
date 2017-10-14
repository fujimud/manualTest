//  UI Automation 

// Created by Dan Fujimura on 8/26/2017.

package mainSrc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import mainSrc.mainCodes.jsonFile.JsonException;

public class mainCodes {

	public final String CONFIGPATH = "src/config/";
	public final String PATH2RECORDS = CONFIGPATH + "records/";
	public final String PATHJSONRECORDS = CONFIGPATH + "urlRecords.json";
	public final String LOGLOCATION = CONFIGPATH + "logs/";
	public final String MAP = CONFIGPATH + "NavigationMap.json";

	public final Boolean DISPLAYLOGS2CONSOLE = false;

	logger log = new logger();
	IOfile ioFile = new IOfile();
	jsonFile jFile = new jsonFile();

	//// Navigate through T-Mobile website

	///////////////////////////////////////////////////////////////////////////////////
	public void router(String distination) {
		// Purpose: navigation flow

		try {
			Object fileName = new JSONParser().parse(new FileReader(MAP));
			Object getDirections = jFile.getJsonObject(fileName, distination.toLowerCase());
			List<String> navigation = new ArrayList<String>(Arrays.asList(getDirections.toString().split("/")));
			for (String point : navigation) {
				System.out.println(">>  " + point);
				switch (point) {
				case "home":
					System.out.println("home page");
					break;
				case "detail":
					System.out.println("Product detail page");
					break;
				case "phone":
					System.out.println("Product page");
					break;
				case "product":
					System.out.println("Test destination");
					break;
				default:
					System.out.println("Not good");
				}
			}
		} catch (JsonException | IOException | ParseException e) {
			e.printStackTrace();
			log.error("router", message);
		}

	}

	//// Selenium //////////////////////////////////////////////////////////////////

	public void openWebPage(String browser, String webpageName)
			throws FileNotFoundException, IOException, ParseException {
		jsonFile json = new jsonFile();

		Object webpage = new JSONParser().parse(new FileReader(CONFIGPATH + "TC_navigation.json"));
		System.out.println("webpage completed >>>>> " + webpage);
		System.out.println("FIND >>>> " + webpageName);

		Object pageName;
		try {
			pageName = json.getJsonObject(webpage, webpageName);

			System.out.println("pageName completed  >>>> " + pageName.toString());

			String name = pageName.toString();
			// System.out.println("name completed");

			Object fileName = new JSONParser().parse(new FileReader(PATHJSONRECORDS));
			// System.out.println("fileName completed");

			Object data = json.getJsonObject(fileName, "landing");
			// System.out.println("data completed");

			data = json.getJsonArray(data, "application", name);
			// System.out.println("RESULT >>> " + data.toString());
		} catch (JsonException e) {

			e.printStackTrace();
		}
	}

	//// JSON /////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////
	public class jsonFile {

		////////////////////////////////////////////////////////////////////////////////////
		public String returnDataFromJsonFile(String fileLocation, String JsonPath, String findMatch)
				throws JsonException {
			// Purpose: Retrieve data from a json file. It recognize and deals with both
			// Json Objects and Json Arrays.
			// If a value then the result is returned as a string
			// Parameters:
			// fileLocation is the path and json file location
			// JsonPath: a listing of keys that leads to where the value needed is located.
			// semi-colon is the delimiter for the path needed. While > is used to identify
			// the key that stores the value
			// example: "landings;pagename>link"
			// findMatch: Key name used to return it's value

			// Verify that the parameters all contain data or an exception will be thrown
			String errorMsg = null;

			if (JsonPath.trim().isEmpty() || findMatch.trim().isEmpty() || (fileLocation.trim().isEmpty())) {
				throw new JsonException("getDataFromJsonFile: Each parameter requires data, please provide the data");
			}

			String result = null;
			jsonFile json = new jsonFile();
			Object ListingOfPossibleWebpageNames;
			Object jsonFile;
			String[] keys;
			String[] path;

			try {
				// Return a standardized name for one of the recognized companies
				ListingOfPossibleWebpageNames = new JSONParser()
						.parse(new FileReader(CONFIGPATH + "nameVariations.json"));
				findMatch = (String) json.getJsonObject(ListingOfPossibleWebpageNames, findMatch);
			} catch (IOException | ParseException e) {
				errorMsg = "There is an error with the json file at the following location >> " + CONFIGPATH
						+ "nameVariations.json";
				throw new JsonException(errorMsg);
			}

			try {
				jsonFile = new JSONParser().parse(new FileReader(fileLocation));
			} catch (IOException | ParseException e) {
				errorMsg = "There is an error with the json file at the following location >> " + fileLocation;
				throw new JsonException(errorMsg);
			}

			// Verify the key used to get the value is identified after the '>'"
			keys = JsonPath.split(">");
			if (keys.length <= 1) {
				throw new JsonException(
						"getDataFromJsonFile: Key used to identify which value to retrieve is missing. The key is usually preceeded by '>' ");
			}
			path = keys[0].split(";");

			// get the jsonObject and jsonArray
			for (String pointer : path) {
				if (jsonFile instanceof JSONObject) {
					jsonFile = json.getJsonObject(jsonFile, pointer); // This will return a Json object
				} else {
					jsonFile = json.getJsonArray(jsonFile, pointer, findMatch); // this will return a Json array
				}
			}

			// Verify that the return value is not a JSON array
			String key = keys[1];
			if ((jsonFile instanceof JSONArray)) {
				throw new JsonException(
						"getDataFromJsonFile: This is a json array and it failed to get the value for '" + key + "'");
			} else {
				result = (String) json.getJsonObject(jsonFile, key);
			}

			return result;
		}

		////// Test code delete
		////// later/////////////////////////////////////////////////////////////////////////////
		public void throwJsonException(String pathName) throws JsonException {

			Object jsonFile = null;
			try {
				jsonFile = new JSONParser().parse(new FileReader(pathName));
			} catch (FileNotFoundException e1) {
				e1.getStackTrace();
				StringWriter sw = new StringWriter();
				e1.printStackTrace(new PrintWriter(sw));
				String message = "No file could be found at the provided location '" + pathName + "' \n"
						+ sw.toString();
				throw new JsonException(message);
			} catch (IOException e2) {
				e2.getStackTrace();
				StringWriter sw = new StringWriter();
				e2.printStackTrace(new PrintWriter(sw));
				String message = "There some error when trying to read the json file found in '" + pathName + "' \n"
						+ sw.toString();
				throw new JsonException(message);
			} catch (ParseException e3) {
				e3.getStackTrace();
				StringWriter sw = new StringWriter();
				e3.printStackTrace(new PrintWriter(sw));
				String message = "There a problem with the json file when trying to parse it's data in the following file '"
						+ pathName + "' \n" + sw.toString();
				throw new JsonException(message);
			}
		}

		///////////////////////////////////////////////////////////////////////
		// my exception class
		@SuppressWarnings("serial")
		public class JsonException extends Exception {
			String msg;

			JsonException(String msg) {
				this.msg = msg;
			}

			public String toString() {
				return ("JsonException> " + msg);
			}

		}

		////////////////////////////////////////////////////////////////////////
		public Object getJsonObject(Object obj, String key) throws JsonException {
			// System.out.println("JSONObject retrieve the value based from the provided
			// key");

			JSONObject tempJO = (JSONObject) obj;
			obj = (Object) tempJO.get(key.toLowerCase());

			/*
			 * if (obj != null) { log.info("getJsonObject", "When searching for key '" + key
			 * + "', a match was found"); return obj; } else { String message =
			 * "getJsonObject: When searching for key '" + key +
			 * "', a match could not be found"; throw new JsonException(message); }
			 */

			// try {
			if (obj != null) {
				log.info("getJsonObject", "When searching for key '" + key + "', a match was found");
				return obj;
			} else {
				throw new JsonException(
						"getJsonObject: When searching for key '" + key + "', a match could not be found");
			}
			// }/ catch (Exception e) {
			// throw new JsonException(e, "");
			// }
		}

		/////////////////////////////////////////////////////////////////////////
		public Object getJsonArray(Object jArray, String findKey, String matchValue) throws JsonException {
			// Purpose: return the jsonarray when it finds its first matching value
			// String objArray = null;
			String message = null;
			for (Object obj : (JSONArray) jArray) {
				JSONObject jObject = (JSONObject) obj;
				Object value = jObject.get(findKey);
				if (value != null) {
					if (value.toString().equals(matchValue)) {
						log.info("getJsonArray",
								"match found for <" + matchValue + "> in the object >>>> " + obj.toString());
						return obj;
					} else {
						message = "getJsonArray: No matching value found for <" + matchValue + "> in the object >>>> "
								+ obj.toString();
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
		// logType = different available types of message like debug, step, warning,
		// info, error
		// header = could be like a method or class name
		// message = information that are to be added into the log
		// messageEntry = concatenated string of header + " " + logType + " " + message
		// Date: 8/29/2017

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
			// System.out.println("Testing printlog");
			messageEntry = (header + " " + logType + " " + message);
			switch (logType) {
			case "INFO:":
			case "DEBUG:":
				if (DISPLAYLOGS2CONSOLE)
					System.out.println(messageEntry);
				break;
			default:
				if (DISPLAYLOGS2CONSOLE)
					System.err.println(messageEntry);
			}

			// System.out.println("LOG: >>> " + LOGLOCATION);

			// enter data into a log file
			LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
			String datestamp = currentDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmSS");
			String timestamp = currentDateTime.format(formatter) + "z ";
			Path filePath = Paths.get(LOGLOCATION + "LOG_" + datestamp + ".log");

			if (!filePath.toFile().exists()) {
				// System.out.println("logger >>>> creating file for >>>> " +
				// filePath.toString());
				filePath.getParent().toFile().mkdirs();
			}
			ioFile.putSingleLineIntoFile(filePath, timestamp + " " + messageEntry);

		} // public void printLog()
	}

	//// IO Files ///////////////////////////////////////////////////////
	public class IOfile {

		//// MS Excel //////////////////////////////////////////////
		public void readFromTC_SpreadSheet(String fileLocation) throws IOException {
			FileInputStream inputStream = new FileInputStream(new File(fileLocation));
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);
			HashMap<String, String> tcMap = new HashMap<String, String>();
			TreeMap<String, String> testSuite_Map = new TreeMap<>(tcMap);
			JSONObject jsonObj = new JSONObject();
			
			
			// System.out.println(wb.getSheetName(1));

			ArrayList<String> listOfLabels = new ArrayList<String>();
			listOfLabels.add("Test case name");
			listOfLabels.add("Test case description");
			listOfLabels.add("Created By");
			listOfLabels.add("Summary");
			listOfLabels.add("Test priority");
			listOfLabels.add("Business requirement");
			listOfLabels.add("Precondition");
			listOfLabels.add("Created date");
			listOfLabels.add("Originatpr");
			listOfLabels.add("Automation");
			listOfLabels.add("overall result");

			ArrayList<String> labels_for_test_steps = new ArrayList<String>();
			labels_for_test_steps.add("Step");
			labels_for_test_steps.add("Expected result");
			labels_for_test_steps.add("Actual result");
			labels_for_test_steps.add("Status");
			labels_for_test_steps.add("Test step");
			labels_for_test_steps.add("test result (pass. fail, blocked)");
			labels_for_test_steps.add("Note");

			ArrayList<String> exclude_The_Following_SheetNames = new ArrayList<String>();
			exclude_The_Following_SheetNames.add("template");
			exclude_The_Following_SheetNames.add("parameters");
			//exclude_The_Following_SheetNames.add("testcasescenario");
			exclude_The_Following_SheetNames.add("presentation");

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {

				String sheetName = wb.getSheetName(i).trim();
				XSSFSheet sheet = wb.getSheetAt(i);
				int number_of_rows_with_blank = 0;
				Boolean skip_Writing_This_SpreadSheet = false;

				// Exclude the following sheet names
				Iterator<String> skip_SheetNames = exclude_The_Following_SheetNames.iterator();
				while (skip_SheetNames.hasNext()) {
					if (sheetName.toLowerCase().trim().equals(skip_SheetNames.next().toString()))
						skip_Writing_This_SpreadSheet = true;
				}
				
				
				if (skip_Writing_This_SpreadSheet == false) {
					// skip_Writing_This_SpreadSheet = false;

					System.out.println("////////////////////////////");
					System.out.println(sheetName);

					testSuite_Map.put(sheetName, "");
					Iterator<Row> rows = sheet.iterator();
					while (rows.hasNext()) {
						if (number_of_rows_with_blank >= 20) {
							System.out.println("to many blanks");
							break;
						} else {
							number_of_rows_with_blank++;

						}

						Row row = rows.next();
						Iterator<Cell> cells = row.cellIterator();
						String key = "";
						String value = "";
						while (cells.hasNext()) {
							Cell cell = cells.next();
							Boolean this_Is_A_TestCase_Step = false;
							
							switch (cell.getCellType()) {

							case Cell.CELL_TYPE_STRING:

								// Identify Key and values from the spreadsheet
								String cellValue = cell.getStringCellValue().trim();
								String cellValue_AsLowerCase = cellValue.toLowerCase();
								Iterator<String> labels = listOfLabels.iterator();
								Iterator<String> tc_steps = labels_for_test_steps.iterator();
								Boolean tc_label_found_a_match = false;

								// determine if this is the start of the test steps
								while (tc_steps.hasNext()) {
									if (cellValue_AsLowerCase.startsWith(tc_steps.next().toLowerCase())) {
										System.out.println("This is a test steps > " + cellValue);
										this_Is_A_TestCase_Step = true;
										HashMap<String, String> stepsMap = new HashMap<String, String>();
										
										
										break;
									}
								} // while (tc_steps.hasNext()) {

								// System.out.println(cellValue + "\t");
								// can a matching label be found from the array
								while (labels.hasNext()) {
									String temp_label = labels.next().toLowerCase();
									int label_size = temp_label.length() + 2;

									// determine if a cellValue contain a valid label
									if ((cellValue_AsLowerCase.startsWith(temp_label)) && ((label_size) >= cellValue_AsLowerCase.length())) {
										this_Is_A_TestCase_Step = true;
										break;
									}
									temp_label = "";
								}

								// set the cell value as a value
								if (this_Is_A_TestCase_Step == false) {
									if (tc_label_found_a_match == false) {
										value = cellValue;
										System.out.println("Value > " + value);
										tcMap.put(key, value);
										tcMap.put("cellPosition", cell.getColumnIndex());
										
										//tcMap.put("value", cellValue);
										//tcMap.put("cellPosition", String.valueOf(cell.getColumnIndex()));
										//testSuite_Map(key, tcMap);
										//testSuite_Map(sheetName, tcMap);
									} // if (tc_label_found_a_match.FALSE) {

								} else {
									// set the value as a key
									key = cellValue;
									System.out.println("Key > " + key);
									// tcMap.put(key, "");

								}

								break;
							case Cell.CELL_TYPE_NUMERIC:
								SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
								value = sdf.format(cell.getDateCellValue());
								System.out.println("NUMERIC > " + value);
								tcMap.put(key, value);

								// value = cell.getNumericCellValue(); // value needs to be a double
								break;
							case Cell.CELL_TYPE_BLANK:
								// System.out.println("BLANK " + number_of_rows_with_blank);

								break;
							case Cell.CELL_TYPE_FORMULA:
								// System.out.println("FORMULA > " + cell.getCellFormula() + "\t");
								System.out.println("Formual > " + cell.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								System.out.println(cell.getBooleanCellValue() + "\t");
								break;
							case Cell.CELL_TYPE_ERROR:
								System.out.println(cell.getErrorCellValue() + "\t");
								break;
							} // switch (cell.getCellType())
						} // while (cells.hasNext())
						testSuite_Map.putAll(tcMap);
						jsonObj.put(sheetName, tcMap);
						
					} // while (rows.hasNext())
					System.out.println("\n\n");

					Set<Entry<String, String>> set = testSuite_Map.entrySet();
					// Set<Entry<String, String>> set = tcMap.entrySet();
					Iterator<Entry<String, String>> iterator = set.iterator();
					System.out.println("JSONObject >> " + jsonObj);
					while (iterator.hasNext()) {
						Map.Entry mentry = (Map.Entry) iterator.next();
						System.out.println("key is : " + mentry.getKey() + " & value is : ");
						System.out.println(mentry.getValue());
					} // while(iterator.hasNext())
				} // if (skip_Writing_This_SpreadSheet == true)
			} // for (int i = 0; i < wb.getNumberOfSheets(); i++)
		} // public void readFromTC_SpreadSheet(String fileLocation) throws IOException

		@SuppressWarnings("deprecation")
		public void readSpreadSheet(String fileLocation, String sheetName) throws IOException {

			FileInputStream inputStream = new FileInputStream(new File(fileLocation));
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = wb.getSheetAt(0);

			ArrayList<String> listOfLabels = new ArrayList<String>();
			listOfLabels.add("test case name");
			listOfLabels.add("description");
			listOfLabels.add("summary");
			listOfLabels.add("business");
			listOfLabels.add("precondition");
			listOfLabels.add("date");
			listOfLabels.add("origin");
			listOfLabels.add("step");
			listOfLabels.add("expected result");
			listOfLabels.add("actual result");
			listOfLabels.add("note");

			Iterator<Row> rows = sheet.iterator();
			int cnt = 1;

			while (rows.hasNext()) {
				Row row = rows.next();

				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						System.out.println(cell.getStringCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.println(cell.getNumericCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.println(cell.getBooleanCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_FORMULA:
						System.out.println(cell.getCachedFormulaResultType() + "\t");
						break;
					case Cell.CELL_TYPE_BLANK:
						System.out.println("This is a blank " + cnt++);
						number_of_rows_with_blank++;
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
			// Using Java 8 to write this:
			// http://javarevisited.blogspot.com/2015/07/3-ways-to-read-file-line-by-line-in.html

			List<String> line = new ArrayList<String>();
			try {
				Files.lines(pathName).map(s -> s.trim()).filter(s -> !s.isEmpty()).forEach(line::add);
			} catch (IOException e) {
				System.err.println("ERROR: getDataFromFile not file found >>>  " + pathName);
			}

			return line;

		} // List<String> getDataFromFile(Path pathName)

		//////////////////////////////////////////////////////////////////////////////////////////////////
		public Boolean putStringArrayIntoFile(Path pathName, String[] data) {
			for (String line : data) {
				//if (!putSingleLineIntoFile(pathName, line))
					//return false;
				putSingleLineIntoFile(pathName, line)
			}
			return true;
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////
		public Boolean putArrayListIntoFile(Path pathName, ArrayList<String> arrCollection) {
			for (String line : arrCollection) {
				// if (!putSingleLineIntoFile(pathName, line))
				// return false;
				putSingleLineIntoFile(pathName, line);
			}
			return true;
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////

		public void putSingleLineIntoFile(Path pathName, String txtFile) {
			try (BufferedWriter writer = Files.newBufferedWriter(pathName, Charset.forName("UTF-8"),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
				writer.write(txtFile);
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		} // DoesFileExist(Path pathName)
	}

} // maidCodes}
