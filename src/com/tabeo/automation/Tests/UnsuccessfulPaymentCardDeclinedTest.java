package com.tabeo.automation.Tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tabeo.automation.framework.core.Browser;
import com.tabeo.automation.framework.core.PaymentInformation;

public class UnsuccessfulPaymentCardDeclinedTest extends Browser{
	
	static final String cardDeclinedMessage = "Your card has been declined.";
	static Logger logger = LogManager.getLogger(UnsuccessfulPaymentCardDeclinedTest.class);
	@BeforeMethod
	void LoginToApplication() throws IOException, InterruptedException {
		LoginTestWithGmailOath login = new LoginTestWithGmailOath();
		login.LoginTestWithGmailAuthentication();
	}
	@Test
	public void paymentDeclinedTestCase() throws Exception{
		PaymentInformation paymentPage = new PaymentInformation(driver);
		paymentPage.paymentButtonInstallment().click();
		waitUntilPageLoaded();
		loadUserPropertiesfile();
		wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.elementToBeClickable(paymentPage.cardNumber()));
		setCardDetails(paymentPage.cardNumber(),userDetails.getProperty("incorrectcardnumber"));
		setCardDetails(paymentPage.cardCvc(),userDetails.getProperty("cardcvc"));
		setCardDetails(paymentPage.cardExpiryDate(),userDetails.getProperty("cvvexpirydate"));
		setCardDetails(paymentPage.billingName(),userDetails.getProperty("billingname"));
		paymentPage.subscribeButton().click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
		switchToFrame(0);
		switchToFrame("__stripeJSChallengeFrame");
		switchToFrame("acsFrame");
		paymentPage.completeAuthentication().click();
		driver.switchTo().defaultContent();
		try{
			if(cardDeclinedMessage.equals(paymentPage.cardDeclinedMessage().getText())){
				logger.info("Your card payment is declined");
			}
		}catch(Exception e){
			logger.info("your card Payment is successful");
			Assert.fail();
			getScreenShot(driver, FailureScreenshots);
		}
		
		
	}
	
	public void setCardDetails(WebElement element , String cardDetails){
		Actions driverActions = new Actions(driver);
		driverActions.moveToElement(element).click().build().perform();
		element.sendKeys(cardDetails);
	}
	@AfterClass
	public void closeBrowser(){
		tearDownTest();
	}


}
