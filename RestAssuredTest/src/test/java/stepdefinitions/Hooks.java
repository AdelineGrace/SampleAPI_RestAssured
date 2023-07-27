package stepdefinitions;

import dataProviders.ConfigReader;
import io.cucumber.java.BeforeAll;

public class Hooks {

	@BeforeAll
	public static void beforeAll() 
	{
	   ConfigReader.loadProperty();
	}
	
	
}
