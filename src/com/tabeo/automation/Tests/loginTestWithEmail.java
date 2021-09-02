package com.tabeo.automation.Tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.tabeo.automation.framework.core.Browser;
import com.tabeo.automation.framework.core.GmailLoginPage;
import com.tabeo.automation.framework.core.TabeoLoginPage;

public class loginTestWithEmail extends Browser {

	@Test
	public void loginTestWithEmailAddress() throws IOException {

		Logger logger = LogManager.getLogger(loginTestWithEmail.class);
		BasicConfigurator.configure();
		Properties userDetails = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\resources\\data.properties");
		userDetails.load(fis);
		try {
			driver = initializeDriver();
		} catch (IOException e) {
			System.out.println("WebDriver is not intialized");
		}
		driver.get(userDetails.getProperty("url"));
		TabeoLoginPage loginPage = new TabeoLoginPage(driver);
		// click on the sign in button
		loginPage.signInButton().click();
		loginPage.emailIDInput().sendKeys(userDetails.getProperty("username"));
		loginPage.signWithEmailButton().click();
		logger.info("Verifying the Email Notification sent Message");

		String emailNotificationMessage = loginPage.verifyEmailNotification().getText();

		if (emailNotificationMessage.equals("A sign in link has been sent to your email address.")) {

			logger.info("An email to sign in to the application is sent to the email address");
		}
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(loginPage.verifyEmailNotification()));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(userDetails.getProperty("gmailurl"));
		By languageDropDownLocator = By.xpath("//div[contains(@jsname,'wQNmvb') and contains(@aria-selected,'true')]");
		driver.findElement(languageDropDownLocator).click();
		By englishLanguageLocator = By.xpath(
				"//div[contains(@jsname,'wQNmvb') and contains(@role,'option')]//span[contains(text(),'‪English (United Kingdom)‬')]");
		driver.findElement(englishLanguageLocator).click();
		waitUntilPageLoaded();
		GmailLoginPage gmailLoginPage = new GmailLoginPage(driver);
		JavascriptExecutor j = (JavascriptExecutor) driver;
		if (j.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page has loaded");
		}
		wait.until(ExpectedConditions.elementToBeClickable(gmailLoginPage.emailORPhoneNumberInput()));
		Actions webDriverActions = new Actions(driver);
		webDriverActions.moveToElement(gmailLoginPage.emailORPhoneNumberInput()).build().perform();
		gmailLoginPage.emailORPhoneNumberInput().sendKeys(userDetails.getProperty("username"));
		gmailLoginPage.nextButton().click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		gmailLoginPage.passwordInput().sendKeys(userDetails.getProperty("password"));
		wait.until(ExpectedConditions.elementToBeClickable(gmailLoginPage.nextButton()));
		gmailLoginPage.nextButton().click();
		By qualityAssuranceBannerLocator = By.xpath("//span[contains(text(),'Quality Assurance')]");
		try {
			if ("Quality Assurance".equals(driver.findElement(qualityAssuranceBannerLocator).getText())) {

				logger.info("User is able to login to the application successfully");
			}
		} catch (Exception e) {
			logger.info("Unable to login to the application");
		}
		driver.quit();
	}
}
