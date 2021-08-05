package com.ae.qa.pagesTenantAdmin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.pages.InformationPage;
import com.ae.qa.pages.LoginPage;
import com.ae.qa.util.Messages;
import com.aventstack.extentreports.Status;

public class HomePageTA extends TestBase{
	public LoginPage loginpage = new LoginPage();
	public WebDriverWait wait=new WebDriverWait(driver,60);
	public InformationPageTA informationpageta=new InformationPageTA();
	
	@FindBy(xpath="//input[@id='oldpswd']")
	WebElement oldPswd;
	@FindBy(xpath="//input[@id='newpswd']")
	WebElement newPswd;
	@FindBy(xpath="//input[@id='confirmpswd']")
	WebElement newConfirmPswd;
	@FindBy(xpath="//button[text()='Change']")
	WebElement changeBtn;
	@FindBy(xpath="//h2[text()='Activate']")
	WebElement uploadLicense;
	@FindBy(id="licenseFileControl")
	//@CacheLookup
	WebElement chooseFile;
	@FindBy(xpath="//button[@name='upload']")
	WebElement uploadBtn;
	@FindBy(xpath="//*[@id='step-license']/h4")
	WebElement licenseBox;
	@FindBy(xpath="//h4[@title='VALID']")
	WebElement validLicenseBox;
	@FindBy(xpath = "//p[@class='alert-message-text']")
	WebElement alertMessage;
	@FindBy(xpath = "//span[text()='Workflows']")
	WebElement workflowsTab;
	@FindBy(xpath = "//a[text()='Workflow List']")
	WebElement workflowListTab;
	@FindBy(xpath = "//button[@name='add-new']/span")
	WebElement importTab;
	@FindBy(id = "workflow_name")
	WebElement workflowName;
	@FindBy(id = "description")
	WebElement wfDescription;
	@FindBy(id = "category_name")
	WebElement wfCategory;
	@FindBy(xpath="//fieldset[@class='workflow-fieldset']/div/label//input")
	WebElement ChooseWFToImport;
	@FindBy(name = "submit")
	WebElement createBtn;
	@FindBy(xpath="//span[text()='Home']")
	WebElement homeTab;
	
	
	public HomePageTA() {
		PageFactory.initElements(driver, this);
	}
	
	public void validateUploadLicenseNewUser(String wfName,String wfdes,String category) throws Exception {
		//UNverified TA user login with new changed password
		loginpage.login(prop.getProperty("username_TA1"),prop.getProperty("password_TA1")); 
		//Negative scenario -Verify without uploading license whether user can import wf (user will get error)
		wait.until(ExpectedConditions.visibilityOf(workflowsTab));
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", workflowsTab);
		Reporter.log("Workflows Tab is clicked",true);
		Thread.sleep(2000);
		importTab.click();
		Reporter.log("Import button clicked",true);
		workflowName.sendKeys(wfName);
		Thread.sleep(3000);
		wfDescription.sendKeys(wfdes);
		Thread.sleep(3000);
		Select wfCategory_drpdown = new Select(wfCategory);
		wfCategory_drpdown.selectByVisibleText(category);
		Thread.sleep(3000);
		ChooseWFToImport.sendKeys(prop.getProperty("WFToImportPath"));
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("arguments[0].click();", createBtn);
		Reporter.log("Create Button is clicked",true);
		//check failure message
		 wait.until(ExpectedConditions.visibilityOf(alertMessage));
		 String actual_successMsg = alertMessage.getText();
			System.out.println("Success message: " + actual_successMsg);
			String expected_successMsg = Messages.ImportWOLicense;
			Assert.assertEquals(actual_successMsg, expected_successMsg, "Without license import is still successful");
			Reporter.log("Import is not possible w/o License ",true);
		//Now navigate to home tab to upload license
			wait.until(ExpectedConditions.visibilityOf(homeTab));
			js.executeScript("arguments[0].click();", homeTab);
			Reporter.log("HomeTab is clicked",true);
		//Click on License upload box
		wait.until(ExpectedConditions.visibilityOf(uploadLicense));
		//JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", uploadLicense);
		Reporter.log("Upload Licens ebox is clicked",true);
		//Choose license file to upload
		chooseFile.sendKeys(prop.getProperty("licenseFilePath"));
		Reporter.log("License file submitted",true); 
		//Upload the license by clicking upload button
		wait.until(ExpectedConditions.elementToBeClickable(uploadBtn));
		 Thread.sleep(2000);
		 uploadBtn.click();
		 Reporter.log("Upload button is clicked",true);
		 //check success message comes or not
		 wait.until(ExpectedConditions.visibilityOf(alertMessage));
		 String actual_successMsg1 = alertMessage.getText();
			System.out.println("Success message: " + actual_successMsg);
			String expected_successMsg1 = Messages.licenseUpload;
			Assert.assertEquals(actual_successMsg1, expected_successMsg1, "License not uploaded successfully");
			Reporter.log("License Uploaded successfully for Tenant",true);
			Thread.sleep(2000);
			informationpageta.validateSignOut();
}
	//backup
/*	public void validateUploadLicenseNewUser() throws Exception {
		//Unverified TA user login with first time password
		loginpage.login(prop.getProperty("username_TA"),prop.getProperty("FT_password_TA")); 
		Thread.sleep(2000);
		//Change password with new password
		oldPswd.sendKeys(prop.getProperty("FT_password_TA"));
		Thread.sleep(2000);
		newPswd.sendKeys(prop.getProperty("password_TA"));
		Thread.sleep(2000);
		newConfirmPswd.sendKeys(prop.getProperty("password_TA"));
		Thread.sleep(2000);
		JavascriptExecutor js_change=(JavascriptExecutor)driver;
		js_change.executeScript("arguments[0].click();", changeBtn);
		//Login with new Credentials
		driver.navigate().to(prop.getProperty("url"));
		loginpage.login(prop.getProperty("username_TA"),prop.getProperty("password_TA")); 
		//Click on License upload box
		wait.until(ExpectedConditions.visibilityOf(uploadLicense));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", uploadLicense);
		Reporter.log("Upload Licens ebox is clicked",true);
		//Choose license file to upload
		chooseFile.sendKeys(prop.getProperty("licenseFilePath"));
		Reporter.log("License file submitted",true); 
		//Upload the license by clicking upload button
		wait.until(ExpectedConditions.elementToBeClickable(uploadBtn));
		 Thread.sleep(2000);
		 uploadBtn.click();
		 Reporter.log("Upload button is clicked",true);
		
		 //check success message comes or not
		 wait.until(ExpectedConditions.visibilityOf(alertMessage));
		 String actual_successMsg = alertMessage.getText();
			System.out.println("Success message: " + actual_successMsg);
			String expected_successMsg = Messages.licenseUpload;
			Assert.assertEquals(actual_successMsg, expected_successMsg, "License not uploaded successfully");
			Reporter.log("License Uploaded successfully for Tenant",true);
			Thread.sleep(2000);
			informationpageta.validateSignOut();

	}*/
}
	

