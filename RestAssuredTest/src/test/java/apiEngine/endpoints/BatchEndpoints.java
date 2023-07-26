package apiEngine.endpoints;

import apiEngine.model.request.AddBatchRequest;
import apiEngine.routes.ProgramBatchRoutes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BatchEndpoints {
	
	String baseUrl;
	
	public BatchEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public Response CreateBatch(AddBatchRequest batchReq) 
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(batchReq).post(ProgramBatchRoutes.createBatch());
		
		return response;
	}
	
	public Response GetAllBatches(String dataKey)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.get(ProgramBatchRoutes.getAllBatches(dataKey));
		return response;
	}
	
	public Response GetBatchById(Integer batchId, String dataKey)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.get(ProgramBatchRoutes.getBatchById(batchId,dataKey));
		return response;
	}
	
	public Response GetBatchByName(String batchName,String dataKey)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.get(ProgramBatchRoutes.getBatchByBatchName(batchName,dataKey));
		return response;
	}
	
	public Response GetBatchByProgramId(Integer programId,String dataKey)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.get(ProgramBatchRoutes.getBatchByProgramId(programId,dataKey));
		return response;
	}
	
	public Response updateBatch(AddBatchRequest batchReq,Integer batchId, String dataKey) 
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.body(batchReq).put(ProgramBatchRoutes.updateBatch(batchId, dataKey));
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}
	
	public Response deleteBatch(Integer batchId, String dataKey) {
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.delete(ProgramBatchRoutes.deleteBatch(batchId,dataKey));
		return response;
		
	}
	
	public Response DeleteBatchById(Integer batchId) {
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.delete(ProgramBatchRoutes.deleteBatchById(batchId));
		return response;
		
	}

}
