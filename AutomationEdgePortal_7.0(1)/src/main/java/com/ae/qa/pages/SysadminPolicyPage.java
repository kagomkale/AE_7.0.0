package com.ae.qa.pages;

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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.util.Messages;

public class SysadminPolicyPage extends TestBase {
	public LoginPage loginpage = new LoginPage();
	public static WebDriverWait wait = new WebDriverWait(driver, 600);
	public InformationPage informationpage=new InformationPage();
	@FindBy(xpath = "//span[(text()='Settings')]")
	WebElement settingsTab;
	@FindBy(xpath = "//a[text()='Sysadmin Policy']")
	WebElement sysadminPolicyTab;
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
	@FindBy(xpath = "//div[@class='card-body']/ul/li[3]/b")
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

	public SysadminPolicyPage() {
		// this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void validatePswdComplexity(String Username,String Password,String minLength, String maxLength,String upperCase,String lowerCase,String specialChar,
			String digitsIp, String invalidPswd) throws Exception {
		loginpage.login(Username,Password);
		Reporter.log("User log in Successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(sysadminPolicyTab));
		js.executeScript("arguments[0].click();", sysadminPolicyTab);
		System.out.println("Sysadmin Policy tab clicked");
		Thread.sleep(2000);
		pswdPolicyDrpDwn.click();
		// wait.until(ExpectedConditions.visibilityOf(noOfAttempts));
		//clear all criteria first
		for (int i = 1; i <= 2; i++) {
			minLengthInput.sendKeys(Keys.BACK_SPACE);
			maxLengthInput.sendKeys(Keys.BACK_SPACE);
			uppercaseInput.sendKeys(Keys.BACK_SPACE);
			lowercaseInput.sendKeys(Keys.BACK_SPACE);
			specialCharInput.sendKeys(Keys.BACK_SPACE);
			digitsInput.sendKeys(Keys.BACK_SPACE);
		}
		//set new criteria
		minLengthInput.sendKeys(minLength);
		maxLengthInput.sendKeys(maxLength);
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
		oldPswd.sendKeys(Password);
		Thread.sleep(3000);
		newPswd.sendKeys(invalidPswd);
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
	
	public void validatePasswordHistory(String Username,String FT_password,String Password,String pswdHistory1) throws Exception {
		loginpage.ValidateFirstTimeLogin(Username,FT_password,Password);
		driver.navigate().to(prop.getProperty("url"));
		loginpage.login(Username,Password);
		Reporter.log("User log in Successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(sysadminPolicyTab));
		js.executeScript("arguments[0].click();", sysadminPolicyTab);
		System.out.println("Sysadmin Policy tab clicked");
		Thread.sleep(2000);
		pswdPolicyDrpDwn.click();
		// wait.until(ExpectedConditions.visibilityOf(noOfAttempts));
		for (int i = 1; i <= 2; i++) {
			passwordHistoryInput.sendKeys(Keys.BACK_SPACE);
		}
		int pswdHistory=Integer.valueOf(pswdHistory1);
		passwordHistoryInput.sendKeys("" + pswdHistory);
		Thread.sleep(2000);
		saveBtn.click();
		Reporter.log(
				"Below validation validates number of password to be checked is equal to what user set in confirm Policy pop-up",true);
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
		Reporter.log("Password policy updated",true);
		// different conditions
		// Click on usersTab and change Password
		for (int i = 1; i <= pswdHistory; i++) {

			wait.until(ExpectedConditions.visibilityOf(UserNameTab));
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("arguments[0].click();", UserNameTab);
			Thread.sleep(2000);
			changePswd.click();
			if (i == 1) {
				oldPswd.sendKeys(Password);// Pune@0
				System.out.println(Password);
				Thread.sleep(1000);
				newPswd.sendKeys(Password + i);// Pune@01
				System.out.println(Password + i);
				Thread.sleep(1000);
				newConfirmPswd.sendKeys(Password + i);// Pune@01
				System.out.println(Password+ i);
				Thread.sleep(1000);
				changeBtn.click();
				Thread.sleep(15000);
				} 
				else if (i < pswdHistory) {
				oldPswd.sendKeys(Password+ (i - 1));// i=2;Pune@01//i=3;Pune@02
				System.out.println(Password + (i - 1));
				Thread.sleep(1000);
				newPswd.sendKeys(Password+ i);// Pune@02//Pune@03
				System.out.println(Password + i);
				Thread.sleep(1000);
				newConfirmPswd.sendKeys(Password+ i);// Pune@02//Pune@03
				System.out.println(Password + i);
				Thread.sleep(1000);
				changeBtn.click();
				Thread.sleep(15000);
			} else if (i == pswdHistory) {// i=4;
				oldPswd.sendKeys(Password+ (i - 1));// i=4;Pune@03
				System.out.println(Password+ (i - 1));
				Thread.sleep(1000);
				newPswd.sendKeys(Password);// Pune@0
				System.out.println(Password);
				Thread.sleep(1000);
				newConfirmPswd.sendKeys(Password);// Pune@0
				System.out.println(Password);
				Thread.sleep(1000);
				changeBtn.click();
				Thread.sleep(10000);
				String Actual_successMsg1 = PopUpMsg.getText();
				System.out.println("Success Message after changing password in " + i + "time: " + Actual_successMsg1);
				String Expected_successMsg1 = "Your new password must be different from your previous [" + i
						+ "] passwords.";
				System.out.println("Expected Success Message:"+Expected_successMsg1);
				Assert.assertEquals(Actual_successMsg1, Expected_successMsg1);
			}
		}
		Reporter.log("Password History of sysadmin validated successfully");
	//	informationpage.validateSignOut();
	}


	public void validateNoOfAttempts(String Username,String Password,String invalidPwd,String noOfattempt) throws Exception {
		loginpage.login(Username,Password);
		Reporter.log("User log in Successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(sysadminPolicyTab));
		js.executeScript("arguments[0].click();", sysadminPolicyTab);
		Reporter.log("Sysadmin Policy tab clicked",true);
		Thread.sleep(2000);
	    int NoOfAttempt=Integer.parseInt(noOfattempt);
		for (int i = 1; i <= 2; i++) {
			noOfAttempts.sendKeys(Keys.BACK_SPACE);
		}
		noOfAttempts.sendKeys("" + NoOfAttempt);
		Thread.sleep(2000);
		saveBtn.click();
		Reporter.log(
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
		informationpage.validateSignOut();
		// For verifying attempts by logging in
		for (int i = 1; i <= NoOfAttempt; i++) {
			if (i < NoOfAttempt) {
				loginpage.login(Username,invalidPwd);
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
				loginpage.login(Username,invalidPwd);
				String Actual_Fail = PopUpMsg.getText();
				System.out.println("Actual_msg:" + Actual_Fail);
				String Expected_Fail = Messages.failPasswordPolicy;
				System.out.println("Expected_msg:" + Expected_Fail);
				Assert.assertEquals(Actual_Fail, Expected_Fail);
			}
		}
		Reporter.log("No. of attempts is validated successfully",true);
	}

	

}
