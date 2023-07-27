package stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Map;

import org.apache.http.HttpStatus;

import apiEngine.model.request.AddAssignmentRequest;
import apiEngine.model.request.AddBatchRequest;
import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.request.AddSubmitRequest;
import apiEngine.model.request.AddUserRequest;
import apiEngine.model.request.PutSubmission;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Batch;
import apiEngine.model.response.Program;
import apiEngine.model.response.Submission;
import apiEngine.model.response.User;
import dataProviders.ConfigReader;
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

public class AssignmentSubmitSteps extends BaseStep 
{
	
	RequestSpecification request;
	Response response;
	AddSubmitRequest addSubmitRequest;
	PutSubmission putsubmission;
	
	
	Map<String, String> excelDataMap;

	Program program;
	Batch batch;
	User user;
	Assignment assignment;

	static int programId;
	static int batchId;
	static String userId;
	static int assignmentId;
	static int submissionId;
	static Submission submissionAdded;
	
	
	public void SetupPreRequisites() 
	{

		try 
		{
			excelDataMap = null;
			AddProgramRequest programReq = null;
			AddBatchRequest batchReq = null;
			AddUserRequest userReq = null;
			AddAssignmentRequest assignmentReq = null;

			// create program
			excelDataMap = ExcelReader.getData("Post_Program_Assignment", "program");
			if (null != excelDataMap && excelDataMap.size() > 0) 
			{
				programReq = new AddProgramRequest(excelDataMap.get("programName") + DynamicValues.SerialNumber(),
						excelDataMap.get("programStatus"), excelDataMap.get("programDescription"));
			}

			response = programEndpoints.CreateProgram(programReq);
			if(response.statusCode() == 201)
			{
				program = response.getBody().as(Program.class);
				programId = program.programId;
				LoggerLoad.logInfo("Program created with id- " + programId);
			}
			else
			{
				LoggerLoad.logInfo("Program not created for assignment submit module");
			}

			// create batch
			excelDataMap = ExcelReader.getData("Post_Batch_Assignment", "batch");
			if (null != excelDataMap && excelDataMap.size() > 0) 
			{
				batchReq = new AddBatchRequest(excelDataMap.get("BatchName") + DynamicValues.SerialNumber(), 
						excelDataMap.get("BatchStatus"), excelDataMap.get("BatchDescription"), 
						Integer.parseInt(excelDataMap.get("NoOfClasses")), programId);
			}

			response = batchEndpoints.CreateBatch(batchReq);
			if(response.statusCode() == 201)
			{
				batch = response.getBody().as(Batch.class);
				batchId = batch.batchId;
				LoggerLoad.logInfo("Program batch created with id- " + batchId);
			}
			else
			{
				LoggerLoad.logInfo("Program Batch not created for assignment submit module");
			}

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
			if(response.statusCode() == 201)
			{
				user = response.getBody().as(User.class);
				userId = user.userId;
				LoggerLoad.logInfo("User created with id- " + userId);
			}
			else
			{
				LoggerLoad.logInfo("User not created for assignment submit module");
			}
			
			// create assignment
			excelDataMap = ExcelReader.getData("Post_Assignment_Submit", "assignment");
			if (null != excelDataMap && excelDataMap.size() > 0) 
			{
				assignmentReq = new AddAssignmentRequest(excelDataMap.get("assignmentName") + DynamicValues.SerialNumber(),
						excelDataMap.get("assignmentDescription"), batchId, excelDataMap.get("comments"), userId,
						excelDataMap.get("dueDate"), userId, excelDataMap.get("pathAttachment1"), excelDataMap.get("pathAttachment2"),
						excelDataMap.get("pathAttachment3"), excelDataMap.get("pathAttachment4"), excelDataMap.get("pathAttachment5"));
			}
			
			response = assignmentEndpoints.CreateAssignment(assignmentReq);
			if(response.statusCode() == 201)
			{
				assignment = response.getBody().as(Assignment.class);
				assignmentId = assignment.assignmentId;
				LoggerLoad.logInfo("Assignment created with id- " + assignmentId);
			}
			else
			{
				LoggerLoad.logInfo("Assignment not created for assignment submit module");
			}

		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}
	

	public void Cleanup() 
	{
		// delete assignment
		response = assignmentEndpoints.DeleteAssignmentById(assignmentId);
		if(response.statusCode() == 200)
			LoggerLoad.logInfo("Assignment deleted with id- " + assignmentId);
		else
			LoggerLoad.logInfo("Assignment not deleted for assignment submit module");
		
		// delete user
		response = userEndpoints.DeleteUserById(userId);
		if(response.statusCode() == 200)
			LoggerLoad.logInfo("User deleted with id- " + userId);
		else
			LoggerLoad.logInfo("User not deleted for assignment submit module");

		// delete batch
		response = batchEndpoints.DeleteBatchById(batchId);
		if(response.statusCode() == 200)
			LoggerLoad.logInfo("Program batch deleted with id- " + batchId);
		else
			LoggerLoad.logInfo("Program batch not deleted for assignment submit module");
		
		// delete program
		response = programEndpoints.DeleteProgramById(programId);
		if(response.statusCode() == 200)
			LoggerLoad.logInfo("Program deleted with id- " + programId);
		else
			LoggerLoad.logInfo("Program not deleted for assignment submit module");
	}

	
	@Given("User creates POST Assignment Submit Request with fields from {string} with {string}")
	public void user_creates_post_assignment_submit_request_with_fields_from_with(String sheetName, String dataKey) 
	{
		try 
		{
			// create program, batch, user and assignment for creating new assignment submission
			if (dataKey.equals("Post_AssignmentSubmit_Valid")) 
			{
				SetupPreRequisites();
			}
			
			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			Integer reqAssignmentId = null; 
			String reqUserId = null, subDesc = null, subComments = null, subPathAttach1 = null, subPathAttach2 = null, 
					subPathAttach3 = null, subPathAttach4 = null, subPathAttach5 = null, subDateTime = null;
			
			excelDataMap = ExcelReader.getData(dataKey, sheetName);

			if (excelDataMap != null && excelDataMap.size() > 0) 
			{
				if(!excelDataMap.get("subDesc").isBlank())
					subDesc = excelDataMap.get("subDesc");
				if(!excelDataMap.get("subDateTime").isBlank())
					subDateTime = excelDataMap.get("subDateTime");
				if(!dataKey.equals("Post_AssignmentSubmit_MissingAssignmentId"))
					reqAssignmentId = assignmentId;
				if(!dataKey.equals("Post_AssignmentSubmit_MissingUserId"))
					reqUserId = userId;
				
				subComments = excelDataMap.get("subComments");
				subPathAttach1 = excelDataMap.get("subPathAttach1");
				subPathAttach2 = excelDataMap.get("subPathAttach2");
				subPathAttach3 = excelDataMap.get("subPathAttach3");
				subPathAttach4 = excelDataMap.get("subPathAttach4");
				subPathAttach5 = excelDataMap.get("subPathAttach5");
			}
			
			addSubmitRequest = new AddSubmitRequest(reqAssignmentId, reqUserId,subDesc, subComments, subPathAttach1, 
					subPathAttach2, subPathAttach3, subPathAttach4, subPathAttach5, subDateTime);
			
			LoggerLoad.logInfo("Assignment Submit POST request created for- " + dataKey);
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends HTTP POST Assignment Submit Request for {string}")
	public void user_sends_http_post_assignment_submit_request_for(String dataKey) 
	{
		try 
		{
			response = submitEndpoints.CreateAssignmentSubmission(addSubmitRequest);
			LoggerLoad.logInfo("Assignment Submit POST request sent for- " + dataKey);
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives response for POST Assignment Submit {string} with {string}")
	public void user_receives_response_for_post_assignment_submit_with(String sheetName, String dataKey) 
	{
		try 
		{
			response.then().log().all().extract().response();
			
			switch(dataKey)
			{
				case "Post_AssignmentSubmit_Valid" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_CREATED)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getsubmissionjsonschema.json")));
					
					// Validate values in response
					Submission submissionResponse = response.getBody().as(Submission.class);
					
					assertTrue(submissionResponse.submissionId != null && submissionResponse.submissionId != 0);
					
					assertEquals(addSubmitRequest.assignmentId, submissionResponse.assignmentId);
					assertEquals(addSubmitRequest.subComments, submissionResponse.subComments);
					assertEquals(addSubmitRequest.userId, submissionResponse.userId);
					assertEquals(addSubmitRequest.subDesc, submissionResponse.subDesc);
					assertEquals(addSubmitRequest.subPathAttach1, submissionResponse.subPathAttach1);
					assertEquals(addSubmitRequest.subPathAttach2, submissionResponse.subPathAttach2);
					assertEquals(addSubmitRequest.subPathAttach3, submissionResponse.subPathAttach3);
					assertEquals(addSubmitRequest.subPathAttach4, submissionResponse.subPathAttach4);
					assertEquals(addSubmitRequest.subPathAttach5, submissionResponse.subPathAttach5);
					//assertEquals(addSubmitRequest.subDateTime, submissionResponse.subDateTime);
					assertTrue(submissionResponse.gradedBy == null);
					assertTrue(submissionResponse.gradedDateTime == null);
					assertTrue(submissionResponse.grade == -1);
					
					submissionId = submissionResponse.submissionId;
					submissionAdded = submissionResponse;
					
					break;
					
				default : 
					LoggerLoad.logInfo(response.jsonPath().prettyPrint());
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("400statuscodejsonschema.json")));
					
					// Validate error json
					JsonPath jsonPathEvaluator = response.jsonPath();
					assertEquals(excelDataMap.get("message"), jsonPathEvaluator.get("message"));
					assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
					
					break;
					
			}
			
			LoggerLoad.logInfo("Assignment POST response validated for- " + dataKey);
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	
	//Get_allsubmission
	
	@Given("Get request for all submission module")
	public void get_request_for_all_submission_module() {
	    
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

	@When("User sends HTTPS Request for submission module")
	public void user_sends_https_request_for_submission_module() {
	    
		try
		{
			response = submitEndpoints.GetAllAssignments();
			
			LoggerLoad.logInfo("GET all assignments request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
		
	}

	@Then("User receives response ok status with response body for submission module")
	public void user_receives_response_ok_status_with_response_body_for_submission_module() {
	    
		try
		{
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_OK)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("getallsubmissionjsonschema.json")));
			LoggerLoad.logInfo("Get All Assigment is validated");
		
	}catch(Exception ex) 
			{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}}

	//Get_grade_by_AssignmentId
	
	@Given("Check if user able to retrieve a grades with valid Assignment ID")
	public void check_if_user_able_to_retrieve_a_grades_with_valid_assignment_id() {
		
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			LoggerLoad.logInfo("Get assignments by Id request created");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	   
	}

	@When("User creates GET Request for the LMS API endpoint with {string} and {string} valid Assignment ID in submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_and_valid_assignment_id_in_submission_module(String sheetName, String datakey) {
	    
		try {
			switch(datakey)
			{
			case "Get_Valid_assignmentid":
				response= submitEndpoints.GetgradebyAssignmentid(assignmentId);
				LoggerLoad.logInfo("Assignment ID" +assignmentId);
				break;
				
			case "Get_InValid_assignmentid":
				
				Map<String, String> excelDataMap = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.GetgradebyAssignmentid(Integer.parseInt(excelDataMap.get("AssignmentId")));
				System.out.println(response.asPrettyString());
				break;
			
			}
		LoggerLoad.logInfo("Get assignment by id request sent for- " + datakey);
	}
	catch (Exception ex) 
	{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}
		
	}

	@Then("User receives response ok status with response body for assignment ID in submission module")
	public void user_receives_response_ok_status_with_response_body_for_assignment_id_in_submission_module() {
	    
		try
		{
			if(response.statusCode()==200) {
				
				response.asPrettyString();
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_OK)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("getsubmissionjsonschema.json")));
				
				
			}else if(response.statusCode()==404)
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("404statuscodejsonschema.json")));
			
			LoggerLoad.logInfo("Get submission based on assignment Id");
		
	}catch(Exception ex) 
			{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}}
	
	//Get_Grade_by_Valid_StudentID
	
	@Given("User creates GET Request for the LMS API endpoint with valid Student Id for submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_student_id_for_submission_module() {
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			LoggerLoad.logInfo("Get request by studentId");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	   
		
	}

	@When("User sends HTTPS Request for Student ID for {string} and {string} submission module")
	public void user_sends_https_request_for_student_id_for_and_submission_module(String sheetName, String datakey) {
	    
		try {
			switch(datakey)
			{
			case "Get_Valid_StudentId":
				Map<String, String> excelDataMap = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.GetgradebyStudentId(excelDataMap.get("StudentID"));
				System.out.println(response.asPrettyString());
				
				break;
				
			case "Get_InValid_StudentId":
				Map<String, String> excelDataMap1 = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.GetgradebyStudentId(excelDataMap1.get("StudentID"));
				System.out.println(response.asPrettyString());
				break;
			
			}
		LoggerLoad.logInfo("Get assignment by Student id is validated- " + datakey);
	}
	catch (Exception ex) 
	{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}
	}

	@Then("User receives response ok status with response body for student ID for submission module")
	public void user_receives_response_ok_status_with_response_body_for_student_id_for_submission_module() {
	    
		try
		{
			if(response.statusCode()==200) {
				
				response.asPrettyString();
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_OK)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("getsubmissionstudentId.json")));
				
				
			}else if(response.statusCode()==404)
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("404statuscodejsonschema.json")));
			
			LoggerLoad.logInfo("Get submission based on Student Id");
		
	}catch(Exception ex) 
			{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}}
	
		
	
		
	// Get_gradeby_BatchID
	
	@Given("User creates GET Request for the LMS API endpoint with valid Batch Id for submission module")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_batch_id_for_submission_module() {
	    
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			LoggerLoad.logInfo("Get request by BatchId");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
		
	}

	@When("User sends HTTPS Request for Batch ID for {string} and  {string} submission module")
	public void user_sends_https_request_for_batch_id_for_and_submission_module(String sheetName, String datakey) {
	    
		try {
			switch(datakey)
			{
			//pass from excel
			case "Get_Valid_BatchId":
				Map<String, String> excelDataMap = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.GetgradebyBatchId(Integer.parseInt(excelDataMap.get("batchID")));
				System.out.println(response.asPrettyString());
				break;
				
			case "Get_InValid_BatchId":
				response= submitEndpoints.GetgradebyBatchId(batchId);
				System.out.println(response.asPrettyString());
				break;
			
			}
		LoggerLoad.logInfo("Get grade by Batchid request is validated " + datakey);
	}
	catch (Exception ex) 
	{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}
		
	}

	@Then("User receives response ok status with response body for Batch ID for submission module")
	public void user_receives_response_ok_status_with_response_body_for_batch_id_for_submission_module() {
		try
		{
			if(response.statusCode()==200) {
				
				response.asPrettyString();
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_OK)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("getsubmissionstudentId.json")));
				
				
			}else if(response.statusCode()==404)
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("404statuscodejsonschema.json")));
			
			LoggerLoad.logInfo("Get submission based on batch Id");
		
	}catch(Exception ex) 
			{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}}
		
	   
	//Get_Submission_by_BatchID
	
	@Given("User creates GET Request for the LMS API endpoint with valid Batch Id for submission")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_batch_id_for_submission() {
		
		 
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			LoggerLoad.logInfo("Get request by BatchId");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
		
	    
	}

	@When("User sends HTTPS Request for submission by Batch ID for {string} and {string} submission module")
	public void user_sends_https_request_for_submission_by_batch_id_for_and_submission_module(String sheetName, String datakey) {
	    
		try {
			switch(datakey)
			{
			case "Get_Valid_batchIdbysubmission":
				
				Map<String, String> excelDataMap = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.getsubmissionbybatchId(Integer.parseInt(excelDataMap.get("batchID")));
				System.out.println(response.asPrettyString());
				break;
				
			case "Get_InValid_batchIdbysubmission":
				Map<String, String> excelDataMap1 = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.getsubmissionbybatchId(Integer.parseInt(excelDataMap1.get("batchID")));
				System.out.println(response.asPrettyString());
				break;
			
			}
		LoggerLoad.logInfo("Get assignment by Batch_id request sent for- " + datakey);
	}
	catch (Exception ex) 
	{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}
		
	}

	@Then("User receives response ok status with response body for submission by Batch ID")
	public void user_receives_response_ok_status_with_response_body_for_submission_by_batch_id() {
		
		try
		{
			if(response.statusCode()==200) {
				
				response.asPrettyString();
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_OK)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("getsubmissionjsonschema.json")));
				
				
			}else if(response.statusCode()==404)
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("404statuscodejsonschema.json")));
			
			LoggerLoad.logInfo("Get submission based on batch Id");
		
	}catch(Exception ex) 
			{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}}

	//Get_Submission_by_Valid_UserID
	
	@Given("User creates GET Request for the LMS API endpoint with valid User Id for submission")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_user_id_for_submission() {
	    
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			LoggerLoad.logInfo("Get request by BatchId");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends HTTPS Request for submission by UserID {string} and {string}")
	public void user_sends_https_request_for_submission_by_user_id_and(String sheetName, String datakey) {
		
		try {
			switch(datakey)
			{
			case "Get_Valid_userId":
				Map<String, String> excelDataMap = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.getsubmissionbyUserId(excelDataMap.get("UserID"));
				System.out.println(response.asPrettyString());
				break;
				
			case "Get_InValid_userId":
				Map<String, String> excelDataMap1 = ExcelReader.getData(datakey, sheetName);
				response= submitEndpoints.getsubmissionbyUserId(excelDataMap1.get("UserID"));
				System.out.println(response.asPrettyString());
				break;
			
			}
		LoggerLoad.logInfo("Get assignment submission by User id request sent for- " + datakey);
	}
	catch (Exception ex) 
	{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}
	    
	}

	@Then("User receives response ok status with response body for submission by UserID")
	public void user_receives_response_ok_status_with_response_body_for_submission_by_user_id() {
		
		try
		{
			if(response.statusCode()==200) {
				
				response.asPrettyString();
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_OK)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("getsubmissionjsonschema.json")));
				
				
			}else if(response.statusCode()==404)
			response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
					getClass().getClassLoader().getResourceAsStream("404statuscodejsonschema.json")));
			
			LoggerLoad.logInfo("Get submission based on batch Id");
		
	}catch(Exception ex) 
			{
		LoggerLoad.logInfo(ex.getMessage());
		ex.printStackTrace();
	}}
	
	//Put Resumbit request
	
	@Given("User creates PUT Request for Resubmitting a submission module in the LMS API submissionModule {string} with {string}")
	public void user_creates_put_request_for_resubmitting_a_submission_module_in_the_lms_api_submission_module_with(String sheetName, String dataKey) { {
	   
		try 
		{	
			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");
			
			
			String gradedBy=null;
			String gradedDateTime=null;
			String grade=null;
			String subDateTime=null;
			String subPathAttach5=null;
			String subPathAttach4=null;
			String subPathAttach3=null;
			String subPathAttach2=null;
			Serializable subDesc=null;
			String subComments=null;
			String subPathAttach1=null;
			
			excelDataMap = ExcelReader.getData(dataKey, sheetName);

			switch(dataKey) {
			
			case "Put_Submission_Valid":
												
				subComments = excelDataMap.get("subComments");
				subDesc = excelDataMap.get("subDesc");
				subPathAttach1 = excelDataMap.get("subPathAttach1");
				subPathAttach2 = excelDataMap.get("subPathAttach2");
				subPathAttach3 = excelDataMap.get("subPathAttach3");
				subPathAttach4 = excelDataMap.get("subPathAttach4");
				subPathAttach5 = excelDataMap.get("subPathAttach5");
				subDateTime = excelDataMap.get("subDateTime");
				gradedBy=excelDataMap.get("gradedBy");
				gradedDateTime=excelDataMap.get("gradedDateTime");
				grade=excelDataMap.get("grade");
				
			
			
			putsubmission = new PutSubmission(submissionId,assignmentId, userId, subDesc, subComments,
					subPathAttach1, subPathAttach2, subPathAttach3,subPathAttach4,
					subPathAttach5, subDateTime, gradedBy, gradedDateTime, grade);
			
			System.out.println(+submissionId);
			System.out.println(+assignmentId);
			System.out.println(userId);
			
			
			LoggerLoad.logInfo("Assignment PUT request created");
			
			break;
			
			case "Put_Submission_Invalid":
				
				subComments = excelDataMap.get("subComments");
				subDateTime = excelDataMap.get("subDateTime");
				subDesc = excelDataMap.get("subDesc");
				subPathAttach1 = excelDataMap.get("subPathAttach1");
				subPathAttach2 = excelDataMap.get("subPathAttach2");
				subPathAttach3 = excelDataMap.get("subPathAttach3");
				subPathAttach4 = excelDataMap.get("subPathAttach4");
				subPathAttach5 = excelDataMap.get("subPathAttach5");
				gradedBy=excelDataMap.get("gradedBy");
				gradedDateTime=excelDataMap.get("gradedDateTime");
				grade=excelDataMap.get("grade");
				
				putsubmission = new PutSubmission(submissionId,assignmentId, userId, subDesc, subComments,
						subPathAttach1, subPathAttach2, subPathAttach3,subPathAttach4,
						subPathAttach5, subDateTime, gradedBy, gradedDateTime, grade);
				
				System.out.println(+submissionId);
				System.out.println(+assignmentId);
				System.out.println(userId);
				
			LoggerLoad.logInfo("Assignment PUT request is Not created");
		} }
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
	}
	@When("User sends PUT Request for Resubmitting with mandatory and additional fields  {string} with {string}")
	public void user_sends_put_request_for_resubmitting_with_mandatory_and_additional_fields_with(String sheetName, String dataKey) {
	    
		try 
		{
			if(dataKey.equals("Put_Submission_Invalid"))
			{
				response = submitEndpoints.PutupdateReAssignment(putsubmission, Integer.parseInt(ConfigReader.getInvalidAssignmentId()));
			}
			else
				response = submitEndpoints.PutupdateReAssignment(putsubmission, submissionId);
			
			LoggerLoad.logInfo("Assignment PUT request sent for - " + dataKey);
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
		
	}

	@Then("User receives Status with response body for put resubmit {string} with {string} in submission module")
	public void user_receives_status_with_response_body_for_put_resubmit_with_in_submission_module(String sheetName, String dataKey) {
		
		try 
		{
			response.then().log().all().extract().response();
			
			switch(dataKey)
			{
				case "Put_Submission_Valid" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_CREATED)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getsubmissionjsonschema.json")));
					
					// Validate values in response
					Submission submissionResponse = response.getBody().as(Submission.class);
					
					assertTrue(submissionResponse.submissionId != null && submissionResponse.submissionId != 0);
					
					assertEquals(putsubmission.assignmentId, submissionResponse.assignmentId);
					assertEquals(putsubmission.subcomments, submissionResponse.subComments);
					assertEquals(putsubmission.userId, submissionResponse.userId);
					assertEquals(putsubmission.subDesc, submissionResponse.subDesc);
					assertEquals(putsubmission.subPathAttach1, submissionResponse.subPathAttach1);
					assertEquals(putsubmission.subPathAttach2, submissionResponse.subPathAttach2);
					assertEquals(putsubmission.subPathAttach3, submissionResponse.subPathAttach3);
					assertEquals(putsubmission.subPathAttach4, submissionResponse.subPathAttach4);
					assertEquals(putsubmission.subPathAttach5, submissionResponse.subPathAttach5);
					assertTrue(submissionResponse.gradedBy == null);
					assertTrue(submissionResponse.gradedDateTime == null);
					assertTrue(submissionResponse.grade == -1);
					
					submissionId = submissionResponse.submissionId;
					submissionAdded = submissionResponse;
					
					break;
					
				default : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("400statuscodejsonschema.json")));
					
					// Validate error json
					JsonPath jsonPathEvaluator = response.jsonPath();
					assertEquals(excelDataMap.get("message"), jsonPathEvaluator.get("message"));
					assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
					
					break;
					
			}
			
			LoggerLoad.logInfo("AssignmentSubmission Put response validated for- " + dataKey);
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	
	
	//Put_updategrade
	
	
		@Given("User creates PUT Request for updating a grade in submission module in the LMS API submissionModule")
		public void user_creates_put_request_for_updating_a_grade_in_submission_module_in_the_lms_api_submission_module(String sheetName,String dataKey) {
			
			try 
			{	
				RestAssured.baseURI = baseUrl;
				RequestSpecification request = RestAssured.given();
				request.header("Content-Type", "application/json");
				
				
				String gradedBy=null;
				String gradedDateTime=null;
				String grade=null;
				String subDateTime=null;
				String subPathAttach5=null;
				String subPathAttach4=null;
				String subPathAttach3=null;
				String subPathAttach2=null;
				Serializable subDesc=null;
				String subComments=null;
				String subPathAttach1=null;
				
				excelDataMap = ExcelReader.getData(dataKey, sheetName);

				switch(dataKey) {
				
				case "Put_GradeSubmission_Valid":
													
					subComments = excelDataMap.get("subComments");
					subDesc = excelDataMap.get("subDesc");
					subPathAttach1 = excelDataMap.get("subPathAttach1");
					subPathAttach2 = excelDataMap.get("subPathAttach2");
					subPathAttach3 = excelDataMap.get("subPathAttach3");
					subPathAttach4 = excelDataMap.get("subPathAttach4");
					subPathAttach5 = excelDataMap.get("subPathAttach5");
					subDateTime = excelDataMap.get("subDateTime");
					gradedBy=excelDataMap.get("gradedBy");
					gradedDateTime=excelDataMap.get("gradedDateTime");
					grade=excelDataMap.get("grade");
					
				
				
				putsubmission = new PutSubmission(submissionId,assignmentId, userId, subDesc, subComments,
						subPathAttach1, subPathAttach2, subPathAttach3,subPathAttach4,
						subPathAttach5, subDateTime, gradedBy, gradedDateTime, grade);
				
				LoggerLoad.logInfo("Assignment PUT request created");
				
				break;
				
				case "Put_GradeSubmission_InValid":
					
					subComments = excelDataMap.get("subComments");
					subDateTime = excelDataMap.get("subDateTime");
					subDesc = excelDataMap.get("subDesc");
					subPathAttach1 = excelDataMap.get("subPathAttach1");
					subPathAttach2 = excelDataMap.get("subPathAttach2");
					subPathAttach3 = excelDataMap.get("subPathAttach3");
					subPathAttach4 = excelDataMap.get("subPathAttach4");
					subPathAttach5 = excelDataMap.get("subPathAttach5");
					gradedBy=excelDataMap.get("gradedBy");
					gradedDateTime=excelDataMap.get("gradedDateTime");
					grade=excelDataMap.get("grade");
					
					putsubmission = new PutSubmission(submissionId,assignmentId, userId, subDesc, subComments,
							subPathAttach1, subPathAttach2, subPathAttach3,subPathAttach4,
							subPathAttach5, subDateTime, gradedBy, gradedDateTime, grade);
					
				LoggerLoad.logInfo("Assignment PUT request is Not created");
			} }
			catch (Exception ex) 
			{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		    
		

		@When("User sends PUT Request for Grade submission with mandatory and additional fields  {string} with {string}")
		public void user_sends_put_request_for_grade_submission_with_mandatory_and_additional_fields_with(String sheetName, String dataKey) {
			
			try 
			{
				if(dataKey.equals("Put_GradeSubmission_InValid"))
				{
					response = submitEndpoints.PutupdateReAssignment(putsubmission, Integer.parseInt(ConfigReader.getInvalidAssignmentId()));
				}
				else
					response = submitEndpoints.PutupdateReAssignment(putsubmission, submissionId);
				
				LoggerLoad.logInfo("Assignment PUT request sent for - " + dataKey);
			} 
			catch (Exception ex) 
			{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
			}
		    
		}

		@Then("User receives Status with response body for put Grade {string} with {string} in submission module")
		public void user_receives_status_with_response_body_for_put_grade_with_in_submission_module(String sheetName, String dataKey) {
		   
			try 
			{
				response.then().log().all().extract().response();
				
				switch(dataKey)
				{
					case "Put_GradeSubmission_Valid" : 
						response.then().assertThat()
							// Validate response status
							.statusCode(HttpStatus.SC_CREATED)
							// Validate content type
							.contentType(ContentType.JSON)
							// Validate json schema
							.body(JsonSchemaValidator.matchesJsonSchema(
								getClass().getClassLoader().getResourceAsStream("getsubmissionjsonschema.json")));
						
						// Validate values in response
						Submission submissionResponse = response.getBody().as(Submission.class);
						
						assertTrue(submissionResponse.submissionId != null && submissionResponse.submissionId != 0);
						
						assertEquals(putsubmission.assignmentId, submissionResponse.assignmentId);
						assertEquals(putsubmission.subcomments, submissionResponse.subComments);
						assertEquals(putsubmission.userId, submissionResponse.userId);
						assertEquals(putsubmission.subDesc, submissionResponse.subDesc);
						assertEquals(putsubmission.subPathAttach1, submissionResponse.subPathAttach1);
						assertEquals(putsubmission.subPathAttach2, submissionResponse.subPathAttach2);
						assertEquals(putsubmission.subPathAttach3, submissionResponse.subPathAttach3);
						assertEquals(putsubmission.subPathAttach4, submissionResponse.subPathAttach4);
						assertEquals(putsubmission.subPathAttach5, submissionResponse.subPathAttach5);
						assertEquals(putsubmission.gradedBy, submissionResponse.gradedBy);
						assertEquals(putsubmission.grade, submissionResponse.grade);
						
						submissionId = submissionResponse.submissionId;
						submissionAdded = submissionResponse;
						
						break;
						
					default : 
						response.then().assertThat()
							// Validate response status
							.statusCode(HttpStatus.SC_BAD_REQUEST)
							// Validate json schema
							.body(JsonSchemaValidator.matchesJsonSchema(
								getClass().getClassLoader().getResourceAsStream("400statuscodejsonschema.json")));
						
						// Validate error json
						JsonPath jsonPathEvaluator = response.jsonPath();
						assertEquals(excelDataMap.get("message"), jsonPathEvaluator.get("message"));
						assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
						
						break;
						
				}
				
				LoggerLoad.logInfo("AssignmentSubmission Put grade response validated for- " + dataKey);
			} 
			catch (Exception ex) 
			{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
			}

		}

		

	//Delete submission ID
	
	@Given("User creates DELETE Request with {string} scenario")
	public void user_creates_delete_request_with_scenario(String string) 
	{
		try
		{
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			
			LoggerLoad.logInfo("DELETE assignment submit by id request for scenario " + string + " created");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends the HTTP Delete Assignment Submit Request {string}")
	public void user_sends_the_http_delete_assignment_submit_request(String string) 
	{
		try
		{
			response = submitEndpoints.DeleteSubmissionById(submissionId);
			
			LoggerLoad.logInfo("DELETE assignment submit by id request sent for - " + string);
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives response for DELETE Assignment Submit {string} with {string}")
	public void user_receives_response_for_delete_assignment_submit_with(String sheetName, String dataKey) 
	{
		try
		{
			JsonPath jsonPathEvaluator = null;
			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			
			switch(dataKey)
			{
				case "Delete_AssignmentSubmit_ValidId" :
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
				
				case "Delete_AssignmentSubmit_DeletedId" :
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
					assertEquals(excelDataMap.get("message") + submissionAdded.submissionId + " ", jsonPathEvaluator.get("message"));
					assertEquals(excelDataMap.get("success"), Boolean.toString(jsonPathEvaluator.get("success")));
					
					break;
			}
		
			LoggerLoad.logInfo("DELETE assignment submission by id response validated for- " + dataKey);
			
			if(dataKey.equals("Delete_AssignmentSubmit_ValidId"))
				Cleanup();	
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
}