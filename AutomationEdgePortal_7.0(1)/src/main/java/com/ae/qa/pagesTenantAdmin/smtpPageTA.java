package com.ae.qa.pagesTenantAdmin;

import java.util.concurrent.TimeUnit;

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
import com.ae.qa.pages.LoginPage;
import com.ae.qa.util.Messages;

public class smtpPageTA extends TestBase {
	public LoginPageTA loginpageta = new LoginPageTA();
	public static WebDriverWait wait = new WebDriverWait(driver, 120);
	public InformationPageTA informationpageta=new InformationPageTA();
	@FindBy(xpath = "//span[(text()='Settings')]")
	WebElement settingsTab;
	@FindBy(xpath = "//li/a[contains(text(),'SMTP')]")
	WebElement smtpTab;
	@FindBy(xpath = "//button[@name='add-new']")
	WebElement addSmtpBtn;
	@FindBy(id = "host")
	WebElement hostName;
	@FindBy(id = "port")
	WebElement portNo;
	@FindBy(id = "authenticate")
	WebElement authenticateCheckbx;
	@FindBy(id = "smtpUserName")
	WebElement smtpUserName;
	@FindBy(id = "smtpPassword")
	WebElement smtpPassword;
	@FindBy(id = "encryptionType")
	WebElement encryptionTypeDrpDwn;
	@FindBy(id = "smtpPersonalName")
	WebElement personalName;
	@FindBy(name = "test-smtp")
	WebElement testConnectionBtn;
	@FindBy(xpath = "//div/p[@class='alert-message-text']")
	WebElement successMsgBox;
	@FindBy(xpath = "//button[@name='save'and @type='submit']")
	WebElement saveBtn;

	public smtpPageTA() {
		PageFactory.initElements(driver, this);
	}

	public void validateSetSmtpServer(String hname, String portno, String uName, String pswd, String encrypType,
			String pName) throws Exception {
		loginpageta.login(prop.getProperty("username_TA"), prop.getProperty("password_TA"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", settingsTab);
		Reporter.log("Settings tab clicked",true);
		wait.until(ExpectedConditions.visibilityOf(smtpTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", smtpTab);
		Reporter.log("SMTP tab clicked",true);
		Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(addSmtpBtn)); 
        JavascriptExecutor js_addBtn=(JavascriptExecutor)driver;
        js_addBtn.executeScript("arguments[0].click();", addSmtpBtn);
       // Reporter.log("Add smtp configuration",true);
		Reporter.log("Add smtp config button clicked",true);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		hostName.sendKeys(hname);
		Thread.sleep(2000);
		portNo.sendKeys(portno);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if (!authenticateCheckbx.isSelected()) {
			authenticateCheckbx.click();
		} else {
			System.out.println("Authenticate checkbox is already selected.");
		}
		smtpUserName.sendKeys(uName);
		Thread.sleep(2000);
		smtpPassword.sendKeys(pswd);
		Thread.sleep(2000);
		Select encryption_DropDown = new Select(encryptionTypeDrpDwn);
		encryption_DropDown.selectByVisibleText(encrypType);
		Thread.sleep(2000);
		personalName.sendKeys(pName);
		Thread.sleep(2000);
		testConnectionBtn.click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		String Actual_testConnectionMsg = successMsgBox.getText();
		String Expected_testConnectionMsg = "SMTP test connection successful";
		Assert.assertEquals(Actual_testConnectionMsg, Expected_testConnectionMsg, "Test connection Failed");
		Reporter.log("Tested connection successfully",true);
		Thread.sleep(6000);
		saveBtn.click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		String Actual_smtpConnectionMsg = successMsgBox.getText();
		String Expected_smtpConnectionMsg = Messages.addSmtpServer;
		Assert.assertEquals(Actual_smtpConnectionMsg, Expected_smtpConnectionMsg, "SMTP configuration Failed");
		Reporter.log("Smtp configured successfully",true);
		informationpageta.validateSignOut();

	}
}
