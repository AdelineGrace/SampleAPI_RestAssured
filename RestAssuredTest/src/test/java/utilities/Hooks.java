package utilities;




import dataProviders.ConfigReader;
import io.cucumber.java.BeforeAll;

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
