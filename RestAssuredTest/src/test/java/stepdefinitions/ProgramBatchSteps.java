package stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

//import org.json.simple.JSONObject;

import apiEngine.model.request.AddBatchRequest;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Batch;
import dataProviders.ConfigReader;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;

public class ProgramBatchSteps {
	
	
	static final String baseUrl = ConfigReader.getProperty("base.url");
	static final String batchUrl = ConfigReader.getProperty("batch.url");
	RequestSpecification request;
	Response response;
	

	@Given("User creates GET Request for the LMS API {string}")
	public void user_creates_get_request_for_the_lms_api(String string) {
		switch (string) {
		case "batchModule":
			LoggerLoad.logInfo("Method : user_creates_get_request_for_the_lms_api - inside case batchModule");
			RestAssured.baseURI = baseUrl+batchUrl;
			break;

		default:
			RestAssured.baseURI = baseUrl;
			break;
		}
		
        request = RestAssured.given();
	}

	@When("User sends HTTPS Request for endpoint from {string} with {string}")
	public void user_sends_https_request_for_endpoint_from_with(String sheetName, String dataKey) {
		
		Map<String,String> excelDataMap=null;
		String endpoint=null;
		try {
			excelDataMap = ExcelReader.getData(dataKey,sheetName);
			if(null != excelDataMap && excelDataMap.size() > 0 ) {
				endpoint = excelDataMap.get("endpoint");
				LoggerLoad.logInfo("Method : user_sends_https_request_for_endpoint_from_with - endpoint ::" +endpoint);
				switch (endpoint) {
				case "batches":
					if(dataKey.equals("Get_All_Valid"))
						response = request.get();
					else
						response = request.get("invalidbatch");
					break;
				case "batchId":
					if(dataKey.equals("Get_ByBatchId_Valid"))
						response = request.get(endpoint+"/"+ConfigReader.getProperty("e2e.batchid"));
					else
						response = request.get(endpoint+"/0000");
					break;
				case "batchName":
					if(dataKey.equals("Get_ByBatchName_Valid"))
						response = request.get(endpoint+"/"+ConfigReader.getProperty("e2e.batchname"));
					else
						response = request.get(endpoint+"/0000");
					break;
				case "program":
					if(dataKey.equals("Get_ByProgramId_Valid"))
						response = request.get(endpoint+"/"+ConfigReader.getProperty("e2e.programid"));
					else
						response = request.get(endpoint+"/0000");
					break;

				default:
					response = request.get(endpoint);
					break;
				}
				response.then().log().all();
			}
			
		} catch (Exception e) {
			if(e.getMessage().contains("NO DATA FOUND for dataKey")) {
				LoggerLoad.logError("NO DATA FOUND for dataKey");
				
				assertTrue(false);
			} else {
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}

	@Then("User receives Status Code  with response body for endpoint {string} with {string}")
	public void user_receives_status_code_with_response_body_for_endpoint_with(String sheetName, String dataKey) {
		Map<String,String> excelDataMap=null;
		String endpoint=null;
		
		try {
			excelDataMap = ExcelReader.getData(dataKey,sheetName);
			if(null != excelDataMap && excelDataMap.size() > 0 ) {
				endpoint = excelDataMap.get("endpoint");
				switch (endpoint) {
				case "batches":
					validateGetAllBatchesResponse(dataKey);
					break;
				case "batchId":
					validateGetBatchByIdResponse(dataKey);
					break;
				case "batchName":
					validateGetBatchByNameResponse(dataKey);
					
					break;
				case "program":
					validateGetBatcheByProgramIdResponse(dataKey);
					
					break;

				default:
					
					break;
				}
			}
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block.
			if(e.getMessage().contains("NO DATA FOUND for dataKey")) {
				assertTrue(true);
			} else {
				e.printStackTrace();
			}
			
		}
	}
	
	private void validateGetAllBatchesResponse(String dataKey) {
		
		if(dataKey.equals("Get_All_Valid")) {
			response.then().statusCode(200);
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<Batch> batchList = jsonPathEvaluator.getList("", Batch.class);
			System.out.print(batchList.get(0).batchId);
			
			/*response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/getallbatchesjsonschema.json")));*/
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallbatchesjsonschema.json")));
		
		} else if(dataKey.equals("Get_All_Invalid")) {
			if(response.statusCode() == 404 || response.statusCode() == 405) {
				/*response.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/404errorjsonschema.json")));*/
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404errorjsonschema.json")));
			}
		} else {
			assertTrue(false);
		}
	
	}
	
	private void validateGetBatchByNameResponse(String dataKey) {
		
		if(dataKey.equals("Get_ByBatchName_Valid")) {
			response.then().statusCode(200);
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<Batch> batchList = jsonPathEvaluator.getList("", Batch.class);
			System.out.print(batchList.get(0).batchId);
			
			/*response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/getallbatchesjsonschema.json")));*/
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallbatchesjsonschema.json")));
		
		} else if(dataKey.equals("Get_ByBatchName_Invalid")) {
			if(response.statusCode() == 404 || response.statusCode() == 405) {
				/*response.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/404errorjsonschema.json")));*/
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
			}
		} else {
			assertTrue(false);
		}
	
	}
	
	private void validateGetBatchByIdResponse(String dataKey) {
		
		if(dataKey.equals("Get_ByBatchId_Valid") ) {
			response.then().statusCode(200);
			Batch batch = response.getBody().as(Batch.class);
			System.out.print(batch.batchId);
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getbatchbyidjsonschema.json")));
		
		} else if(dataKey.equals("Get_ByBatchId_Invalid")) {
			response.then().statusCode(404);
			/*response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/404errorjsonschema.json")));*/
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));

		}else {
			assertTrue(false);
		}
	
	}
	
	private void validateGetBatcheByProgramIdResponse(String dataKey) {
		
		if(dataKey.equals("Get_ByProgramId_Valid")) {
			response.then().statusCode(200);
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<Batch> batchList = jsonPathEvaluator.getList("", Batch.class);
			System.out.print(batchList.get(0).batchId);
			
			/*response.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/getallbatchesjsonschema.json")));*/
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallbatchesjsonschema.json")));
		
		} else if(dataKey.equals("Get_ByProgramId_Invalid")) {
			if(response.statusCode() == 404 || response.statusCode() == 405) {
				/*response.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/404errorjsonschema.json")));*/
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
			}
		} else {
			assertTrue(false);
		}
	
	}
	@Given("User creates POST Request for the LMS API {string}")
	public void user_creates_post_request_for_the_lms_api(String string) {
		switch (string) {
		case "batchModule":
			RestAssured.baseURI = baseUrl+batchUrl;
			break;

		default:
			RestAssured.baseURI = baseUrl;
			break;
		}
		
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
	}
		
	

	@When("User sends POST Request with mandatory and additional fields  {string} with {string}")
	public void user_sends_post_request_with_mandatory_and_additional_fields_with(String sheetName, String dataKey) {
	
		Map<String,String> excelDataMap=null;
		String endpoint=null, Batchname=null, BatchStatus=null,  BatchDescription=null ;
		String dynamicBatchname =null;
		Integer NoOfClasses=null,Programid = null;
		try {
			
			
			excelDataMap = ExcelReader.getData(dataKey,sheetName);
			if(null != excelDataMap && excelDataMap.size() > 0 ) {
				 if(dataKey.equals("Post_Batch_Existing")) {
					 dynamicBatchname = ConfigReader.getProperty("e2e.existingbatchname");
					 LoggerLoad.logDebug("existing");
				 }else {
					 if(!(dataKey.equals("Post_Batch_Missing_BatchName")) && (!(excelDataMap.get("BatchName").isBlank()))) {
						 Batchname=excelDataMap.get("BatchName");
						 LoggerLoad.logDebug("Batchname: "+Batchname);
						 String generateinvalidID=RandomStringUtils.randomNumeric(3);
						 dynamicBatchname = "Jul23-TestingTurtles-" +ConfigReader.getProperty("e2e.programname")+"-"+Batchname+"-"+generateinvalidID;
						 LoggerLoad.logInfo("dynamicBatchname"+dynamicBatchname);
						 
					 }
				}
				if(!excelDataMap.get("BatchStatus").isBlank()) {
					BatchStatus = excelDataMap.get("BatchStatus");
				}
				if(!excelDataMap.get("NoOfClasses").isBlank()) {
					NoOfClasses = Integer.parseInt(excelDataMap.get("NoOfClasses"));
					
				}
				if(!excelDataMap.get("BatchDescription").isBlank()) {
					BatchDescription = excelDataMap.get("BatchDescription");
				}
				
				if(dataKey.equals("Post_Batch_Missing_ProgramId")) {
					 Programid = null;
					 
				}else {
					 Programid = Integer.parseInt(ConfigReader.getProperty("e2e.programid"));
					 LoggerLoad.logDebug("programid: "+Programid);
			    }
				 
				AddBatchRequest batch = new AddBatchRequest(dynamicBatchname, BatchStatus, BatchDescription,NoOfClasses, Programid);
				response = request.body(batch).post();
				//response.then().log().all();
				 
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

	

	@Then("User receives Status with response body for post {string} with {string}")
	public void user_receives_status_code_with_response_body_for_post(String sheetName, String dataKey) throws IOException {
		//response.then().log().all();
		response= response.then().log().all().extract().response();	
		if(dataKey.equals("Post_Batch_Valid")) {
			response.then().statusCode(201);
			Batch resBatch = response.getBody().as(Batch.class);
			System.out.println(resBatch.batchId);
			JsonPath js = response.jsonPath();
			int batchId = js.getInt("batchId");
			String batchName = js.getString("batchName");
			System.out.println("Response BatchId"+batchId);
			ConfigReader.setProperty("e2e.batchid", Integer.toString(batchId));
			ConfigReader.setProperty("e2e.batchname", batchName);
			ConfigReader.setProperty("e2e.existingbatchname", batchName);
		    response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getbatchbyidjsonschema.json")));
		}
		else if(dataKey.equals("Post_Batch_Existing")) {
			response.then().statusCode(400);
			
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
			System.out.println("FAIL");
			
		}
		else if(dataKey.equals("Post_Batch_Missing_BatchStatus")||dataKey.equals("Post_Batch_Missing_BatchName")||dataKey.equals("Post_Batch_Missing_NoOfClasses")||dataKey.equals("Post_Batch_Missing_ProgramId")){
			response.then().statusCode(400);
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
				System.out.println("FAIL");
			
		}
		else {
			assertTrue(false);
		}
		
	}
	
	@Given("User creates PUT Request for the LMS API {string}")
	public void user_creates_put_request_for_the_lms_api(String string) {
		switch (string) {
		case "batchModule":
			LoggerLoad.logInfo("Method : user_creates_get_request_for_the_lms_api - inside case batchModule");
			RestAssured.baseURI = baseUrl+batchUrl;
			break;

		default:
			RestAssured.baseURI = baseUrl;
			break;
		}
		
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
	}

	@When("User sends PUT Request with mandatory and additional fields  {string} with {string}")
	public void user_sends_put_request_with_mandatory_and_additional_fields_with(String sheetName, String dataKey ) {
		Map<String,String> excelDataMap=null;
		String endpointBatchId=null, Batchname=null, BatchStatus=null,  BatchDescription=null ;
		String dynamicBatchname =null;
		Integer NoOfClasses=null,Programid = null;
		try {
			
			
			excelDataMap = ExcelReader.getData(dataKey,sheetName);
			if(null != excelDataMap && excelDataMap.size() > 0 ) {
				
				if(dataKey.equals("Put_Batch_Invalid")){
					endpointBatchId="/0000";
				} else {
					endpointBatchId="/"+ConfigReader.getProperty("e2e.batchid");
				}
						
				
			  if(!(dataKey.equals("Put_Batch_Missing_BatchName")) && (!(excelDataMap.get("BatchName").isBlank()))) {
						 Batchname=excelDataMap.get("BatchName");
						 LoggerLoad.logDebug("Batchname: "+Batchname);
						 String generateinvalidID=RandomStringUtils.randomNumeric(3);
						 dynamicBatchname = "Jul23-TestingTurtles-" +ConfigReader.getProperty("e2e.programname")+"-"+Batchname+"-"+generateinvalidID;
						 LoggerLoad.logInfo("dynamicBatchname"+dynamicBatchname);
						 
			  }
				
				if(!excelDataMap.get("BatchStatus").isBlank()) {
					BatchStatus = excelDataMap.get("BatchStatus");
				}
				if(!excelDataMap.get("NoOfClasses").isBlank()) {
					NoOfClasses = Integer.parseInt(excelDataMap.get("NoOfClasses"));
					
				}
				if(!excelDataMap.get("BatchDescription").isBlank()) {
					BatchDescription = excelDataMap.get("BatchDescription");
				}
				
				if(dataKey.equals("Put_Batch_Missing_ProgramId")) {
					 Programid = null;
					 
				}else {
					 Programid = Integer.parseInt(ConfigReader.getProperty("e2e.programid"));
					 LoggerLoad.logDebug("programid: "+Programid);
			    }
				 
				AddBatchRequest batch = new AddBatchRequest(dynamicBatchname, BatchStatus, BatchDescription,NoOfClasses, Programid);
				response = request.body(batch).put(endpointBatchId);
				//response.then().log().all();
				 
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Then("User receives Status with response body for put {string} with {string}")
	public void user_receives_status_with_response_body_for_put_with(String sheetName, String dataKey) throws IOException {
		//response.then().log().all();
		response= response.then().log().all().extract().response();	
		if(dataKey.equals("Put_Batch_Valid")) {
			response.then().statusCode(200);
			Batch resBatch = response.getBody().as(Batch.class);
			System.out.println(resBatch.batchId);
			JsonPath js = response.jsonPath();
			int batchId = js.getInt("batchId");
			String batchName = js.getString("batchName");
			System.out.println("Response BatchId"+batchId);
			ConfigReader.setProperty("e2e.batchid", Integer.toString(batchId));
			ConfigReader.setProperty("e2e.batchname", batchName);
			ConfigReader.setProperty("e2e.existingbatchname", batchName);
		    response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getbatchbyidjsonschema.json")));
		}
		
		else if( dataKey.equals("Put_Batch_Missing_BatchStatus")||dataKey.equals("Put_Batch_Missing_BatchName")||dataKey.equals("Put_Batch_Missing_NoOfClasses")||dataKey.equals("Put_Batch_Missing_ProgramId")){
			response.then().statusCode(400);
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
				System.out.println("FAIL");
			
		}
		else if(dataKey.equals("Put_Batch_Invalid")){
			response.then().statusCode(404);
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
				System.out.println("FAIL");
			
		}
		else {
			assertTrue(false);
		}
	}
	
	
	@Given("User creates DELETE Request for the LMS API {string}")
	public void user_creates_delete_request_for_the_lms_api(String string) {
		switch (string) {
		case "batchModule":
			LoggerLoad.logInfo("Method : user_creates_get_request_for_the_lms_api - inside case batchModule");
			RestAssured.baseURI = baseUrl+batchUrl;
			break;

		default:
			RestAssured.baseURI = baseUrl;
			break;
		}
		
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
	}

	@When("User sends DELETE Request for {string} with {string} batchId")
	public void user_sends_delete_request_for_with_batch_id(String sheetName, String dataKey) {
		response = request.delete("/"+ConfigReader.getProperty("e2e.batchid"));
		//response.then().log().all();
	}

	@Then("User receives Status with response body for {string} with {string}")
	public void user_receives_status_with_response_body_for_with(String sheetName, String dataKey) {
		//response.then().log().all();
		response= response.then().log().all().extract().response();	
		if(dataKey.equals("Delete_Batch_Valid")) {
			response.then().statusCode(200);
		}else {
			response.then().statusCode(404);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
			System.out.println("FAIL");
			
		}
	}
}
