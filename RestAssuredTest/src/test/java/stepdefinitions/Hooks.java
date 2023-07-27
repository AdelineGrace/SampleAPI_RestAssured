package stepdefinitions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.output.WriterOutputStream;

import dataProviders.ConfigReader;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;

public class Hooks {

	@BeforeAll
	public static void beforeAll() 
	{
	   ConfigReader.loadProperty();
	   FileWriter fileWriter;
	try {
		fileWriter = new FileWriter(ConfigReader.getProperty("test.data.path")+ConfigReader.getProperty("restassured.log"));
		PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter), true);

	    RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     
	}
	
	
}
