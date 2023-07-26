package apiEngine.endpoints;

import apiEngine.model.request.AddSubmitRequest;
import apiEngine.routes.AssignmentRoutes;
import apiEngine.routes.SubmissionRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AssignmentSubmitEndpoints {
	
String baseUrl;
	
	public AssignmentSubmitEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	
	public Response CreateAssignmentSubmission(AddSubmitRequest submitReq)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(submitReq).post(SubmissionRoutes.createAssignmentSubmission());
		
		return response;
	}
	
	public Response DeleteSubmissionById(int submissionId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		
		Response response = request.delete(SubmissionRoutes.deleteSubmissionBySubmissionId(submissionId));
		
		return response;
	}

}
