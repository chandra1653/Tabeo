package com.tabeo.automation.Tests;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.tabeo.automation.framework.core.Browser;
import com.tabeo.automation.framework.core.GmailLoginPage;
import com.tabeo.automation.framework.core.TabeoLoginPage;

public class LoginTestWithGmailOath extends Browser {
	static Logger logger = LogManager.getLogger(LoginTestWithGmailOath.class);
	final static String qaBanner = "Quality Assurance";
	@Test
	public void LoginTestWithGmailAuthentication() throws IOException, InterruptedException{
		
		loginWithOAuth();
	}
	
	@AfterTest
	public void tearDown(){
		tearDownTest();
	}
	
	public void loginWithOAuth() throws IOException, InterruptedException{
		BasicConfigurator.configure();
		loadUserPropertiesfile();
		try {
			driver = initializeDriver();
		} catch (IOException e) {
			System.out.println("WebDriver is not intialized");
		}

		driver.get(userDetails.getProperty("url"));
		TabeoLoginPage loginPage = new TabeoLoginPage(driver);
		// click on the signwith gmail button
		loginPage.signInButton().click();
		loginPage.signInWithGmailButton().click();
		waitUntilPageLoaded();
		wait= new WebDriverWait(driver, 20);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(WebDriverException.class);
		wait.ignoring(NoSuchElementException.class);
		wait.pollingEvery(Duration.ofSeconds(1));
		By languageDropDownLocator = By.xpath("//div[contains(@jsname,'wQNmvb') and contains(@aria-selected,'true')]");
		wait.until(ExpectedConditions.elementToBeClickable(languageDropDownLocator));
		driver.findElement(languageDropDownLocator).click();
		By englishLanguageLocator = By.xpath(
				"//div[contains(@jsname,'wQNmvb') and contains(@role,'option')]//span[contains(text(),'‪English (United Kingdom)‬')]");
		wait.until(ExpectedConditions.elementToBeClickable(englishLanguageLocator));
		driver.findElement(englishLanguageLocator).click();
		waitUntilPageLoaded();
		GmailLoginPage gmailLoginPage = new GmailLoginPage(driver);
		Actions webDriverActions = new Actions(driver);
		logger.info(safeClick(gmailLoginPage.gmailEmailORPhoneNumberInputLocator));
		webDriverActions.click(gmailLoginPage.emailORPhoneNumberInput()).doubleClick().build().perform();
		gmailLoginPage.emailORPhoneNumberInput().sendKeys(userDetails.getProperty("username"));
		gmailLoginPage.nextButton().click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		gmailLoginPage.passwordInput().sendKeys(userDetails.getProperty("password"));
		wait.until(ExpectedConditions.elementToBeClickable(gmailLoginPage.nextButton()));
		safeClick(gmailLoginPage.nextButtonLocator);
		By qualityAssuranceBannerLocator = By.xpath("//span[contains(text(),'Quality Assurance')]");
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(qualityAssuranceBannerLocator));
			if ("Quality Assurance".equals(driver.findElement(qualityAssuranceBannerLocator).getText())) {

				logger.info("User is able to login to the application successfully");
			}
			}
			catch(Exception e){
				logger.info("Unable to login to the application");
				Assert.fail();
			}
		
	}


	
	
}
