package com.ae.qa.pages;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.Test;

import com.ae.qa.base.TestBase;
import com.ae.qa.util.ExcelHandler;

public class IntegrationTypesPage extends TestBase {
	/*LoginPage loginpage;
	PluginAssignmentsPage pluginassignmentspage;

	public PluginAssignmentsPageTest() {
		super();
	}
	
	@Test
	public void validateAssignTenantAllPluginsTest(Method method) throws Exception {
		extentTest = extent.createTest("validateAssignTenantAllPluginsTest", "Verify assigning tenant all plugins");
		Map<String,String> TestDataInMap=ExcelHandler.getTestDataInMap(prop.getProperty("sheetname"),method.getName());
		pluginassignmentspage = new PluginAssignmentsPage();
		pluginassignmentspage.validateAssignTenantAllPlugins(TestDataInMap.get("TenantOrgCode"));//("BR01","MSSQL-Server");
		extentTest.log(extentTest.getStatus(), "All Plugins are assigned to tenant successfully");
		ExcelHandler.UpdateTestResultsToExcel(prop.getProperty("sheetname"), "Pass", method.getName());
	}*/

}
