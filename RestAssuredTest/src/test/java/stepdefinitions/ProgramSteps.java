package stepdefinitions;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hamcrest.core.IsNot;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
//import cucumber.api.java.en.Given;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import dataProviders.ExcelReader;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;


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
		public static HashMap map = new HashMap<>();
		

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
			System.out.println(resp.getBody().asPrettyString());
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
					.header("Connection","keep-alive");
				System.out.println(resp.getBody().asPrettyString());
				
		    }
		    else if (option.equals("invalid")) {
		    	resp.then()
				.assertThat()
				.statusCode(404);
		    	System.out.println(resp.getBody().asPrettyString());
		    	System.out.println("Invalid Program Id!");
		    }
			
		}
		///////********POST******************************
		@Given("User creates POST Request with fields {string} and {int} from excel")
		public void user_creates_post_request_for_lms_api_endpoint(String sheetName, Integer RowNum) throws InvalidFormatException, IOException {
			ExcelReader reader = new ExcelReader();
			String excelfile_path="C:\\Users\\anoop\\Downloads\\ProgramExcel.xlsx";
			List<Map<String,String>> logindata= reader.getData(excelfile_path, sheetName);
			String pgmName= logindata.get(RowNum).get("ProgramName");
			String pgmStatus= logindata.get(RowNum).get("ProgramStatus");
			String pgmDesc= logindata.get(RowNum).get("ProgramDesc");
			String pgmStatusCode= logindata.get(RowNum).get("statusCode");
			System.out.println("Pgm name from excel="+pgmName);
			//	requestSpec=RestAssured.given().baseUri(baseurl);
			//map.put("programName",pgmName);
			//map.put("programDescription",pgmDesc);
			//map.put("programStatus",pgmStatus);
			
			JSONObject jbody = new JSONObject();
			jbody.put("programName",pgmName);
			jbody.put("programDescription",pgmDesc);
			jbody.put("programStatus",pgmStatus);
			
			requestSpec=RestAssured.given().baseUri(baseurl+pgm_postEndpoint);
			requestSpec.header("Content-Type", "application/json"); // Add the Json to the body of the request 
			requestSpec.body(jbody.toJSONString()); // Post the request and check the response
			
			requestSpec
				.contentType("application/json")
				.body(map);
		
		}
		@When("User sends request Body with mandatory , additional  fields.")
		public void user_sends_https_request_and_request_body_with_mandatory_additional_fields() {
		    
			resp=requestSpec.when().post();
		}

		@Then("User receives Status with response body")
		public void user_receives_created_status_with_response_body() {
		    
		   resp.then().log().all().extract().response();
		   System.out.println("The status received: " + resp.statusLine());
		   System.out.println(resp.getBody().asPrettyString());
		   System.out.println("Status code---"+resp.statusCode());
		   			   
		}
//************** PUT By ID ******************
		@Given("User creates PUT Request with fields {string} and {int} from excel")
		public void user_creates_put_request_with_fields_and_from_excel(String string, Integer int1) {
		   
		}

		@When("User sends request Body with valid\\/invalid programID and missing field")
		public void user_sends_request_body_with_valid_invalid_program_id_and_missing_field() {
		    
		}

		
		
		
		

		
	}


