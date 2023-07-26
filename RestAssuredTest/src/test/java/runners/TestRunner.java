package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
			plugin = {"pretty", "html:target/index.html",
					"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
					//"junit:target/Destination.xml",
					//"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
					}, //reporting purpose
			monochrome=false,  //console output color
			features = {"src/test/resources/features/ProgramModule.feature"}, //location of feature files
			glue= "stepdefinitions" //location of step definition files
		)
public class TestRunner {
	
}