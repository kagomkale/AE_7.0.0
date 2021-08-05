package com.ae.qa.pagesTenantAdmin;

import java.util.ArrayList;
import java.util.List;
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
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.pages.InformationPage;
import com.ae.qa.pages.LoginPage;
import com.ae.qa.util.Messages;

public class TenantPolicyPageTA extends TestBase{
	public LoginPageTA loginpageta = new LoginPageTA();
	public static WebDriverWait wait = new WebDriverWait(driver, 600);
	public InformationPageTA informationpageta=new InformationPageTA();
	@FindBy(xpath = "//span[(text()='Settings')]")
	WebElement settingsTab;
	@FindBy(xpath = "//a[text()='Tenant Policy']")
	WebElement tenantPolicyTab;
	@FindBy(xpath = "//input[@id='attemptsInput']")
	WebElement noOfAttempts;
	@FindBy(xpath = "//button[@name='submit']")
	WebElement saveBtn;
	@FindBy(xpath = "//div/ul/li[1]/b")
	WebElement verifyAttempts;
	@FindBy(id = "popup-button-ok")
	WebElement okBtn;
	@FindBy(xpath = "//p[@class='alert-message-text']")
	WebElement PopUpMsg;
	@FindBy(id="login-username")
	WebElement userProfileTab;
	@FindBy(name = "sign-out")
	WebElement signOutBtn;
	@FindBy(id = "uname")
	WebElement username;
	@FindBy(id = "pswd")
	WebElement password;
	@FindBy(xpath = "//span[@class='fa fa-caret-right']")
	WebElement pswdPolicyDrpDwn;
	@FindBy(id = "passwordHistoryInput")
	WebElement passwordHistoryInput;
	@FindBy(xpath = "//div[@class='card-body']/ul/li[5]/b")
	WebElement verifyHistory;
	@FindBy(id = "change-pswd")
	WebElement changePswd;
	@FindBy(xpath = "//div[@id='login-username']")
	WebElement UserNameTab;
	@FindBy(id = "oldpswd")
	WebElement oldPswd;
	@FindBy(id = "newpswd")
	WebElement newPswd;
	@FindBy(id = "confirmpswd")
	WebElement newConfirmPswd;
	@FindBy(xpath = "//button[text()='Change']")
	WebElement changeBtn;
	@FindBy(id = "minLengthInput")
	WebElement minLengthInput;
	@FindBy(id = "maxLengthInput")
	WebElement maxLengthInput;
	@FindBy(id = "uppercaseInput")
	WebElement uppercaseInput;
	@FindBy(id = "lowercaseInput")
	WebElement lowercaseInput;
	@FindBy(id = "specialCharInput")
	WebElement specialCharInput;
	@FindBy(id = "digitsInput")
	WebElement digitsInput;
	@FindBy(xpath = "//span[text()='Users']")
	WebElement usersTab;
	@FindBy(xpath = "//a[text()='Tenant Users']")
	WebElement tenantUsersTab;
	@FindBy(xpath="//select[@formcontrolname='action']")
	WebElement selectAction;
	@FindBy(xpath="//button[text()='Next']")
	WebElement nextBtn;
	@FindBy(xpath="//input[@id='pswd']")
	WebElement Unlock_newPswd;
	@FindBy(xpath="//input[@id='confirmPswd']")
	WebElement Unlock_cnfPswd;
	@FindBy(xpath="//button[@name='submit']")
	WebElement enableBtn;
	@FindBy(id = "change-pswd")
	WebElement changePswdTab;
	@FindBy(xpath = "//div/h2[contains(text(),'Home')]")
	WebElement homePageTitle;
	

	public TenantPolicyPageTA() {
		PageFactory.initElements(driver, this);
	}
	//Below Test cases are respect to Sysadmin unlocking its Tenant
	public void validateNoOfAttempts(String Username, String Password,String InvalidPswd, String NoOfAttempt) throws Exception {
		loginpageta.login(Username,Password);
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(tenantPolicyTab));
		js.executeScript("arguments[0].click();", tenantPolicyTab);
		System.out.println("Tenant Policy tab clicked");
		Thread.sleep(2000);
		int noOfAttempt=Integer.valueOf(NoOfAttempt);
		// wait.until(ExpectedConditions.visibilityOf(noOfAttempts));
		for (int i = 1; i <= 2; i++) {
			noOfAttempts.sendKeys(Keys.BACK_SPACE);
		}
		noOfAttempts.sendKeys(""+noOfAttempt);
		Thread.sleep(2000);
		saveBtn.click();
		System.out.println(
				"Below validation validates no.of attempts for wrong password is equal to what user set in confirm Policy pop-up");
		String confirmAtempts = verifyAttempts.getText();
		int cnfAtempt = Integer.parseInt(confirmAtempts);
		System.out.println(
				"No.of wrong attempts actually set is:" + cnfAtempt + " No.of attempts set by user is:" + NoOfAttempt);
		if (cnfAtempt == noOfAttempt) {
			System.out.println("No.of wrong attempts actually set is:" + cnfAtempt + " No.of attempts set by user is:"
					+ NoOfAttempt);
			okBtn.click();
		} else {
			Assert.assertTrue(false, "No.of attempts provided by user does not match with message in Confirm policy.");
		}
		String Actual_SuccessMsg = PopUpMsg.getText();
		String Expected_SuccessMsg = Messages.updatePasswordPolicy;
		System.out.println("Actual Message after password policy update:" + Actual_SuccessMsg);
		System.out.println("Expected Message after password policy update:" + Expected_SuccessMsg);
		Assert.assertEquals(Actual_SuccessMsg, Expected_SuccessMsg, "Password policy not updated.");
		//WebElement user_profile = driver.findElement(By.xpath("//span[text()='" + prop.getProperty("username_purge") + "']"));
		userProfileTab.click();
		Thread.sleep(2000);
		signOutBtn.click();
		// For verifying attempts by logging in
		for (int i = 1; i <= noOfAttempt; i++) {
			if (i < noOfAttempt) {
				loginpageta.login(Username,InvalidPswd);
				String Actual_Msg = PopUpMsg.getText();
				System.out.println("Actual_msg:" + Actual_Msg);
				if (Actual_Msg.contentEquals("You have made [" + i+ "] unsuccessful attempt(s). The maximum retry attempts allowed for login are [" + NoOfAttempt
						+ "]")) 
				{
					System.out.println(i + "th Unsuccessful attempt");
					// Assert.assertTrue(true);
				}
				for (int m = 0; m < 10; m++) {
					username.sendKeys(Keys.BACK_SPACE);
					password.sendKeys(Keys.BACK_SPACE);
					Thread.sleep(3000);
				}
			} else if (i == noOfAttempt) {
				System.out.println(i + "th Unsuccessful attempt");
				loginpageta.login(Username,InvalidPswd);
				String Actual_Fail = PopUpMsg.getText();
				System.out.println("Actual_msg:" + Actual_Fail);
				String Expected_Fail = Messages.failPasswordPolicy;
				System.out.println("Expected_msg:" + Expected_Fail);
				Assert.assertEquals(Actual_Fail, Expected_Fail);
			}
		}

	}
	
	public void validateUnlockTenant(String unlock_newPswd,String unlock_cnfPswd) throws Exception {
		loginpageta.login(prop.getProperty("username_TA"), prop.getProperty("password_TA"));
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		System.out.println("Users tab clicked");
		wait.until(ExpectedConditions.visibilityOf(tenantUsersTab));
		js.executeScript("arguments[0].click();", tenantUsersTab);
		System.out.println("Tenant Users tab clicked");
		Thread.sleep(2000);
		WebElement user_record=driver.findElement(By.xpath("//table/tr/td/div[text()='"+prop.getProperty("username_noOfAttemptsTA")+"']/../../td/button"));
		//table/tr/td/div[text()='TenantAdmin1']/../../td/span[text()='LOCKED']
		//5.6.0.-//table/tr/td/div[text()='Tenant_Abc10']/../../td/button
		//table/tr/td/div[text()='TenantAdmin9']/../../../td/button[text()='LOCKED']
		user_record.click();
		Thread.sleep(3000);
		Select selectAction_dropdwn=new Select(selectAction);
		selectAction_dropdwn.selectByVisibleText("Unlock User");
		Thread.sleep(2000);
		nextBtn.click();
		Thread.sleep(2000);
		Unlock_newPswd.sendKeys(unlock_newPswd);
		Thread.sleep(2000);
		Unlock_cnfPswd.sendKeys(unlock_cnfPswd);
		Thread.sleep(2000);
		enableBtn.click();
		String Actual_SuccessMsg = PopUpMsg.getText();
		String Expected_SuccessMsg = Messages.unlock_TA;
		System.out.println("Actual Message after password policy update:" + Actual_SuccessMsg);
		System.out.println("Expected Message after password policy update:" + Expected_SuccessMsg);
		Assert.assertEquals(Actual_SuccessMsg, Expected_SuccessMsg, "Tenant Admin account is not unlocked.");
		informationpageta.validateSignOut();
	}
	//Below Test cases are respect to Tenant Admin unlocking its Tenant
	public void validateNoOfAttemptsRespectToTA(int NoOfAttempt) throws Exception {
		loginpageta.login(prop.getProperty("username_respToTA"), prop.getProperty("password_respToTA"));
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(tenantPolicyTab));
		js.executeScript("arguments[0].click();", tenantPolicyTab);
		System.out.println("Tenant Policy tab clicked");
		Thread.sleep(2000);
		// wait.until(ExpectedConditions.visibilityOf(noOfAttempts));
		for (int i = 1; i <= 2; i++) {
			noOfAttempts.sendKeys(Keys.BACK_SPACE);
		}
		noOfAttempts.sendKeys("" + NoOfAttempt);
		Thread.sleep(2000);
		saveBtn.click();
		System.out.println(
				"Below validation validates no.of attempts for wrong password is equal to what user set in confirm Policy pop-up");
		String confirmAtempts = verifyAttempts.getText();
		int cnfAtempt = Integer.parseInt(confirmAtempts);
		System.out.println(
				"No.of wrong attempts actually set is:" + cnfAtempt + " No.of attempts set by user is:" + NoOfAttempt);
		if (cnfAtempt == NoOfAttempt) {
			System.out.println("No.of wrong attempts actually set is:" + cnfAtempt + " No.of attempts set by user is:"
					+ NoOfAttempt);
			okBtn.click();
		} else {
			Assert.assertTrue(false, "No.of attempts provided by user does not match with message in Confirm policy.");
		}
		String Actual_SuccessMsg = PopUpMsg.getText();
		String Expected_SuccessMsg = Messages.updatePasswordPolicy;
		System.out.println("Actual Message after password policy update:" + Actual_SuccessMsg);
		System.out.println("Expected Message after password policy update:" + Expected_SuccessMsg);
		Assert.assertEquals(Actual_SuccessMsg, Expected_SuccessMsg, "Password policy not updated.");
		//WebElement user_profile = driver.findElement(By.xpath("//span[text()='" + prop.getProperty("username_purge") + "']"));
		userProfileTab.click();
		Thread.sleep(2000);
		signOutBtn.click();
		// For verifying attempts by logging in
		for (int i = 1; i <= NoOfAttempt; i++) {
			if (i < NoOfAttempt) {
				loginpageta.login(prop.getProperty("username_respToTA"), prop.getProperty("password_respToTA_invalid"));
				String Actual_Msg = PopUpMsg.getText();
				System.out.println("Actual_msg:" + Actual_Msg);
				if (Actual_Msg.contentEquals("You have made [" + i
						+ "] unsuccessful attempt(s). The maximum retry attempts allowed for login are [" + NoOfAttempt
						+ "]")) {
					System.out.println(i + "th Unsuccessful attempt");
					// Assert.assertTrue(true);
				}
				for (int m = 0; m < 10; m++) {
					username.sendKeys(Keys.BACK_SPACE);
					password.sendKeys(Keys.BACK_SPACE);
					Thread.sleep(3000);
				}
			} else if (i == NoOfAttempt) {
				System.out.println(i + "th Unsuccessful attempt");
				loginpageta.login(prop.getProperty("username_respToTA"), prop.getProperty("password_respToTA_invalid"));
				String Actual_Fail = PopUpMsg.getText();
				System.out.println("Actual_msg:" + Actual_Fail);
				String Expected_Fail = Messages.failPasswordPolicy;
				System.out.println("Expected_msg:" + Expected_Fail);
				Assert.assertEquals(Actual_Fail, Expected_Fail);
			}
		}

	}
	
	public void validateUnlockTenantRespectToTA(String UserToUnlock,String newPswd,String cnfPswd) throws Exception {
		loginpageta.login(prop.getProperty("username_TA"), prop.getProperty("password_TA"));
		wait.until(ExpectedConditions.visibilityOf(usersTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", usersTab);
		System.out.println("Users tab clicked");
		wait.until(ExpectedConditions.visibilityOf(tenantUsersTab));
		js.executeScript("arguments[0].click();", tenantUsersTab);
		System.out.println("Tenant Users tab clicked");
		Thread.sleep(2000);
		WebElement user_record=driver.findElement(By.xpath("//table/tr/td/div[text()='"+UserToUnlock+"']/../../td/button"));
		//WebElement user_record=driver.findElement(By.xpath("//table/tr/td/div[text()='"+prop.getProperty("username_respToTA")+"']/../../td/button"));
		//table/tr/td/div[text()='TenantAdmin1']/../../td/span[text()='LOCKED']
//5.6.0-->/table/tr/td/div[text()='TonyKakkar']/../../td/button
		wait.until(ExpectedConditions.elementToBeClickable(user_record));
		user_record.click();
		Thread.sleep(3000);
		Select selectAction_dropdwn=new Select(selectAction);
		selectAction_dropdwn.selectByVisibleText("Unlock User");
		Thread.sleep(2000);
		nextBtn.click();
		Thread.sleep(2000);
		Unlock_newPswd.sendKeys(newPswd);
		Thread.sleep(2000);
		Unlock_cnfPswd.sendKeys(cnfPswd);
		Thread.sleep(2000);
		enableBtn.click();
		String Actual_SuccessMsg = PopUpMsg.getText();
		String Expected_SuccessMsg = Messages.unlock_TA;
		System.out.println("Actual Message after password policy update:" + Actual_SuccessMsg);
		System.out.println("Expected Message after password policy update:" + Expected_SuccessMsg);
		Assert.assertEquals(Actual_SuccessMsg, Expected_SuccessMsg, "Tenant Admin account is not unlocked.");
		informationpageta.validateSignOut();
	}
	
	public void validateTenantLogin(String UnlockUser,String Pswd,String NewPswd,String cnfPswd) throws Exception {
		loginpageta.login(UnlockUser,Pswd);
		Thread.sleep(5000);
		oldPswd.sendKeys(Pswd);
		Thread.sleep(1000);
		newPswd.sendKeys(NewPswd);
		Thread.sleep(1000);
		newConfirmPswd.sendKeys(cnfPswd);
		changeBtn.click();
		Thread.sleep(30000);
		loginpageta.login(UnlockUser,cnfPswd);
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		String actual_pageTitle = homePageTitle.getText();
		System.out.println("User navigated to " + actual_pageTitle + "Tab");
		Assert.assertEquals(actual_pageTitle, "Home", "User is not navigated to tenants tab");
	}

	public void validatePasswordHistory(int pswdHistory) throws Exception {
		loginpageta.login(prop.getProperty("username_pwdH_TA"), prop.getProperty("password_pwdH_TA"));
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(tenantPolicyTab));
		js.executeScript("arguments[0].click();", tenantPolicyTab);
		System.out.println("Tenant Policy tab clicked");
		Thread.sleep(2000);
		pswdPolicyDrpDwn.click();
		// wait.until(ExpectedConditions.visibilityOf(noOfAttempts));
		for (int i = 1; i <= 2; i++) {
			passwordHistoryInput.sendKeys(Keys.BACK_SPACE);
		}
		passwordHistoryInput.sendKeys("" + pswdHistory);
		Thread.sleep(2000);
		saveBtn.click();
		System.out.println(
				"Below validation validates number of password to be checked is equal to what user set in confirm Policy pop-up");
		String confirmHistory = verifyHistory.getText();
		int cnfAtempt = Integer.parseInt(confirmHistory);
		System.out.println(
				"No.of wrong attempts actually set is:" + cnfAtempt + " No.of attempts set by user is:" + pswdHistory);
		if (cnfAtempt == pswdHistory) {
			System.out.println("No. of last password to be checked that actually set is:" + cnfAtempt
					+ " No.of attempts set by user is:" + pswdHistory);
			okBtn.click();
		} else {
			Assert.assertTrue(false,
					"No.of last pswd to be checked provided by user does not match with message in Confirm policy.");
		}
		String Actual_SuccessMsg = PopUpMsg.getText();
		String Expected_SuccessMsg = Messages.updatePasswordPolicy;
		System.out.println("Actual Message after password policy update:" + Actual_SuccessMsg);
		System.out.println("Expected Message after password policy update:" + Expected_SuccessMsg);
		Assert.assertEquals(Actual_SuccessMsg, Expected_SuccessMsg, "Password policy not updated.");
		// different conditions
		// Click on usersTab and change Password
		for (int i = 1; i <= pswdHistory; i++) {

			wait.until(ExpectedConditions.visibilityOf(UserNameTab));
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("arguments[0].click();", UserNameTab);
			Thread.sleep(2000);
			changePswd.click();
			if (i == 1) {
				oldPswd.sendKeys(prop.getProperty("password_pwdH_TA"));// Pune@0
				System.out.println(prop.getProperty("password_pwdH_TA"));
				Thread.sleep(1000);
				newPswd.sendKeys(prop.getProperty("password_new_TA") + i);// Pune@01
				System.out.println(prop.getProperty("password_new_TA") + i);
				Thread.sleep(1000);
				newConfirmPswd.sendKeys(prop.getProperty("password_cnf_TA") + i);// Pune@01
				System.out.println(prop.getProperty("password_cnf_TA") + i);
				Thread.sleep(1000);
				changeBtn.click();
				Thread.sleep(3000);
			/*	String Actual_successMsg = PopUpMsg.getText();
				System.out.println("Success Message after changing password in" + i + "time: " + Actual_successMsg);
				String Expected_successMsg = Messages.changePassword;
				Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Password not changed");
				Thread.sleep(5000);*/
			} else if (i < pswdHistory) {
				oldPswd.sendKeys(prop.getProperty("password_pwdH_TA") + (i - 1));// i=2;Pune@01//i=3;Pune@02
				System.out.println(prop.getProperty("password_pwdH_TA") + (i - 1));
				Thread.sleep(1000);
				newPswd.sendKeys(prop.getProperty("password_new_TA") + i);// Pune@02//Pune@03
				System.out.println(prop.getProperty("password_new_TA") + i);
				Thread.sleep(1000);
				newConfirmPswd.sendKeys(prop.getProperty("password_cnf_TA") + i);// Pune@02//Pune@03
				System.out.println(prop.getProperty("password_cnf_TA") + i);
				Thread.sleep(1000);
				changeBtn.click();
				Thread.sleep(3000);
			/*	String Actual_successMsg = PopUpMsg.getText();
				System.out.println("Success Message after changing password in" + i + "time: " + Actual_successMsg);
				String Expected_successMsg = Messages.changePassword;
				Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Password not changed");
				Thread.sleep(5000);*/
			} else if (i == pswdHistory) {// i=4;
				oldPswd.sendKeys(prop.getProperty("password_pwdH_TA") + (i - 1));// i=4;Pune@03
				System.out.println(prop.getProperty("password_pwdH_TA") + (i - 1));
				Thread.sleep(1000);
				newPswd.sendKeys(prop.getProperty("password_new_TA"));// Pune@0
				System.out.println(prop.getProperty("password_new_TA"));
				Thread.sleep(1000);
				newConfirmPswd.sendKeys(prop.getProperty("password_cnf_TA"));// Pune@0
				System.out.println(prop.getProperty("password_cnf_TA"));
				Thread.sleep(1000);
				changeBtn.click();
				Thread.sleep(3000);
				String Actual_successMsg1 = PopUpMsg.getText();
				System.out.println("Success Message after changing password in " + i + "time: " + Actual_successMsg1);
				String Expected_successMsg1 = "Your new password must be different from your previous [" + i
						+ "] passwords.";
				Assert.assertEquals(Actual_successMsg1, Expected_successMsg1);
			}
		}
	}

	public void validatePswdComplexity(int minLength, int maxLength, int upperCase, int lowerCase, int specialChar,
			int digitsIp, String invalidPswd) throws Exception {
		// issue here is we cannot clear the text in url as well as hours
		loginpageta.login(prop.getProperty("username_TA"), prop.getProperty("password_TA"));
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(tenantPolicyTab));
		js.executeScript("arguments[0].click();", tenantPolicyTab);
		System.out.println("Tenant Policy tab clicked");
		Thread.sleep(2000);
		pswdPolicyDrpDwn.click();
		// wait.until(ExpectedConditions.visibilityOf(noOfAttempts));
		for (int i = 1; i <= 2; i++) {
			minLengthInput.sendKeys(Keys.BACK_SPACE);
			maxLengthInput.sendKeys(Keys.BACK_SPACE);
			uppercaseInput.sendKeys(Keys.BACK_SPACE);
			lowercaseInput.sendKeys(Keys.BACK_SPACE);
			specialCharInput.sendKeys(Keys.BACK_SPACE);
			digitsInput.sendKeys(Keys.BACK_SPACE);
		}
		minLengthInput.sendKeys("" + minLength);
		maxLengthInput.sendKeys("" + maxLength);
		uppercaseInput.sendKeys("" + upperCase);
		lowercaseInput.sendKeys("" + lowerCase);
		specialCharInput.sendKeys("" + specialChar);
		digitsInput.sendKeys("" + digitsIp);
		Thread.sleep(2000);
		saveBtn.click();
		List<WebElement> elements = driver.findElements(By.xpath("//div[@class='card-body']/ul/ul/li"));
		ArrayList<String> Output_Attempts = new ArrayList<String>();
		for (WebElement element : elements) {
			String element_value = element.getText();
			// System.out.println(element_value);
			Output_Attempts.add(element_value);
		}
		System.out.println("Expected Arraylist is:" + Output_Attempts);
		okBtn.click();
		Thread.sleep(3000);
		String Actual_SuccessMsg = PopUpMsg.getText();
		String Expected_SuccessMsg = Messages.updatePasswordPolicy;
		System.out.println("Actual Message after password policy update:" + Actual_SuccessMsg);
		System.out.println("Expected Message after password policy update:" + Expected_SuccessMsg);
		Assert.assertEquals(Actual_SuccessMsg, Expected_SuccessMsg, "Password policy not updated.");
		System.out.println(
				"Till now we set the criteria for password and now we are verifying the same while changing pwd");
		wait.until(ExpectedConditions.visibilityOf(UserNameTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", UserNameTab);
		Thread.sleep(2000);
		changePswd.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		oldPswd.sendKeys(prop.getProperty("password_TA"));
		// System.out.println(prop.getProperty("password_complexity"));
		Thread.sleep(3000);
		newPswd.sendKeys(invalidPswd);
		// System.out.println(invalidPswd);
		Thread.sleep(3000);
		newConfirmPswd.click();
		List<WebElement> cp_elements = driver.findElements(By.xpath("//div[@class='error']/div/ul/li"));
		wait.until(ExpectedConditions.visibilityOfAllElements(cp_elements));
		ArrayList<String> error_Attempts = new ArrayList<String>();
		for (WebElement cp_element : cp_elements) {
			String cpelement_value = cp_element.getText();
			// System.out.println(cpelement_value);
			error_Attempts.add(cpelement_value);
			Thread.sleep(2000);
		}
		System.out.println("Values in arrayList Before:" + error_Attempts);
		// WebElement cp_psw =
		// driver.findElement(By.xpath("//div[@class='error']/div"));
		// String pswd_criteria = cp_psw.getText();
		String pswd_criteria = "Password length should be between " + minLength + "-" + maxLength;
		error_Attempts.add(4, pswd_criteria);
		System.out.println("Values in arrayList After:" + error_Attempts);
		System.out.println(
				"Compare thye arraylist in confirm policy popup with arraylist having error msg on change password");
		if (Output_Attempts.equals(error_Attempts)) {
			Assert.assertTrue(true, "Validation done");
		} else {
			Assert.assertTrue(false, "Validation Fail");
		}

	}
}



