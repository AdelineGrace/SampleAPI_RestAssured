package apiEngine.endpoints;

import apiEngine.model.request.AddBatchRequest;
import apiEngine.model.response.Batch;
import apiEngine.response.RestResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BatchEndpoints {
	
	String baseUrl;
	
	public BatchEndpoints(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public RestResponse<Batch> CreateBatch(AddBatchRequest batchReq) 
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		Response response = request.body(batchReq).post("/batches");
		System.out.println("response - " + response.asPrettyString());
		Batch batchRes = response.getBody().as(Batch.class);
		System.out.println(batchRes.batchId);
		
		return new RestResponse<Batch>(Batch.class, response);
	}

}
