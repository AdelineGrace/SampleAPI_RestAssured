package apiTests;

import java.util.List;

import apiEngine.model.response.Assignment;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FirstTest {
	
	public static void main(String[] args) {
		
		String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
		RequestSpecification request;
		Response response;
		
		RestAssured.baseURI = baseUrl;
		
		// Get Request
		request = RestAssured.given().log().all();
		
        response = request.get("/assignments");
        
        System.out.println(response.getStatusCode());
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
		System.out.print(assignmentList.get(0).assignmentId);
		
	}

}
