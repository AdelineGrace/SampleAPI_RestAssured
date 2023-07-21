package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FirstTest {
	
	public static void main(String[] args) {
		
		String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
		RequestSpecification request = RestAssured.given();
		Response response;
		
		RestAssured.baseURI = baseUrl;
		
		// Get Request
        response = request.get("/assignments");
        
        System.out.println(response.getStatusCode());
	}

}
