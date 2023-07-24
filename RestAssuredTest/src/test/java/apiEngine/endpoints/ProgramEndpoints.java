package apiEngine.endpoints;

import apiEngine.model.request.AddProgramRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProgramEndpoints {
	
	String baseUrl;
	
	public ProgramEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response CreateProgram(AddProgramRequest programReq)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(programReq).post("/saveprogram");
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}

}
