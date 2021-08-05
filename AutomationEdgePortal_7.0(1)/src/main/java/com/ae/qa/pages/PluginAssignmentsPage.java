package com.ae.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.util.Messages;

public class PluginAssignmentsPage extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 220);
	public LoginPage loginpage = new LoginPage();
	public WebElements webelements = new WebElements();
	public InformationPage informationpage=new InformationPage();

	@FindBy(xpath = "//span[(text()='Plugins')]")
	WebElement pluginsTab;
	@FindBy(xpath = "//a[text()='Plugin Assignments']")
	WebElement pluginAssignmentsTab;
	@FindBy(id = "pluginRadio")
	WebElement pluginRadioBtn;
	@FindBy(id = "tenantRadio")
	WebElement tenantRadioBtn;
	@FindBy(id = "dropDownElement")
	WebElement pluginList;
	@FindBy(id = "edit-button")
	WebElement editBtn;
	@FindBy(id = "select-all")
	WebElement selectAllTenant;
	@FindBy(id = "popup-button-ok")
	WebElement okBtn;
	@FindBy(xpath = "//button[@id='uploadButton']/span")
	WebElement saveBtn;
	@FindBy(xpath = "//p[@class='alert-message-text']")
	WebElement success_Message;

	public PluginAssignmentsPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions
	public void validateAssignPluginSingleTenant(String pluginName, String tenanOrgCode) throws InterruptedException {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Plugins Tab
		wait.until(ExpectedConditions.visibilityOf(pluginsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", pluginsTab);
		// click on pluginAssignments Tab
		wait.until(ExpectedConditions.visibilityOf(pluginAssignmentsTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", pluginAssignmentsTab);
		Thread.sleep(3000);
		// Verify if pluginRadio btn is already selected
		if (!pluginRadioBtn.isSelected()) {
			pluginRadioBtn.click();
		} else {
			System.out.println("Plugin Radio Btn is already selected");
		}
		
		Select plugin_dropdwn = new Select(pluginList);
		plugin_dropdwn.selectByValue(pluginName);
		Thread.sleep(3000);
		editBtn.click();
		Thread.sleep(7000);
		// verify if all tenant org code is selected-deselect it first and then select
		// single tenant
		if (selectAllTenant.isSelected()) {
			selectAllTenant.click();
			System.out.println(
					"Tenant orgcode select all checkbox deselected and now please select single tenant org code");
		} else {
			for (int i = 0; i < 2; i++) {
				selectAllTenant.click();
			}
			System.out.println("Please select single tenant org code");
		}
		okBtn.click();
		Thread.sleep(2000);
		WebElement tenant_Orgcode = driver.findElement(By.xpath("//input[@id='select-" + tenanOrgCode + "']"));
		tenant_Orgcode.click();
		Thread.sleep(2000);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", saveBtn);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String Actual_successMsg = success_Message.getText();
		System.out.println("Actual Sucess Message" + Actual_successMsg);
		String Expected_successMsg = Messages.assignPluginSingleTenant ;
		System.out.println("Expected Sucess Message" + Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Plugin not assigned to single tenant.");
		Reporter.log("Plugin assigned to single tenant",true);
		informationpage.validateSignOut();
	}

	public void validateAssignPluginAllTenant(String pluginName) throws InterruptedException {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Plugins Tab
		wait.until(ExpectedConditions.visibilityOf(pluginsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", pluginsTab);
		// click on pluginAssignments Tab
		wait.until(ExpectedConditions.visibilityOf(pluginAssignmentsTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", pluginAssignmentsTab);
		Thread.sleep(3000);
		// Verify if pluginRadio btn is already selected
		if (!pluginRadioBtn.isSelected()) {
			pluginRadioBtn.click();
		} else {
			System.out.println("Plugin Radio Btn is already selected");
		}
		Select plugin_dropdwn = new Select(pluginList);
		plugin_dropdwn.selectByValue(pluginName);
		Thread.sleep(3000);
		editBtn.click();
	wait.until(ExpectedConditions.visibilityOf(selectAllTenant));
		// verify if all tenant org code is selected-deselect it first and then select
		// single tenant
		if (!selectAllTenant.isSelected()) {
			selectAllTenant.click();
			System.out.println(
					"All tenant selected");
		} else {
			System.out.println("all tenant already selected");
		}
		// okBtn.click();
		Thread.sleep(2000);
		// WebElement
		// tenant_Orgcode=driver.findElement(By.xpath("//input[@id='select-"+tenanOrgCode+"']"));
		// tenant_Orgcode.click();
		// Thread.sleep(2000);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", saveBtn);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String Actual_successMsg = success_Message.getText();
		System.out.println("Actual Sucess Message" + Actual_successMsg);
		String Expected_successMsg=Messages.assignPluginSingleTenant;
		System.out.println("Expected Sucess Message" + Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Plugin not assigned to all tenant.");
		Reporter.log("Plugin assigned to all tenant successfully",true);
		informationpage.validateSignOut();
	}
	
	public void validateAssignPluginToMultipleTenants(String pluginName, String tenantOrgCode1,String tenantOrgCode2,String tenantOrgCode3) throws InterruptedException {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Plugins Tab
		wait.until(ExpectedConditions.visibilityOf(pluginsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", pluginsTab);
		// click on pluginAssignments Tab
		wait.until(ExpectedConditions.visibilityOf(pluginAssignmentsTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", pluginAssignmentsTab);
		Thread.sleep(3000);
		// Verify if pluginRadio btn is already selected
		if (!pluginRadioBtn.isSelected()) {
			pluginRadioBtn.click();
		} else {
			System.out.println("Plugin Radio Btn is already selected");
		}
		
		Select plugin_dropdwn = new Select(pluginList);
		plugin_dropdwn.selectByValue(pluginName);
		Thread.sleep(3000);
		editBtn.click();
		Thread.sleep(7000);
		// verify if all tenant org code is selected-deselect it first and then select
		// single tenant
		if (selectAllTenant.isSelected()) {
			selectAllTenant.click();
			System.out.println(
					"Tenant orgcode select all checkbox deselected and now please select multiple tenant org code");
		} else {
			for (int i = 0; i < 2; i++) {
				selectAllTenant.click();
			}
			System.out.println("Please select Multiple tenants org code");

		}
		okBtn.click();
		Thread.sleep(2000);
		WebElement tenant_Orgcode1 = driver.findElement(By.xpath("//input[@id='select-" + tenantOrgCode1 + "']"));
		tenant_Orgcode1.click();
		Thread.sleep(2000);
		WebElement tenant_Orgcode2 = driver.findElement(By.xpath("//input[@id='select-" + tenantOrgCode2 + "']"));
		tenant_Orgcode2.click();
		Thread.sleep(2000);
		WebElement tenant_Orgcode3 = driver.findElement(By.xpath("//input[@id='select-" + tenantOrgCode3 + "']"));
		tenant_Orgcode3.click();
		Thread.sleep(2000);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", saveBtn);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String Actual_successMsg = success_Message.getText();
		System.out.println("Actual Sucess Message" + Actual_successMsg);
		String Expected_successMsg = Messages.assignPluginSingleTenant ;
		System.out.println("Expected Sucess Message" + Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Plugin not assigned to multiple tenants.");
		Reporter.log("Plugin assigned to multiple tenant",true);
		informationpage.validateSignOut();

	}
	
	public void validateAssignTenantSinglePlugin(String tenantCode,String pluginName) throws InterruptedException {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Plugins Tab
		wait.until(ExpectedConditions.visibilityOf(pluginsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", pluginsTab);
		// click on pluginAssignments Tab
		wait.until(ExpectedConditions.visibilityOf(pluginAssignmentsTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", pluginAssignmentsTab);
		wait.until(ExpectedConditions.visibilityOf(tenantRadioBtn));
		// Verify if pluginRadio btn is already selected
		if (!tenantRadioBtn.isSelected()) {
			tenantRadioBtn.click();
		} else {
			Reporter.log("Tenant Radio Btn is already selected",true);
		}
		Thread.sleep(5000);
		Select tenant_dropdwn = new Select(pluginList);
		tenant_dropdwn.selectByValue(tenantCode);
		Thread.sleep(5000);
		editBtn.click();
		wait.until(ExpectedConditions.visibilityOf(selectAllTenant));
		// verify if all plugins are selcted if yes then deselct and then select single plugin which needs to assign
		if (selectAllTenant.isSelected()) {
			Reporter.log("If all plugins are already selected then we need to deslect it first",true);
			selectAllTenant.click();
			okBtn.click();
			Reporter.log(
					"Plugin name select all checkbox deselected and now please select single Plugin",true);
		} else {
			for (int i = 0; i < 2; i++) {
				selectAllTenant.click();
			}
			Reporter.log("Please select single plugin",true);
		}
		Thread.sleep(2000);
		WebElement plugin_Name = driver.findElement(By.xpath("//input[@id='select-" + pluginName + "']"));
		if(!plugin_Name.isSelected()) {
		plugin_Name.click();
		} else {
			System.out.println(pluginName+" is already selected");
		}
		Thread.sleep(5000);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", saveBtn);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String Actual_successMsg = success_Message.getText();
		System.out.println("Actual Sucess Message" + Actual_successMsg);
		String Expected_successMsg = Messages.assignPluginSingleTenant ;
		System.out.println("Expected Sucess Message" + Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Single Plugin not assigned to desired tenant.");
		Reporter.log("Single plugin assigned to desired tenant",true);
		informationpage.validateSignOut();
	}
	public void validateAssignTenantMultiplePlugins(String TenantCode, String PluginName1,String PluginName2) throws Exception {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Plugins Tab
		wait.until(ExpectedConditions.visibilityOf(pluginsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", pluginsTab);
		// click on pluginAssignments Tab
		wait.until(ExpectedConditions.visibilityOf(pluginAssignmentsTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", pluginAssignmentsTab);
		wait.until(ExpectedConditions.visibilityOf(tenantRadioBtn));
		// Select tenant radio button
			tenantRadioBtn.click();
			Reporter.log("Tenant Radio Btn is selected",true);
		Thread.sleep(5000);
		Select tenant_dropdwn = new Select(pluginList);
		tenant_dropdwn.selectByValue(TenantCode);
		Thread.sleep(5000);
		editBtn.click();
		wait.until(ExpectedConditions.visibilityOf(selectAllTenant));
		// verify if all plugins are selected if yes,deselect and then select specific ones
		if (selectAllTenant.isSelected()) {
			Reporter.log("If all plugins are already selected then we need to deselect it first",true);
			selectAllTenant.click();
			okBtn.click();
			Reporter.log(
					"Plugin name select all checkbox deselected and now please specific Plugins which needs to assign tenant",true);
		} else {
			for (int i = 0; i < 2; i++) {
				selectAllTenant.click();
			}
			Reporter.log("Please select specific/multiple plugins",true);
		}
		Thread.sleep(2000);
		WebElement plugin_Name1 = driver.findElement(By.xpath("//input[@id='select-" + PluginName1 + "']"));
		Reporter.log(PluginName1+" is selected", true);
	/*	if(!plugin_Name1.isSelected()) {
		plugin_Name1.click();
		} else {
			System.out.println(PluginName1+" is already selected");
		}*/
		Thread.sleep(5000);
		WebElement plugin_Name2 = driver.findElement(By.xpath("//input[@id='select-" + PluginName2 + "']"));
		Reporter.log(PluginName2+" is selected", true);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", saveBtn);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String Actual_successMsg = success_Message.getText();
		System.out.println("Actual Sucess Message" + Actual_successMsg);
		String Expected_successMsg = Messages.assignPluginSingleTenant ;
		System.out.println("Expected Sucess Message" + Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Single Plugin not assigned to desired tenant.");
		Reporter.log("Single plugin assigned to desired tenant",true);
		informationpage.validateSignOut();
	}
	public void validateAssignTenantAllPlugins(String TenantCode) throws Exception {
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		Reporter.log("User log in Successfully",true);
		// click Plugins Tab
		wait.until(ExpectedConditions.visibilityOf(pluginsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", pluginsTab);
		// click on pluginAssignments Tab
		wait.until(ExpectedConditions.visibilityOf(pluginAssignmentsTab));
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].click();", pluginAssignmentsTab);
		wait.until(ExpectedConditions.visibilityOf(tenantRadioBtn));
		// Select tenant radio button
			tenantRadioBtn.click();
			Reporter.log("Tenant Radio Btn is selected",true);
		Thread.sleep(5000);
		Select tenant_dropdwn = new Select(pluginList);
		tenant_dropdwn.selectByValue(TenantCode);
		Thread.sleep(5000);
		editBtn.click();
		wait.until(ExpectedConditions.visibilityOf(selectAllTenant));
		// verify if all plugins are selected if yes,deselect and then select specific ones
		if (!selectAllTenant.isSelected()) {
			selectAllTenant.click();
			Reporter.log("All Plugins selected",true);
		} else {
			Reporter.log("all plugins are already selected",true);
		}
		Thread.sleep(5000);
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].click();", saveBtn);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String Actual_successMsg = success_Message.getText();
		System.out.println("Actual Sucess Message" + Actual_successMsg);
		String Expected_successMsg = Messages.assignPluginSingleTenant ;
		System.out.println("Expected Sucess Message" + Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "All Plugins are not assigned to desired tenant.");
		Reporter.log("All plugins are assigned to desired tenant",true);
		informationpage.validateSignOut();
		
	}
}
