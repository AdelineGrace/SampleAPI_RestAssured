package stepdefinitions;

import java.util.List;

import apiEngine.model.request.AddSubmission;
import apiEngine.model.request.AddAssignmentRequest;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Submission;
import apiEngine.routes.AssignmentRoutes;
import apiEngine.routes.SubmissionRoutes;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SubmissionStep {

	String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
	RequestSpecification request;
	Response response;
	Submission submission;
	
	
	@Given("Get request for all submission module")
	public void get_request_for_all_submission_module()  {
	    
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		
	}
	@When("User sends HTTPS Request for submission module")
	public void user_sends_https_request_for_submission_module() {
		response = request.get(SubmissionRoutes.getAllAssignments());
	    
	}
	
	@Then("User receives response ok status with response body for submission module")
	public void user_receives_response_ok_status_with_response_body_for_submission_module(){
		
		if(response.getStatusCode() == 200)
		{
			response.then().statusCode(200);
			System.out.println(response.getStatusCode());
			System.out.println(response.asPrettyString());
			
		//	JsonPath jsonPathEvaluator = response.jsonPath();
		//	List<Submission> SubmissionList = jsonPathEvaluator.getList("", Submission.class);
		//	System.out.print(SubmissionList.get(0).submissionId);
			
	//	response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getsubmissionjsonschema.json")));
		}
		else
		{
			
		}
	   
	}
	
	@Given("Check if user able to retrieve a grades with valid Assignment ID")
	public void check_if_user_able_to_retrieve_a_grades_with_valid_assignment_id() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User creates GET Request for the LMS API endpoint with valid Assignment ID in submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_assignment_id_in_submission_module() {
		response = request.get(SubmissionRoutes.getsubmissionByAssignmentId(3344));
	}

	@Then("User receives response ok status with response body for assignment ID in submission module")
	public void user_receives_response_ok_status_with_response_body_for_assignment_id_in_submission_module() {
	    
		System.out.println(response.getStatusCode());
	}

	// Tag3
	@Given("User creates GET Request for the LMS API endpoint with invalid Assignemnt ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_assignemnt_id() {
	    
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		
	}

	@When("User sends HTTPS Request for invalid assignment ID")
	public void user_sends_https_request_for_invalid_assignment_id() {
	    
		response = request.get(SubmissionRoutes.getsubmissionByAssignmentId(3276));
	}

	@Then("User receives Not Found Status with message and boolean success details for submission module")
	public void user_receives_not_found_status_with_message_and_boolean_success_details_for_submission_module() {
	    
		System.out.println(response.getStatusCode());
	}

	
	//tag4
	
	@Given("User creates GET Request for the LMS API endpoint with valid Student Id for submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_student_id_for_submission_module() {
	    
		RestAssured.baseURI = baseUrl;
		System.out.println(baseUrl);
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for Student ID for submission module")
	public void user_sends_https_request_for_student_id_for_submission_module() {
	  
		response = request.get(SubmissionRoutes.getGradeByStudentId("U8158"));
		System.out.println(response.asPrettyString());
		
	}

	@Then("User receives response ok status with response body for student ID for submission module")
	public void user_receives_response_ok_status_with_response_body_for_student_id_for_submission_module() {
	    
		System.out.println(response.getStatusCode());
	}
	
	//tag5
	
	@Given("User creates GET Request for the LMS API endpoint with invalid Student ID for submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_student_id_for_submission_module() {
	    
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for Invalid Student ID for submission module")
	public void user_sends_https_request_for_invalid_student_id_for_submission_module() {
		
		response = request.get(SubmissionRoutes.getGradeByStudentId("U7777"));
	    
	}

	@Then("User receives Not Found Status with message and boolean success details for student ID for submission module")
	public void user_receives_not_found_status_with_message_and_boolean_success_details_for_student_id_for_submission_module() {
	    
		System.out.println(response.getStatusCode());
	}



	//tag6
	@Given("User creates GET Request for the LMS API endpoint with valid Batch Id for submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_batch_id_for_submission_module() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for Batch ID for submission module")
	public void user_sends_https_request_for_batch_id_for_submission_module() {
		response = request.get(SubmissionRoutes.getGradeBybatchId(6463));	    
	}

	@Then("User receives response ok status with response body for Batch ID for submission module")
	public void user_receives_response_ok_status_with_response_body_for_batch_id_for_submission_module() {
		
		System.out.println(response.getStatusCode());
	    
	}
	
	//tag7
	
	@Given("User creates GET Request for the LMS API endpoint with invalid Batch ID for submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_batch_id_for_submission_module() {
	    
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}
	
	
	@When("User sends HTTPS Request for Invalid Batch ID for submission module")
	public void user_sends_https_request_for_invalid_batch_id_for_submission_module() {
	    
		response = request.get(SubmissionRoutes.getGradeBybatchId(6501));
	}

	@Then("User receives Not Found Status with message and boolean success details for Batch ID for submission module")
	public void user_receives_not_found_status_with_message_and_boolean_success_details_for_batch_id_for_submission_module() {
	   
		System.out.println(response.getStatusCode());
	}

	
	// tag8
	
	@Given("User creates GET Request for the LMS API endpoint with valid Batch Id for submission")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_batch_id_for_submission() {
	
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for submission by Batch ID for submission module")
	public void user_sends_https_request_for_submission_by_batch_id_for_submission_module() {
		
		response = request.get(SubmissionRoutes.getsubmissionBybatchId(6463));
	}

	@Then("User receives response ok status with response body for submission by Batch ID")
	public void user_receives_response_ok_status_with_response_body_for_submission_by_batch_id() {
		
		System.out.println(response.getStatusCode());
	}


//tag9
	
	@Given("User creates GET Request for the LMS API endpoint with invalid Batch ID for submission")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_batch_id_for_submission() {
	   
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for submission by Invalid Batch ID")
	public void user_sends_https_request_for_submission_by_invalid_batch_id() {
		
		response = request.get(SubmissionRoutes.getsubmissionBybatchId(6666));
	}

	@Then("User receives Not Found Status with message and boolean success details for submission by Batch ID")
	public void user_receives_not_found_status_with_message_and_boolean_success_details_for_submission_by_batch_id() {
	 
		System.out.println(response.getStatusCode());
	}

	
	// tag10
	
	@Given("User creates GET Request for the LMS API endpoint with valid User Id for submission")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_user_id_for_submission() {
	    
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for submission by UserID")
	public void user_sends_https_request_for_submission_by_user_id() {
	    
		response = request.get(SubmissionRoutes.getsubmissionByUserId("U8158"));
		
	}

	@Then("User receives response ok status with response body for submission by UserID")
	public void user_receives_response_ok_status_with_response_body_for_submission_by_user_id() {
	    
		System.out.println(response.getStatusCode());
	}

	
	//tag11
	
	@Given("User creates GET Request for the LMS API endpoint with invalid UserID for submission")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_user_id_for_submission() {
	    
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for submission by Invalid UserID")
	public void user_sends_https_request_for_submission_by_invalid_user_id() {
		
		response = request.get(SubmissionRoutes.getsubmissionByUserId("U888"));
	    
	}

	@Then("User receives Not Found Status with message and boolean success details for submission by UserID")
	public void user_receives_not_found_status_with_message_and_boolean_success_details_for_submission_by_user_id() {
	    
		System.out.println(response.getStatusCode());
	}



//tag 12 Post_request
	
	@Given("User creates POST Request for the LMS API endpoint for submission")
	public void user_creates_post_request_for_the_lms_api_endpoint_for_submission() {
	    
		AddSubmission submission = new AddSubmission();
		submission.assignmentId(3352);
		submission.userId("U8158");
		submission.subDesc("Testing");
		submission.subcomments("testing MIP");
		submission.subPathAttach1("null");
		submission.subPathAttach2("file2.json");
		submission.subPathAttach3("file3.json");
		submission.subPathAttach4("file4.json");
		submission.subPathAttach5("file5.json");
		submission.subDateTime("07-26-2023 11:36:00");
		submission.gradedBy("U7876");
		submission.gradedDateTime("07-27-2023 11:36:00");
		submission.grade("5");
		
		
		
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
		response = request.body(submission).post("/assignmentsubmission");
		System.out.println("response - " + response.asPrettyString());
		Submission resSubmission = response.getBody().as(Submission.class);
		System.out.println(resSubmission.submissionId);
		
	}

	@When("User sends HTTPS Request and request Body with Mandatory field")
	public void user_sends_https_request_and_request_body_with_mandatory_field() {
	    
		
	}

	@Then("User receives ok created status with response body for post request submission")
	public void user_receives_ok_created_status_with_response_body_for_post_request_submission() {
	    
		System.out.println(response.getStatusCode());
	}



// tag 13 Postrequest_with_Negative_scenario1
	
	@Given("User creates POST Request for the LMS API endpoint for submission module")
	public void user_creates_post_request_for_the_lms_api_endpoint_for_submission_module() {
	    
		//this.baseUrl=ConstantURL.Post_URl;
		this.request=RestAssured.given().header("Content-Type", "application/json");
		
		
	}

	@When("User sends HTTPS Request and request Body with Mandatory field	from {string} and {int} submission module")
	public void user_sends_https_request_and_request_body_with_mandatory_field_from_and_submission_module(String string, Integer int1) {
	   
	}

	@Then("User receives Bad request status created status with response body for post request submission module")
	public void user_receives_bad_request_status_created_status_with_response_body_for_post_request_submission_module() {
	    
	}
	
	// tag 14 Postrequest_with_Negative_scenario2

	@Given("User creates POST Request for the submission module LMS API endpoint")
	public void user_creates_post_request_for_the_submission_module_lms_api_endpoint() {
	   
	}

	@When("User sends HTTPS Request and request Body without Mandatory field	from {string} and {int} submission module")
	public void user_sends_https_request_and_request_body_without_mandatory_field_from_and_submission_module(String string, Integer int1) {
	   
	}

	@Then("User receives Bad request with response body for post request submission module")
	public void user_receives_bad_request_with_response_body_for_post_request_submission_module() {
	    
	}






	
	
}