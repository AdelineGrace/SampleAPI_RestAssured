package apiEngine.endpoints;

import apiEngine.model.request.AddProgramRequest;
import apiEngine.routes.ProgramRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

public class ProgramEndpoints {
	
	String baseUrl;
	
	public ProgramEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response CreateProgram(AddProgramRequest programReq)
	{
		LoggerLoad.logInfo("baseUrl" + baseUrl );
		RestAssured.baseURI = baseUrl;
		
		RequestSpecification request = RestAssured.given().body(programReq);
		request.header("Content-Type", "application/json");
		
		Response response = request.when().post(ProgramRoutes.createProgram());
		
		return response;
	}
	
	public Response DeleteProgramById(int programId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		
		Response response = request.get(ProgramRoutes.deleteProgramById(programId));
		
		return response;
	}

}
