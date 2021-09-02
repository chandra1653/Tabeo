package com.tabeo.automation.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TabeoLoginPage {
	WebDriver driver;

	public TabeoLoginPage(WebDriver driver) {
		this.driver = driver;
	}

	By signInButtonLocator = By.xpath("//button[text()='Sign in']");
	By signInWithGmailButtonLocator= By.xpath("//button[text()='Sign in with Google']");
	By emailIDInputLocator = By.xpath("//input[@type='email']");
	By signWithEmailButtonLocator = By.xpath("//button[text()='Sign in with email']");
	By verifyEmailNotificationLocator = By.xpath("//div/p[contains(text(),'A sign in link has been sent to your email address.')]");
	

	public WebElement signInButton() {

		return driver.findElement(signInButtonLocator);
	}
	public WebElement emailIDInput() {

		return driver.findElement(emailIDInputLocator);
	}
	public WebElement signWithEmailButton() {

		return driver.findElement(signWithEmailButtonLocator);
	}
	public WebElement verifyEmailNotification() {

		return driver.findElement(verifyEmailNotificationLocator);
	}
	
	public WebElement signInWithGmailButton() {

		return driver.findElement(signInWithGmailButtonLocator);
	}

}
