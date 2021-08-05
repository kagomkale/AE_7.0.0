package com.ae.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.Reporter;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.util.Messages;

public class TenantUsersPage extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 200);
	public WebElements webelements = new WebElements();
	public LoginPage loginpage = new LoginPage();
	public InformationPage informationpage=new InformationPage();

	@FindBy(xpath = "//span[text()='Users']")
	WebElement usersTab;
	@FindBy(xpath = "//a[text()='Tenant Users']")
	WebElement tenantUsersTab;
	@FindBy(xpath = "//button[@name='add-cred']/span")
	WebElement addBtn;
	@FindBy(id = "tenantOrgCode")
	WebElement tenantdropdown;
	@FindBy(id = "fname")
	WebElement fName;
	@FindBy(id = "lname")
	WebElement lName;
	@FindBy(id = "useremail")
	WebElement userMail;
	@FindBy(id = "username")
	WebElement userName;
	@FindBy(id = "pswd")
	WebElement pswd;
	@FindBy(id = "confirmPswd")
	WebElement confirmPswd;
	// @FindBy(id="role")
	@FindBy(xpath = "//select[@id='role']")
	WebElement roledropdown;
	@FindBy(name = "submit")
	WebElement createBtn;
	@FindBy(xpath = "//span[@class='fa fa-refresh']")
	WebElement refreshBtn;
	@FindBy(xpath = "//select[@id='pageSize'][1]")
	WebElement pageNumber;
	@FindBy(xpath = "//table[@class='ae-table table table-hover table-bordered table-striped mb-0']/tr[2]/td/span[@class='fa fa-edit']")
	WebElement editBtn;
	@FindBy(xpath = "//button[@name='save' and @type='submit']")
	WebElement saveBtn;
	@FindBy(xpath = "//div/p[contains(text(),'User updated successfully')]")
	WebElement editUserMsg;

	public TenantUsersPage() {
		PageFactory.initElements(driver, this);
		// this.driver=driver;
	}

	public void creatingTenantUser(String tenantOrgCode, String FName, String LName, String UserMail, String UserName,
			String Pswd, String ConfirmPswd, String RoleName) throws Exception {
		// Click Users Tab
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		// Click TenantUsers Tab
		wait.until(ExpectedConditions.visibilityOf(tenantUsersTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", tenantUsersTab);
		// click add new
		wait.until(ExpectedConditions.visibilityOf(addBtn));
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", addBtn);
		Reporter.log("started creating new Tenant",true);
		// Start form 
		//Locating the select dropdown for Tenant
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		Select tenant = new Select(tenantdropdown);
		tenant.selectByValue(tenantOrgCode);
		Thread.sleep(3000);
		fName.sendKeys(FName);
		Thread.sleep(3000);
		lName.sendKeys(LName);
		Thread.sleep(3000);
		userMail.sendKeys(UserMail);
		Thread.sleep(3000);
		userName.sendKeys(UserName);
		Thread.sleep(3000);
		pswd.sendKeys(Pswd);
		Thread.sleep(3000);
		confirmPswd.sendKeys(ConfirmPswd);
		Thread.sleep(2000);
		Select select = new Select(roledropdown);
		select.selectByVisibleText(RoleName);
		Thread.sleep(5000);
		// create button
		JavascriptExecutor js5 = (JavascriptExecutor) driver;
		js5.executeScript("arguments[0].click();", createBtn);
		Reporter.log("User is created successfully",true);
		Thread.sleep(30000);
		for (int i = 0; i <= 3; i++) {
			String actual_UserName = driver
					.findElement(By.xpath("//table/tr/td/div[contains(@title,'" + UserName + "')]")).getText();
			String expected_UserName = UserName;
			System.out.println("Actual Username:" + actual_UserName);
			System.out.println("Expected Username:" + expected_UserName);
			Assert.assertEquals(actual_UserName, expected_UserName, "New user can not added in list");
			Reporter.log("New user is verified and present in the webtable",true);
			break;
		}
		informationpage.validateSignOut();
	}

	public void EditTenantUser(String tenantOrgCode, String FName, String LName, String UserMail, String UserName,
			String Pswd, String ConfirmPswd, String RoleName,String NewUserMail)throws Exception {
		// Click Users Tab
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		// Click TenantUsers Tab
		wait.until(ExpectedConditions.visibilityOf(tenantUsersTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", tenantUsersTab);
		// click add new
		wait.until(ExpectedConditions.visibilityOf(addBtn));
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", addBtn);
		Reporter.log("started creating new Tenant",true);
		// Start form
		// Locating the select dropdown for Tenant
		wait.until(ExpectedConditions.visibilityOf(tenantdropdown));
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("window.scrollBy(0,600);", tenantdropdown);
		Select tenant = new Select(tenantdropdown);
		tenant.selectByValue(tenantOrgCode);
		fName.sendKeys(FName);
		Thread.sleep(3000);
		lName.sendKeys(LName);
		Thread.sleep(3000);
		userMail.sendKeys(UserMail);
		Thread.sleep(3000);
		userName.sendKeys(UserName);
		Thread.sleep(3000);
		pswd.sendKeys(Pswd);
		Thread.sleep(3000);
		confirmPswd.sendKeys(ConfirmPswd);
		Thread.sleep(4000);
		// role dropdown
		// roledropdown.click();
		// driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		Select select = new Select(roledropdown);
		select.selectByVisibleText(RoleName);
		// create button
		Thread.sleep(5000);
		// createBtn.click();
		JavascriptExecutor js5 = (JavascriptExecutor) driver;
		js5.executeScript("arguments[0].click();", createBtn);
		Reporter.log("User is created successfully",true);
		Thread.sleep(10000);
		// driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		// verify whether user is present in table or not
		for (int i = 0; i <= 3; i++) {
			String actual_UserName = driver.findElement(By.xpath("//table/tr/td/div[@title='" + UserName + "']"))
					.getText();
			String expected_UserName = UserName;
			System.out.println("Actual Username:" + actual_UserName);
			System.out.println("Expected Username:" + expected_UserName);
			Assert.assertEquals(actual_UserName, expected_UserName, "New user can not added in list");
			log.info("New user is verified and present in the webtable");
			break;
		}
		Thread.sleep(2000);
		editBtn.click();
		for (int i = 0; i < 30; i++) {
			userMail.sendKeys(Keys.BACK_SPACE);
		}
		userMail.sendKeys(NewUserMail);
	/*	for (int i = 0; i < 30; i++) {
			pswd.sendKeys(Keys.BACK_SPACE);
		}
		pswd.sendKeys(NewPswd);
		for (int i = 0; i < 30; i++) {
			confirmPswd.sendKeys(Keys.BACK_SPACE);
		}
		confirmPswd.sendKeys(NewConfirmPswd);*/
		saveBtn.click();
		String actual_EditUserMsg = editUserMsg.getText();
		String expected_EditUserMsg = Messages.editUser;
		System.out.println("Actual Username:" + actual_EditUserMsg);
		System.out.println("Expected Username:" + expected_EditUserMsg);
		Assert.assertEquals(actual_EditUserMsg, expected_EditUserMsg, "Tenant User details not edited successfully");
		Reporter.log("Tenant User details got edited.",true);
		informationpage.validateSignOut();
	}
}
