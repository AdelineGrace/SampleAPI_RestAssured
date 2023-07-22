package stepdefinitions;

import java.util.ArrayList;
import java.util.List;
import apiEngine.model.request.AddAssignmentRequest;
import apiEngine.model.request.AddBatchRequest;
import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.request.AddUserRequest;
import apiEngine.model.request.UserRoleMap;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Batch;
import apiEngine.model.response.Program;
import apiEngine.model.response.User;
import apiEngine.routes.AssignmentRoutes;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AssignmentSteps {
	
	String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
	RequestSpecification request;
	Response response;
	Assignment assignment;
	
	
	// Create new Assignment
	@Given("User creates POST Request for the LMS API endpoint")
	public void user_creates_post_request_for_the_lms_api_endpoint() 
	{
		// add program
		AddProgramRequest program = new AddProgramRequest("TestingTurtles_54781", "Active", "testing");
		
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
		response = request.body(program).post("/saveprogram");
		System.out.println("response - " + response.asPrettyString());
		Program resProgram = response.getBody().as(Program.class);
		System.out.println(resProgram.programId);
		
		// add batch
		AddBatchRequest batch = new AddBatchRequest("TestingTurtles_54781", "Active", "testing", 2, resProgram.programId);
		
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
		response = request.body(batch).post("/batches");
		System.out.println("response - " + response.asPrettyString());
		Batch resBatch = response.getBody().as(Batch.class);
		System.out.println(resBatch.batchId);
		
		// add user
		AddUserRequest user = new AddUserRequest();
		user.UserComments("testing");
		user.UserEduUg("BE");
		user.UserEduPg("MS");
		user.UserFirstName("TestingTurtles_123456");
		user.UserLastName("TestUser");
		user.UserMiddleName("");
		user.UserLinkedinUrl("");
		user.UserLocation("USA");
		user.UserPhoneNumber(123456789);
		user.UserTimeZone("EST");
		user.UserVisaStatus("H4");
		UserRoleMap roleMap = new UserRoleMap("R01", "Active");
		List<UserRoleMap> roleMaps = new ArrayList<UserRoleMap>();
		roleMaps.add(roleMap);
		user.UserRoleMaps(roleMaps);
		
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
		response = request.body(user).post("/users/users/roleStatus");
		System.out.println("response - " + response.asPrettyString());
		User resUser = response.getBody().as(User.class);
		System.out.println(resUser.userId);
		
		// add Assignment
		AddAssignmentRequest assignment = new AddAssignmentRequest();
		assignment.AssignmentDescription("Testing");
		assignment.AssignmentName("TestingTurtles_12345");
		assignment.BatchId(resBatch.batchId);
		assignment.Comments("Testing");
		assignment.CreatedBy(resUser.userId);
		assignment.DueDate("2023-07-29T22:00:04.964+00:00");
		assignment.GraderId(resUser.userId);
		assignment.PathAttachment1("file1.json");
		assignment.PathAttachment2("file2.json");
		assignment.PathAttachment3("file3.json");
		assignment.PathAttachment4("file4.json");
		assignment.PathAttachment5("file5.json");
		
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
		response = request.body(assignment).post("/assignments");
		System.out.println("response - " + response.asPrettyString());
		Assignment resAssignment = response.getBody().as(Assignment.class);
		System.out.println(resAssignment.assignmentId);
		
	}

	@When("User sends POST Request with mandatory and additional fields")
	public void user_sends_post_request_with_mandatory_and_additional_fields() 
	{
		
	}

	@Then("User receives Created Status with response body.")
	public void user_receives_created_status_with_response_body() 
	{
	    
	}
	
	// Get All Assignments
	@Given("User creates GET Request for the LMS API endpoint")
	public void user_creates_get_request_for_the_lms_api_endpoint() 
	{
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends GetAllAssignments Request")
	public void user_sends_GetAllAssignments_request() 
	{
		response = request.get(AssignmentRoutes.getAllAssignments());
	}

	@Then("User receives OK Status with response body containing all assignments")
	public void user_receives_ok_status_with_response_body_containing_all_assignments() 
	{
		if(response.getStatusCode() == 200)
		{
			response.then().statusCode(200);
			System.out.println(response.getStatusCode());
			System.out.println(response.asPrettyString());
			
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
			System.out.print(assignmentList.get(0).assignmentId);
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getassignmentsjsonschema.json")));
		}
		else
		{
			
		}
	}
	
	// Get Assignment by valid id
	@Given("User creates GET Request for the LMS API endpoint with valid Assignment ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_assignment_id() 
	{
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends GetAssignmentByAssignmentid Request with valid id")
	public void user_sends_GetAssignmentByAssignmentid_request_with_valid_id() 
	{
		response = request.get(AssignmentRoutes.getAssignmentByAssignmentId(3197));
	}

	@Then("User receives OK Status with response body containing valid assignment")
	public void user_receives_ok_status_with_response_body_containing_valid_assignment() 
	{
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		
		Assignment assignment = response.getBody().as(Assignment.class);
		System.out.print(assignment.assignmentId);
		
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getassignmentbyidjsonschema.json")));
	}
	
	// Get Assignment by invalid id
	@Given("User creates GET Request for the LMS API endpoint with invalid Assignment ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_assignment_id() 
	{
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends GetAssignmentByAssignmentid Request with invalid id")
	public void user_sends_get_assignment_by_assignmentid_request_with_invalid_id() 
	{
		response = request.get(AssignmentRoutes.getAssignmentByAssignmentId(1));
	}

	@Then("User receives Not Found Status with message and boolean success details")
	public void user_receives_not_found_status_with_message_and_boolean_success_details() 
	{
		System.out.println(response.getStatusCode());
	}

}
