package apiEngine.endpoints;

import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.response.Program;
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
		Response response = request.get(AssignmentRoutes.getAllAssignments());
		
		return response;
	}
	
	
	public static Program CreateProgram()
	{
		AddProgramRequest program = new AddProgramRequest("TestingTurtles_54781", "Active", "testing");
		
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		
		request.header("Content-Type", "application/json");
		Response response = request.body("\"programName\": \"Jun23-APICollectors-SDET-332\",\n"
				+ "    \"programDescription\": \"DA\",\n"
				+ "    \"programStatus\": \"Active\"").post("/saveprogram");
		System.out.println("response - " + response.asPrettyString());

		Program resProgram = response.getBody().as(Program.class);
		
		return resProgram;
	}
	
}
