package stepdefinitions;

import java.util.List;

import apiEngine.endpoints.AssignmentEndpoints;
import apiEngine.model.response.Assignment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AssignmentSteps {
	
	String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
	RequestSpecification request;
	Response response;
	Assignment assignment;
	
	@Given("User creates GET Request for the LMS API endpoint")
	public void user_creates_get_request_for_the_lms_api_endpoint() 
	{
		request = AssignmentEndpoints.createBaseRequest();
	}

	@When("User sends HTTPS Request")
	public void user_sends_https_request() 
	{
		response = AssignmentEndpoints.GetAssignments(request);
	}

	@Then("User receives OK Status with response body.")
	public void user_receives_ok_status_with_response_body() 
	{
		
		System.out.println(response.getStatusCode());
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
		System.out.print(assignmentList.get(0).assignmentId);
		
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getassignmentsjsonschema.json")));
	}

}
