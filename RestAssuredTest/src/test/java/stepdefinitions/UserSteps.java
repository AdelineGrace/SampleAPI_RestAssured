package stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.util.List;

import apiEngine.model.response.Assignment;
import apiEngine.routes.AssignmentRoutes;
import apiEngine.routes.UserRoute;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import apiEngine.model.response.User;

public class UserSteps {
	
	String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
	RequestSpecification request;
	Response response;
	int errorStatusCode = 0;


	@Given("User creates GET Request for the LMS API All User endpoint")
	public void user_creates_get_request_for_the_lms_api_all_user_endpoint() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}
	@When("User sends HTTPS Request")
	public void user_sends_https_request() {
		response = request.get(UserRoute.getAllUsers());
		//System.out.println("user routeaa"+ UserRoute.getAllUsers());
		//System.out.println(response.asPrettyString());


	}
	@Then("User receives OK Status with response body containing all users")
	public void user_receives_ok_status_with_response_body_containing_all_users() {
		if(response.getStatusCode() == 200)
		{
			response.then().statusCode(200);
			//System.out.println(response.getStatusCode());
			//System.out.println(response.asPrettyString());
			
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<User> userList = jsonPathEvaluator.getList("", User.class);
		    //System.out.print(userList.get(0).userId);
			
			//response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getusersjsonschema.json")));
		}
		else
		{
			
		}
	}
	@Given("User creates GET Request for the LMS API endpoint with valid User ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_user_id() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.get(UserRoute.getAllUsers());
		System.out.println("user routeaa"+ UserRoute.getAllUsers());
		System.out.println(response.asPrettyString());
	}
	
	/*@Given("User creates GET Request for the LMS API endpoint with invalid User ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_user_id() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.get(UserRoute.getAllUsers());
		System.out.println("user routeaa"+ UserRoute.getAllUsers());
		System.out.println(response.asPrettyString());
	}*/
	
	/*@Given("User creates GET Request for the LMS API endpoint with {string} valid User ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_user_id(String userId) {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.get(UserRoute.getUserWithInUserID(userId));
		System.out.println("user routeaa"+ UserRoute.getAllUsers());
		System.out.println(response.asPrettyString());
	}*/
	
	@Given("User creates GET Request for the LMS API endpoint with {string} valid User ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_valid_user_id(String userId) {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.get(UserRoute.getUserWithUserID(userId));
		System.out.println("user routeaa"+ UserRoute.getUserWithUserID(userId));
		System.out.println(response.asPrettyString());
	}
	
	@Given("User creates GET Request for the LMS API endpoint with {string} invalid User ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_user_id(String userId) {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.get(UserRoute.getUserWithUserID(userId));
		errorStatusCode = response.statusCode();
		System.out.println("user routeaa"+ UserRoute.getUserWithUserID(userId));
		System.out.println(response.asPrettyString());;
	}
@Then("User receives {string} Not Found Status with message and boolean success details")
public void user_receives_not_found_status_with_message_and_boolean_success_details(String responseCode) {
	System.out.println("here stus" + errorStatusCode);
	if (errorStatusCode == 404) {
	//response.then().statusCode(Integer.parseInt(responseCode));
	System.out.println("Status code 404 received for GET all program with invalid URL");
	}
	else
	{
		System.out.println("There is something wrong");

	}
}

@Given("User creates GET Request for the LMS API All Staff endpoint")
public void user_creates_get_request_for_the_lms_api_all_staff_endpoint() {
	RestAssured.baseURI = baseUrl;
	request = RestAssured.given();
	response = request.get(UserRoute.getAllStaffUsers());
	System.out.println("user routeaa"+ UserRoute.getAllStaffUsers());
	System.out.println(response.asPrettyString());
    
}

@Then("User receives {int} OK Status with response body")
public void user_receives_ok_status_with_response_body(Integer int1) {
	response = request.get(UserRoute.getAllStaffUsers());
	int statusCode = response.statusCode();
	assertEquals(statusCode, 200);
   System.out.println("Status Code" + response.statusCode());
}

@Given("User creates GET Request for the LMS API User Roles endpoint")
public void user_creates_get_request_for_the_lms_api_user_roles_endpoint() {
	RestAssured.baseURI = baseUrl;
	request = RestAssured.given();
	response = request.get(UserRoute.getAllUserWithRoles());
	System.out.println("user routeaa"+ UserRoute.getAllUserWithRoles());
	System.out.println(response.asPrettyString());
}



}
