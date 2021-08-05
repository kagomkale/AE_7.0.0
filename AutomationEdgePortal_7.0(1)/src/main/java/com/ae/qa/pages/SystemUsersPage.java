package com.ae.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.util.Messages;

public class SystemUsersPage extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 150);
	public WebElements webelements = new WebElements();
	public LoginPage loginpage = new LoginPage();
	public InformationPage informationpage=new InformationPage();

	@FindBy(xpath = "//span[text()='Users']")
	WebElement usersTab;
	@FindBy(xpath = "//a[text()='System Users']")
	WebElement systemUsersTab;
	@FindBy(name = "add-new")
	WebElement addBtn;
	@FindBy(id = "tenantOrgCode")
	WebElement tenantdrpdown;
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
	@FindBy(xpath = "//div[contains(text(),'Passwords Mismatch!')]")
	WebElement confirmationError;
	@FindBy(xpath = "//button[@name='submit']")
	WebElement createBtn;
	@FindBy(xpath = "//span[@class='fa fa-refresh']")
	WebElement refreshBtn;
	@FindBy(xpath = "//button[@name='save' and @type='submit']")
	WebElement saveBtn;
	@FindBy(xpath = "//div/p[contains(text(),'User updated successfully')]")
	WebElement editUserMsg;

	public SystemUsersPage() {
		PageFactory.initElements(driver, this);
	}

	public void creatingSystemAdmin(String FName, String LName, String UserMail, String UserName, String Pswd,
			String ConfirmPswd) throws Exception {
		// Click Users Tab
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User logged in successfully",true);
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		// Click TenantUsers Tab
		wait.until(ExpectedConditions.visibilityOf(systemUsersTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", systemUsersTab);
		// click add new
		wait.until(ExpectedConditions.visibilityOf(addBtn));
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", addBtn);
		log.info("started creating new system admin");
		// Start form
		Select select = new Select(tenantdrpdown);
		select.selectByValue("SYSADMIN");
		Thread.sleep(2000);
		fName.sendKeys(FName);
		Thread.sleep(2000);
		lName.sendKeys(LName);
		Thread.sleep(2000);
		userMail.sendKeys(UserMail);
		Thread.sleep(2000);
		userName.sendKeys(UserName);
		Thread.sleep(2000);
		pswd.sendKeys(Pswd);
		Thread.sleep(2000);
		confirmPswd.sendKeys(ConfirmPswd);
		Thread.sleep(10000);
		js2.executeScript("arguments[0].click();", createBtn);
		//createBtn.click();
		//Actions action = new Actions(driver);
		//action.click(createBtn).build().perform();
		Thread.sleep(15000);
		for (int i = 0; i <= 2; i++) {
			String actual_UserName = driver.findElement(By.xpath("//table/tr/td/label[@title='" + UserName + "']"))
					.getText();
			String expected_UserName = UserName;
			System.out.println("Actual Username:" + actual_UserName);
			System.out.println("Expected Username:" + expected_UserName);
			Assert.assertEquals(actual_UserName, expected_UserName, "System Admin can not added in list");
			Reporter.log("System Admin is verified and present in the webtable",true);
			break;
		}
		informationpage.validateSignOut();
	}

	public void creatingSystemAdminWithWrongPswd(String FName, String LName, String UserMail, String UserName,
			String Pswd, String ConfirmPswd) throws Exception {
		// Click Users Tab
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		// Click TenantUsers Tab
		wait.until(ExpectedConditions.visibilityOf(systemUsersTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", systemUsersTab);
		// click add new
		wait.until(ExpectedConditions.visibilityOf(addBtn));
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", addBtn);
		log.info("started creating new system admin");
		// Start form
		Select select = new Select(tenantdrpdown);
		select.selectByValue("SYSADMIN");
		Thread.sleep(3000);
		fName.sendKeys(FName);
		Thread.sleep(2000);
		lName.sendKeys(LName);
		Thread.sleep(3000);
		userMail.sendKeys(UserMail);
		Thread.sleep(3000);
		userName.sendKeys(UserName);
		Thread.sleep(3000);
		pswd.sendKeys(Pswd);
		Thread.sleep(3000);
		confirmPswd.sendKeys(ConfirmPswd);
		Thread.sleep(12000);
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("arguments[0].click();", createBtn);
		String errorMsg = confirmationError.getText();
		Reporter.log("Actual message when user both passwords are not same: "+errorMsg,true);
		Assert.assertEquals(errorMsg, Messages.passwordMismatch, "Not getting correct result on password mismatch");
	    Reporter.log("Got correct error when there is mismatch in password",true);
		informationpage.validateSignOut();
		
	}

	public void EditSystemUsers(String FName, String LName, String UserMail, String UserName, String Pswd,
			String ConfirmPswd, String NewUserMail) throws Exception {
		// SystemUsersPage s1=new SystemUsersPage();
		// s1.creatingSystemAdmin(FName, LName, UserMail, UserName, Pswd, ConfirmPswd);
		// Thread.sleep(6000);
		// Click Users Tab
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		// Click TenantUsers Tab
		wait.until(ExpectedConditions.visibilityOf(systemUsersTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", systemUsersTab);
		// click add new
		wait.until(ExpectedConditions.visibilityOf(addBtn));
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", addBtn);
		log.info("started creating new system admin");
		// Start form
		Select select = new Select(tenantdrpdown);
		select.selectByValue("SYSADMIN");
		Thread.sleep(2000);
		fName.sendKeys(FName);
		Thread.sleep(2000);
		lName.sendKeys(LName);
		Thread.sleep(3000);
		userMail.sendKeys(UserMail);
		Thread.sleep(3000);
		userName.sendKeys(UserName);
		Thread.sleep(3000);
		pswd.sendKeys(Pswd);
		Thread.sleep(3000);
		confirmPswd.sendKeys(ConfirmPswd);
		Thread.sleep(12000);
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("arguments[0].click();", createBtn);
	
		Thread.sleep(10000);

		driver.findElement(By.xpath("//table/tr/td/label[@title='" + UserName + "']")).click();
		System.out.println("clicking on edit user & editing emailID");
		for (int i = 0; i < 30; i++) {
			userMail.sendKeys(Keys.BACK_SPACE);
		}
		userMail.sendKeys(NewUserMail);
		saveBtn.click();
		String actual_EditUserMsg = editUserMsg.getText();
		String expected_EditUserMsg = Messages.editSystemUser;
		System.out.println("Actual Username:" + actual_EditUserMsg);
		System.out.println("Expected Username:" + expected_EditUserMsg);
		Assert.assertEquals(actual_EditUserMsg, expected_EditUserMsg, "System User details not edited successfully");
		Reporter.log("System User details got edited.",true);
		informationpage.validateSignOut();
	}
}
