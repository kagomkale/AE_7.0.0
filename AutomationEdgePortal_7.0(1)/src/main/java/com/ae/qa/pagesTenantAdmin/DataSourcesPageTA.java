package com.ae.qa.pagesTenantAdmin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.openqa.selenium.support.ui.Select;
import com.ae.qa.base.TestBase;
import com.ae.qa.util.Messages;
//import com.codoid.products.fillo.Select;

public class DataSourcesPageTA extends TestBase {
	public LoginPageTA loginpage = new LoginPageTA();
	public static WebDriverWait wait = new WebDriverWait(driver, 300);
	public InformationPageTA Info = new InformationPageTA();
	@FindBy(xpath = "//*[@id='reports-menuitem']/a/span")
	WebElement reportsTab;
	@FindBy(xpath = "//*[@id='reports-submenu']/ul/li[2]/a")
	WebElement datasourceTab;
	@FindBy(xpath = "//*[@id='main-component']/ng-component/div[1]/div/div/button")
	WebElement addBtn;
	@FindBy(id ="databaseType")
	WebElement database_drpdown;
	@FindBy(xpath = "//*[@id='name']")
	WebElement datasourceName;
	@FindBy(xpath = "//*[@id='connectionString']")
	WebElement ConnectionString;
	@FindBy(xpath = "//*[@id='username']")
	WebElement username;
	@FindBy(xpath = "//*[@id='password']")
	WebElement Password;
	@FindBy(xpath = "//*[@id='datasourceModal']/div/div/form/div[2]/button[1]")
	WebElement button;
	@FindBy(xpath = "//*[@id='datasourceModal']/div/div/form/div[2]/button[2]")
	WebElement Create;
	@FindBy(xpath = "//div/p[@class='alert-message-text']")
	WebElement successMsgBox;

	public DataSourcesPageTA() {
		PageFactory.initElements(driver, this);
	}

	public void configDataSource(String dbType, String datasourcename, String connectionString, String userName, String password)
			throws Exception {
		loginpage.login(prop.getProperty("username_TA"), prop.getProperty("password_TA"));
		Reporter.log("User LogIn Succesfully", true);
		wait.until(ExpectedConditions.visibilityOf(reportsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", reportsTab);
		Reporter.log("Report tab clicked",true);
		wait.until(ExpectedConditions.visibilityOf(datasourceTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", datasourceTab);
		Reporter.log("DataSource  tab clicked", true);
		Thread.sleep(4000);
		addBtn.click();
		Thread.sleep(3000);
		Reporter.log("Data-Source Form opened",true);
		Select database_Type=new Select(database_drpdown);
		database_Type.selectByVisibleText(dbType);
		Thread.sleep(3000);
		datasourceName.sendKeys(datasourcename);
		Thread.sleep(3000);
		ConnectionString.sendKeys(connectionString);
		Thread.sleep(3000);
		username.sendKeys(userName);
		Thread.sleep(3000);
		Password.sendKeys(password);
		Thread.sleep(3000);
		button.click();
		Reporter.log("Button Clicked",true);
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String Actual_testMsg = successMsgBox.getText();
		String Expected_testMsg = Messages.tstdataSource1;
		System.out.println("Actual datsource-Test message is :" + Actual_testMsg);
		System.out.println("Expected datasource-test message is :" + Expected_testMsg);
		Assert.assertEquals(Actual_testMsg, Expected_testMsg, "Test Connection failed");
		Thread.sleep(10000);
		Create.click();
		Thread.sleep(2000);
		Reporter.log("Create button is clicked.", true);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String Actual_CreateMsg = successMsgBox.getText();
		String Expected_CreateMsg = Messages.tstdataSource;
		System.out.println("Actual created message is :" + Actual_CreateMsg);
		System.out.println("Expected Created message is :"+ Expected_CreateMsg);
		Assert.assertEquals(Actual_CreateMsg, Expected_CreateMsg, "Datasource configuration failed");
		Reporter.log("Datsource created succesfully", true);
		Info.validateSignOut();
	}
}
