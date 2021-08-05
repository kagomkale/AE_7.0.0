package com.ae.qa.pages;

import java.util.ArrayList;
import java.util.List;
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

public class IntegrationServicesPage extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 220);
	public LoginPage loginpage = new LoginPage();
	public WebElements webelements = new WebElements();
	public InformationPage informationpage=new InformationPage();

	@FindBy(xpath = "//span[(text()='Integration')]")
	WebElement integrationTab;
	@FindBy(xpath = "//a[text()='Services']")
	WebElement servicesTab;
	@FindBy(xpath="//button[@name='new-req']/span")
	WebElement addBtn;
	@FindBy(id="name")
	WebElement Name;
	@FindBy(id="updateConfJobLowerLimitMinutes")
	WebElement ConfJobLowerLimit;
	@FindBy(id="updateResponseJobLowerLimitSeconds")
	WebElement ResponseJobLowerLimit ;
	@FindBy(id="1-machineName")
	WebElement machineName;
	@FindBy(id="1-userName")
	WebElement userName;
	@FindBy(id="1-clusterIP")
	WebElement hostName;
	@FindBy(id="1-clusterName")
	WebElement clusterPort;
	@FindBy(xpath="//button[@id='submitBtn']/span")
	WebElement createBtn;
	
	
	public IntegrationServicesPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void validateAddIntegrationServices(String serviceName,String confjoblowerlimit ,String responsejoblowerlimit,String machinename,String username,
			String hostname,String clusterport) throws Exception{
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click IntegrationTab Tab
		wait.until(ExpectedConditions.visibilityOf(integrationTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", integrationTab);
		// click on services Tab
		wait.until(ExpectedConditions.visibilityOf(servicesTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", servicesTab);
		wait.until(ExpectedConditions.visibilityOf(addBtn));
		addBtn.click();
		Thread.sleep(3000);
		Name.sendKeys(serviceName);
		Thread.sleep(3000);
		ConfJobLowerLimit.clear();
		Thread.sleep(3000);
		ConfJobLowerLimit.sendKeys(confjoblowerlimit);
		Thread.sleep(3000);
		ResponseJobLowerLimit.clear();
		Thread.sleep(3000);
		ResponseJobLowerLimit.sendKeys(responsejoblowerlimit);
		Thread.sleep(3000);
		machineName.sendKeys(machinename);
		Thread.sleep(3000);
		userName.sendKeys(username);
		Thread.sleep(3000);
		hostName.sendKeys(hostname);
		Thread.sleep(3000);
		clusterPort.clear();
		Thread.sleep(3000);
		clusterPort.sendKeys(clusterport);
		Thread.sleep(5000);
	//	createBtn.click();
	//	js1.executeScript("arguments[0].click();", createBtn);
	//	createBtn.sendKeys(Keys.ENTER);
	    Actions action=new Actions(driver);
		action.moveToElement(createBtn).click().build().perform();
	//	driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		//Now we have to validate whether the service is created or not in table 
		/*ArrayList <String> expected_Record= new ArrayList<String>();
		expected_Record.add(machinename);
		expected_Record.add(username);
		expected_Record.add(hostname);
		expected_Record.add(clusterport);
		System.out.println("Expected record in the table is: "+expected_Record);*/
	//	driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		//Fetch actual values note:-give wait element clickable and try to click somewhere else in the form
		
		ArrayList<String> actual_content = new ArrayList<String>();
		//for(int i=2;i<6;i++) {
	//	List<WebElement> Table_content = driver.findElements(By.xpath("//div[@class='table w-100 mul-options-list']/table/tr/td["+i+"]"));
			List<WebElement> Table_content = driver.findElements(By.xpath("//div[@class='table w-100 mul-options-list']/table/tr[3]/td"));
	//	wait.until(ExpectedConditions.visibilityOfAllElements(Table_content));
		for (WebElement element : Table_content) {
			String element_value = element.getText();
			System.out.println(element_value);
			actual_content.add(element_value);
			//System.out.println(actual_content);
			Thread.sleep(2000);
		 }
		
		System.out.println("Actual content in the table is :" +actual_content);
		Thread.sleep(2000);
		ArrayList <String> expected_Record= new ArrayList<String>();
		expected_Record.add(machinename);
		expected_Record.add(username);
		expected_Record.add(hostname);
		expected_Record.add(clusterport);
		System.out.println("Expected record in the table is: "+expected_Record);
		if(actual_content.equals(expected_Record)) {
			Assert.assertTrue(true);
			Reporter.log("Integration services validation successfully.",true);
		} else {
			Assert.assertTrue(false);
			Reporter.log("Integration services validation failed.",true);
		} 
		informationpage.validateSignOut();
	}

}
