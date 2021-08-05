package com.ae.qa.pagesTenantAdmin;
import org.apache.poi.poifs.crypt.dsig.KeyInfoKeySelector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.pages.WebElements;
import com.ae.qa.util.Messages;

public class ProcessStudioPageTA extends TestBase{
	public WebDriverWait wait = new WebDriverWait(driver, 150);
	public WebElements webelements = new WebElements();
	public LoginPageTA loginpageta = new LoginPageTA();
	public InformationPageTA informationpageta=new InformationPageTA();
	
	@FindBy(xpath = "//span[text()='Process Studio']")
	WebElement processStudioTab;
	@FindBy(xpath = "//a[text()='Workflow List']")
	WebElement workflowListTab;
	@FindBy(xpath="//button[@title='for Windows']")
	WebElement downloadBtn;
	@FindBy(xpath="//div[@class='inline-flex-item pull-right']/button")
	WebElement assignLicenseBtn;
	@FindBy(xpath="//input[@placeholder='Search']")
	WebElement searchBar;
	@FindBy(xpath="//input[@type='checkbox']")
	WebElement assignTocheckbox;
	@FindBy(xpath="//button[text()='Save']")
	WebElement saveBtn;

	public ProcessStudioPageTA() {
		PageFactory.initElements(driver, this);
	}

	public void validateDownloadandAssignPS() throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(processStudioTab));
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();",processStudioTab);
		Reporter.log("Process Studio Tab is clicked",true);
		Thread.sleep(2000);
		downloadBtn.click();
		Thread.sleep(5000);
		Reporter.log("Process-studio download started",true);
		Thread.sleep(20000);
		//download will continue, assign the license
		assignLicenseBtn.click();
		Reporter.log("Assign License Button is clicked",true);
		//Because we want to assign the ps license to same TA we created
		searchBar.sendKeys(prop.getProperty("username_TA1"));
		Thread.sleep(2000);
		assignTocheckbox.click();
		Thread.sleep(2000);
		saveBtn.click();
		Reporter.log("Save button is clicked",true);
		//As there is no success message after ps assignment we will cross check with again going to assign ps tab
		//and then check if checkbox is already selected
		Thread.sleep(10000);
		assignLicenseBtn.click();
		Reporter.log("Assign License Button is clicked",true);
		for(int i=1;i<30;i++) {
			searchBar.sendKeys(Keys.BACK_SPACE);
		}
		searchBar.sendKeys(prop.getProperty("username_TA1"));
		Thread.sleep(2000);
		//Boolean status=assignTocheckbox.isSelected();
		if(assignTocheckbox.isSelected()) {
			Reporter.log("Process studio license assigned already",true);
			Assert.assertTrue(assignTocheckbox.isSelected());
		} else {
			Reporter.log("Process studio license is not assigned correctly",true);	
			Assert.assertTrue(assignTocheckbox.isSelected());
		}
		informationpageta.validateSignOut();
	}

	
}
