package apiEngine.endpoints;


import apiEngine.model.request.AddAssignmentRequest;
import apiEngine.routes.AssignmentRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AssignmentEndpoints {
	
	String baseUrl;
	
	public AssignmentEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	
	public Response CreateAssignment(AddAssignmentRequest assignmentReq)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		request.then().log().all();
		
		Response response = request.body(assignmentReq).post(AssignmentRoutes.createAssignment());
		
		response.then().log().all();
		
		return response;
	}
	
	public Response GetAllAssignments()
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		
		request.then().log().all();
		
		Response response = request.get(AssignmentRoutes.getAllAssignments());
		
		response.then().log().all();
		
		return response;
	}
	
	public Response GetAssignmentByAssignmentId(int assignmentId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		
		request.then().log().all();
		
		Response response = request.get(AssignmentRoutes.getAssignmentByAssignmentId(assignmentId));
		
		response.then().log().all();
		
		return response;
	}
	
	public Response GetAssignmentByBatchId(int batchId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		
		request.then().log().all();
		
		Response response = request.get(AssignmentRoutes.getAssignmentByBatchId(batchId));
		
		response.then().log().all();
		
		return response;
	}
	
	public Response UpdateAssignment(AddAssignmentRequest assignmentReq, int assignmentId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		request.then().log().all();
		
		Response response = request.body(assignmentReq).put(AssignmentRoutes.updateAssignmentById(assignmentId));
		
		response.then().log().all();
		
		return response;
	}
	
	public Response DeleteAssignmentById(int assignmentId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		
		request.then().log().all();
		
		Response response = request.delete(AssignmentRoutes.deleteAssignmentById(assignmentId));
		
		response.then().log().all();
		
		return response;
	}
	
}