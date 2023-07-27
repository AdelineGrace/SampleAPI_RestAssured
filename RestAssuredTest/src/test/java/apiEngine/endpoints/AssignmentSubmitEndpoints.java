package apiEngine.endpoints;

import apiEngine.model.request.AddSubmitRequest;
import apiEngine.model.request.PutSubmission;
import apiEngine.routes.AssignmentRoutes;
import apiEngine.routes.SubmissionRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

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

	public  Response GetAllAssignments() {
		
				
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(SubmissionRoutes.getAllAssignments());
		return response;
		
	}
	public  Response GetgradebyAssignmentid(int assignmentid) {
		
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(SubmissionRoutes.getsubmissionByAssignmentId(assignmentid));
		return response;
		
	}

	public Response GetgradebyStudentId(String userId) {
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(SubmissionRoutes.getGradeByStudentId(userId));
		return response;
		
	}

	public Response GetgradebyBatchId(int batchId) {
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(SubmissionRoutes.getGradeBybatchId(batchId));
		return response;
	}

	public Response getsubmissionbybatchId(int batchId) {
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(SubmissionRoutes.getsubmissionBybatchId(batchId));
		return response;
		
	}

	public Response getsubmissionbyUserId(String UserId) {
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(SubmissionRoutes.getsubmissionByUserId(UserId));
		LoggerLoad.logInfo(response.asPrettyString());
		return response;
		
	}

	public Response PutupdateReAssignment(PutSubmission putsubmission, int Submissionid) {
		
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(putsubmission).put(SubmissionRoutes.putResubmit(Submissionid));
		LoggerLoad.logInfo(response.asPrettyString());
		return response;
	}

	public Response UpdategradeAssignment(PutSubmission putsubmission, int Submissionid) {
		
		{
			
			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");
			
			Response response = request.body(putsubmission).put(SubmissionRoutes.putGradesubmission(Submissionid));
			
			LoggerLoad.logInfo(response.asPrettyString());
			
			return response;
			
		}
	
	
	}
		
}