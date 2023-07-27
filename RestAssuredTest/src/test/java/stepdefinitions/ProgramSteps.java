package stepdefinitions;

import static org.junit.Assert.assertEquals;

import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Program;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
//import io.restassured.path.json.exception.*;
import org.json.simple.JSONObject;

import utilities.DynamicValues;
import utilities.LoggerLoad;
import dataProviders.ConfigReader;

public class ProgramSteps {

  String baseurl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
  RequestSpecification requestSpec;
  Response resp;
  ValidatableResponse valid_resp;
  static int programId1, programId2;
  static String programName1 , programName2;
  static int updatedId1, updatedId2;
  static String updatedName1 , updatedName2;
 
  
  
  ////////////////////// Post ///////////////////////////
  @Given( "User creates POST Request with fields {string} and {string} from excel" )
  public void user_creates_post_request_for_lms_api_endpoint( String dataKey,String sheetName) throws Exception ,IOException {
    try {
	      AddProgramRequest program;
	      String existingname = programName1;
	      Map<String, String> excelDataMap = null;
	      excelDataMap = ExcelReader.getData(dataKey, sheetName);
	      String pgmName= null, pgmStatus = null, pgmDesc= null;
	      if (null != excelDataMap && excelDataMap.size() > 0) 
	      {	
	    	 
	          if(!excelDataMap.get("ProgramName").isBlank())
	        	  pgmName = excelDataMap.get("ProgramName");
	          if(!excelDataMap.get("ProgramName").isBlank())
	        	  pgmStatus = excelDataMap.get("ProgramStatus");
	          pgmDesc = excelDataMap.get("ProgramDesc");
	          if (dataKey.equals("postExist")) {
	        	  program = new AddProgramRequest(existingname , pgmStatus, pgmDesc);     
	    	  }
	          else {     
	        	  program = new AddProgramRequest(pgmName + DynamicValues.SerialNumber(), pgmStatus, pgmDesc);
	          }  
	
	        RestAssured.baseURI = baseurl;
	        this.requestSpec =
	          RestAssured
	            .given()
	            .header("Content-Type", "application/json")
	            .body(program);
	        
	      }
    	} catch (Exception ex) {
		      LoggerLoad.logInfo(ex.getMessage());
		      ex.printStackTrace();
    }
  }

  @When("User sends request Body with mandatory , additional  fields.")
  public void user_sends_https_request_and_request_body_with_mandatory_additional_fields() {

	try 
		{
			this.resp = this.requestSpec.when().post(ConfigReader.getProperty("pgm.post"));
			//System.out.println("response-> " + resp.asPrettyString());
		}catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }

  @Then( "User receives Status with response body {string} and {string} from excel.")
  public void user_receives_created_status_with_response_body( String DataKey, String sheetName) throws Exception {
	  try 
		{  
		  String jsonString = this.resp.asString();
		  this.resp.then().log().all().extract().response();
		  switch(DataKey)
			{
				case "postNew" : 
					System.out.println("Status code--->" + resp.statusCode());
					//PgmIDForDelete = resProgram.programId;
				      resp.then().assertThat().statusCode(201)
				        .and().body(JsonSchemaValidator.matchesJsonSchema(getClass()
				              .getClassLoader()
				              .getResourceAsStream("getPgmbyIdjsonschema.json")));
				      	LoggerLoad.logInfo("Program POST request created");
				      	Program programResponse = resp.getBody().as(Program.class);
				      	programId1 = programResponse.programId;
				      	programName1 = programResponse.programName;
						//assignmentAdded = assignmentResponse;
				break;
				case "postNew1":
					System.out.println("Status code--->" + resp.statusCode());
					//PgmIDForDelete = resProgram.programId;
				      resp.then().assertThat().statusCode(201).and()
				        .body(JsonSchemaValidator.matchesJsonSchema(getClass()
				              .getClassLoader()
				              .getResourceAsStream("getPgmbyIdjsonschema.json")));
				      	LoggerLoad.logInfo("Program POST request created");
				      	Program programResponse2 = resp.getBody().as(Program.class);
				      	programId2 = programResponse2.programId;
				      	programName2 = programResponse2.programName;
				break;     	
				case "postExist":
					System.out.println("Status code--->" + resp.statusCode());
			    	this.resp.then().assertThat().statusCode(400);
			    	assertEquals(jsonString.contains("cannot create program"), true);
			    	LoggerLoad.logInfo("Program POST request already Exist");
			    break;	
				case "postMissing":
					System.out.println("Status code--->" + resp.statusCode());
			    	this.resp.then().assertThat().statusCode(500);
			    	LoggerLoad.logInfo("Program POST request Missing Value");
			    break;	
				default : 
					resp.then().assertThat().statusCode(404);
					LoggerLoad.logInfo("Program POST Bad Request");
				break;		
			}
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }

  ///////************Get all**********************

  @Given("User creates GET ALL Request for the LMS API endpoint for Program Module")
  public void user_creates_get_request_for_the_lms_api_endpoint_for_program_module() {
	  try
		{
		  
		  RestAssured.baseURI = baseurl;
		  requestSpec = RestAssured.given();
			LoggerLoad.logInfo("GET all assignments request created");
		}
	  catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }

  @When("User sends HTTPS Request in Program Module")
  public void user_sends_https_request_program_module() {
    this.resp = requestSpec.when().get(ConfigReader.getProperty("pgm.getall"));
  }

  @Then("User receives 200 OK Status with response body for Program Module")
  public void user_receives_ok_status_with_response_body() {
		try
		{
			  	System.out.println("Status code--->" + resp.statusCode());
			  	 valid_resp = resp.then().assertThat().statusCode(200).contentType(ContentType.JSON)
				      .and().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallProgramjsonschema.json")));
				    LoggerLoad.logInfo("GET all assignments request validated");
		}
		  catch (Exception ex) 
			{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
			}
		
  }

  ////*******Get One Pgm by Id************
  @Given("User creates GET BY ID Request for the LMS API endpoint for Program Module")
  public void user_creates_get_request_for_the_lms_api_endpoint_for_valid() {
	  
	  RestAssured.baseURI = baseurl;
	  requestSpec = RestAssured.given();
    
  }

  @When("User sends HTTPS Request with valid or invalid  Program Id from {string}  Program Module.")
  public void user_sends_https_request_with_valid( String DataKey) {
	  try
	  {
		  switch(DataKey) {
			  case "Get_Id_valid":
				  	//Integer pgmidV = Integer.parseInt(ConfigReader.getProperty("pgm.getbyid_valid"));
				  Integer pgmid = programId1;
			  		resp = requestSpec.when().get(ConfigReader.getProperty("pgm.getbyId") + pgmid );
			  break;
			  case "Get_Id_invalid":
				  Integer pgmidI = 0;
				  resp = requestSpec.when().get(ConfigReader.getProperty("pgm.getbyId") + pgmidI );
			  break;
		  }
  		
	  		LoggerLoad.logInfo("GET By Program Id request created");
	  }
	  catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }

  @Then( "User receives valid or invalid  {string} with response body for ProgramModule.")
  public void user_receives_status_and_details(String option) {
	  //int GetIdstatuscode = resp.getStatusCode();
	  String jsonString = resp.asString();
	  if (option.equals("valid")) 
	  {
		  	System.out.println("Status code--->" + resp.statusCode());
		      resp.then().assertThat().statusCode(200).contentType(ContentType.JSON)
		        .and().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getPgmbyIdjsonschema.json")));
		      System.out.println(resp.getBody().asPrettyString());
	  } else if (option.equals("invalid")) 
	  {
		  	System.out.println("Status code--->" + resp.statusCode());
		      System.out.println("Invalid Program Id!");
		      resp.then().assertThat().statusCode(404).contentType(ContentType.JSON);
		      System.out.println(resp.statusCode());
		     // assertEquals(jsonString.contains("not found"), true);
	  }
	  LoggerLoad.logInfo("GET By Program Id request validated");
  }

  //////****** PUT************/////////////////

  @Given("User creates PUT Request with programID for Program Module.")
  public void user_creates_put_request_with_valid_program_id_for_program_module() {
	  	RestAssured.baseURI = baseurl;
	  	requestSpec = RestAssured.given();
	  	requestSpec.header("Content-Type", "application/json");
  }

  @When("User sends PUT request with valid programID from {string} and {string} for Program Module")
  public void user_sends_put_request_body_with_valid_program_id_for_program_module(String dataKey,String sheetName) {
    try
    {		Map<String, String> excelDataMap = null;
    		excelDataMap = ExcelReader.getData(dataKey, sheetName);
    
    		if (null != excelDataMap && excelDataMap.size() > 0) {
    			
    			String pgmName = programName1;
    			String pgmStatus = excelDataMap.get("ProgramStatus");
    			String pgmDesc = excelDataMap.get("ProgramDesc");
			        
			        System.out.println("ID FOR PUT Valid->" + programId1+"---"+pgmName);
			        AddProgramRequest addpgm = new AddProgramRequest(pgmName + DynamicValues.SerialNumber(),pgmStatus,pgmDesc);
			        resp = requestSpec.body(addpgm).put(ConfigReader.getProperty("pgm.putById") + programId1); 
			          
			 }
		    
		    LoggerLoad.logInfo("PUT By Valid Program Id request created");
	  }
	  catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
    
  }


  @When("User sends PUT request with invalid programID from {string} and {string} for Program Module")
  public void user_sends_put_request_body_with_invalid_program_id(String dataKey,String sheetName) {
	  try {
	      
		      Map<String, String> excelDataMap = null;
		      excelDataMap = ExcelReader.getData(dataKey, sheetName);
		      
		      if (null != excelDataMap && excelDataMap.size() > 0) {
		        String pgmName = excelDataMap.get("ProgramName");
		        String pgmStatus = excelDataMap.get("ProgramStatus");
		        String pgmDesc = excelDataMap.get("ProgramDesc");
		        Integer getPgmId = 0000;
				    //AddProgramRequest pgmresp;
				    //pgmresp = new AddProgramRequest("JUL-23-RESTAPI-Turtle01_New","InActive","JUL-23-RESTAPI-Turtle01_DESC_New");
				    //resp = requestSpec.body(pgmresp).put("/putprogram/"+getPgmId);
				    JSONObject body = new JSONObject();
				    //body.put("programId", getPgmId);
				    body.put("programName", pgmName + DynamicValues.SerialNumber());
				    body.put("programDescription", pgmDesc);
				    body.put("programStatus", pgmStatus);
				    //System.out.println(body);
				    resp = requestSpec.body(body).put(ConfigReader.getProperty("pgm.putById") + getPgmId);
		      }
		      LoggerLoad.logInfo("PUT By InValid Program Id request created");
	      }
	      catch (Exception ex) 
			{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
			}
			    
  }

  @When("User sends PUT request  with missing programID from {string} and {string} for Program Module")
  public void user_sends_put_request_body_with_missing_program_id_for_program_module(String dataKey,String sheetName) {
	  try {
	      
		      Map<String, String> excelDataMap = null;
		      excelDataMap = ExcelReader.getData(dataKey, sheetName);
		      
		      if (null != excelDataMap && excelDataMap.size() > 0) {
		        String pgmName = null;
		        String pgmStatus = excelDataMap.get("ProgramStatus");
		        String pgmDesc = excelDataMap.get("ProgramDesc");
		        Integer pgmId = null;
				    //AddProgramRequest pgmresp;
				    //pgmresp = new AddProgramRequest("JUL-23-RESTAPI-Turtle01_New","InActive","JUL-23-RESTAPI-Turtle01_DESC_New");
				    //resp = requestSpec.body(pgmresp).put("/putprogram/"+getPgmId);
				    JSONObject body = new JSONObject();
				   // body.put("programId",pgmId);
				    body.put("programName", pgmName);
				    body.put("programDescription", pgmDesc);
				    //System.out.println(body);
				    resp = requestSpec.body(body).put(ConfigReader.getProperty("pgm.putById") + programId1);
		      }
		      LoggerLoad.logInfo("PUT By Missing field with  Program Id request created");
	      }
	      catch (Exception ex) 
			{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
			} 
}

  @Then("User receives Status with response body for the {string} for Program Module." )
  public void user_receives_status_with_response_body_for_the_for_program_module( String KeyOption) {
	  resp.then().log().all().extract().response();
	  switch(KeyOption)
	  {
	  case "putIdValid":
		  Program programResponse = resp.getBody().as(Program.class);
	      	updatedId1 = programResponse.programId;
	      	updatedName1 = programResponse.programName;
		  System.out.println("**Status Code-->" + resp.statusCode());
		  resp.then().assertThat().statusCode(200)
		  .contentType(ContentType.JSON)
	        .body(JsonSchemaValidator.matchesJsonSchema( getClass().getClassLoader().getResourceAsStream("getPgmbyIdjsonschema.json")));
		 
		  
	  break;
	  case "putIdInvalid":
		  System.out.println("**Status Code-->" + resp.statusCode());
	      resp.then().assertThat().statusCode(404); // Not Found
	  break;
	  case "putIdMissing":
		  System.out.println("**Status Code-->" + resp.statusCode());
	    resp.then().assertThat().statusCode(400);  
	  break;
	  }
	  LoggerLoad.logInfo("PUT By valid,invalid and missing field Program Id request validated");
  }
  
  //////////////////// PUT with Program Name ///////////////////////
  @Given("User creates PUT Request with programName for Program Module.")
  public void user_creates_put_request_with_program_name_for_program_module() {
	  	RestAssured.baseURI = baseurl;
	  	requestSpec = RestAssured.given();
	  	requestSpec.header("Content-Type", "application/json");
  }

  @When("User sends PUT request Body with valid programName from {string} and {string}  for Program Module")
  public void user_sends_put_request_body_with_valid_program_name_for_program_module(String dataKey,String sheetName) {
	  try {
	      
	      Map<String, String> excelDataMap = null;
	      excelDataMap = ExcelReader.getData(dataKey, sheetName);
	      
	      if (null != excelDataMap && excelDataMap.size() > 0) {
	    	  	String pgmName = programName2;
		        String pgmStatus = excelDataMap.get("ProgramStatus");
		        String pgmDesc = excelDataMap.get("ProgramDesc");
					
				  AddProgramRequest addpgm;  
			    addpgm =new AddProgramRequest( pgmName +  DynamicValues.SerialNumber(), pgmStatus,pgmDesc);
			    resp = requestSpec.body(addpgm).put(ConfigReader.getProperty("pgm.putByName") + pgmName); 
			    
			   /* JSONObject body = new JSONObject();
			    body.put("programName", pgmName +  DynamicValues.SerialNumber());
			    body.put("programDescription", pgmDesc);
			    body.put("programStatus", pgmStatus);
			    System.out.println(body);
			    resp = requestSpec.body(body).put(ConfigReader.getProperty("pgm.putByName") + pgmName); */
			    
			    System.out.println("Update By Name valid-->" + resp.asString());
	      }
	      LoggerLoad.logInfo("PUT By Valid Program Name request created");
	  }
	  catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		} 
  }

  

  @When("User sends PUT request Body with invalid programName from {string} and {string}  for Program Module")
  public void user_sends_put_request_body_with_invalid_program_name_for_program_module(String dataKey,String sheetName) {
	  try {
	      
	      Map<String, String> excelDataMap = null;
	      excelDataMap = ExcelReader.getData(dataKey, sheetName);
	      
	      if (null != excelDataMap && excelDataMap.size() > 0) {
	    	  	String pgmName = excelDataMap.get("ProgramName");
		        String pgmStatus = excelDataMap.get("ProgramStatus");
		        String pgmDesc = excelDataMap.get("ProgramDesc");
	  
		        AddProgramRequest addpgm;
		        addpgm = new AddProgramRequest( pgmName,pgmStatus, pgmDesc );
		        resp = requestSpec.body(addpgm).put(ConfigReader.getProperty("pgm.putByName") + pgmName);
		        
	      }
	      LoggerLoad.logInfo("PUT By InValid Program Name request created");
	  }
	  catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		} 
  }

  @When("User sends PUT request Body with missing programName from {string} and {string}  for Program Module")
  public void user_sends_put_request_body_with_missing_program_name_for_program_module(String dataKey,String sheetName) {
	  try {
	      
	      Map<String, String> excelDataMap = null;
	      excelDataMap = ExcelReader.getData(dataKey, sheetName);
	      
	      if (null != excelDataMap && excelDataMap.size() > 0) {
	    	  	String pgmNameM = programName2;
		        String pgmStatusM = null;
		        String pgmDescM = null;
	  
				/*  AddProgramRequest addpgm;
			    String pgmName =" " , pgmStatus = " ", pgmdesc= " ";
			    addpgm = new AddProgramRequest(pgmName,pgmStatus,pgmdesc);
			    resp = requestSpec.body(addpgm).put("/program/"+"JUL23-JAN-Turtle00"); 
			   */
			   JSONObject body = new JSONObject();
			    //body.put("programId", " ");
				body.put("programName", " ");
				 body.put("programDescription", pgmDescM);
				 body.put("programStatus", pgmStatusM);
			    resp = requestSpec.body(body).put(ConfigReader.getProperty("pgm.putByName")+pgmNameM);
			    
			    //System.out.println("RESPONSE BODY__**>>"+resp.body());
			    //String jsonString = resp.asString();
			    //System.out.println("RESPONSE STRING--**>>"+jsonString);
	      }
	      LoggerLoad.logInfo("PUT By Missing field with  Program Name request created");
	  	}
	  	catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		} 
  }
  
  @Then("User receives Status for the {string} for Program Module with programName.")
  public void user_receives_status_for_the_for_program_module_with_program_name(String KeyOption  ) {
	  try {
			  resp.then().log().all().extract().response();
			  switch(KeyOption)
			  {
			  case "putNameValid":
				  Program programResponse = resp.getBody().as(Program.class);
			      	updatedId2 = programResponse.programId;
			      	updatedName2 = programResponse.programName;
				  System.out.println("Status code--->" + resp.statusCode());
				  resp.then().assertThat().statusCode(200)
				  .contentType(ContentType.JSON)
			        .body(JsonSchemaValidator.matchesJsonSchema( getClass().getClassLoader().getResourceAsStream("getPgmbyIdjsonschema.json")));
				  
				  
				  break;
			  case "putNameInvalid":
				  System.out.println("Status code--->" + resp.statusCode());
			      resp.then().assertThat().statusCode(404); // Not Found
			  break;
			  case "putNameMissing":
				  System.out.println("Status code--->" + resp.statusCode());
				  resp.then().assertThat().statusCode(400);  // BAD Response
			  break;
			  }
			  LoggerLoad.logInfo("PUT By valid,invalid and missing ProgramName  request validated");
	  }
	  catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
  }

  // ************************* DELETE By program name ***********************************

	@Given("User creates DELETE Request for the LMS API endpoint for Program Module")
	public void given_endpoint_with_DELETE_By_ProgramName() {
		
		RestAssured.baseURI=baseurl;
		requestSpec= RestAssured.given().header("Content-Type", "application/json");
	}

	@When("User sends Delete Request with valid or invalid ProgramName from {string} and {string} for Program Module")
	public void user_sends_delete_request_with_valid_invalid_for(String dataKey,String sheetName) {
		try {
				Map<String, String> excelDataMap = null;
				excelDataMap = ExcelReader.getData(dataKey, sheetName);
		      switch(dataKey)
		      {
		      
		      case "Delete_pgmNamevalid":		    	  
			    	 String pgmNamevalid = updatedName1;
					//String deletePgmName= pgmName;
					System.out.println("PGMName for DELETE->"+pgmNamevalid);
					resp= requestSpec.delete(ConfigReader.getProperty("pgm.deleteByNme")+pgmNamevalid);    ////capture response from Delete request		    
					break;
		      case "Delete_pgmNameinvalid":		    	  
		    	  String pgmNameInvalid = excelDataMap.get("ProgramName");
					//String deletePgmName= pgmName;
					System.out.println("PGMName for DELETE->"+pgmNameInvalid);
					resp= requestSpec.delete(ConfigReader.getProperty("pgm.deleteByNme")+pgmNameInvalid);    ////capture response from Delete request		    
					break;
		      }
		      LoggerLoad.logInfo("Delete By valid,invalid Program Name request created");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
	

	@Then("User receives status as {string}  with response body for ProgramModule.")
	public void user_receives_status_as_with_response_body(String dataKey) {
		try {
		    	  	      
				if(dataKey.equals("Delete_pgmNamevalid")) {
					String jsonString =resp.asString();
					System.out.println("Status--->"+resp.statusCode());
					resp.then()
					.assertThat()
					.statusCode(200);		
					assertEquals(jsonString.contains("deleted Successfully!"), true);
				}else if(dataKey.equals("Delete_pgmNameinvalid")) {
					String jsonString =resp.asString();
					System.out.println("Status-->"+resp.statusCode());
					resp.then()
					.assertThat()
					.statusCode(404);		
					assertEquals(jsonString.contains("no record found"), true);
				}
				LoggerLoad.logInfo("DELETE by valid,invalid Program Name response validated");
		      
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	////*********** DELETE by Program id************************
	 
	 @Given("User creates DELETE Request for the LMS API endpoint for Program Module with PGMID")
		public void given_endpoint_with_DELETE_By_ProgramId() {
				
			RestAssured.baseURI=baseurl;
			requestSpec= RestAssured.given().header("Content-Type", "application/json");
		}
	 
	@When("User sends DELETE Request with valid ProgramID")
	public void user_sends_delete_request_with_valid_ProgramID() {
			try
			{
					Integer getPgmId = updatedId2;
					System.out.println("ID FOR DELETE->"+getPgmId);
					System.out.println("PgmName FOR DELETE->"+updatedName2);
					resp= requestSpec.delete(ConfigReader.getProperty("pgm.deletebyId")+getPgmId);					
							
			
			LoggerLoad.logInfo("DELETE by valid Program Id response created");
			}
			catch (Exception ex) 
			{
					LoggerLoad.logInfo(ex.getMessage());
					ex.printStackTrace();
			}
	}
	@Then("User receives status for valid  Programid for ProgramModule")
	public void user_receives_status_as_with_valid_Programid() {
		try
		{
			System.out.println("Status code--->" + resp.statusCode());
			String jsonString =resp.asString();
			System.out.println("Json String After DELETE Command--->"+jsonString);
			resp.then()
			.assertThat()
			.statusCode(200);		
			assertEquals(jsonString.contains("deleted Successfully!"), true);	
			LoggerLoad.logInfo("DELETE by valid Program Id response validated");
		}
		catch (Exception ex) 
		{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
		}
	}
	@When("User sends DELETE Request with invalid ProgramID")
	public void user_sends_delete_request_with_invalid_ProgramID() {
		try
		{
			Integer getPgmId=20;
			//System.out.println("static id-->"+PgmIDForDelete);
			// resp = requestSpec.queryParam("programName", "JUL-23-RESTAPI-Turtle02").when().get("/allPrograms");
			//resp = requestSpec.when().get("/allPrograms");				
			System.out.println("ID FOR DELETE->"+getPgmId);
			resp= requestSpec.delete(ConfigReader.getProperty("pgm.deletebyId")+getPgmId);
			LoggerLoad.logInfo("DELETE by invalid Program Id response created");
		}
		catch (Exception ex) 
		{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
		}
	}

	@Then("User receives status for invalid  Programid for ProgramModule")
	public void user_receives_status_as_with_Programid_invalid() {
		try
		{
			System.out.println("Status code--->" + resp.statusCode());
			String jsonString =resp.asString();
			System.out.println("Json String After DELETE Command--->"+jsonString);
			resp.then()
			.assertThat()
			.statusCode(404);		
			assertEquals(jsonString.contains("no record found"), true);	
			LoggerLoad.logInfo("DELETE by invalid Program Id response validate");
		}
		catch (Exception ex) 
		{
				LoggerLoad.logInfo(ex.getMessage());
				ex.printStackTrace();
		}
	}	
}

