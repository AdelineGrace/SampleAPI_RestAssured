package apiEngine.endpoints;

import apiEngine.model.request.AddUserRequest;
import apiEngine.model.request.AssignUserRoleProgramBatchStatus;
import apiEngine.model.request.UpdateUserRequest;
import apiEngine.model.request.UserRoleMap;
import apiEngine.routes.ProgramBatchRoutes;
import apiEngine.routes.UserRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

public class UserEndpoints {
	
	String baseUrl;
	
	public UserEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response CreateUser(AddUserRequest userReq) 
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(userReq).post("/users/users/roleStatus");
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}
	
	public Response UpdateUser (UpdateUserRequest userReq, String userId, String dataKey) 
	{
		LoggerLoad.logDebug(userReq.toString());
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(userReq).put(UserRoutes.updateUser(userId, dataKey));
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}
	
	public Response UpdateUserRole (UserRoleMap userRoleMap, String userId, String dataKey) 
	{
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(userRoleMap).put(UserRoutes.updateUserRole(userId, dataKey));
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}
	
	public Response UpdateUserBatch (AssignUserRoleProgramBatchStatus assignUserRoleProgramBatchStatus, String userId, String dataKey) 
	{
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(assignUserRoleProgramBatchStatus).put(UserRoutes.updateUserRoleBatch(userId, dataKey));
		request.given().log().all(true);
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}
}
