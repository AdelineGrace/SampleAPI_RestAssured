package apiEngine.endpoints;

import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.response.Program;
import apiEngine.response.RestResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProgramEndpoints {
	
	String baseUrl;
	
	public ProgramEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public RestResponse<Program> CreateProgram(AddProgramRequest programReq)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(programReq).post("/saveprogram");
		System.out.println("response - " + response.asPrettyString());
		Program programRes = response.getBody().as(Program.class);
		
		System.out.println(programRes.programId);
		
		return new RestResponse<Program>(Program.class, response);
	}

}
