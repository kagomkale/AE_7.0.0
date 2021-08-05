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
import com.ae.qa.pages.LoginPage;
import com.ae.qa.pages.WebElements;
import com.ae.qa.util.Messages;
import com.aventstack.extentreports.Status;

public class WorkflowListPageTA extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 150);
	public WebElements webelements = new WebElements();
	public LoginPageTA loginpageta = new LoginPageTA();
	public InformationPageTA informationpageta=new InformationPageTA();
	@FindBy(xpath = "//span[text()='Workflows']")
	WebElement workflowsTab;
	@FindBy(xpath = "//a[text()='Workflow List']")
	WebElement workflowListTab;
	@FindBy(xpath = "//button[@name='add-new']/span")
	WebElement importTab;
	@FindBy(id = "workflow_name")
	WebElement workflowName;
	@FindBy(id = "description")
	WebElement wfDescription;
	@FindBy(id = "category_name")
	WebElement wfCategory;
	@FindBy(id = "is_assisted")
	WebElement assistedCheckbox;
	@FindBy(name = "rdpEnabled")
	WebElement enableRDPCheckbox;
	@FindBy(xpath="//fieldset[@class='workflow-fieldset']/div/label//input")
	WebElement ChooseWFToImport;
	@FindBy(name = "submit")
	WebElement createBtn;
	@FindBy(id = "wfPriority")
	WebElement wfPriority;
	@FindBy(id = "expectedCompletionTime")
	WebElement expected_completionTime;
	@FindBy(id = "maxCompletionTime")
	WebElement max_CompletionTime;
	@FindBy(id = "cleanupOldReqHours")
	WebElement cleanupOldReqHours;
	@FindBy(id = "manualExecutionTime")
	WebElement manualExecutionTime;
	@FindBy(id = "manualTimeUnit")
	WebElement manualTimeUnit;
	@FindBy(name = "submit")
	WebElement saveBtn;
	@FindBy(xpath = "//div/p[@class='alert-message-text']")
	WebElement success_msg;
	@FindBy(xpath = "//div/h5[text()='Show file content']")
	WebElement showFileContent;
	@FindBy(xpath = "//div/h5[text()='Content:']")
	WebElement content;
	@FindBy(name = "dropdown-selector")
	WebElement importDrpDwn;
	@FindBy(xpath = "//span[text()='Export']")
	WebElement exportBtn;
	@FindBy(xpath = "//span[@class='mul-dorpdown-button']")
	WebElement selectWf;
	@FindBy(xpath = "//div[@class='right-inner-addon']/input[@name='search']")
	WebElement searchBar;
	@FindBy(id = "isVerified")
	WebElement verifiedCheckbx;
	@FindBy(id = "export-btn")
	WebElement ewfBtn;
	@FindBy(id = "wfIcon")
	WebElement wfIcon;
	@FindBy(xpath = "//form/fieldset[2]/legend[1]/span")
	WebElement emailNotification;
	@FindBy(id = "notifyWfFailure")
	WebElement notifyWfFailureBox;
	@FindBy(id = "notifyLongRunningWf")
	WebElement notifyExceedingTimeBox;
	@FindBy(id = "ROLE_TENANT_ADMIN")
	WebElement roleTA;
	@FindBy(id = "ROLE_WORKFLOW_ADMIN")
	WebElement roleWA;
	@FindBy(id = "toEmail")
	WebElement emailID;
	@FindBy(id = "sendToRequestCreator")
	WebElement reqCreator;
	@FindBy(id = "failureMessage")
	WebElement failMsg;
	@FindBy(id = "isSeqExec")
	WebElement enableSeqExec;
	@FindBy(xpath="//span[@class='text-danger']")
	WebElement smtpEmailNotification;
	@FindBy(id="wfName")
	WebElement WfsList;
	//@FindBy(xpath="//*[@id='options-list']/li[6]/label")
	//WebElement Workflow;
	@FindBy(id="export-btn")
	WebElement ExportBtn;
	@FindBy(xpath="//*[@id='psExportModal']/export-workflow/div/div/form/div[1]")
	WebElement Anywhere;
	@FindBy(xpath="//*[@id='options-list']/li/label")
	WebElement Wfselect;

	// tr/td[contains(text(),'xyz')]/../td/span[@title='Show workflow files']
	public WorkflowListPageTA() {
		PageFactory.initElements(driver, this);
	}

	public void validateImportWorkflow(String wfName, String wfdes, String category, String priority,
			String expTime, String maxTime, String cleanUpHrs, String manExeTime, String tUnit) throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(workflowsTab));
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", workflowsTab);
		Reporter.log("Workflows Tab is clicked",true);
		Thread.sleep(2000);
		importTab.click();
		Reporter.log("Import button clicked",true);
		workflowName.sendKeys(wfName);
		Thread.sleep(3000);
		wfDescription.sendKeys(wfdes);
		Thread.sleep(3000);
		Select wfCategory_drpdown = new Select(wfCategory);
		wfCategory_drpdown.selectByVisibleText(category);
		Thread.sleep(3000);
		if (assistedCheckbox.isSelected()) {
			Reporter.log("assisted Workflow is selected",true);
			assistedCheckbox.click();
			Reporter.log("assisted Workflow is unselected",true);
		} else {
			Reporter.log("Is Assisted Workflow checkbox is unselected");
		}
		Thread.sleep(3000);
		if (enableRDPCheckbox.isSelected()) {
			Reporter.log("Enable RDP checkbox is selected",true);
			enableRDPCheckbox.click();
			Reporter.log("Enable RDP checkbox is unselected",true);
		} else {
			System.out.println("Enable RDP checkbox is unselected");
		}
		Thread.sleep(3000);
		ChooseWFToImport.sendKeys(prop.getProperty("WFToImportPath"));
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("arguments[0].click();", createBtn);
		Reporter.log("Create Button is clicked",true);
		wait.until(ExpectedConditions.visibilityOf(wfPriority));
		Select wfPriority_drpdown = new Select(wfPriority);
		wfPriority_drpdown.selectByVisibleText(priority);
		Reporter.log("Priority is set",true);
		Thread.sleep(2000);
		expected_completionTime.clear();
		expected_completionTime.sendKeys(expTime);
		Reporter.log("Expected Completion Time in Seconds is set",true);
		Thread.sleep(2000);
		max_CompletionTime.clear();
		max_CompletionTime.sendKeys(maxTime);
		Reporter.log("Maximum Completion Time in Seconds is set",true);
		Thread.sleep(2000);
		cleanupOldReqHours.clear();
		cleanupOldReqHours.sendKeys(cleanUpHrs);
		Reporter.log("Cleanup Requests older than Hours fields is set",true);
		Thread.sleep(2000);
		manualExecutionTime.clear();
		manualExecutionTime.sendKeys(manExeTime);
		Reporter.log("Manual Execution Time is set",true);
		wait.until(ExpectedConditions.visibilityOf(manualTimeUnit));
		Select manualTimeUnit_drpdown = new Select(manualTimeUnit);
		manualTimeUnit_drpdown.selectByVisibleText(tUnit);
		Reporter.log("Manual Execution time unit is set",true);
		Thread.sleep(3000);
		String emailActual_message=smtpEmailNotification.getText();
		Reporter.log("Alert Message when user dont configure smtp"+emailActual_message,true);
		String emailExpected_message=Messages.smtpNotConfig;
		Assert.assertEquals(emailActual_message,emailExpected_message, "Not getting correct message when smtp not set");
		Reporter.log("Getting correct message for email notification as smtp not configured",true);
		Thread.sleep(3000);
		saveBtn.click();
		Reporter.log("Save button is clicked",true);
		wait.until(ExpectedConditions.visibilityOf(success_msg));
		String Actual_successMsg = success_msg.getText();
		System.out.println("Actual Message : " + Actual_successMsg);
		String Expected_successMsg = Messages.updateWorkflow;
		System.out.println("Expected Message :"+Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Workflow not updated");
		Reporter.log("Workflow updated",true);
		WebElement sliderToEnableWF=driver.findElement(By.xpath("//table/tr[2]/td[@title='"+wfName+"']/../td[6]/label/span"));
		sliderToEnableWF.click();
		Reporter.log("Workflow is enabled successfully",true);
		Thread.sleep(3000);
		informationpageta.validateSignOut();
	}
	
	public void ValidateUploadInvalidIcon(String wfname, String IconPath) throws Exception{
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(workflowsTab));
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", workflowsTab);
		Reporter.log("Workflows Tab is clicked",true);
		Thread.sleep(3000);
		WebElement editBtn= driver.findElement(By.xpath("//table/tr/td[@title='"+wfname+"']/../td[7]/span[@title='Edit Worklfow']"));
		editBtn.click();
		Reporter.log("Edit button is clicked",true);
		wfIcon.sendKeys(IconPath);
		wait.until(ExpectedConditions.visibilityOf(success_msg));
		String Actual_successMsg = success_msg.getText();
		System.out.println("Actual Message : " + Actual_successMsg);
		String Expected_successMsg = Messages.InvalidWFIcon;
		System.out.println("Expected Message :"+Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Workflow icon updated");
		Reporter.log("Icon Updation done successfully",true);
		Thread.sleep(3000);
		informationpageta.validateSignOut();
	}

	public void ValidateUploadIcon(String wfname, String IconPath) throws Exception{
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(workflowsTab));
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", workflowsTab);
		Reporter.log("Workflows Tab is clicked",true);
		Thread.sleep(3000);
		WebElement editBtn= driver.findElement(By.xpath("//table/tr/td[@title='"+wfname+"']/../td[7]/span[@title='Edit Worklfow']"));
		editBtn.click();
		Reporter.log("Edit button is clicked",true);
		wfIcon.sendKeys(IconPath);
		Thread.sleep(3000);
		saveBtn.click();
		Reporter.log("Save button is clicked",true);
		wait.until(ExpectedConditions.visibilityOf(success_msg));
		String Actual_successMsg = success_msg.getText();
		System.out.println("Actual Message : " + Actual_successMsg);
		String Expected_successMsg = Messages.updateWorkflow;
		System.out.println("Expected Message :"+Expected_successMsg);
		Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Workflow not updated");
		Reporter.log("Icon Updation done successfully",true);
		Thread.sleep(3000);
		informationpageta.validateSignOut();
	}
	public void ValidateEditManualExecution(String wfname,String manExeTime, String tUnit) throws Exception
	  {
		  loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		  Reporter.log("User LogIn Succesfully",true);
		  wait.until(ExpectedConditions.visibilityOf(workflowsTab));
		  JavascriptExecutor js=(JavascriptExecutor)driver;
		  js.executeScript("arguments[0].click();", workflowsTab);
		  System.out.println("workflowTab clicked");
		  wait.until(ExpectedConditions.visibilityOf(workflowListTab));
		  JavascriptExecutor js1=(JavascriptExecutor)driver;
		  js1.executeScript("arguments[0].click();", workflowListTab);
		  Reporter.log("workflowList  tab clicked",true);
		  Thread.sleep(4000);
		  //Click on Edit Button
		  WebElement edit_btn = driver.findElement(By.xpath("//tr/td[contains(text(),'"+wfname +"')]/../td/span[@title='Edit Worklfow']"));
		  js.executeScript("arguments[0].click();", edit_btn);
		  Thread.sleep(3000);
		  Reporter.log("Edit button is Clicked",true);
		  //Clear and Enter Manual Execution time.
			manualExecutionTime.clear();
			manualExecutionTime.sendKeys(manExeTime);
			wait.until(ExpectedConditions.visibilityOf(manualTimeUnit));
			Select manualTimeUnit_drpdown = new Select(manualTimeUnit);
			manualTimeUnit_drpdown.selectByVisibleText(tUnit);
		    Thread.sleep(3000);
			saveBtn.click();
			wait.until(ExpectedConditions.visibilityOf(success_msg));
			String Actual_successMsg = success_msg.getText();
			Reporter.log("Aactual Success Message after editing workflow: " + Actual_successMsg,true);
			String Expected_successMsg = Messages.updateWorkflow;
			Reporter.log("Expected Success Message after editing workflow: " +Expected_successMsg ,true);
			Assert.assertEquals(Actual_successMsg, Expected_successMsg, "Workflow not updated");
			Reporter.log("Workflow updated",true);
			informationpageta.validateSignOut();
	  }
	public void ValidateExportWorkflow(String wfName) throws Exception
	{
		  loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		  Reporter.log("User LogIn Succesfully",true);
		  wait.until(ExpectedConditions.visibilityOf(workflowsTab));
		  JavascriptExecutor js=(JavascriptExecutor)driver;
		  js.executeScript("arguments[0].click();", workflowsTab);
		  System.out.println("workflowTab clicked");
		  wait.until(ExpectedConditions.visibilityOf(workflowListTab));
		  JavascriptExecutor js1=(JavascriptExecutor)driver;
		  js1.executeScript("arguments[0].click();", workflowListTab);
		  Reporter.log("workflowList  tab clicked",true);
		  importDrpDwn.click();
		Thread.sleep(2000);
		Reporter.log("Clicked the Dropdown button", true);
		exportBtn.click();
		Thread.sleep(2000);
		Reporter.log("Clicked the Export option from dropdown", true);
		WfsList.click();
		Thread.sleep(2000);
		Reporter.log("Clicked the Workflows dropdown ", true);
		searchBar.click();
		Thread.sleep(2000);
		Reporter.log("Clicked on search bar");
		searchBar.sendKeys(wfName);
		Wfselect.click();
		Reporter.log("Checked the Workflow name ", true);
		WfsList.click();
		Thread.sleep(2000);
		ExportBtn.click();
		Thread.sleep(2000);
		Reporter.log("Clicked on export bottun", true);
		String actual_successMsg = success_msg.getText();
		System.out.println("Actual success msg: " + actual_successMsg);
		String expected_successMsg = Messages.exportWorkflow;
		System.out.println("Expected success msg: " + expected_successMsg);
		Assert.assertEquals(actual_successMsg, expected_successMsg, "export does not started.");
		informationpageta.validateSignOut();
	}
}
