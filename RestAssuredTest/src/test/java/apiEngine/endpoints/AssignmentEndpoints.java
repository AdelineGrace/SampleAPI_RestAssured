package apiEngine.endpoints;

import apiEngine.routes.AssignmentRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AssignmentEndpoints {

	private static final String BASE_URL = "https://bookstore.toolsqa.com";

	public static RequestSpecification createBaseRequest() 
	{
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		
		return request;
	}
	
	public static Response GetAssignments(RequestSpecification request)
	{
		Response response = request.get(AssignmentRoutes.getAssignments());
		
		return response;
	}
	
}
