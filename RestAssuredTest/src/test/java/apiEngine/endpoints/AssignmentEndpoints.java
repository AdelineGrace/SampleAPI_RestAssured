package apiEngine.endpoints;

import java.util.List;

import apiEngine.model.request.AddAssignmentRequest;
import apiEngine.model.response.Assignment;
import apiEngine.response.RestResponse;
import apiEngine.routes.AssignmentRoutes;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AssignmentEndpoints {
	
	String baseUrl;
	
	public AssignmentEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	
	public RestResponse<Assignment> CreateAssignment(AddAssignmentRequest assignmentReq)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.body(assignmentReq).post(AssignmentRoutes.createAssignment());
		System.out.println("response - " + response.asPrettyString());
		Assignment assignmentRes = response.getBody().as(Assignment.class);
		System.out.println(assignmentRes.assignmentId);
		
		return new RestResponse<Assignment>(Assignment.class, response);
	}
	
	public void GetAllAssignments()
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(AssignmentRoutes.getAllAssignments());
		System.out.println(response.getStatusCode());
		System.out.println(response.asPrettyString());
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		List<Assignment> assignmentList = jsonPathEvaluator.getList("", Assignment.class);
		System.out.print(assignmentList.get(0).assignmentId);
	}
}