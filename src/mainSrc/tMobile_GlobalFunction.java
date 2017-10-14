package mainSrc;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class tMobile_GlobalFunction extends mainCodes {
	mainCodes mc = new mainCodes();
	jsonFile jf = new mainCodes.jsonFile();
	public WebDriver driver = null;
	
	
	//// route ////////////////////////////////////////////////////////////////////
	public void route(String type_of_browser, String testCaseName) throws InterruptedException {
		connect2webdriver(type_of_browser);		
		//driver.get("https://www.t-mobile.com");
		WebDriverWait wait = new WebDriverWait(driver, 100);
	
		driver.get("http://demoqa.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("get2ProductDetailpage\":{\"click_navbar\":\"My T-MOBILE")));
		
		
		driver.findElement(By.xpath(".//*[@id='menu-item-374']/a")).click();
		driver.wait(100);
		driver.navigate().back();
		driver.wait(100);
		driver.navigate().forward();
		driver.wait(100);
		driver.navigate().to("http://demoqa.com");
		driver.navigate().refresh();
		
		
		
		
		
		//System.out.println("Page title: " + driver.getTitle());
		//System.out.println("Page size: " + driver.getTitle().length());
		//String URL = driver.getCurrentUrl();
		//if (URL == "https://www.t-mobile.com/") {
		//	System.out.println("url matches");
		//} else {
		//	System.err.println("there's a problem with the URL, it shows >> " + URL);
		//}
		//String pagesource = driver.getPageSource();
		//System.out.println("Page source size: " + pagesource.length());
		//driver.close();
		
		//Object fileName;
		//Object objData;
		//try {
			//int number_of_pages_being_tested  = 0;
			//fileName = new JSONParser().parse(new FileReader("testCases/testCases.json"));
			//JSONObject joTemp = (JSONObject) fileName;
			//Object getDirections = (Object) joTemp.get("go2Plans");
			//JSONArray jaTemp = (JSONArray) getDirections;
		/*	
			String pageTitle = driver.getTitle();
			System.out.println("TITLE >> " + pageTitle);
				
			switch (pageTitle) {
				case "Cell Phones | 4G Phones | iPhone and Android Phones | T-Mobile":

					//driver.findElement(By.linkText(("My T-Mobile"))).click();
					driver.
					break;
				case"My T-Mobile Online | Access Messages, Minutes & Bills | T-Mobile":
					// My T-Mobile
					System.out.println("bad news");
					break;
				default:	
			}
			*/
		//} catch (IOException | ParseException  e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		

	
		
			
	}
	
	
	//// webdriver /////////////////////////////////////////////////////////////////
	public void connect2webdriver(String browsertype) {

		switch (browsertype.toLowerCase().trim()) {
		case "chrome":
			
			ChromeOptions co = new ChromeOptions();
			co.addArguments("--start-maximized");
			System.setProperty("webdriver.chrome.driver", "\\webdriver\\chromedriver.exe");				
			this.driver = new ChromeDriver(co);
			this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			break;
		case "internetexplorer":
		case "ie":
			this.driver = new InternetExplorerDriver();
			this.driver.manage().window().maximize();
			System.setProperty("webdriver.ie.driver", "\\webdriver\\IEDriverServer.exe");
			break;
		// case "firefox":
		// case "ff":
			
		// FirefoxDriver driver = new FirefoxDriver();
		//	driver.manage().window().maximize();
		// break;
		default:
			log.error("connect2webdriver", "Browser '" + browsertype + "' is currently not supported");
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	public void homeActionPlan(String testCaseName) {
		try {
			Object fileName = new JSONParser().parse(new FileReader("testCases/testcase_data.json"));
			Object getDirections = jFile.getJsonObject(fileName, testCaseName);

		} catch (mainCodes.jsonFile.JsonException | IOException | ParseException e) {
			e.printStackTrace();
		}

	}
}
