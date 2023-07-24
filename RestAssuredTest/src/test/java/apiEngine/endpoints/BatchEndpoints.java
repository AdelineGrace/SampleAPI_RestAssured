package apiEngine.endpoints;

import apiEngine.model.request.AddBatchRequest;
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
		
		Response response = request.body(batchReq).post("/batches");
		System.out.println("response - " + response.asPrettyString());
		
		return response;
	}

}
