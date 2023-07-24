package stepdefinitions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.groovy.json.internal.JsonStringDecoder;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
//import cucumber.api.java.en.Given;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import dataProviders.ExcelReader;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.*;

import org.json.simple.JSONObject;

import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.response.Assignment;
import apiEngine.model.response.Program;


public class ProgramSteps {

	
		String baseurl="https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
		public String pgm_getallendpoint="/allPrograms";
		public String pgm_getOnepgm="/programs/{programId}";
		public String pgm_postEndpoint="/saveprogram";
		public String pgm_putByName="/program/{programName}";
		public String pgm_putById="/putprogram/{programId}";
		public String pgm_deleteById="/deletebyprogid/{programId}";
		public String pgm_deleteByNme="/deletebyprogname/{programName}";
		
		RequestSpecification requestSpec;
		Response resp;
		//public static HashMap map = new HashMap<>();
		
	///////********POST******************************
			@Given("User creates POST Request with fields {string} and {string} from excel")
			public void user_creates_post_request_for_lms_api_endpoint(String RowNum,String sheetName) throws Exception {
				ExcelReader reader = new ExcelReader();
				Map<String,String> excelDataMap=null;
				excelDataMap = reader.getData(RowNum,sheetName);
				String pgmName= excelDataMap.get("ProgramName");
				String pgmStatus= excelDataMap.get("ProgramStatus");
				String pgmDesc= excelDataMap.get("ProgramDesc");
				String pgmStatusCode= excelDataMap.get("statusCode");
				//Integer statusCode= Integer.parseInt(pgmStatusCode);
				System.out.println("Pgm name from excel="+pgmName);
				System.out.println("Pgm Status from excel="+pgmStatus);
				System.out.println("Pgm Desc from excel="+pgmDesc);
				
			/*	JSONObject jbody = new JSONObject();
				jbody.put("programName",pgmName);
				jbody.put("programDescription",pgmDesc);
				jbody.put("programStatus",pgmStatus);
				requestSpec=RestAssured.given()
						.header("Content-Type", "application/json")
						.body(jbody.toJSONString()); // Post the request and check the response
				*/
				AddProgramRequest program ;
				if(pgmName.isBlank()) {
					pgmName="";
				 program = new AddProgramRequest(pgmName,pgmStatus,pgmDesc);
				 requestSpec=RestAssured.given()
							.header("Content-Type", "application/json")
							.body(program);
				}else if(pgmStatus.isBlank()) {
					pgmStatus="";
					 program = new AddProgramRequest(pgmName,pgmStatus,pgmDesc);
					 requestSpec=RestAssured.given()
								.header("Content-Type", "application/json")
								.body(program);
				}else {
					program = new AddProgramRequest(pgmName,pgmStatus,pgmDesc);
					 requestSpec=RestAssured.given()
								.header("Content-Type", "application/json")
								.body(program);
				}						
			}
			@When("User sends request Body with mandatory , additional  fields.")
			public void user_sends_https_request_and_request_body_with_mandatory_additional_fields() {
			    
				resp=requestSpec.when().post(baseurl+pgm_postEndpoint);
				//System.out.println("Path -> " +baseurl+pgm_postEndpoint);
				System.out.println("response-> " + resp.asPrettyString());
			}

			@Then("User receives Status with response body {string} and {string} from excel")
			public void user_receives_created_status_with_response_body(String RowNum,String sheetName) throws Exception {
			    
			   resp.then().log().all().extract().response();
			   System.out.println("The status received:-> " + resp.statusLine());			   
			   System.out.println("Status code--->"+resp.statusCode());
			   resp.then().assertThat()
			      .body(JsonSchemaValidator.matchesJsonSchema(new File("src\\test\\resources\\ProgramSchema.json")));
			   
			   			   
			}
		///////************Get all**********************
		
		@Given("User creates GET Request for the LMS API endpoint for Program Module")
		public void user_creates_get_request_for_the_lms_api_endpoint_for_program_module() {
			this.requestSpec=RestAssured.given().baseUri(this.baseurl);
		}

		@When("User sends HTTPS Request in Program Module")
		public void user_sends_https_request_program_module() {
			 this.resp= requestSpec.when().get(pgm_getallendpoint);
		}

		@Then("User receives 200 OK Status with response body.")
		public void user_receives_ok_status_with_response_body() {
			
		    ValidatableResponse valid_resp= resp.then();
		 //  int id= resp.jsonPath().getInt("programId");
		   //System.out.println("Program id : -"+id);
		    System.out.println(resp.getStatusCode());
			//System.out.println(resp.getBody().asPrettyString());
			valid_resp.assertThat().statusCode(200);    
		}
		
		////*******Get One Pgm by Id************
		@Given("User creates GET Request for the LMS API endpoint for valid")
		public void user_creates_get_request_for_the_lms_api_endpoint_for_valid() {
			requestSpec=RestAssured.given().baseUri(baseurl);
		   
		}
		@When("User sends HTTPS Request with valid or invalid for  {int}")
		public void user_sends_https_request_with_valid(Integer pgmid) {
				resp= requestSpec.when().get("/programs/"+pgmid);	
		}
		
		@Then("User receives valid or invalid as {string} and {int} with response body.")
		public void user_receives_status_and_details(String option,Integer statuscode) {
			int GetIdstatuscode = resp.getStatusCode();
		    if(option.equals("valid")) {
				resp.then()
					.assertThat()
					.statusCode(200)
					.and()
					.body(JsonSchemaValidator.matchesJsonSchema(new File("src\\test\\resources\\ProgramSchema.json")));
					//.header("Connection","keep-alive");
				System.out.println(resp.getBody().asPrettyString());
				
		    }
		    else if (option.equals("invalid")) {
		    	System.out.println("Invalid Program Id!");
		    	resp.then()
				.assertThat()
				.statusCode(404)
				.and()
				.body(JsonSchemaValidator.matchesJsonSchema(new File("src\\test\\resources\\ProgramSchema.json")));				
		    	//System.out.println(resp.getBody().asPrettyString());
		    	
		    }
			
		}
		
		// ************************* DELETE ***********************************
	
		@Given("User creates DELETE Request for the LMS API endpoint for Program Module")
		public void user_creates_delete_request_for_the_lms_api_endpoint_for_program_module() {
			//requestSpec=RestAssured.given().baseUri(baseurl);
					//.header("Content-Type", "application/json");	
			RestAssured.baseURI=baseurl;
		}

		@When("User sends Delete Request with valid or invalid ProgramName {string}.")
		public void user_sends_delete_request_with_valid_invalid_for(String pgmName) {
			String deletePgmName= pgmName;
			resp= requestSpec.delete("/deletebyprogname/"+deletePgmName);    ////capture response from Delete request
		    
		}

		@Then("User receives status as {string}  with response body.")
		public void user_receives_status_as_with_response_body(String option) {
			if(option.equals("valid")) {
				String jsonString =resp.asString();
				System.out.println("Json String After DELETE Command--->"+jsonString);
				resp.then()
				.assertThat()
				.statusCode(200);		
				assertEquals(jsonString.contains("deleted Successfully!"), true);
			}else if(option.equals("invalid")) {
				String jsonString =resp.asString();
				System.out.println("Json String After DELETE Command--->"+jsonString);
				resp.then()
				.assertThat()
				.statusCode(404);		
				assertEquals(jsonString.contains("no record found"), true);
			}
		}

		@When("User sends DELETE Request with valid or invalid for {int}")
		public void user_sends_delete_request_with_valid_or_invalid_for(Integer int1) {
			
			/*resp = requestSpec.get(pgm_getallendpoint);			
			JsonPath jsonPathEvaluator = resp.jsonPath();
			List<Program> allPgm = jsonPathEvaluator.getList("", Program.class);
			//System.out.print(allPgm);*/
			
			RestAssured.baseURI= "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
			RequestSpecification httpRequest = RestAssured.given();
			Response res = httpRequest.param("programId","10717").get("/allPrograms");
			ResponseBody body = res.body();
			//Converting the response body to string object
			String rbdy = body.asString();
			//Creating object of JsonPath and passing the string response body as parameter
			JsonPath jpath = new JsonPath(rbdy);
			//Storing publisher name in a string variable
			String title = jpath.getString("programId");
			System.out.println("ResponseBody received--->"+title);
			
			/*resp = RestAssured.given().queryParam("programName","JUL-23-RESTAPI-Turtle02")
					.get("/allPrograms");
			ResponseBody respnsbody= resp.body();
			String rbdy  = respnsbody.asString();
			JsonPath jpath = new JsonPath(rbdy);
			String title = jpath.getString("programName");
			System.out.println("ResponseBody received--->"+title);*/
			
		
		}


		@Then("User receives status as {string} with valid or invalid Programid.")
		public void user_receives_status_as_with_valid_invalid(String string) {
		    
		    
		}

			
		
		//////****** PUT************/////////////////

@Given("User creates PUT Request with fields {string} and putIdValid from excel")
public void user_creates_put_request_with_fields_and_put_id_valid_from_excel(String string) {
    
    
}

@When("User sends PUT request Body with valid\\/invalid programID and missing field")
public void user_sends_put_request_body_with_valid_invalid_program_id_and_missing_field() {
    
    
}

@Then("User receives Status with response body")
public void user_receives_status_with_response_body() {
    
    
}

@Given("User creates PUT Request with fields {string} and putIdInvalid from excel")
public void user_creates_put_request_with_fields_and_put_id_invalid_from_excel(String string) {
    
    
}

@Given("User creates PUT Request with fields {string} and putIdMissing from excel")
public void user_creates_put_request_with_fields_and_put_id_missing_from_excel(String string) {
    
    
}

@Given("User creates PUT Request with fields {string} and putNameValid from excel")
public void user_creates_put_request_with_fields_and_put_name_valid_from_excel(String string) {
    
    
}

@When("User sends PUT request Body with valid\\/invalid ProgramName and missing field")
public void user_sends_put_request_body_with_valid_invalid_program_name_and_missing_field() {
    
    
}

@Given("User creates PUT Request with fields {string} and putNameInvalid from excel")
public void user_creates_put_request_with_fields_and_put_name_invalid_from_excel(String string) {
    
    
}

@Given("User creates PUT Request with fields {string} and putNameMissing from excel")
public void user_creates_put_request_with_fields_and_put_name_missing_from_excel(String string) {
    
    
}

	
	}


