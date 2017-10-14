package mainSrc;

import java.io.FileReader;

import org.json.simple.parser.JSONParser;

import mainSrc.mainCodes.jsonFile;
import mainSrc.mainCodes.jsonFile.JsonException;

	
	
public class practiceJavaHere {

	
	public enum Websites {
		EBAY, FACEBOOK, GOOGLE, ATT
	}
	
	Websites wName;
	

	
	
	public practiceJavaHere(Websites wName) {
		this.wName = wName;
	}
	
	public void getKeyFromJson() {
		Object fileName = new JSONParser().parse(new FileReader("testCases/testcase_data.json"));
		Object getDirections = jFile.getJsonObject(fileName, "get2ProductDetailpage");
		for (String key: getDirections) {
			System.out.println("key >> " + key);
		}
	}	
	
	
	public void companyDetails() {
		switch(wName) {
			case EBAY:
				System.out.println("This is Ebay");
				break;
			case FACEBOOK:
				System.out.println("This is Facebook");
				break;
			default:
					System.err.println("No match found");
		}
	}
	
	public void throwJsonException() {
		int a = 1;
		try {
			if (a == 0) {
				System.out.println("wrong place");
			} else {
				throw new testException("Help");
			}
		} catch (testException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	public class testException extends Exception {
		String msg;
		
		testException(String msg) {
			this.msg = msg;
		}
		
		public String toString() {
			return ("test exception: " + msg);
		}
	}
}
