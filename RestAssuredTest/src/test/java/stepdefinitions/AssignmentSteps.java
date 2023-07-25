package stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import utilities.DynamicValues;
import utilities.LoggerLoad;

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
	static Assignment assignmentAdded;

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
		response = request.delete(ProgramBatchRoutes.deleteBatch(batchId,"valid"));
		
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
						addAssignmentReq = new AddAssignmentRequest(String.valueOf(assignmentAdded.assignmentName),
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
					
					// Validate values in response
					Assignment assignmentResponse = response.getBody().as(Assignment.class);
					
					assertTrue(assignmentResponse.assignmentId != null && assignmentResponse.assignmentId != 0);
					
					assertEquals(addAssignmentReq.assignmentName, assignmentResponse.assignmentName);
					assertEquals(addAssignmentReq.assignmentDescription, assignmentResponse.assignmentDescription);
					assertEquals(addAssignmentReq.comments, assignmentResponse.comments);
					assertEquals(addAssignmentReq.batchId, assignmentResponse.batchId);
					assertEquals(addAssignmentReq.createdBy, assignmentResponse.createdBy);
					assertEquals(addAssignmentReq.dueDate, assignmentResponse.dueDate);
					assertEquals(addAssignmentReq.graderId, assignmentResponse.graderId);
					assertEquals(addAssignmentReq.pathAttachment1, assignmentResponse.pathAttachment1);
					assertEquals(addAssignmentReq.pathAttachment2, assignmentResponse.pathAttachment2);
					assertEquals(addAssignmentReq.pathAttachment3, assignmentResponse.pathAttachment3);
					assertEquals(addAssignmentReq.pathAttachment4, assignmentResponse.pathAttachment4);
					assertEquals(addAssignmentReq.pathAttachment5, assignmentResponse.pathAttachment5);
					
					assignmentId = assignmentResponse.assignmentId;
					assignmentAdded = assignmentResponse;
					
					break;
					
				case "Post_Assignment_Existing" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("400statuscodejsonschema.json")));
					
					// Validate error json
					JsonPath jsonPathEvaluator = response.jsonPath();
					assertEquals(excelDataMap.get("message") + assignmentAdded.assignmentName + " ", jsonPathEvaluator.get("message"));
					assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
					
					break;
			}
			
			LoggerLoad.logInfo("Assignment POST response validated");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	// Get All Assignments
	@Given("User creates GET Request for the LMS API endpoint")
	public void user_creates_get_request_for_the_lms_api_endpoint() 
	{
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			LoggerLoad.logInfo("GET all assignments request created");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends GetAllAssignments Request")
	public void user_sends_GetAllAssignments_request() 
	{
		try
		{
			response = request.get(AssignmentRoutes.getAllAssignments());
			LoggerLoad.logInfo("GET all assignments request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives OK Status with response body containing all assignments")
	public void user_receives_ok_status_with_response_body_containing_all_assignments() 
	{
		try
		{
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_OK)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("getassignmentsjsonschema.json")));
	
			// Validate json response values
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
			
			List<Assignment> filteredAssignments = assignmentList.stream().filter((assignment) -> assignment.assignmentId.equals(assignmentId))
				.collect(Collectors.toList());
			
			assertTrue(filteredAssignments.size() == 1);
			assertEquals(assignmentAdded.assignmentName, filteredAssignments.get(0).assignmentName);
			assertEquals(assignmentAdded.assignmentDescription, filteredAssignments.get(0).assignmentDescription);
			assertEquals(assignmentAdded.comments, filteredAssignments.get(0).comments);
			assertEquals(assignmentAdded.batchId, filteredAssignments.get(0).batchId);
			assertEquals(assignmentAdded.createdBy, filteredAssignments.get(0).createdBy);
			assertEquals(assignmentAdded.dueDate, filteredAssignments.get(0).dueDate);
			assertEquals(assignmentAdded.graderId, filteredAssignments.get(0).graderId);
			assertEquals(assignmentAdded.pathAttachment1, filteredAssignments.get(0).pathAttachment1);
			assertEquals(assignmentAdded.pathAttachment2, filteredAssignments.get(0).pathAttachment2);
			assertEquals(assignmentAdded.pathAttachment3, filteredAssignments.get(0).pathAttachment3);
			assertEquals(assignmentAdded.pathAttachment4, filteredAssignments.get(0).pathAttachment4);
			assertEquals(assignmentAdded.pathAttachment5, filteredAssignments.get(0).pathAttachment5);
			
			LoggerLoad.logInfo("Get all assignments response validated");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	// Get Assignment by valid Assignment id
	@Given("User creates GET Request for the LMS API endpoint with {string} scenario")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_scenario(String string) 
	{
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			
			LoggerLoad.logInfo("Get assignment request created for " + string);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends {string} Request with valid id")
	public void user_sends_request_with_valid_id(String scenario)
	{
		try
		{
			switch(scenario)
			{
				case "Get_Assignment_ValidAssignmentId" :
					response = request.get(AssignmentRoutes.getAssignmentByAssignmentId(assignmentId));
					break;
					
				case "Get_Assignment_ValidBatchId" :
					response = request.get(AssignmentRoutes.getAssignmentByBatchId(batchId));
					break;
			}
	
			LoggerLoad.logInfo("Get assignment by id request sent");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives OK Status with response body containing valid assignment {string}")
	public void user_receives_ok_status_with_response_body_containing_valid_assignment(String scenario) 
	{
		try
		{	
			Assignment assignmentResponse = null;
			
			switch(scenario)
			{
				case "Get_Assignment_ValidAssignmentId" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getassignmentbyidjsonschema.json")));
					
					assignmentResponse = response.getBody().as(Assignment.class);
					break;
				
				case "Get_Assignment_ValidBatchId" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getassignmentsjsonschema.json")));
					
					JsonPath jsonPathEvaluator = response.jsonPath();
					List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
					assignmentResponse = assignmentList.get(0);
					break;
			}
	
			// Validate json response values
			
			assertEquals(assignmentAdded.assignmentId, assignmentResponse.assignmentId);
			assertEquals(assignmentAdded.assignmentName, assignmentResponse.assignmentName);
			assertEquals(assignmentAdded.assignmentDescription, assignmentResponse.assignmentDescription);
			assertEquals(assignmentAdded.comments, assignmentResponse.comments);
			assertEquals(assignmentAdded.batchId, assignmentResponse.batchId);
			assertEquals(assignmentAdded.createdBy, assignmentResponse.createdBy);
			assertEquals(assignmentAdded.dueDate, assignmentResponse.dueDate);
			assertEquals(assignmentAdded.graderId, assignmentResponse.graderId);
			assertEquals(assignmentAdded.pathAttachment1, assignmentResponse.pathAttachment1);
			assertEquals(assignmentAdded.pathAttachment2, assignmentResponse.pathAttachment2);
			assertEquals(assignmentAdded.pathAttachment3, assignmentResponse.pathAttachment3);
			assertEquals(assignmentAdded.pathAttachment4, assignmentResponse.pathAttachment4);
			assertEquals(assignmentAdded.pathAttachment5, assignmentResponse.pathAttachment5);
			
			LoggerLoad.logInfo("Get assignment by id response validated");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	// Delete Assignment by id
	@Given("User creates DELETE Request for the LMS API endpoint with {string} scenario")
	public void user_creates_delete_request_for_the_lms_api_endpoint_with_scenario(String string) 
	{
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			
			LoggerLoad.logInfo("DELETE assignment by id request for scenario " + string + " created");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends the HTTP Delete Request")
	public void user_sends_the_http_delete_request()
	{
		try
		{
			response = request.delete(AssignmentRoutes.deleteAssignmentById(assignmentId));
			
			LoggerLoad.logInfo("DELETE assignment by id request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives response for DELETE {string} with {string}")
	public void user_receives_response_for_delete_with(String sheetName, String dataKey) 
	{
		try
		{
			JsonPath jsonPathEvaluator = null;
			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			
			switch(dataKey)
			{
				case "Delete_Assignment_ValidId" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("200statuscodejsonschema.json")));
					
					// Validate response json
					jsonPathEvaluator = response.jsonPath();
					assertEquals(excelDataMap.get("message"), jsonPathEvaluator.get("message"));
					assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
					
					break;
				
				case "Delete_Assignment_DeletedId" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_NOT_FOUND)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("404statuscodejsonschema.json")));
					
					// Validate response json
					jsonPathEvaluator = response.jsonPath();
					assertEquals(excelDataMap.get("message") + assignmentAdded.assignmentId + " ", jsonPathEvaluator.get("message"));
					assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
					
					break;
			}
		
			LoggerLoad.logInfo("DELETE assignment by id response validated");
			
			Cleanup();	
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	// Get Assignment by invalid id
	@Given("User creates GET Request for the LMS API endpoint with {string}")
	public void user_creates_get_request_for_the_lms_api_endpoint_with(String string)
	{
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			
			LoggerLoad.logInfo("Get assignment by deleted id request created for " + string);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends {string} Request with invalid id")
	public void user_sends_request_with_invalid_id(String scenario)
	{
		try
		{
			switch(scenario)
			{
				case "Get_Assignment_DeletedAssignmentId" :
					response = request.get(AssignmentRoutes.getAssignmentByAssignmentId(assignmentId));
					break;
					
				case "Get_Assignment_DeletedBatchId" :
					response = request.get(AssignmentRoutes.getAssignmentByBatchId(batchId));
					break;
			}

			LoggerLoad.logInfo("Get assignment by deleted id request sent");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives Not Found Status with message and boolean success details {string}")
	public void user_receives_not_found_status_with_message_and_boolean_success_details(String scenario)
	{
		try
		{
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("404statuscodejsonschema.json")));
					
			// Validate response json
			JsonPath jsonPathEvaluator = response.jsonPath();
			excelDataMap = ExcelReader.getData(scenario, "assignment");
			
			switch(scenario)
			{
				case "Get_Assignment_DeletedAssignmentId" :
					assertEquals(excelDataMap.get("message") + assignmentId + " ", jsonPathEvaluator.get("message"));
					break;
					
				case "Get_Assignment_DeletedBatchId" :
					assertEquals(excelDataMap.get("message") + batchId + " ", jsonPathEvaluator.get("message"));
					break;
			}
			assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
					
			LoggerLoad.logInfo("Get assignment by deleted id response validated");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
