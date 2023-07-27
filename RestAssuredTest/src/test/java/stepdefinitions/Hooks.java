package stepdefinitions;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import dataProviders.ConfigReader;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class Hooks {

	@BeforeAll
	public static void beforeAll() 
	{
	   ConfigReader.loadProperty();
	try {
//		fileWriter = new FileWriter(ConfigReader.getProperty("test.data.path")+ConfigReader.getProperty("restassured.log"));
//		PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter), true);
//
//	    RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
	    
	    OutputStream file = new FileOutputStream(ConfigReader.getProperty("test.data.path") + "request_log.txt");
		PrintStream stream = new PrintStream(file, true);
		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		
		OutputStream fileR = new FileOutputStream(ConfigReader.getProperty("test.data.path") + "response_log.txt");
		PrintStream streamR = new PrintStream(fileR, true);
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(streamR));
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     
	}
	
	
}
