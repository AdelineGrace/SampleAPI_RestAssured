package apiEngine.endpoints;

import apiEngine.model.request.AddUserRequest;
import apiEngine.routes.UserRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
		
		Response response = request.body(userReq).post(UserRoutes.createUser());
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}
	
	public Response DeleteUserById(String userId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		
		Response response = request.get(UserRoutes.deleteUserById(userId));
		
		return response;
	}
}
