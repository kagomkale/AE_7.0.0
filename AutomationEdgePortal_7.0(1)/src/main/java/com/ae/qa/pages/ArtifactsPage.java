package com.ae.qa.pages;

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
import com.ae.qa.pagesTenantAdmin.InformationPageTA;
import com.ae.qa.pagesTenantAdmin.LoginPageTA;
import com.ae.qa.util.Messages;

public class ArtifactsPage extends TestBase {
	public WebDriverWait wait = new WebDriverWait(driver, 200);
	public WebElements webelements = new WebElements();
	public LoginPage loginpage = new LoginPage();
	public InformationPage informationpage=new InformationPage();
	
	  @FindBy(xpath="//span[text()='Artifacts']")
	  WebElement ArtifactsTab;
	  @FindBy(name="upload")
	  WebElement uploadBtn;
////button[@name='upload']
	 @FindBy(id="name")
	 WebElement artifactName;
	 @FindBy(id="version")
	 WebElement version;
	 @FindBy(xpath="//input[@id='fileName-input']")
	 WebElement artifactFile;
	 @FindBy(xpath="//button[@name='assign']")
	 WebElement uploadSubmitBtn;
	 @FindBy(xpath = "//p[@class='alert-message-text']")
	 WebElement alertMessage;
	 
	  public ArtifactsPage()
	  {
		  PageFactory.initElements(driver, this);
	  }
	  //Here we are uploading PS in artifacts &covering negative scenario as when user give invalid input as Process studio or 
	  //process Studio than correct Process Studio
	  public void ValidateUploadPS(String InvalidPsName,String Version,String ValidPsName) throws Exception{
		  loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		  wait.until(ExpectedConditions.visibilityOf(ArtifactsTab));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", ArtifactsTab);
			uploadBtn.click();
			Thread.sleep(3000);
			Reporter.log("Upload button is clicked",true);
			artifactName.sendKeys(InvalidPsName);
			Thread.sleep(2000);
			version.sendKeys(Version);
			Thread.sleep(2000);
			artifactFile.sendKeys(prop.getProperty("uploadPSFile"));
			Thread.sleep(2000);
			uploadSubmitBtn.click();
			Reporter.log("Upload PS button clicked",true);
			wait.until(ExpectedConditions.visibilityOf(alertMessage));
			String actual_FailureMsg = alertMessage.getText();
			Reporter.log("Failure message for Invalid Artifact name: " + actual_FailureMsg,true);
			String expected_failureMsg = Messages.invalidArtifactName;
			Assert.assertEquals(actual_FailureMsg, expected_failureMsg, "Check artifacts details again");
			Reporter.log("Please upload artifacts with valid name",true);
			Thread.sleep(13000);
			for(int i = 0; i < 50; i++) {
			artifactName.sendKeys(Keys.BACK_SPACE);
			}
			Thread.sleep(3000);
			artifactName.sendKeys(ValidPsName);
			Reporter.log("Artifact name has given correctly",true);
			uploadSubmitBtn.click();
			Reporter.log("Upload PS button clicked",true);
			Thread.sleep(2000);
			String actual_SuccessMsg = alertMessage.getText();
			Reporter.log("Success message for valid Artifact name: " + actual_SuccessMsg,true);
			String expected_SuccessMsg = Messages.validArtifactName;
			Assert.assertEquals(actual_SuccessMsg,expected_SuccessMsg, "Artifacts not uploaded");
			Reporter.log("Artifact uploaded successfully",true);
			Thread.sleep(3000);
			informationpage.validateSignOut();
	  }
	  
	  

}
