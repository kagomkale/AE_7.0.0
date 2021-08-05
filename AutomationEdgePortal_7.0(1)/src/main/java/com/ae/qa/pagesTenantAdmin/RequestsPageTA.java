package com.ae.qa.pagesTenantAdmin;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ae.qa.base.TestBase;
import com.ae.qa.pages.WebElements;
import com.ae.qa.util.Messages;

public class RequestsPageTA extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 150);
	public WebElements webelements = new WebElements();
	public LoginPageTA loginpageta = new LoginPageTA();
	public InformationPageTA informationpageta = new InformationPageTA();

	@FindBy(xpath = "//span[text()='Requests']")
	WebElement requestsTab;
	@FindBy(xpath = "//*[@id='main-component']/ng-component/div[3]/div[3]/div[2]/table/tbody[1]/tr/td[3]/span[1]")
	WebElement requestStatus;
	@FindBy(name = "refresh-btn")
	WebElement refershTableBtn;
	@FindBy(xpath = "//span[contains(@id,'trow-true')]")
	WebElement reqStatus;
	@FindBy(xpath = "//table[@class='ae-table table table-bordered table-hover']/tbody/tr[2]/td/div/span[1]")
	WebElement execMessage;
	@FindBy(xpath = "//table[@id='agentTable']/tr[3]/td[5]/span")
	WebElement status;
	@FindBy(xpath = "//span[@class='mul-dorpdown-button']")
	WebElement showColumnDrpdown;
	@FindBy(xpath = "//span[@class='mul-checkmark']")
	WebElement selectAllCheckBox;
	@FindBy(xpath = "//span[@class='mul-dorpdown-button']/div")
	WebElement columnCount;
	@FindBy(name="download-requests")
	WebElement downloadRequest;
	@FindBy(id="downloadBtn")
	WebElement downloadBtn;

	public RequestsPageTA() {
		PageFactory.initElements(driver, this);
	}

	public void validateRequestStatus() throws Exception {
		// Here status like New,ExecutionStarted,Completed and Failure are covered
		// whatever the status is tc pass because the moto is to check submitted request
		// and not its status
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully", true);
		Thread.sleep(20000);
		wait.until(ExpectedConditions.visibilityOf(requestsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", requestsTab);
		Reporter.log("Requests Tab is clicked", true);
		refershTableBtn.click();
		Thread.sleep(10000);
		String wfStatus = requestStatus.getText();
		Boolean flag = false;
		if (wfStatus.equals("New")) {
			Thread.sleep(10000);
			Reporter.log("Workflow status is:" + wfStatus, true);
			refershTableBtn.click();
			Assert.assertTrue(!flag);
		} else if (wfStatus.equals("ExecutionStarted")) {
			Reporter.log("Execution of workflow just started", true);
			Thread.sleep(10000);
			refershTableBtn.click();
			Thread.sleep(3000);
			reqStatus.click();
			Reporter.log("Workflow status is:" + wfStatus, true);
			Assert.assertTrue(!flag);
		} else if (wfStatus.equals("Complete")) {
			reqStatus.click();
			Reporter.log("Additional status details is clicked", true);
			String actual_message = execMessage.getText();
			Reporter.log("Message after execution of wf:" + actual_message, true);
			String expected_message = Messages.executionMessage;
			Reporter.log("Workflow status is:" + wfStatus, true);
			Assert.assertEquals(actual_message, expected_message, "Execution is not successful");
		} else if (wfStatus.equals("Failure")) {
			Reporter.log("Workflow status is:" + wfStatus, true);
			Assert.assertTrue(!flag);
		} else {
			Reporter.log("Workflow status is neither completed nor Failure, but other than that: " + wfStatus, true);
			Assert.assertTrue(flag);
		}
		// informationpageta.validateSignOut();
	}

	public void checkColumnsInRequests() throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully", true);
		wait.until(ExpectedConditions.visibilityOf(requestsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", requestsTab);
		Reporter.log("Requests Tab is clicked", true);
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
		Assert.assertEquals(sizeOfColumnsInt, 23, "All columns not selected");
		Reporter.log("All Column got displayed in records after Checking select All checkbox.", true);
		informationpageta.validateSignOut();
	}

	public void deselectAllInRequests() throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully", true);
		wait.until(ExpectedConditions.visibilityOf(requestsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", requestsTab);
		Reporter.log("Requests Tab is clicked", true);
		Thread.sleep(2000);
		showColumnDrpdown.click();
		Thread.sleep(5000);
		if (selectAllCheckBox.isSelected()) {
			selectAllCheckBox.click();
			Reporter.log("Clicked on Select All and resulted into deselected all columns.", true);
		} else {
			for (int i = 1; i <= 2; i++) {
				selectAllCheckBox.click();
			}
			Reporter.log("Select All Checkbox is not already selected, Hence selected twice to deselect it.", true);
		}
		String sizeOfColumn = columnCount.getText();
		int sizeOfColumnsInt = Integer.valueOf(sizeOfColumn);// 3
		Reporter.log("Selected count of No. of colums " + sizeOfColumnsInt, true);// 3
		List<WebElement> TotalColumn = driver
				.findElements(By.xpath("//tr[@class='header-caption-row bg-primary']/th"));
		Reporter.log("Total No of columns in table: " + TotalColumn.size(), true);
		List<String> value = new ArrayList<String>();
		for (int i = 0; i < TotalColumn.size(); i++) {
			Reporter.log("Field of Columns: " + TotalColumn.get(i).getText(), true);
			value.add(TotalColumn.get(i).getText());
		}
		if (value.contains("Id") || value.contains("Workflow Name") || value.contains("Status")) {
			Reporter.log("Column Value found", true);
			Assert.assertEquals(sizeOfColumnsInt, 3, "All columns are not deselected. ");
			Reporter.log("All Options got deselected except Id,Workflow Name,Status.", true);
		} else {
			Reporter.log("Column value not found", true);
			Assert.assertTrue(false);
		}
		informationpageta.validateSignOut();
	}

	public void SpecificColumnInRequests() throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully", true);
		wait.until(ExpectedConditions.visibilityOf(requestsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", requestsTab);
		Reporter.log("Requests Tab is clicked", true);
		Thread.sleep(2000);
		showColumnDrpdown.click();
		Thread.sleep(5000);
		if (selectAllCheckBox.isSelected()) {
			selectAllCheckBox.click();
			Reporter.log("Clicked on Select All and resulted into deselected all columns.", true);
		} else {
			for (int i = 1; i <= 2; i++) {
				selectAllCheckBox.click();
				Thread.sleep(3000);
			}
			Reporter.log("Select All Checkbox is not already selected, Hence selected twice to deselect it.", true);

		}
		// select 2 more columns
		driver.findElement(By.xpath("//a/span[text()='Priority']")).click();
		driver.findElement(By.xpath("//a/span[text()='Completed']")).click();
		String sizeOfColumn = columnCount.getText();
		int sizeOfColumnsInt = Integer.valueOf(sizeOfColumn);
		System.out.println("Selected count of No. of colums " + sizeOfColumnsInt);
		List<WebElement> TotalColumn = driver
				.findElements(By.xpath("//tr[@class='header-caption-row bg-primary']/th"));
		System.out.println("Total No of columns in table: " + TotalColumn.size());
		List<String> value = new ArrayList<String>();
		for (int i = 0; i < TotalColumn.size(); i++) {
			// String Field=TotalColumn.get(i).getText();
			Reporter.log("Field of Columns: " + TotalColumn.get(i).getText(), true);
			value.add(TotalColumn.get(i).getText());
		}
		if (value.contains("Id") && value.contains("Workflow Name") && value.contains("Priority")
				&& value.contains("Status") && value.contains("Completed")) {
			Reporter.log("Column Value found", true);
			Assert.assertTrue(true);
			Assert.assertEquals(sizeOfColumnsInt, TotalColumn.size(), "All columns selected not found in table.");
			Reporter.log("All columns selected found in table.", true);
		} else {
			Reporter.log("Column value not found", true);
			Assert.assertTrue(false);
		}
		informationpageta.validateSignOut();
	}
	
	public void validateDownloadRequest() throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully", true);
		wait.until(ExpectedConditions.visibilityOf(requestsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", requestsTab);
		Reporter.log("Requests Tab is clicked", true);
		downloadRequest.click();
		Thread.sleep(3000);
		downloadBtn.click();
		Reporter.log("Download button is clicked & request download started",true);
		Thread.sleep(5000);
		informationpageta.validateSignOut();
	}
	public void validateRequestAdvSearch() throws Exception {
		loginpageta.login(prop.getProperty("username_TA1"), prop.getProperty("password_TA1"));
		Reporter.log("User logged in successfully", true);
		Thread.sleep(20000);
		wait.until(ExpectedConditions.visibilityOf(requestsTab));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", requestsTab);
		Reporter.log("Requests Tab is clicked", true);
		refershTableBtn.click();
		Thread.sleep(10000);
		// webelements.AdvanceSearchField("name", "eq", tName);
		// webelements.ExtraAdvanceSearch("orgCode", "eq", orgCode);
	}

	// loginpageta.login(prop.getProperty("username_TA"),
	// prop.getProperty("password_TA"));
	// Reporter.log("User logged in successfully",true);
	/*
	 * wait.until(ExpectedConditions.visibilityOf(requestsTab)); JavascriptExecutor
	 * js= (JavascriptExecutor) driver;
	 * js.executeScript("arguments[0].click();",requestsTab);
	 * Reporter.log("Requests Tab is clicked",true); Thread.sleep(4000);
	 * //refershTableBtn.click(); Reporter.log("Refreshed Table ",true); String
	 * wfStatus=requestStatus.getText(); Boolean flag=false;
	 * if(wfStatus.equals("Complete")) { reqStatus.click();
	 * Reporter.log("Additional status details is clicked",true); String
	 * actual_message=execMessage.getText();
	 * Reporter.log("Message after execution of wf:"+actual_message, true); String
	 * expected_message = Messages.executionMessage;
	 * Reporter.log("Workflow status is not completed & status is:"+wfStatus,true);
	 * Assert.assertEquals(actual_message,
	 * expected_message,"Execution is not successful"); } else
	 * if(wfStatus.equals("Failure")){
	 * Reporter.log("Workflow status is not completed & status is:"+wfStatus,true);
	 * Assert.assertTrue(!flag); } else { //mostly status will be either completed
	 * or failure but for others it is covered Reporter.
	 * log("Workflow status is neither completed nor Failure, but other than that: "
	 * +wfStatus,true); Assert.assertTrue(flag); }
	 * informationpageta.validateSignOut(); }
	 */
}