package stepdefinitions;

import java.util.Map;



import dataProviders.ConfigReader;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProgramBatchSteps {
	
	
	static final String baseUrl = ConfigReader.getProperty("base.url");
	static final String batchUrl = ConfigReader.getProperty("batch.url");
	RequestSpecification request;
	Response response;
	

	@Given("User creates GET Request for the LMS API {string}")
	public void user_creates_get_request_for_the_lms_api(String string) {
		switch (string) {
		case "batchModule":
			RestAssured.baseURI = baseUrl+batchUrl;
			break;

		default:
			RestAssured.baseURI = baseUrl;
			break;
		}
		
        request = RestAssured.given();
	}

	@When("User sends HTTPS Request for endpoint from {string} with {string}")
	public void user_sends_https_request_for_endpoint_from_with(String sheetName, String dataKey) {
		Map<String,String> excelDataMap=null;
		String endpoint=null;
		try {
			excelDataMap = ExcelReader.getData(dataKey,sheetName);
			endpoint = excelDataMap.get("endpoint");
			switch (endpoint) {
			case "batches":
				response = request.get();
				break;
			case "batchId":
				
				response = request.get(endpoint+"/6408");
				break;
			case "batchName":
				
				response = request.get(endpoint+"/Sdet");
				break;
			case "program":
				
				response = request.get(endpoint+"/10679");
				break;

			default:
				response = request.get(endpoint);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("User receives Status Code  with response body for endpoint {string} with {string}")
	public void user_receives_status_code_with_response_body_for_endpoint_with(String sheetName, String dataKey) {
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asPrettyString());
	}
	
}
