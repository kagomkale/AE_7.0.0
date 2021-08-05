package com.ae.qa.pages;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.util.Messages;

public class TenantsPage extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 180);
	public LoginPage loginpage = new LoginPage();
	public InformationPage informationpage=new InformationPage();
	public WebElements webelements = new WebElements();

	@FindBy(xpath = "//h2")
	WebElement tenantsPageTitle;
	@FindBy(xpath = "//span[(text()='Tenants')]")
	WebElement tenantsTab;
	@FindBy(name = "add-tenant")
	WebElement addTenantBtn;
	@CacheLookup
	@FindBy(id = "tenantName")
	WebElement tenantName;
	@FindBy(id = "description")
	WebElement descriptionOfTenant;
	@FindBy(id = "orgCode")
	WebElement organizationCode;
	@FindBy(name = "submit")
	WebElement createBtn;
	@FindBy(name = "cancel")
	WebElement cancelBtn;
	@FindBy(xpath = "//p[@class='alert-message-text']")
	WebElement alertMessage;
	@FindBy(xpath = "//table/tr[2]/td[5]/span")
	WebElement licenseStatus;

	public TenantsPage() {
		PageFactory.initElements(driver, this);
	}
	
	// Actions
	public String validateTenantsPageTitle() throws InterruptedException {
		// TODO Auto-generated method stub
		if (tenantsPageTitle.isDisplayed()) {
			return tenantsPageTitle.getText();
		} else {
			Thread.sleep(3000);
			return tenantsPageTitle.getText();
		}
	}

	public void addNewTenants(String tName, String tDescription, String orgCode) throws Exception {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Tenants Tab
	 	wait.until(ExpectedConditions.visibilityOf(tenantsTab));
		JavascriptExecutor js_tenant = (JavascriptExecutor) driver;
		js_tenant.executeScript("arguments[0].click();", tenantsTab);
		// Click add Tenant button
		wait.until(ExpectedConditions.visibilityOf(addTenantBtn));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", addTenantBtn);
		// Fill details like Tenant Name, Description and Organization code
		wait.until(ExpectedConditions.visibilityOf(tenantName));
		tenantName.sendKeys(tName);
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOf(descriptionOfTenant));
		descriptionOfTenant.sendKeys(tDescription);
		wait.until(ExpectedConditions.visibilityOf(organizationCode));
		organizationCode.sendKeys(orgCode);
		// Create button
		wait.until(ExpectedConditions.visibilityOf(createBtn));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", createBtn);
		// do advance search
		// Verify Success message
		wait.until(ExpectedConditions.visibilityOf(alertMessage));
		String actual_successMsg = alertMessage.getText();
		System.out.println("Success message: " + actual_successMsg);
		String expected_successMsg = Messages.createTenant;
		Assert.assertEquals(actual_successMsg, expected_successMsg, "Tenant not created successfully");
		Reporter.log("Tenant created successfully",true);
		// refresh the page
	  // webelements.clickrefreshBtn();
		webelements.AdvanceSearchField("name", "eq", tName);
		webelements.ExtraAdvanceSearch("orgCode", "eq", orgCode);
	//	webelements.InputSearchField(tName);
		// Verify data in table now
		Reporter.log("Below validation is to validate new tenant record is visible in webtable",true);
		String actual_TenantName = driver.findElement(By.xpath("//table/tr/td[text()='" + tName + "']")).getText();
		String expected_TenantName = tName;
		System.out.println("Actual:"+actual_TenantName+"Expected:"+expected_TenantName);
		Assert.assertEquals(actual_TenantName, expected_TenantName, "Tenant can not added in list");
		Reporter.log("New Tenant is present in the table-Validated successfully",true);
		informationpage.validateSignOut();
	}

	public void addNewTenantsWithDuplicateOrgCode(String ttName, String ttDescription, String oorgCode)
			throws Exception {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Tenants Tab
			 	wait.until(ExpectedConditions.visibilityOf(tenantsTab));
				JavascriptExecutor js_tenant = (JavascriptExecutor) driver;
				js_tenant.executeScript("arguments[0].click();", tenantsTab);
		// Click add Tenant button
		wait.until(ExpectedConditions.visibilityOf(addTenantBtn));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", addTenantBtn);
		// Fill details like Tenant Name, Description and Organization code
		wait.until(ExpectedConditions.visibilityOf(tenantName));
		tenantName.sendKeys(ttName);
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOf(descriptionOfTenant));
		descriptionOfTenant.sendKeys(ttDescription);
		wait.until(ExpectedConditions.visibilityOf(organizationCode));
		organizationCode.sendKeys(oorgCode);
		// Create button
		wait.until(ExpectedConditions.visibilityOf(createBtn));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", createBtn);
		// Verify Success message
		wait.until(ExpectedConditions.visibilityOf(alertMessage));
		String actual_failureMsg = alertMessage.getText();
		Reporter.log("Failure message after entering same org code : " + actual_failureMsg,true);
		String expected_Msg = "Duplicate orgcode ["+oorgCode+"].";
		// String expected_failureMsg="Duplicate orgcode ["+orgCode.toUpperCase()+"].";
		Assert.assertEquals(actual_failureMsg, expected_Msg,"User might not got correct error on duplicate org code");
		Reporter.log ("New Usernot created due to duplicate orgcode.",true);
		informationpage.validateSignOut();
	}

	public String createUniqueName() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		String datetime=ft.format(date);
		return datetime;
	}

	public void verifyLicenseInfo() throws InterruptedException {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
	     Reporter.log("User log in Successfully",true);
		// click Tenants Tab
		wait.until(ExpectedConditions.visibilityOf(tenantsTab));
		JavascriptExecutor js_tenant = (JavascriptExecutor) driver;
		js_tenant.executeScript("arguments[0].click();", tenantsTab);
	     Thread.sleep(3000);
		String actual_licenseStatus = licenseStatus.getText();
		System.out.println("The License Status for System Admin is " + actual_licenseStatus);
		String expected_licenseStatus = "NA";
		Assert.assertEquals(actual_licenseStatus, expected_licenseStatus, "License Status could not match.");
		Reporter.log("License status verified", true);
		informationpage.validateSignOut();

	}
}
