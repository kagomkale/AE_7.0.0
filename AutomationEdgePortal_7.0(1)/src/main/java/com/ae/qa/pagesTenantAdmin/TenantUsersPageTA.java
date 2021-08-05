package com.ae.qa.pagesTenantAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.pages.InformationPage;
import com.ae.qa.pages.WebElements;
import com.ae.qa.util.Messages;
import com.ae.qa.util.TestUtil;

public class TenantUsersPageTA extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 200);
	public WebElements webelements = new WebElements();
	public LoginPageTA loginpageta = new LoginPageTA();
	public InformationPageTA informationpageta=new InformationPageTA();
	public TestUtil testutil=new TestUtil();

	@FindBy(xpath = "//span[text()='Users']")
	WebElement usersTab;
	@FindBy(xpath = "//a[text()='Tenant Users']")
	WebElement tenantUsersTab;
	@FindBy(xpath = "//a[text()='User Groups']")
	WebElement userGroupsTab;
	@FindBy(xpath = "//button[@name='add-cred']/span")
	WebElement addBtn;
	@FindBy(xpath = "//button[@name='add-new']/span")
	WebElement addBtnUG;
	@FindBy(id = "authType")
	WebElement userTypedropdown;
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
	@FindBy(id = "role")
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
	@FindBy(id = "gname")
	WebElement groupNameField;
	@FindBy(id = "description")
	WebElement descriptionField;
	@FindBy(xpath = "//*[@name='create']")
	WebElement createBtnUG;
	@FindBy(xpath = "//div/p[@class='alert-message-text']")
	WebElement actual_userGroupMsg;
	@FindBy(id = "change-pswd")
	WebElement changePswdTab;
	@FindBy(id = "oldpswd")
	WebElement oldPswd;
	@FindBy(id = "newpswd")
	WebElement newPswd;
	@FindBy(id = "confirmpswd")
	WebElement newConfirmPswd;
	@FindBy(xpath = "//button[text()='Change']")
	WebElement changeBtn;
	@FindBy(xpath="//div/p[@class='alert-message-text']")
	WebElement alertMessage;
	@FindBy(xpath="//span/span[text()='Select Groups']")
	WebElement AddUserToGroup;
	@FindBy(xpath="//input[@name='search']")
	WebElement searchBar;
	@FindBy(xpath="//span[@class='mul-checkmark']")
	WebElement checkGroupName;
	
	public TenantUsersPageTA() {
		PageFactory.initElements(driver, this);
	}
	//Workflow Admin //Tenant Admin //User Admin 
	public void creatingUser(String userType, String FName, String LName, String UserMail, String UserName,
			String Pswd, String ConfirmPswd, String RoleName) throws Exception {
		// Click Users Tab
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User log in Successfully",true);
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
		Reporter.log("started creating new " +RoleName,true);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Select user_type = new Select(userTypedropdown);
		user_type.selectByVisibleText(userType);
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
		Thread.sleep(10000);
		Select select = new Select(roledropdown);
		select.selectByVisibleText(RoleName);
		Thread.sleep(5000);
		// create button
		JavascriptExecutor js5 = (JavascriptExecutor) driver;
		js5.executeScript("arguments[0].click();", createBtn);
		Reporter.log(RoleName + " is created successfully",true);
		wait.until(ExpectedConditions.visibilityOf(alertMessage));
		String actual_successMsg = alertMessage.getText();
		String expected_successMsg = Messages.createUser;
		System.out.println("Actual message:" + actual_successMsg);
		Assert.assertEquals(actual_successMsg, expected_successMsg,"User not created.");
		Reporter.log(RoleName + " created successfully",true);
		informationpageta.validateSignOut();
	}
//Create Tenant User and edit email ID
	public void valiateCreatingTenantUser(String userType, String FName, String LName, String UserMail, String UserName,
			String Pswd, String ConfirmPswd, String RoleName) throws Exception {
		// Click Users Tab
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User log in Successfully",true);
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
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Select user_type = new Select(userTypedropdown);
		user_type.selectByVisibleText(userType);
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
		Thread.sleep(10000);
		Select select = new Select(roledropdown);
		select.selectByVisibleText(RoleName);
		Thread.sleep(5000);
	/*	AddUserToGroup.click();
		searchBar.sendKeys(groupName);
		Thread.sleep(3000);
		checkGroupName.click();*/
		// create button
		JavascriptExecutor js5 = (JavascriptExecutor) driver;
		js5.executeScript("arguments[0].click();", createBtn);
		Reporter.log("User is created successfully",true);
		wait.until(ExpectedConditions.visibilityOf(alertMessage));
		String actual_successMsg = alertMessage.getText();
		String expected_successMsg = Messages.createUser;
		System.out.println("Actual message:" + actual_successMsg);
		Assert.assertEquals(actual_successMsg, expected_successMsg,"User not created.");
		Reporter.log(RoleName + " created successfully",true);
		informationpageta.validateSignOut();
	}
	
	public void ValidateUnverifiedStatus(String userType, String FName, String LName, String UserMail, String UserName,
			String Pswd, String ConfirmPswd, String RoleName) throws Exception {
		valiateCreatingTenantUser(userType,FName,LName,UserMail,UserName,Pswd,ConfirmPswd,RoleName);
		driver.navigate().to(prop.getProperty("url"));
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User log in Successfully",true);
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		WebElement status=driver.findElement(By.xpath("//table/tr/td/div[@title='"+UserName+"']/../../td/button"));
		String Actual_Status=status.getText();
		Reporter.log("Actual Status of New Tenant user is :"+Actual_Status,true);
		String Expected_Status= "UNVERIFIED";
		Reporter.log("Expected Status of New Tenant user is :"+Expected_Status,true);
		Assert.assertEquals(Actual_Status,Expected_Status,"Status of newly created user is not Unverified");
		Reporter.log("Status of newly created user is UNVERIFIED, verified successfully",true);
		informationpageta.validateSignOut();
	}
	public void ValidateActiveStatus(String UserName,String Pswd, String NewPswd) throws Exception {
		loginpageta.ValidateFirstTimeLogin(UserName,Pswd,NewPswd);
		Thread.sleep(5000);
		driver.navigate().to(prop.getProperty("url"));
		loginpageta.login(UserName,NewPswd);
		Reporter.log("User log in Successfully",true);
		informationpageta.validateSignOut();
		Thread.sleep(3000);
		Reporter.log("Login into TA to verify status",true);
		driver.navigate().to(prop.getProperty("url"));
		Thread.sleep(3000);
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		WebElement status=driver.findElement(By.xpath("//table/tr/td/div[@title='"+UserName+"']/../../td/button"));
		String Actual_Status=status.getText();
		Reporter.log("Actual Status of Tenant user after changing password is :"+Actual_Status,true);
		String Expected_Status= "ACTIVE";
		Reporter.log("Expected Status of Tenant user after changing password is :"+Expected_Status,true);
		Assert.assertEquals(Actual_Status,Expected_Status,"Status of newly created user is not Active");
		Reporter.log("Status of user whose First time password changed is ACTIVE, verified successfully",true);
		informationpageta.validateSignOut();
	}

	/*	
	public void creatingWorkflowAdmin(String userType, String FName, String LName, String UserMail, String UserName,
			String Pswd, String ConfirmPswd, String RoleName, String NewPswd) throws Exception {
		// Click Users Tab
		loginpageta.login(prop.getProperty("username_TA"), prop.getProperty("password_TA"));
		Reporter.log("User log in Successfully",true);
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
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Select user_type = new Select(userTypedropdown);
		user_type.selectByVisibleText(userType);
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
		Thread.sleep(10000);
		Select select = new Select(roledropdown);
		select.selectByVisibleText(RoleName);
		Thread.sleep(5000);
		// create button
		JavascriptExecutor js5 = (JavascriptExecutor) driver;
		js5.executeScript("arguments[0].click();", createBtn);
		Reporter.log("User is created successfully",true);
		for (int i = 0; i <= 3; i++) {
			String actual_UserName = driver
					.findElement(By.xpath("//table/tr/td/div[contains(@title,'" + UserName + "')]")).getText();
			String expected_UserName = UserName;
			System.out.println("Actual Username:" + actual_UserName);
			System.out.println("Expected Username:" + expected_UserName);
			Assert.assertEquals(actual_UserName, expected_UserName, "New user can not added in list");
			Reporter.log("New user is verified and present in the webtable");
			break;
		}
		informationpageta.validateSignOut();
		//As it is unverified user we need to change password first time and then relogin with new password
		driver.navigate().to(prop.getProperty("url"));
		Reporter.log("Again navigate to AE portal",true);
	//	testutil.ChangePasswordAfterLogin(UserName,Pswd, NewPswd, NewPswd);
		loginpageta.login(UserName,Pswd);
		Reporter.log("User log in successfully",true);
		Thread.sleep(5000);
		oldPswd.sendKeys(Pswd);
		Thread.sleep(1000);
		newPswd.sendKeys(NewPswd);
		Thread.sleep(1000);
		newConfirmPswd.sendKeys(NewPswd);
	//	wait.until(ExpectedConditions.elementToBeClickable(changeBtn));
		Thread.sleep(4000);
		changeBtn.click();
		Thread.sleep(3000);
		//Now relogin with new password and check whether all tabs are present as per role defined
		Thread.sleep(5000);
		driver.navigate().to(prop.getProperty("url"));
		loginpageta.login(UserName, NewPswd);
		Reporter.log("Workflow admin navigated to AE successfully",true);
		List<WebElement> Tabs_Name = driver.findElements(By.xpath("//a/span"));
		ArrayList<String> actual_content = new ArrayList<String>();
		for (WebElement element : Tabs_Name) {
			String element_value = element.getText();
			System.out.println(element_value);
			actual_content.add(element_value);
			Thread.sleep(4000);
		}
		System.out.println("Actual Tabs present for workflow Admin are :" +actual_content);
		//expected Tabs for wf admin are
		ArrayList<String> Expected_content = new ArrayList<String>();
		Expected_content.add("View Profile");
		Expected_content.add("About");
		Expected_content.add("Change Password");
		Expected_content.add("Home");
		Expected_content.add("Workflows");
		Expected_content.add("Agents");
		Expected_content.add("Catalogue");
		Expected_content.add("Requests");
		Expected_content.add("Logs");
		Expected_content.add("Reports");
		Expected_content.add("Plugins");
		Expected_content.add("Process Studio");
		Expected_content.add("File Managment");
		Expected_content.add("Settings");
		System.out.println("Expected Tabs present for workflow Admin are :"+Expected_content);
		//Now applied checkboint to validtae the expected and actual tabs are same for wf admin
		if(actual_content.equals(Expected_content)) {
			Assert.assertTrue(true);
			Reporter.log("Workflow admin has all tabs present as per role.",true);
		} else {
			Assert.assertTrue(false);
			Reporter.log("Workflow Admin do not have all tabs present as per role.",true);
		} 
		informationpageta.validateSignOut();
	}*/

}
