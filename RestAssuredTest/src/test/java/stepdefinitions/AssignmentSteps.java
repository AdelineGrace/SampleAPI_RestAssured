package stepdefinitions;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;

import apiEngine.model.request.AddAssignmentRequest;
import apiEngine.model.request.AddBatchRequest;
import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.request.AddUserRequest;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Batch;
import apiEngine.model.response.Program;
import apiEngine.model.response.User;
import apiEngine.routes.AssignmentRoutes;
import apiEngine.routes.ProgramBatchRoutes;
import apiEngine.routes.ProgramRoutes;
import apiEngine.routes.UserRoutes;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;
import utilities.DynamicValues;

public class AssignmentSteps extends BaseStep {

	String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
	RequestSpecification request;
	Response response;
	AddAssignmentRequest addAssignmentReq;

	Map<String, String> excelDataMap;

	Program program;
	Batch batch;
	User user;

	static int programId;
	static int batchId;
	static String userId;
	static int assignmentId;
	static String existingAssignmentName;

	public void SetupPreRequisites() 
	{

		try 
		{
			excelDataMap = null;
			AddProgramRequest programReq = null;
			AddBatchRequest batchReq = null;
			AddUserRequest userReq = null;

			// create program
			excelDataMap = ExcelReader.getData("Post_Program_Assignment", "program");
			if (null != excelDataMap && excelDataMap.size() > 0) 
			{
				programReq = new AddProgramRequest(excelDataMap.get("programName") + DynamicValues.SerialNumber(),
						excelDataMap.get("programStatus"), excelDataMap.get("programDescription"));
			}

			response = programEndpoints.CreateProgram(programReq);
			program = response.getBody().as(Program.class);
			programId = program.programId;
			System.out.println("Program id - " + programId);

			// create batch
			excelDataMap = ExcelReader.getData("Post_Batch_Assignment", "batch");
			if (null != excelDataMap && excelDataMap.size() > 0) 
			{
				batchReq = new AddBatchRequest(excelDataMap.get("BatchName") + DynamicValues.SerialNumber(), 
						excelDataMap.get("BatchStatus"), excelDataMap.get("BatchDescription"), 
						Integer.parseInt(excelDataMap.get("NoOfClasses")), programId);
			}

			response = batchEndpoints.CreateBatch(batchReq);
			batch = response.getBody().as(Batch.class);
			batchId = batch.batchId;

			// create user
			excelDataMap = ExcelReader.getData("Post_User_Assignment", "user");
			if (null != excelDataMap && excelDataMap.size() > 0) 
			{
				userReq = new AddUserRequest(excelDataMap.get("userFirstName") + DynamicValues.SerialNumber(),
						excelDataMap.get("userLastName"), excelDataMap.get("userMiddleName"),
						excelDataMap.get("userComments"), excelDataMap.get("userEduPg"), excelDataMap.get("userEduUg"),
						excelDataMap.get("userLinkedinUrl"), excelDataMap.get("userLocation"),
						DynamicValues.PhoneNumber(), excelDataMap.get("roleId"), excelDataMap.get("userRoleStatus"),
						excelDataMap.get("userTimeZone"), excelDataMap.get("userVisaStatus"));
			}
			
			response = userEndpoints.CreateUser(userReq);
			user = response.getBody().as(User.class);
			userId = user.userId;

		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void Cleanup() 
	{
		// delete user
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.delete(UserRoutes.deleteUser(userId));

		// delete batch
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.delete(ProgramBatchRoutes.deleteBatch(batchId));
		
		// delete program
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.delete(ProgramRoutes.deleteProgram(programId));
	}

	@Given("User creates POST Assignment Request for the LMS API with fields from {string} with {string}")
	public void user_creates_post_assignment_request_for_the_lms_api_with_fields_from_with(String sheetName, String dataKey) 
	{
		try 
		{
			// create program, batch and user for creating new assignment
			if (dataKey.equals("Post_Assignment_Valid")) 
			{
				SetupPreRequisites();
			}
			

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			excelDataMap = null;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);

			if (excelDataMap != null && excelDataMap.size() > 0) 
			{
				switch(dataKey)
				{
					case "Post_Assignment_Valid" : 
						addAssignmentReq = new AddAssignmentRequest(excelDataMap.get("assignmentName") + DynamicValues.SerialNumber(),
								excelDataMap.get("assignmentDescription"), batchId, excelDataMap.get("comments"), userId,
								"2023-07-29T22:00:04.964+00:00", userId, excelDataMap.get("pathAttachment1"),
								excelDataMap.get("pathAttachment2"), excelDataMap.get("pathAttachment3"),
								excelDataMap.get("pathAttachment4"), excelDataMap.get("pathAttachment5)"));
						break;
					case "Post_Assignment_Existing" : 
						addAssignmentReq = new AddAssignmentRequest(existingAssignmentName,
								excelDataMap.get("assignmentDescription"), batchId, excelDataMap.get("comments"), userId,
								"2023-07-29T22:00:04.964+00:00", userId, excelDataMap.get("pathAttachment1"),
								excelDataMap.get("pathAttachment2"), excelDataMap.get("pathAttachment3"),
								excelDataMap.get("pathAttachment4"), excelDataMap.get("pathAttachment5)"));
						break;
					
				}
			}
			
			LoggerLoad.logInfo("Assignment POST request created");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends HTTP POST Assignment Request")
	public void user_sends_http_post_assignment_request() 
	{
		try 
		{
			response = assignmentEndpoints.CreateAssignment(addAssignmentReq);
			LoggerLoad.logInfo("Assignment POST request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives response for POST {string} with {string}")
	public void user_receives_response_for_post_with(String sheetName, String dataKey) 
	{
		try 
		{
			response.then().log().all().extract().response();
			
			switch(dataKey)
			{
				case "Post_Assignment_Valid" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_CREATED)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getassignmentbyidjsonschema.json")));

					Assignment assignment = response.getBody().as(Assignment.class);
					assignmentId = assignment.assignmentId;
					System.out.println(assignment.assignmentId);
					existingAssignmentName = assignment.assignmentName;
					
					// Validate values in response
					
					break;
					
				case "Post_Assignment_Existing" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST);
					break;
			}
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	// Get All Assignments
	@Given("User creates GET Request for the LMS API endpoint")
	public void user_creates_get_request_for_the_lms_api_endpoint() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends GetAllAssignments Request")
	public void user_sends_GetAllAssignments_request() {
		response = request.get(AssignmentRoutes.getAllAssignments());
	}

	@Then("User receives OK Status with response body containing all assignments")
	public void user_receives_ok_status_with_response_body_containing_all_assignments() {

		response.then().statusCode(200);
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());

		JsonPath jsonPathEvaluator = response.jsonPath();
		List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
		System.out.print(assignmentList.get(0).assignmentId);

		response.then().assertThat().body(JsonSchemaValidator
				.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getassignmentsjsonschema.json")));

	}

	// Get Assignment by valid id
	@Given("User creates GET Request for the LMS API endpoint with valid Assignment ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_assignment_id() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends GetAssignmentByAssignmentid Request with valid id")
	public void user_sends_GetAssignmentByAssignmentid_request_with_valid_id() {
		response = request.get(AssignmentRoutes.getAssignmentByAssignmentId(assignmentId));
	}

	@Then("User receives OK Status with response body containing valid assignment")
	public void user_receives_ok_status_with_response_body_containing_valid_assignment() {
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());

		Assignment assignment = response.getBody().as(Assignment.class);
		System.out.print(assignment.assignmentId);

		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(
				getClass().getClassLoader().getResourceAsStream("getassignmentbyidjsonschema.json")));
	}

	// Get Assignment by invalid id
	@Given("User creates GET Request for the LMS API endpoint with invalid Assignment ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_assignment_id() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends GetAssignmentByAssignmentid Request with invalid id")
	public void user_sends_get_assignment_by_assignmentid_request_with_invalid_id() {
		response = request.get(AssignmentRoutes.getAssignmentByAssignmentId(1));
	}

	@Then("User receives Not Found Status with message and boolean success details")
	public void user_receives_not_found_status_with_message_and_boolean_success_details() {
		System.out.println(response.getStatusCode());
	}

	// Delete Assignment by id
	@Given("User creates DELETE Request for the LMS API endpoint with valid Assignment Id")
	public void user_creates_delete_request_for_the_lms_api_endpoint_with_valid_assignment_id() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends the Delete Request")
	public void user_sends_the_delete_request() {
		response = request.delete(AssignmentRoutes.deleteAssignmentById(assignmentId));
	}

	@Then("User receives Ok status with message")
	public void user_receives_ok_status_with_message() {
		System.out.println(response.getStatusCode());
	}

	// Delete Assignment by invalid id
	@Given("User creates DELETE Request for the LMS API endpoint with invalid Assignment Id")
	public void user_creates_delete_request_for_the_lms_api_endpoint_with_invalid_assignment_id() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends the Delete Request with invalid id")
	public void user_sends_the_delete_request_with_invalid_id() {
		response = request.delete(AssignmentRoutes.deleteAssignmentById(1));
	}

	@Then("User receives Not Found Status with valid message and boolean success details")
	public void user_receives_not_found_with_valid_message_and_boolean_details() {
		System.out.println(response.getStatusCode());
	}

}
