package stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;

import apiEngine.endpoints.BatchEndpoints;

//import org.json.simple.JSONObject;

import apiEngine.model.request.AddBatchRequest;
import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.request.AddUserRequest;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Batch;
import apiEngine.model.response.Program;
import apiEngine.model.response.User;
import apiEngine.routes.AssignmentRoutes;
import apiEngine.routes.ProgramBatchRoutes;
import apiEngine.routes.ProgramRoutes;
import apiEngine.routes.UserRoutes;
import dataProviders.ConfigReader;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.DynamicValues;
import utilities.LoggerLoad;

public class ProgramBatchSteps extends BaseStep {
	

	static final String baseUrl = ConfigReader.getProperty("base.url");
	static final String batchUrl = ConfigReader.getProperty("batch.url");
	RequestSpecification request;
	Response response;
	AddBatchRequest addBatchRequest;
	
	Map<String, String> excelDataMap;

	Program program;
	
	static int programId ;
	static int batchId;
	static String batchName, existingBatchName, programName;
	
	public void SetupPreRequisites() 
	{
		try 
		{
			excelDataMap = null;
			AddProgramRequest programReq = null;
			
			// create program
			excelDataMap = ExcelReader.getData("Post_Program_Assignment", "program");
			if (null != excelDataMap && excelDataMap.size() > 0) 
			{
				programName = excelDataMap.get("programName") ;
				programReq = new AddProgramRequest("Jul23-TestingTurtles-"+programName + DynamicValues.SerialNumber(),
						excelDataMap.get("programStatus"), excelDataMap.get("programDescription"));
			}

			response = programEndpoints.CreateProgram(programReq);
			program = response.getBody().as(Program.class);
			programId = program.programId;
			LoggerLoad.logDebug("Program id - " + programId);
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logError(ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void Cleanup() 
	{
		// delete program
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		response = request.delete(ProgramRoutes.deleteProgramById(programId));
	}

	
	@Given("User creates POST Batch Request for the LMS API with fields from {string} with {string}")
	public void user_creates_post_batch_request_for_the_lms_api_with_fields_from_with(String sheetName, String dataKey) {
		
		try {
			// create program, batch and user for creating new assignment
			if (dataKey.equals("Post_Batch_Valid")) 
			{
				SetupPreRequisites();
			}
			

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			excelDataMap = null;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			
			String Batchname=null, BatchStatus=null,  BatchDescription=null ;
			String dynamicBatchname =null;
			Integer NoOfClasses=null,Programid = null;
			
			if(null != excelDataMap && excelDataMap.size() > 0 ) {
				 if(dataKey.equals("Post_Batch_Existing")) {
					 dynamicBatchname = ConfigReader.getProperty("e2e.existingbatchname");
					 LoggerLoad.logDebug("existing");
				 }else {
					 if(!(dataKey.equals("Post_Batch_Missing_BatchName")) && (!(excelDataMap.get("BatchName").isBlank()))) {
						 Batchname=excelDataMap.get("BatchName");
						 LoggerLoad.logDebug("Batchname: "+Batchname);
						 dynamicBatchname = "Jul23-TestingTurtles-" +programName+"-"+Batchname+"-"+DynamicValues.SerialNumber();
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
					 Programid = programId;
					 LoggerLoad.logDebug("programid: "+Programid);
			    }
			}
			addBatchRequest= new AddBatchRequest(dynamicBatchname, BatchStatus, BatchDescription,NoOfClasses, Programid);
			LoggerLoad.logInfo("Batch POST request created");

		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();
		}
				
	}
		
	

	@When("User sends HTTP POST Batch Request")
	public void user_sends_http_post_batch_request() {
		
		try 
		{
			response = batchEndpoints.CreateBatch(addBatchRequest);
			LoggerLoad.logInfo("Batch POST request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	
		}

	

	@Then("User receives response for POST Batch {string} with {string}")
	public void user_receives_response_for_post_batch_with(String sheetName, String dataKey) throws IOException {
		
		try 
		{
			response.then().log().all().extract().response();
			
			switch(dataKey)
			{
				case "Post_Batch_Valid" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_CREATED)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getbatchbyidjsonschema.json")));

					Batch batch = response.getBody().as(Batch.class);
					batchId = batch.batchId;
					batchName =  batch.batchName;
					existingBatchName = batch.batchName;
					ConfigReader.setProperty("e2e.batchid", Integer.toString(batchId));
					ConfigReader.setProperty("e2e.batchname", batchName);
					ConfigReader.setProperty("e2e.existingbatchname", batchName);
					LoggerLoad.logDebug("batchId:"  + batchId );
					LoggerLoad.logDebug("batchName:"  + batchName );
					
					// Validate values in response
					
					break;
					
				case "Post_Batch_Existing" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
					break;
				case "Post_Batch_Missing_BatchStatus" : 
				case "Post_Batch_Missing_BatchName" : 
				case "Post_Batch_Missing_NoOfClasses" : 
				case "Post_Batch_Missing_ProgramId" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
					break;
				default :
					assertTrue(false);
					break;
			}
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}
	
	
	
	
	@Given("User creates GET Batch Request for the LMS API")
	public void user_creates_get_request_for_the_lms_api() {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends HTTPS GET all batches Request with {string}")
	public void user_sends_https_get_all_batches_request_with(String dataKey) {
		response = batchEndpoints.GetAllBatches(dataKey);
	}

	@Then("User receives Status Code  with response body for endpoint {string}")
	public void user_receives_status_code_with_response_body_for_endpoint(String dataKey) {
		response.then().log().all();
		if(dataKey.equals("Valid")) {
			response.then().statusCode(200);
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<Batch> batchList = jsonPathEvaluator.getList("", Batch.class);
			System.out.print(batchList.get(0).batchId);
			
			
			
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallbatchesjsonschema.json")));
		
		} else if(dataKey.equals("Invalid")) {
			if(response.statusCode() == 404 || response.statusCode() == 405) {
				/*response.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/404errorjsonschema.json")));*/
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404errorjsonschema.json")));
			}
		} else {
			assertTrue(false);
		}
	}
	
	@When("User sends HTTPS GET batch Request with batchId with {string}")
	public void user_sends_https_get_batch_request_with_batch_id_with(String dataKey) {
		response = batchEndpoints.GetBatchById(batchId,dataKey);
	}

	@Then("User receives Status Code  with response body for a batchId with {string}")
	public void user_receives_status_code_with_response_body_for_a_batch_id_with(String dataKey) {
		response.then().log().all();
		if(dataKey.equals("Valid") ) {
			response.then().statusCode(200);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getbatchbyidjsonschema.json")));
		
		} else if(dataKey.equals("Invalid")) {
			response.then().statusCode(404);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));

		}else {
			assertTrue(false);
		}
	}
	
	@When("User sends HTTPS GET batch Request with batchName with {string}")
	public void user_sends_https_get_batch_request_with_batch_name_with(String dataKey) {
		response = batchEndpoints.GetBatchByName(batchName,dataKey);
	}
	

	@Then("User receives Status Code  with response body for a batchName with {string}")
	public void user_receives_status_code_with_response_body_for_a_batch_name_with(String dataKey) {
		response.then().log().all();
		if(dataKey.equals("Valid")) {
			response.then().statusCode(200);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallbatchesjsonschema.json")));
		
		} else if(dataKey.equals("Invalid")) {
			if(response.statusCode() == 404 || response.statusCode() == 405) {
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
			}
		} else {
			assertTrue(false);
		}
	}
	
	@When("User sends HTTPS GET batch Request with progamId with {string}")
	public void user_sends_https_get_batch_request_with_progam_id_with(String dataKey) {
		response = batchEndpoints.GetBatchByProgramId(programId,dataKey);
	}

	@Then("User receives Status Code  with response body for a programId with {string}")
	public void user_receives_status_code_with_response_body_for_a_program_id_with(String dataKey) {
		response.then().log().all();
		if(dataKey.equals("Valid")) {
			response.then().statusCode(200);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallbatchesjsonschema.json")));
		
		} else if(dataKey.equals("Invalid")) {
			if(response.statusCode() == 404 || response.statusCode() == 405) {
				
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
			}
		} else {
			assertTrue(false);
		}
	}
	
	@Given("User creates PUT Batch Request for the LMS API with fields from {string} with {string}")
	public void user_creates_put_batch_request_for_the_lms_api_with_fields_from_with(String sheetName, String dataKey) {
		try {
			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");
			
			excelDataMap = null;
			excelDataMap = ExcelReader.getData(dataKey, sheetName);
			
			String endpointBatchId=null, Batchname=null, BatchStatus=null,  BatchDescription=null ;
			String dynamicBatchname =null;
			Integer NoOfClasses=null,Programid = null;
			
			if(null != excelDataMap && excelDataMap.size() > 0 ) {
				
				if(!(dataKey.equals("Put_Batch_Missing_BatchName")) && (!(excelDataMap.get("BatchName").isBlank()))) {
						 Batchname=excelDataMap.get("BatchName");
						 LoggerLoad.logDebug("Batchname: "+Batchname);
						 String generateinvalidID=RandomStringUtils.randomNumeric(3);
						 dynamicBatchname = "Jul23-TestingTurtles-" +programName+"-"+Batchname+"-"+generateinvalidID;
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
					 Programid = programId;
					 LoggerLoad.logDebug("programid: "+Programid);
			    }
				 
				addBatchRequest = new AddBatchRequest(dynamicBatchname, BatchStatus, BatchDescription,NoOfClasses, Programid);
				//response = request.body(batch).put(endpointBatchId);
				//response.then().log().all();
			}
				 
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	   
	}

	@When("User sends HTTP PUT Request {string}")
	public void user_sends_http_put_request(String dataKey) {
		try 
		{
			response = batchEndpoints.updateBatch(addBatchRequest,batchId,dataKey);
			LoggerLoad.logInfo("Batch POST request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives response for PUT Batch {string} with {string}")
	public void user_receives_response_for_put_batch_with(String sheetName, String dataKey) {
		try 
		{
			response.then().log().all().extract().response();
			
			switch(dataKey)
			{
				case "Put_Batch_Valid" : 
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getbatchbyidjsonschema.json")));

					Batch batch = response.getBody().as(Batch.class);
					batchId = batch.batchId;
					batchName =  batch.batchName;
					existingBatchName = batch.batchName;
					ConfigReader.setProperty("e2e.batchid", Integer.toString(batchId));
					ConfigReader.setProperty("e2e.batchname", batchName);
					ConfigReader.setProperty("e2e.existingbatchname", batchName);
					LoggerLoad.logDebug("batchId:"  + batchId );
					LoggerLoad.logDebug("batchName:"  + batchName );
					
					// Validate values in response
					
					break;
					
				
				case "Put_Batch_Missing_BatchStatus" : 
				case "Put_Batch_Missing_BatchName" : 
				case "Put_Batch_Missing_NoOfClasses" : 
				case "Put_Batch_Missing_ProgramId" : 
				case "Put_Batch_Invalid" :	
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
					break;
				default :
					assertTrue(false);
					break;
			}
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}
	
	@Given("User creates DELETE Request for the LMS API endpoint with {string} batch Id")
	public void user_creates_delete_request_for_the_lms_api_endpoint_with_batch_id(String dataKey) {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
	}

	@When("User sends DELETE Request for {string} batch Id")
	public void user_sends_delete_request_for_batch_id(String dataKey) {
		response = batchEndpoints.deleteBatch(batchId, dataKey);
	}

	@Then("User receives Status with response body for {string} batch Id")
	public void user_receives_status_with_response_body_for_batch_id(String dataKey) {
		response= response.then().log().all().extract().response();	
		if(dataKey.equals("Valid")) {
			response.then().statusCode(200);
			Cleanup();
		}else {
			response.then().statusCode(404);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404getbatchbynameoridjsonschema.json")));
			System.out.println("FAIL");
			Cleanup();
		}
	    
	}
	
}
