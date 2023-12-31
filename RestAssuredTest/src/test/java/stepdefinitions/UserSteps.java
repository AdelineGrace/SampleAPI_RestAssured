package stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;

import apiEngine.model.request.AddAssignmentRequest;
import apiEngine.model.request.AddBatchRequest;
import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.request.AddUserRequest;
import apiEngine.model.request.AssignUserRoleProgramBatchStatus;
import apiEngine.model.request.UpdateUserRequest;
import apiEngine.model.request.UserRoleMap;
import apiEngine.model.request.UserRoleProgramBatches;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Batch;
import apiEngine.model.response.Program;
import apiEngine.routes.AssignmentRoutes;
import apiEngine.routes.ProgramBatchRoutes;
import apiEngine.routes.ProgramRoutes;
import apiEngine.routes.UserRoutes;
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
import utilities.RestAssuredRequestFilter;
import apiEngine.model.response.User;

public class UserSteps extends BaseStep {

	static final String baseUrl = ConfigReader.getProperty("base.url");
	RequestSpecification request;
	Response response;
	AddUserRequest addUserRequest;
	UpdateUserRequest updateUserRequest;
	UserRoleMap userRoleMap;
	AssignUserRoleProgramBatchStatus assignUserRoleProgramBatchStatus;
	int errorStatusCode = 0;
	Map<String, String> excelDataMap;

	Program program;
	Batch batch;
	static List<String> users = new ArrayList<String>();
	static int programId;
	static int batchId;
	static int existingPhoneNumber;
	static int assignmentId;
	static String existingAssignmentName;
	static String roleId;

	public void SetupPreRequisites() {

		try {
			excelDataMap = null;
			AddProgramRequest programReq = null;
			AddBatchRequest batchReq = null;

			// create program
			excelDataMap = ExcelReader.getData("Post_Program_Assignment", "program");
			if (null != excelDataMap && excelDataMap.size() > 0) {
				programReq = new AddProgramRequest(excelDataMap.get("programName") + DynamicValues.SerialNumber(),
						excelDataMap.get("programStatus"), excelDataMap.get("programDescription"));
			}

			response = programEndpoints.CreateProgram(programReq);
			response.then().log().all();
			program = response.getBody().as(Program.class);
			programId = program.programId;
			System.out.println("Program id - " + programId);

			// create batch
			excelDataMap = ExcelReader.getData("Post_Batch_Assignment", "batch");
			if (null != excelDataMap && excelDataMap.size() > 0) {
				batchReq = new AddBatchRequest(excelDataMap.get("BatchName") + DynamicValues.SerialNumber(),
						excelDataMap.get("BatchStatus"), excelDataMap.get("BatchDescription"),
						Integer.parseInt(excelDataMap.get("NoOfClasses")), programId);
			}

			response = batchEndpoints.CreateBatch(batchReq);
			response.then().log().all();
			batch = response.getBody().as(Batch.class);
			batchId = batch.batchId;

			

		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void Cleanup() {

		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			String userId = (String) iterator.next();
			RestAssured.baseURI = baseUrl;
			request = RestAssured.given();
			response = request.delete(UserRoutes.deleteUserById(userId));
			response.then().log().all();
		}
		// delete user

		// delete batch
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.delete(ProgramBatchRoutes.deleteBatch(batchId, "valid"));
		response.then().log().all();

		// delete program
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.delete(ProgramRoutes.deleteProgramById(programId));
		response.then().log().all();
	}

	@Given("User creates POST User Request for the LMS API with fields from {string} with {string}")
	public void user_creates_post_user_request_for_the_lms_api_with_fields_from_with(String sheetName, String dataKey) {
		try {
			// create program, batch for creating new user
			if (dataKey.equals("Post_User_Valid")) {
				SetupPreRequisites();
			}
			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given().filter(new RestAssuredRequestFilter());
			request.header("Content-Type", "application/json");

			excelDataMap = null;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			if (excelDataMap != null && excelDataMap.size() > 0) {
				switch (dataKey) {
				case "Post_User_Valid":
					existingPhoneNumber = DynamicValues.PhoneNumber();
					roleId = excelDataMap.get("roleId");
					addUserRequest = new AddUserRequest(
							excelDataMap.get("userFirstName") + DynamicValues.SerialNumber(),
							excelDataMap.get("userLastName"), excelDataMap.get("userMiddleName"),
							excelDataMap.get("userComments"), excelDataMap.get("userEduPg"),
							excelDataMap.get("userEduUg"), excelDataMap.get("userLinkedinUrl"),
							excelDataMap.get("userLocation"), existingPhoneNumber, excelDataMap.get("roleId"),
							excelDataMap.get("userRoleStatus"), excelDataMap.get("userTimeZone"),
							excelDataMap.get("userVisaStatus"));
					LoggerLoad.logDebug(addUserRequest.toString());
					break;
				case "Post_User_Existing":
					addUserRequest = new AddUserRequest(
							excelDataMap.get("userFirstName") + DynamicValues.SerialNumber(),
							excelDataMap.get("userLastName"), excelDataMap.get("userMiddleName"),
							excelDataMap.get("userComments"), excelDataMap.get("userEduPg"),
							excelDataMap.get("userEduUg"), excelDataMap.get("userLinkedinUrl"),
							excelDataMap.get("userLocation"), existingPhoneNumber, excelDataMap.get("roleId"),
							excelDataMap.get("userRoleStatus"), excelDataMap.get("userTimeZone"),
							excelDataMap.get("userVisaStatus"));
					LoggerLoad.logDebug(addUserRequest.toString());
					break;
				case "Post_User_Missing_PhoneNumber":
				case "Post_User_Missing_FirstName":
				case "Post_User_Missing_LastName":
				case "Post_User_Missing_RoleId":
				case "Post_User_Missing_RoleStatus":
				case "Post_User_Missing_TimeZone":
				case "Post_User_Missing_VisaStatus":
					Integer phoneNumber = null;
					if (!dataKey.equals("Post_User_Missing_PhoneNumber"))
						phoneNumber = DynamicValues.PhoneNumber();
					String userFirstName = null;
					if (!dataKey.equals("Post_User_Missing_FirstName"))
						userFirstName = excelDataMap.get("userFirstName") + DynamicValues.SerialNumber();

					addUserRequest = new AddUserRequest(userFirstName, excelDataMap.get("userLastName"),
							excelDataMap.get("userMiddleName"), excelDataMap.get("userComments"),
							excelDataMap.get("userEduPg"), excelDataMap.get("userEduUg"),
							excelDataMap.get("userLinkedinUrl"), excelDataMap.get("userLocation"), phoneNumber,
							excelDataMap.get("roleId"), excelDataMap.get("userRoleStatus"),
							excelDataMap.get("userTimeZone"), excelDataMap.get("userVisaStatus"));
					LoggerLoad.logDebug(addUserRequest.toString());
					break;
				}
			}
			LoggerLoad.logInfo("Assignment POST request created");
		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();

		}
	}

	@When("User sends HTTP POST User Request")
	public void user_sends_http_post_user_request() {
		try {
			response = userEndpoints.CreateUser(addUserRequest);
			LoggerLoad.logInfo("User POST request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}
	}

	@Then("User receives response for POST User {string} with {string}")
	public void user_receives_response_for_post_user_with(String sheetName, String dataKey) {
		try {
			response.then().log().all().extract().response();

			switch (dataKey) {
			case "Post_User_Valid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_CREATED)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
								getClass().getClassLoader().getResourceAsStream("userjsonschema.json")));

				User user = response.getBody().as(User.class);
				assertTrue(user.userId.startsWith("U"));
				assertEquals(user.userComments,addUserRequest.userComments);
				assertEquals(user.userEduPg,addUserRequest.userEduPg);
				assertEquals(user.userEduUg,addUserRequest.userEduUg);
				assertEquals(user.userFirstName,addUserRequest.userFirstName);
				assertEquals(user.userLastName,addUserRequest.userLastName);
				assertEquals(user.userLinkedinUrl,addUserRequest.userLinkedinUrl);
				assertEquals(user.userLocation,addUserRequest.userLocation);
				assertEquals(user.userMiddleName,addUserRequest.userMiddleName);
				assertEquals(user.userPhoneNumber,addUserRequest.userPhoneNumber);
				assertEquals(user.userTimeZone,addUserRequest.userTimeZone);
				assertEquals(user.userVisaStatus,addUserRequest.userVisaStatus);
				users.add(user.userId);
				break;
			case "Post_User_Existing":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;
			case "Post_User_Missing_PhoneNumber":
			case "Post_User_Missing_FirstName":
			case "Post_User_Missing_LastName":
			case "Post_User_Missing_RoleId":
			case "Post_User_Missing_RoleStatus":
			case "Post_User_Missing_TimeZone":
			case "Post_User_Missing_VisaStatus":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;

			}

		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}
	}

	@Given("User creates PUT User Request for the LMS API with fields from {string} with {string}")
	public void user_creates_put_user_request_for_the_lms_api_with_fields_from_with(String sheetName, String dataKey) {
		try {

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			excelDataMap = null;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			if (excelDataMap != null && excelDataMap.size() > 0) {
				switch (dataKey) {
				case "Put_User_Valid":
					existingPhoneNumber = DynamicValues.PhoneNumber();
					updateUserRequest = new UpdateUserRequest(
							excelDataMap.get("userFirstName") + DynamicValues.SerialNumber(),
							excelDataMap.get("userLastName"), excelDataMap.get("userMiddleName"),
							excelDataMap.get("userComments"), excelDataMap.get("userEduPg"),
							excelDataMap.get("userEduUg"), excelDataMap.get("userLinkedinUrl"),
							excelDataMap.get("userLocation"), existingPhoneNumber, excelDataMap.get("userTimeZone"),
							excelDataMap.get("userVisaStatus"));
					LoggerLoad.logDebug(updateUserRequest.toString());
					break;

				case "Put_User_Missing_PhoneNumber":
				case "Put_User_Missing_FirstName":
				case "Put_User_Missing_LastName":
				case "Put_User_Missing_TimeZone":
				case "Put_User_Missing_VisaStatus":
					Integer phoneNumber = null;
					if (!dataKey.equals("Put_User_Missing_PhoneNumber"))
						phoneNumber = DynamicValues.PhoneNumber();
					String userFirstName = null;
					if (!dataKey.equals("Put_User_Missing_FirstName"))
						userFirstName = excelDataMap.get("userFirstName") + DynamicValues.SerialNumber();

					updateUserRequest = new UpdateUserRequest(userFirstName, excelDataMap.get("userLastName"),
							excelDataMap.get("userMiddleName"), excelDataMap.get("userComments"),
							excelDataMap.get("userEduPg"), excelDataMap.get("userEduUg"),
							excelDataMap.get("userLinkedinUrl"), excelDataMap.get("userLocation"), phoneNumber,
							excelDataMap.get("userTimeZone"), excelDataMap.get("userVisaStatus"));
					LoggerLoad.logDebug(updateUserRequest.toString());
					break;
				}
			}
			LoggerLoad.logInfo("Assignment POST request created");
		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();

		}
	}

	@When("User sends HTTP PUT User Request {string}")
	public void user_sends_http_put_user_request(String dataKey) {
		try {
			String userId = null;
			if (!users.isEmpty()) {
				userId = users.get(0);
			}
			LoggerLoad.logInfo("userId PUT " + userId);
			response = userEndpoints.UpdateUser(updateUserRequest, userId, dataKey);
			LoggerLoad.logInfo("User PUT request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}
	}

	@Then("User receives response for PUT User {string} with {string}")
	public void user_receives_response_for_put_user_with(String sheetName, String dataKey) {
		try {
			response.then().log().all().extract().response();

			switch (dataKey) {
			case "Put_User_Valid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
								getClass().getClassLoader().getResourceAsStream("userjsonschema.json")));

				User user = response.getBody().as(User.class);
				assertTrue(user.userId.startsWith("U"));
				users.add(user.userId);
				break;

			case "Put_User_Missing_PhoneNumber":
			case "Put_User_Missing_FirstName":
			case "Put_User_Missing_LastName":
			case "Put_User_Missing_RoleId":
			case "Put_User_Missing_RoleStatus":
			case "Put_User_Missing_TimeZone":
			case "Put_User_Missing_VisaStatus":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;
			case "Put_User_Invalid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_NOT_FOUND)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;

			}

		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}

	}

	@Given("User creates PUT User Role Request for the LMS API with fields from {string} with {string}")
	public void user_creates_put_user_role_request_for_the_lms_api_with_fields_from_with(String sheetName,
			String dataKey) {
		try {

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			excelDataMap = null;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			if (excelDataMap != null && excelDataMap.size() > 0) {
				switch (dataKey) {
				case "Put_UserRole_Valid":
				case "Put_UserRole_Invalid":
				case "Put_UserRole_Missing_RoleId":
				case "Put_UserRole_Missing_RoleStatus":

					userRoleMap = new UserRoleMap(excelDataMap.get("roleId"), excelDataMap.get("userRoleStatus"));
					LoggerLoad.logDebug(userRoleMap.toString());
					break;
				}
			}
			LoggerLoad.logInfo("Assignment POST request created");
		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();

		}
	}

	@When("User sends HTTP PUT User Role Request {string}")
	public void user_sends_http_put_user_role_request(String dataKey) {
		try {
			String userId = null;
			if (!users.isEmpty()) {
				userId = users.get(0);
			}
			LoggerLoad.logInfo("userId PUT " + userId);
			response = userEndpoints.UpdateUserRole(userRoleMap, userId, dataKey);
			LoggerLoad.logInfo("User PUT request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}
	}

	@Then("User receives response for PUT User Role {string} with {string}")
	public void user_receives_response_for_put_user_role_with(String sheetName, String dataKey) {
		try {
			response.then().log().all().extract().response();

			switch (dataKey) {
			case "Put_UserRole_Valid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.TEXT);
				// Validate json schema
				/*
				 * .body(JsonSchemaValidator.matchesJsonSchema(
				 * getClass().getClassLoader().getResourceAsStream("userjsonschema.json")));
				 */

				break;

			case "Put_UserRole_Missing_RoleId":
			case "Put_UserRole_Missing_RoleStatus":

				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;
			case "Put_UserRole_Invalid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_NOT_FOUND)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;

			}

		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}
	}

	@Given("User creates PUT User batch Request for the LMS API with fields from {string} with {string}")
	public void user_creates_put_user_batch_request_for_the_lms_api_with_fields_from_with(String sheetName,
			String dataKey) {
		try {

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			excelDataMap = null;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			if (excelDataMap != null && excelDataMap.size() > 0) {
				switch (dataKey) {
				case "Put_UserBatch_Valid":
				case "Put_UserBatch_Invalid":
				case "Put_UserBatch_Missing":
					String userId = null;
					if (!users.isEmpty()) {
						userId = users.get(0);
					}

					assignUserRoleProgramBatchStatus = new AssignUserRoleProgramBatchStatus(programId,
							excelDataMap.get("roleId"), userId, batchId, excelDataMap.get("userRoleStatus"));
					LoggerLoad
							.logDebug("assignUserRoleProgramBatchStatus" + assignUserRoleProgramBatchStatus.toString());
					break;
				}
			}
			LoggerLoad.logInfo("Assignment POST request created");
		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();

		}
	}

	@When("User sends HTTP PUT User batch Request {string}")
	public void user_sends_http_put_user_batch_request(String dataKey) {
		try {
			String userId = null;
			if (!users.isEmpty()) {
				userId = users.get(0);
			}
			LoggerLoad.logInfo("userId PUT " + userId);
			response = userEndpoints.UpdateUserBatch(assignUserRoleProgramBatchStatus, userId, dataKey);
			LoggerLoad.logInfo("User PUT request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}
	}

	@Then("User receives response for PUT User batch {string} with {string}")
	public void user_receives_response_for_put_user_batch_with(String sheetName, String dataKey) {
		try {
			response.then().log().all().extract().response();

			switch (dataKey) {
			case "Put_UserBatch_Valid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.TEXT);
				// Validate json schema
				/*
				 * .body(JsonSchemaValidator.matchesJsonSchema(
				 * getClass().getClassLoader().getResourceAsStream("userjsonschema.json")));
				 */

				break;

			case "Put_UserBatch_Missing":

				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;
			case "Put_UserBatch_Invalid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_NOT_FOUND)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader()
								.getResourceAsStream("404getbatchbynameoridjsonschema.json")));

				break;

			}

		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();

		}
	}

	@Given("User creates GET Request for the LMS API All User endpoint")
	public void user_creates_get_request_for_the_lms_api_all_user_endpoint() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request")
	public void user_sends_https_request() {
		response = userEndpoints.GetAllUsers();
	}

	@Then("User receives OK Status with response body containing all users")
	public void user_receives_ok_status_with_response_body_containing_all_users() {
		try {
			response.then().statusCode(200);
			JsonPath jsonPathEvaluator = response.jsonPath();
			//List<User> userList = jsonPathEvaluator.getList("", User.class);
			//LoggerLoad.logDebug(userList.get(0).userId);
			response.then().assertThat().body(JsonSchemaValidator
					.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallusersjsonschema.json")));
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
			Cleanup();
		}
	}

	@Given("User creates GET User Request for the LMS API")
	public void user_creates_get_user_request_for_the_lms_api() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS GET User Request with userId with {string}")
	public void user_sends_https_get_user_request_with_user_id_with(String dataKey) {
		String userId = "U7876";
		if (!users.isEmpty()) {
			userId = users.get(0);
		}
		response = userEndpoints.GetUsersById(userId, dataKey);
	}

	@Then("User receives Status Code  with response body for a userId with {string}")
	public void user_receives_status_code_with_response_body_for_a_user_id_with(String dataKey) {
		try {
			response.then().log().all();
			if (dataKey.equals("Valid")) {
				response.then().statusCode(200);
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("getuserbyidjsonschema.json")));

			} else if (dataKey.equals("Invalid")) {
				if (response.statusCode() == 404 || response.statusCode() == 405) {

					response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
				}
			} else {
				assertTrue(false);
			}
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
			Cleanup();
		}
	}

	@Given("User creates GET Request for the LMS API All User with role endpoint")
	public void user_creates_get_request_for_the_lms_api_all_user_with_role_endpoint() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for User with role")
	public void user_sends_https_request_for_user_with_role() {
		response = userEndpoints.GetAllUsersWithRole();
	}

	@Then("User receives OK Status with response body containing all users with role")
	public void user_receives_ok_status_with_response_body_containing_all_users_with_role() {
		try {
			//response.then().log().all();
			response.then().statusCode(200);
			response.then().assertThat().body(JsonSchemaValidator
					.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getuserbyidjsonschema.json")));
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
			Cleanup();
		}
	}

	@Given("User creates GET Request for the LMS API all staff endpoint")
	public void user_creates_get_request_for_the_lms_api_all_staff_endpoint() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS Request for all staff with role")
	public void user_sends_https_request_for_all_staff_with_role() {
		response = userEndpoints.GetAllStaff();
	}

	@Then("User receives OK Status with response body containing all staff user")
	public void user_receives_ok_status_with_response_body_containing_all_staff_user() {
		try {
			//response.then().log().all();
			response.then().statusCode(200);
			response.then().assertThat().body(JsonSchemaValidator
					.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getuserbyidjsonschema.json")));
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
			Cleanup();
		}
	}

	@Given("User creates DELETE Request for the LMS API endpoint with {string} userId")
	public void user_creates_delete_request_for_the_lms_api_endpoint_with_user_id(String string) {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends DELETE Request for {string} userId")
	public void user_sends_delete_request_for_user_id(String dataKey) {
		String userId = "U7876";
		if (!users.isEmpty()) {
			userId = users.get(0);
		}
		response = userEndpoints.deleteUser(userId, dataKey);
	}

	@Then("User receives Status with response body for {string} userId")
	public void user_receives_status_with_response_body_for_user_id(String dataKey) {
		try {
			response = response.then().log().all().extract().response();
			if (dataKey.equals("Valid")) {
				response.then().statusCode(200);
				Cleanup();
			} else {
				response.then().statusCode(404);
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
				System.out.println("FAIL");
				Cleanup();
			}
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
			Cleanup();
		}

	}

}
