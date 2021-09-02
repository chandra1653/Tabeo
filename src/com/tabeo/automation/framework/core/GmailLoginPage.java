package com.tabeo.automation.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GmailLoginPage {
	
	WebDriver driver;

	public GmailLoginPage(WebDriver driver) {
		this.driver = driver;
	}
	public By gmailSignInButtonLocator = By.xpath("//a[contains(@class,'button') and contains(text(),'Sign in')]");
	public By gmailEmailORPhoneNumberInputLocator = By.xpath("//input[contains(@type,'email') and contains(@jsname,'YPqjbf')]");
	public By nextButtonLocator= By.xpath("//div//span[contains(@jsname,'V67aGc') and contains(text(),'Next')]");
	public By gmailPasswordInputLocator =By.xpath("//input[contains(@type,'password') and contains(@jsname,'YPqjbf')]");
	
	
	public WebElement gmailSignInButton() {

		return driver.findElement(gmailSignInButtonLocator);
	}
	public WebElement emailORPhoneNumberInput() {

		return driver.findElement(gmailEmailORPhoneNumberInputLocator);
	}
	
	public WebElement nextButton() {

		return driver.findElement(nextButtonLocator);
	}
	
	public WebElement passwordInput() {

		return driver.findElement(gmailPasswordInputLocator);
	}

}
