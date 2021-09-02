package com.tabeo.automation.Tests;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.tabeo.automation.framework.core.Browser;
import com.tabeo.automation.framework.core.PaymentInformation;



public class CreateSuccessfulPaymentTest extends Browser{
	
	public static final String purchaseSuccessfulMessage = "Your purchase is ready to be downloaded.";
	static Logger logger = LogManager.getLogger(CreateSuccessfulPaymentTest.class);
	@BeforeMethod
	void LoginToApplication() throws IOException, InterruptedException {
		LoginTestWithGmailOath login = new LoginTestWithGmailOath();
		login.LoginTestWithGmailAuthentication();
	}
	@Test
	public void successfulPaymentTestcase() throws Exception{
		PaymentInformation paymentPage = new PaymentInformation(driver);
		paymentPage.paymentButtonInstallment().click();
		waitUntilPageLoaded();
		loadUserPropertiesfile();
		setCardDetails(paymentPage.cardNumber(),userDetails.getProperty("correctcardnumber"));
		setCardDetails(paymentPage.cardCvc(),userDetails.getProperty("cardcvc"));
		setCardDetails(paymentPage.cardExpiryDate(),userDetails.getProperty("cvvexpirydate"));
		setCardDetails(paymentPage.billingName(),userDetails.getProperty("billingname"));
		paymentPage.subscribeButton().click();
		waitUntilPageLoaded();
		try{
			if(purchaseSuccessfulMessage.equals(paymentPage.paymentSuccessfulMessage().getText())){
				logger.info("Payment is successful");
			}
		}catch(Exception e){
			logger.info("Payment is not successful");
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
