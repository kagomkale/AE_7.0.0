package com.ae.qa.pagesTenantAdmin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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
import com.ae.qa.util.Messages;
import com.ae.qa.util.TestUtil;

public class AgentListPageTA extends TestBase{
	
	  public LoginPageTA loginpage = new LoginPageTA();
	  public static WebDriverWait wait = new WebDriverWait(driver, 300);
	  public InformationPageTA informationpageta=new InformationPageTA();
	  
	  @FindBy(xpath="//span[text()='Agents']")
	  WebElement AgentsTab;
	  @FindBy(xpath="//a[text()='Workflow Assignment']")
	  WebElement WorkflowAssignmentTab;
	  @FindBy(xpath="//div[@class='agent-assign-desktop']/div/div/label")
	  WebElement agentNotReg;
	  @FindBy(xpath="//a[text()='Agent List']")
	  WebElement AgentListTab;
	  @FindBy(id = "download-agent")
	  WebElement downloadAgentBtn;
	  @FindBy(xpath="//*[@name='delete-agent']/span")
	  WebElement downloadBtn;
	  @FindBy(xpath="//*[@id='177']/div/span[3]")
	  WebElement successMsgBox;
	  @FindBy(xpath="//button[@name='refresh-btn']")
	  WebElement refreshTable;
	  @FindBy(xpath="//table[@id='agentTable']/tr[3]/td[5]/span")
	  WebElement status;
	  @FindBy(xpath="//span[@class='mul-dorpdown-button']")
	  WebElement showColumnDrpdown;
	  @FindBy(xpath="//span[@class='mul-checkmark']")
	  WebElement selectAllCheckBox;
	  @FindBy(xpath = "//span[@class='mul-dorpdown-button']/div")
	  WebElement columnCount;
	  public AgentListPageTA()
	  {
		  PageFactory.initElements(driver, this);
	  }
	  
	public void downloadAgent() throws Exception
	  {
	loginpage.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
	Reporter.log("User LogIn Succesfully",true);
	wait.until(ExpectedConditions.visibilityOf(AgentsTab));
	JavascriptExecutor js=(JavascriptExecutor)driver;
	js.executeScript("arguments[0].click();", AgentsTab);
	Reporter.log("User navigated to Agents Tab",true);
	//Verify when user try to assign wf without registering agent
	js.executeScript("arguments[0].click();", WorkflowAssignmentTab);	
	Reporter.log("User navigated to workflow assignment Tab",true);
	Thread.sleep(3000);
	String expected_agentNotRegError=agentNotReg.getText();
	Reporter.log("When Agent is not registered and user try to assign workflow to agent then get error message as: "+expected_agentNotRegError,true);
	String actual_agentNotRegError = "No agent registered";
	Assert.assertEquals(actual_agentNotRegError, expected_agentNotRegError,"Not getting correct error in Workflow assignment Tab");
	Reporter.log("Getting correct error when user didnt register agent & try to assign workflow");
	Thread.sleep(2000);
	Reporter.log("Now User needs to download agent first",true);
	js.executeScript("arguments[0].click();", AgentListTab);
	Reporter.log("User navigated to Agent List Tab",true);
	downloadAgentBtn.click();
	Thread.sleep(3000);
	downloadBtn.click();
	Reporter.log("Agent download started",true);
	Thread.sleep(60000);
	TestUtil.unzip(prop.getProperty("zipFilePath"),prop.getProperty("destDir"));
	Reporter.log("File unzipped properly",true);
	ProcessBuilder pb= new ProcessBuilder(prop.getProperty("AgentRegBatFile"));
	Process process= pb.start();
	BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
			  StringBuilder sb=new StringBuilder();
			//  String line;
			/*  while((line = reader.readLine()) != null) {
				  sb.append(line + "\n");
				  }*/
			//  System.out.println(sb);
			  Thread.sleep(3000);
			 informationpageta.validateSignOut();
			//  driver.quit();
		  }
	
	public void checkStatusOfAgent() throws Exception {
		loginpage.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User LogIn Succesfully",true);
		wait.until(ExpectedConditions.visibilityOf(AgentsTab));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", AgentsTab);
		Reporter.log("User navigated to Agents Tab",true);
		Thread.sleep(4000);
	 for(int i=0;i<2;i++) {
		refreshTable.click();	
		String AgentStatus=status.getText();
		Reporter.log("Current status of Agent is: "+AgentStatus);
		Thread.sleep(3000);
		if(AgentStatus.equals("Running")) {
			Assert.assertEquals(AgentStatus,"Running","Agent is not in running mode");
			break;
			}
		}
			  Reporter.log("Agent is in running mode",true);
	 informationpageta.validateSignOut();
	}
	public void checkColumnsInAgentList() throws Exception {
		loginpage.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(AgentsTab));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", AgentsTab);
		Reporter.log("User navigated to AgentList tab",true);
		Thread.sleep(2000);
		showColumnDrpdown.click();
		Thread.sleep(5000);
		if (!selectAllCheckBox.isSelected()) {
			selectAllCheckBox.click();
			System.out.println("Clicked on Select All CheckBox");
		} else {
			System.out.println("Select All Checkbox is already selected");
		}
		String sizeOfColumn = columnCount.getText();
		int sizeOfColumnsInt = Integer.valueOf(sizeOfColumn);
		System.out.println("Selected count of No. of colums " + sizeOfColumnsInt);
		Assert.assertEquals(sizeOfColumnsInt, 7, "All columns not selected");
		Reporter.log("All Column got displayed in records after Checking select All checkbox.",true);
		informationpageta.validateSignOut();
	}
	public void deselectAllInAgentListTA() throws Exception {
		// Click Logs Tab
		loginpage.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(AgentsTab));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", AgentsTab);
		Reporter.log("User navigated to AgentList tab",true);
		Thread.sleep(2000);
		showColumnDrpdown.click();
		Thread.sleep(5000);
		if (selectAllCheckBox.isSelected()) {
			selectAllCheckBox.click();
			Reporter.log("Clicked on Select All and resulted into deselected all columns.",true);
		} else {
			for (int i = 1; i <= 2; i++) {
				selectAllCheckBox.click();
			}
			Reporter.log("Select All Checkbox is not already selected, Hence selected twice to deselect it.",true);
		}
		String sizeOfColumn = columnCount.getText();
		int sizeOfColumnsInt = Integer.valueOf(sizeOfColumn);//3
		Reporter.log("Selected count of No. of colums " + sizeOfColumnsInt,true);//3
	    List<WebElement> TotalColumn = driver.findElements(By.xpath("//tr[@class='header-caption-row bg-primary']/th/span"));
		Reporter.log("Total No of columns in table: " + TotalColumn.size(),true);
		List<String> value = new ArrayList<String>();
		for (int i = 0; i < TotalColumn.size(); i++) {
			Reporter.log("Field of Columns: " + TotalColumn.get(i).getText(),true);
			value.add(TotalColumn.get(i).getText());
		}
		if (value.contains("Agent Name") || value.contains("Status") || value.contains("Resource Utilization")) {
		Reporter.log("Column Value found",true);
			Assert.assertEquals(sizeOfColumnsInt, 3, "All columns are not deselected. ");
			Reporter.log("All Options got deselected except Agent Name,Status and Resource Utilization.",true);
		} else {
			Reporter.log("Column value not found",true);
			Assert.assertTrue(false);
		}
		informationpageta.validateSignOut();
	}
	public void SpecificColumnInAgentList() throws Exception {
		loginpage.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully",true);
		wait.until(ExpectedConditions.visibilityOf(AgentsTab));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", AgentsTab);
		Reporter.log("User navigated to AgentList tab",true);
		Thread.sleep(2000);
		showColumnDrpdown.click();
		Thread.sleep(5000);
		if (selectAllCheckBox.isSelected()) {
			selectAllCheckBox.click();
			Reporter.log("Clicked on Select All and resulted into deselected all columns.",true);
		} else {
			for (int i = 1; i <= 2; i++) {
				selectAllCheckBox.click();
				Thread.sleep(3000);
			}
			Reporter.log("Select All Checkbox is not already selected, Hence selected twice to deselect it.",true);

		}
		// select 2 more columns
		driver.findElement(By.xpath("//a/span[text()='Agent Type']")).click();
		driver.findElement(By.xpath("//a/span[text()='Executing Workflows']")).click();
		String sizeOfColumn = columnCount.getText();
		int sizeOfColumnsInt = Integer.valueOf(sizeOfColumn);
		System.out.println("Selected count of No. of colums " + sizeOfColumnsInt);
		List<WebElement> TotalColumn = driver.findElements(By.xpath("//tr[@class='header-caption-row bg-primary']/th/span"));
		System.out.println("Total No of columns in table: " + TotalColumn.size());
		List<String> value = new ArrayList<String>();
		for (int i = 0; i < TotalColumn.size(); i++) {
			// String Field=TotalColumn.get(i).getText();
			Reporter.log("Field of Columns: " + TotalColumn.get(i).getText(),true);
			value.add(TotalColumn.get(i).getText());
		}
		if (value.contains("Agent Name") && value.contains("Mode") && value.contains("Resource Utilization")
				&& value.contains("Status") && value.contains("Executing Workflows")) {
			Reporter.log("Column Value found",true);
			Assert.assertTrue(true);
			Assert.assertEquals(sizeOfColumnsInt, TotalColumn.size(), "All columns selected not found in table.");
			Reporter.log("All columns selected found in table.",true);
		} else {
			Reporter.log("Column value not found",true);
			Assert.assertTrue(false);
		}
		informationpageta.validateSignOut();
	}
}	 

