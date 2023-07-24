package apiTests;

import java.util.List;

import apiEngine.endpoints.ProgramEndpoints;
import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.response.Assignment;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FirstTest {
	
	public static void main(String[] args) {
		
		ProgramEndpoints endPoint = new ProgramEndpoints("https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms");
		AddProgramRequest programReq = new AddProgramRequest("TestingTurtles_59871", "Active", "testing");
		 Response response = endPoint.CreateProgram(programReq);
		
		String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
		RequestSpecification request;
		
		RestAssured.baseURI = baseUrl;
		
		
		
		// Get Request
		request = RestAssured.given();
		
        response = request.get("/assignments");
        
        System.out.println(response.getStatusCode());
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
		System.out.print(assignmentList.get(0).assignmentId);
		
	}

}
