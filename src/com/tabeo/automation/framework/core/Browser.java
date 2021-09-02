package com.tabeo.automation.framework.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

	public class Browser {
		
		public static WebDriver driver;
		public static Properties prop;
		public Properties userDetails;
		public Calendar cal;
		public static final String FailureScreenshots=System.getProperty("user.dir") + "\\resources\\ScreenShot";
		public WebDriverWait wait;
		
		public static WebDriver initializeDriver() throws IOException {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\resources\\data.properties");
			prop.load(fis);
			String browserName = prop.getProperty("browser");
			System.out.println(browserName);
			if (browserName.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\resources\\drivers\\chromedriver.exe");
				
				driver = new ChromeDriver(getChromeOptions());
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return driver;
		}

		public static  ChromeOptions getChromeOptions(){
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("--lang=en");
			options.addArguments("chrome.switches", "--disable-extensions");
			options.addArguments("--no-sandbox");
			options.addArguments("--incognito");
			options.addArguments("--profile-directory=Default");
			options.addArguments("--js-flags=--expose-gc");
			options.addArguments("--enable-precise-memory-info");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-default-apps");
			options.addArguments("test-type=browser");
			options.addArguments("disable-infobars");
	        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
			chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			return options.merge(chromeCapabilities);
		}
		public void BrowserClosing() {
			driver.quit();
			driver.close();
		}
		
		public void getScreenShot(WebDriver driver, String filePath) throws Exception {

			TakesScreenshot screenShot = ((TakesScreenshot) driver);

			File SrcFile = screenShot.getScreenshotAs(OutputType.FILE);

			String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());

			File Sanpshotfile = new File(filePath + "Automation_" + timestamp + ".jpg");

			FileUtils.copyFile(SrcFile, Sanpshotfile);
		}


		public  void tearDownTest() {
			driver.manage().deleteAllCookies();
			driver.quit();
		}
		
		public void waitUntilPageLoaded() {
			JavascriptExecutor j = (JavascriptExecutor) driver;
			if (j.executeScript("return document.readyState").toString().equals("complete")) {
				System.out.println(driver.getTitle()+ "Page has loaded");
			}
		}
		public void loadUserPropertiesfile() throws IOException{
			userDetails= new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\resources\\data.properties");
			userDetails.load(fis);
		}
		
		public void switchToFrame(String name){
			List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
			for(int i=0;i<iframes.size();i++){
				System.out.println(iframes.get(i).getText());
				boolean matches = Pattern.matches(name, iframes.get(i).getText());
				if(matches){
					driver.switchTo().frame(name);
				}
			}
		}
		public void switchToFrame(int number){
			List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
			for(int i=0;i<iframes.size();i++){
				if(number==i)
			driver.switchTo().frame(i);
			}
		}
		
		public boolean safeClick(By locator) {
		    boolean result = false;
		    int attempts = 0;
		    while(attempts < 4) {
		        try {
		            driver.findElement(locator).click();
		            result = true;
		            break;
		        } catch(StaleElementReferenceException e) {
		        }
		        attempts++;
		    }
		    return result;
		}
}
