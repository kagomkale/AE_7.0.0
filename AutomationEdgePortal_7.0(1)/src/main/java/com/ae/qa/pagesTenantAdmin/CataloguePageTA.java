package com.ae.qa.pagesTenantAdmin;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.pages.WebElements;

public class CataloguePageTA extends TestBase{
	public WebDriverWait wait = new WebDriverWait(driver, 150);
	public WebElements webelements = new WebElements();
	public LoginPageTA loginpageta = new LoginPageTA();
	public InformationPageTA informationpageta=new InformationPageTA();
	public RequestsPageTA requestspageta=new RequestsPageTA();
	
	@FindBy(xpath = "//span[text()='Catalogue']")
	WebElement catalogueTab;
	@FindBy(xpath="//button[@name='submit']")
	WebElement submitRequestBtn;
	@FindBy(id="popup-button-ok")
	WebElement okBtn;

	public CataloguePageTA() {
		PageFactory.initElements(driver, this);
	}

	public void validateSubmitRequest(String WfName) throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(catalogueTab));
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();",catalogueTab);
		Reporter.log("Catalogue Tab is clicked",true);
		Thread.sleep(2000);
		WebElement wfNameToSendRequest=driver.findElement(By.xpath("//h6[text()='"+WfName+"']"));
		wfNameToSendRequest.click();
		Reporter.log("Workflow whose request needs to be send is selected",true);
		Thread.sleep(2000);
		submitRequestBtn.click();
		Reporter.log("Submit request button is clicked");
		Thread.sleep(2000);
		okBtn.click();
		Reporter.log("OK button is clicked");
		Thread.sleep(2000);
		//requestspageta.validateRequestStatus();
		informationpageta.validateSignOut();
	}
}
