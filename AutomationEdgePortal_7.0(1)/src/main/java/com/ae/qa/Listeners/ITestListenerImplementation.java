package com.ae.qa.Listeners;

import org.apache.commons.logging.Log;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;

public class ITestListenerImplementation implements ITestListener
{
	private static ExtentReports extent;
	private static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}

    // When Test case get Started, this method is called.
	public void onTestStart(ITestResult result) {
		System.out.println(result.getName()+" test case started");	
	}
 // When Test case get passed, this method is called.
	public void onTestSuccess(ITestResult result) {
		 System.out.println("The name of the testcase passed is :"+result.getName());
	}
	// When Test case get failed, this method is called
	public void onTestFailure(ITestResult result) {
		System.out.println("The name of the testcase failed is :"+result.getName());
	}
    // When Test case get Skipped, this method is called.
	public void onTestSkipped(ITestResult result) {
		System.out.println("The name of the testcase Skipped is :"+result.getName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		System.out.println("Execution started on UAT env...");
		//extent= setUp();
		
	}

	public void onFinish(ITestContext context) {
		System.out.println("Execution completed on UAT env...");
		//extent.flush();		
		System.out.println("Generated Report. . .");	
		
	}
	

}



