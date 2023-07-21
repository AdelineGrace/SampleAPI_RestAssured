package stepdefinitions;

import api.model.Assignment;
import apiEngine.model.response.Assignments;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
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
		RestAssured.baseURI = baseUrl;
        request = RestAssured.given();
	}

	@When("User sends HTTPS Request")
	public void user_sends_https_request() 
	{
		response = request.get("/assignments");
	}

	@Then("User receives OK Status with response body.")
	public void user_receives_ok_status_with_response_body() 
	{
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asPrettyString());
		//Assignments assignments = response.getBody().as(Assignments.class); 
		//assignment = assignments.assignments.get(0);
		//System.out.print(assignment.assignmentId);
	}

}
