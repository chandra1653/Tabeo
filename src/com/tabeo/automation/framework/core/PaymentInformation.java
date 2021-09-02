package com.tabeo.automation.framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentInformation {
	WebDriver driver;

	public PaymentInformation(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[contains(text(),'Pay £20/mo')]")
	WebElement paymentButtonInstallment;

	@FindBy(xpath = "//button[contains(text(),'Pay £220')]")
	WebElement paymentButtonComplete;

	@FindBy(xpath = "//input[@id='cardNumber']")
	WebElement cardNumber;

	@FindBy(xpath = "//input[@id='cardExpiry']")
	WebElement cardExpiryDate;
	
	@FindBy(xpath = "//input[@id='cardCvc']")
	WebElement cardCvc;
	
	@FindBy(xpath = "//input[@id='billingName']")
	WebElement billingName;
	
	@FindBy(xpath = "//div[@class='SubmitButton-IconContainer']")
	WebElement subscribeButton;
	
	@FindBy(xpath = "//div//p[contains(text(),'Your purchase is ready to be downloaded.')]")
	WebElement paymentSuccessfulMessage;
	
	@FindBy(xpath = "//button[contains(@type,'submit') and contains(text(),'Complete authentication')]")
	WebElement completeAuthentication;
	
	@FindBy(xpath = "//div//span[contains(text(),'Your card has been declined.')]")
	WebElement cardDeclinedMessage;
	
	
	public WebElement paymentButtonInstallment() {

		return paymentButtonInstallment;
	}

	public WebElement paymentButtonComplete() {

		return paymentButtonComplete;
	}

	public WebElement cardNumber() {

		return cardNumber;
	}

	public WebElement cardExpiryDate() {

		return cardExpiryDate;
	}
	
	public WebElement cardCvc() {

		return cardCvc;
	}

	public WebElement subscribeButton() {

		return subscribeButton;
	}
	
	public WebElement billingName() {

		return billingName;
	}
	
	public WebElement paymentSuccessfulMessage() {

		return paymentSuccessfulMessage;
	}
	
	public WebElement cardDeclinedMessage() {

		return cardDeclinedMessage;
	}
	
	public WebElement completeAuthentication() {

		return completeAuthentication;
	}
}
