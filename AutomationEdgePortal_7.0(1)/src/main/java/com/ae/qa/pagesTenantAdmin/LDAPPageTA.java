package com.ae.qa.pagesTenantAdmin;

import java.util.concurrent.TimeUnit;

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
import com.ae.qa.pages.InformationPage;
import com.ae.qa.pages.LoginPage;
import com.ae.qa.util.Messages;

public class LDAPPageTA extends TestBase {
	public LoginPageTA loginpageta = new LoginPageTA();
	public static WebDriverWait wait=new WebDriverWait(driver,300);
	public InformationPageTA informationpageta=new InformationPageTA();
	@FindBy(xpath="//span[(text()='Settings')]")
	WebElement settingsTab;
	@FindBy(xpath="//li/a[contains(text(),'LDAP')]")
	WebElement ldapTab;
	@FindBy(xpath="//button[@name='add-new']")
	WebElement addLdapBtn;
	@FindBy(id="ldapHost")
	WebElement ldapHostName;
	@FindBy(id="ldapPort")
	WebElement ldapPortNo;
	@FindBy(id="ldapDomain")
	WebElement ldapDomainName;
	@FindBy(id="ldapBase")
	WebElement ldapBaseName;
	@FindBy(name="test-ldap")
	WebElement testConnectionBtn;
	@FindBy(xpath="//div/p[@class='alert-message-text']")
	WebElement successMsgBox;
	@FindBy(xpath="//button[@name='save'and @type='submit']")
	WebElement saveBtn;
	@FindBy(name="update")
	WebElement editBtn;
	
	
	public LDAPPageTA() {
		PageFactory.initElements(driver, this);
	}
	
	public void verifyLdapConfig(String hostName,String portNo,String domainName) throws Exception {
		loginpageta.login(prop.getProperty("username_TA"),prop.getProperty("password_TA"));
		Reporter.log("User log in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", settingsTab);
		System.out.println("Settings tab clicked");
		wait.until(ExpectedConditions.visibilityOf(ldapTab));
		JavascriptExecutor js1=(JavascriptExecutor)driver;
		js1.executeScript("arguments[0].click();", ldapTab);
		Reporter.log("LDAP tab clicked",true);	
		Thread.sleep(5000);
		//wait.until(ExpectedConditions.elementToBeClickable(addLdapBtn));
		addLdapBtn.click();
		Thread.sleep(3000);
		ldapHostName.sendKeys(hostName);
		Thread.sleep(2000);
		ldapPortNo.sendKeys(portNo);
		Thread.sleep(2000);
		ldapDomainName.sendKeys(domainName);
		Thread.sleep(4000);
		testConnectionBtn.click();
		Reporter.log("Test connection button clicked",true);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		String Actual_testMsg=successMsgBox.getText();
		String Expected_testMsg=Messages.testLdap;
		System.out.println("Actual test-connection message is :"+Actual_testMsg+"Expected test-connection message is :"+Expected_testMsg);
		Assert.assertEquals(Actual_testMsg, Expected_testMsg,"Testing LDAP connection failed");
		Thread.sleep(10000);
		saveBtn.click();
		Reporter.log("Save button is clicked.",true);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		String Actual_ldapMsg=successMsgBox.getText();
		String Expected_ldapMsg=Messages.LdapConfig;
		System.out.println("Actual ldap-config message is :"+Actual_ldapMsg+"Expected ldap-config message is :"+Expected_ldapMsg);
		Assert.assertEquals(Actual_ldapMsg,Expected_ldapMsg,"LDAP configuration failed");
		Reporter.log("LDAP configured successfully",true);
		informationpageta.validateSignOut();
	}
	public void verifyEditLdapConfig(String domainName) throws Exception {
		loginpageta.login(prop.getProperty("username_TA"),prop.getProperty("password_TA"));
		Reporter.log("User log in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(settingsTab));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", settingsTab);
		Reporter.log("Settings tab clicked",true);
		wait.until(ExpectedConditions.visibilityOf(ldapTab));
		JavascriptExecutor js1=(JavascriptExecutor)driver;
		js1.executeScript("arguments[0].click();", ldapTab);
		Reporter.log("LDAP tab clicked",true);	
		wait.until(ExpectedConditions.visibilityOf(editBtn));
		editBtn.click();
		Thread.sleep(4000);
		for(int i=0;i<30;i++) {
			ldapDomainName.sendKeys(Keys.BACK_SPACE);
		}
		ldapDomainName.sendKeys(domainName);
		Thread.sleep(4000);
		testConnectionBtn.click();
		Reporter.log("Test connection button clicked",true);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		String Actual_testMsg=successMsgBox.getText();
		String Expected_testMsg=Messages.testLdap;
		System.out.println("Actual test-connection message is :"+Actual_testMsg+"Expected test-connection message is :"+Expected_testMsg);
		Assert.assertEquals(Actual_testMsg, Expected_testMsg,"Testing LDAP connection failed");
		Thread.sleep(10000);
		saveBtn.click();
		Reporter.log("Save button is clicked.",true);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		String Actual_ldapMsg=successMsgBox.getText();
		String Expected_ldapMsg=Messages.LdapConfig;
		System.out.println("Actual ldap-config message is :"+Actual_ldapMsg+"Expected ldap-config message is :"+Expected_ldapMsg);
		Assert.assertEquals(Actual_ldapMsg,Expected_ldapMsg,"LDAP configuration failed");
		Reporter.log("LDAP configuration edited successfully",true);
		informationpageta.validateSignOut();
	}

}
