package com.ae.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;

public class PluginsPage extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 180);
	public LoginPage loginpage = new LoginPage();
	public WebElements webelements = new WebElements();
	public InformationPage informationpage=new InformationPage();

	@FindBy(xpath = "//span[(text()='Plugins')]")
	WebElement pluginsTab;
	@FindBy(name = "upload-zip")
	WebElement uploadZipBtn;
	@CacheLookup
	@FindBy(id = "pluginsFile")
	WebElement chooseFile;
	@FindBy(xpath = "//input[@id='pluginsFile']")
	WebElement chooseFileFromLocation;
	@FindBy(id = "uploadButton")
	WebElement uploadBtn;
	@FindBy(id = "select-all")
	WebElement upgradeAllBox;
	@FindBy(id = "select-all-assignment")
	WebElement assignAllBox;
	@FindBy(xpath = "//button[@name='submit']")
	WebElement saveBtn;
	@FindBy(xpath = "//p[@class='alert-message-text']")
	WebElement alertMessage;
	@FindBy(xpath = "//table/tr[2]/td[5]/span")
	WebElement licenseStatus;

	public PluginsPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions
	public void validateUploadPlugins() throws InterruptedException {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Plugins Tab
		wait.until(ExpectedConditions.visibilityOf(pluginsTab));
		JavascriptExecutor js_tenant = (JavascriptExecutor) driver;
		js_tenant.executeScript("arguments[0].click();", pluginsTab);
		Reporter.log("Plugins Tab is selected",true);
		// Click Upload zip button
		wait.until(ExpectedConditions.visibilityOf(uploadZipBtn));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", uploadZipBtn);
		Reporter.log("Upload zip button clicked",true);
		Thread.sleep(2000);
		//choose file from location
		chooseFileFromLocation.sendKeys(prop.getProperty("uploadPluginFile"));
		Thread.sleep(3000);
		uploadBtn.click();
		Reporter.log("Plugin zip started uploading",true);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", upgradeAllBox);
		Reporter.log("Upgrade all plugin checkbox is selected",true);
		Thread.sleep(1000);
		assignAllBox.click();
		Reporter.log("Assign to all checkbox is selected",true);
		Thread.sleep(1000);
		saveBtn.click();
		Reporter.log("Save button is selected",true);
		Thread.sleep(6000);
		informationpage.validateSignOut();
	}
}
