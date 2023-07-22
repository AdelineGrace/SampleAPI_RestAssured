package utilities;




import dataProviders.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks {

	@BeforeAll
	public static void beforeAll() {
	   ConfigReader.loadProperty();
	}
	
	/*@Before
	public void testStart() {
		LOG.info("*****************************************************************************************");
		LOG.info("	Scenario: "+scenario.getName());
		LOG.info("*****************************************************************************************");
	}*/
}
