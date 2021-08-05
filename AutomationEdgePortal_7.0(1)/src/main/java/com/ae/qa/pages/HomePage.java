package com.ae.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;

public class HomePage extends TestBase {
	public LoginPage loginpage = new LoginPage();
	public static WebDriverWait wait = new WebDriverWait(driver, 600);
	public InformationPage informationpage=new InformationPage();
	@FindBy(xpath="//div[@id='menu-search']/input")
	WebElement SearchMenu;
	@FindBy(xpath = "//span[(text()='Settings')]")
	WebElement settingsTab;
	
	public void validateSearchFunctionality() throws Exception {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
	//	wait.until(ExpectedConditions.visibilityOf(SearchMenu));
	//	JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].value='Users';",SearchMenu);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	//	wait.until(ExpectedConditions.visibilityOf(sysadminPolicyTab));
		//js.executeScript("arguments[0].click();", sysadminPolicyTab);
	//	System.out.println("Sysadmin Policy tab clicked");*/
		SearchMenu.sendKeys("Üsers");
		System.out.println("Search menu clicked");
		Thread.sleep(6000);
	}
}
