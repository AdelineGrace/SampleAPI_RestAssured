package apiEngine.endpoints;

import apiEngine.model.request.AddUserRequest;
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
		
		Response response = request.body(userReq).post("/users/users/roleStatus");
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}
}
