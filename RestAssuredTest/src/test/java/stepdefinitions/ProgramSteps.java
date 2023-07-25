package stepdefinitions;

import static org.junit.Assert.assertEquals;

import apiEngine.model.request.AddProgramRequest;
import apiEngine.model.response.Program;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import io.restassured.path.json.exception.*;
import org.json.simple.JSONObject;
import utilities.LoggerLoad;

public class ProgramSteps {

  String baseurl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
  public String pgm_getallendpoint = "/allPrograms";
  public String pgm_getOnepgm = "/programs/{programId}";
  public String pgm_postEndpoint = "/saveprogram";
  public String pgm_putByName = "/program/{programName}";
  public String pgm_putById = "/putprogram/{programId}";
  public String pgm_deleteById = "/deletebyprogid/{programId}";
  public String pgm_deleteByNme = "/deletebyprogname/{programName}";

  RequestSpecification requestSpec;
  Response resp;
  ValidatableResponse valid_resp;

  ///////********POST******************************

  @Given(
    "User creates POST Request with fields {string} and {string} from excel"
  )
  public void user_creates_post_request_for_lms_api_endpoint( String sheetName, String dataKey) throws Exception {
    try {
      AddProgramRequest program;
      Map<String, String> excelDataMap = null;
      excelDataMap = ExcelReader.getData(dataKey, sheetName);
      if (null != excelDataMap && excelDataMap.size() > 0) {
        String pgmName = excelDataMap.get("ProgramName");
        String pgmStatus = excelDataMap.get("ProgramStatus");
        String pgmDesc = excelDataMap.get("ProgramDesc");
        //String pgmStatusCode= excelDataMap.get("statusCode");
        //Integer statusCode= Integer.parseInt(pgmStatusCode)

        if (pgmName.isBlank()) {
          pgmName = " ";
          program = new AddProgramRequest(pgmName, pgmStatus, pgmDesc);
        } else if (pgmStatus.isBlank()) {
          pgmStatus = " ";
          program = new AddProgramRequest(pgmName, pgmStatus, pgmDesc);
        } else {
          program = new AddProgramRequest(pgmName, pgmStatus, pgmDesc);
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
    this.resp = this.requestSpec.when().post(pgm_postEndpoint);
    //System.out.println("response-> " + resp.asPrettyString());

  }

  @Then(
    "User receives Status with response body {string} and {string} from excel."
  )
  public void user_receives_created_status_with_response_body( String DataKey, String sheetName) throws Exception {
    String jsonString = this.resp.asString();
    this.resp.then().log().all().extract().response();
    if (DataKey.equals("postNew") || DataKey.equals("postNew1")) {
      System.out.println("Status code--->" + resp.statusCode());
      //Program resProgram = resp.getBody().as(Program.class);
      //PgmIDForDelete = resProgram.programId;
      resp
        .then()
        .assertThat()
        .statusCode(201)
        .and()
        .body(
          JsonSchemaValidator.matchesJsonSchema(
            getClass()
              .getClassLoader()
              .getResourceAsStream("getPgmbyIdjsonschema.json")));
      LoggerLoad.logInfo("Program POST request created");
    } else if (DataKey.equals("postExist")) {
      System.out.println("Status code--->" + resp.statusCode());
      this.resp.then().assertThat().statusCode(400);
      assertEquals(jsonString.contains("cannot create program"), true);
      LoggerLoad.logInfo("Program POST request already Exist");
    } else if (DataKey.equals("postMissing")) {
      System.out.println("Status code--->" + resp.statusCode());
      this.resp.then().assertThat().statusCode(500);
      LoggerLoad.logInfo("Program POST request Missing Value");
    }
  }

  ///////************Get all**********************

  @Given("User creates GET Request for the LMS API endpoint for Program Module")
  public void user_creates_get_request_for_the_lms_api_endpoint_for_program_module() {
    this.requestSpec = RestAssured.given().baseUri(this.baseurl);
  }

  @When("User sends HTTPS Request in Program Module")
  public void user_sends_https_request_program_module() {
    this.resp = requestSpec.when().get(pgm_getallendpoint);
  }

  @Then("User receives 200 OK Status with response body.")
  public void user_receives_ok_status_with_response_body() {
    ValidatableResponse valid_resp = resp.then();
    valid_resp
      .assertThat()
      .statusCode(200);
     // .and()
      //.body(
        //JsonSchemaValidator.matchesJsonSchema(
          //getClass().getClassLoader().getResourceAsStream("getallProgramjsonschema.json")));
    //  int id= resp.jsonPath().getInt("programId");
    //System.out.println("Program id : -"+id);
  }

  ////*******Get One Pgm by Id************
  @Given("User creates GET Request for the LMS API endpoint for valid")
  public void user_creates_get_request_for_the_lms_api_endpoint_for_valid() {
    requestSpec =
      RestAssured
        .given()
        .baseUri(baseurl)
        .header("Content-Type", "application/json");
  }

  @When("User sends HTTPS Request with valid or invalid for  {int}")
  public void user_sends_https_request_with_valid(Integer pgmid) {
    resp = requestSpec.when().get("/programs/" + pgmid);
  }

  @Then( "User receives valid or invalid  {string} with response body for ProgramModule.")
  public void user_receives_status_and_details(String option) {
    //int GetIdstatuscode = resp.getStatusCode();
    String jsonString = resp.asString();
    if (option.equals("valid")) {
      resp
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .body(
          JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getPgmbyIdjsonschema.json")
          )
        );
      System.out.println(resp.getBody().asPrettyString());
    } else if (option.equals("invalid")) {
      System.out.println("Invalid Program Id!");
      resp.then().assertThat().statusCode(404);
     // assertEquals(jsonString.contains("not found"), true);
    }
  }

  //////****** PUT************/////////////////

  @Given("User creates PUT Request with programID for Program Module.")
  public void user_creates_put_request_with_valid_program_id_for_program_module() {
    RestAssured.baseURI = baseurl;
    requestSpec =
      RestAssured.given().header("Content-Type", "application/json");
  }

  @When("User sends PUT request Body with valid programID for Program Module")
  public void user_sends_put_request_body_with_valid_program_id_for_program_module() {
    Integer putPgmId ;
    AddProgramRequest addpgm;
    resp = requestSpec.when().get("/allPrograms");
    JsonPath jsonPathEvaluator = resp.jsonPath();
    List<Program> allpgms = jsonPathEvaluator.getList("", Program.class);
    for (Program pgm : allpgms) { // To fetch the PGM Id using PGM Name.**
      if (pgm.programName.equals("JUL-23-RESTAPI-Turtle01")) {
        Integer getPgmId = pgm.programId;
        putPgmId = getPgmId;
        System.out.println("ID FOR PUT Valid->" + getPgmId);
        addpgm = new AddProgramRequest("JUL-23-RESTAPI-Turtle01_New","InActive","JUL-23-RESTAPI-Turtle01_DESC_New");
        resp = requestSpec.body(addpgm).put("/putprogram/" + putPgmId); 
      }
    }
    }

  @Then("User receives Status with response body for the {string} for Program Module." )
  public void user_receives_status_with_response_body_for_the_for_program_module( String KeyOption) {
    valid_resp = resp.then();
    if (KeyOption.equalsIgnoreCase("putIdValid")) {
      String jsonString = resp.asString();
      System.out.println("**After UPdate-->" + jsonString);
      System.out.println("**Status Line-->" + resp.statusLine());
      valid_resp
        .assertThat()
        .statusCode(200)
        .body(
          JsonSchemaValidator.matchesJsonSchema(
            getClass()
              .getClassLoader()
              .getResourceAsStream("getPgmbyIdjsonschema.json")
          )
        );
    } else if (KeyOption.equalsIgnoreCase("putIdInvalid")) {
      System.out.println("**Status Line-->" + resp.statusLine());
      valid_resp.assertThat().statusCode(404);
    } else if (KeyOption.equalsIgnoreCase("putIdMissing")) {
      System.out.println("**Status Line-->" + resp.statusLine());
      valid_resp.assertThat().statusCode(400);
    }
  }

  @When("User sends PUT request Body with invalid programID for Program Module")
  public void user_sends_put_request_body_with_invalid_program_id() {
    Integer getPgmId = 30;
    //AddProgramRequest pgmresp;
    //pgmresp = new AddProgramRequest(30,"JUL-23-RESTAPI-Turtle01_New","InActive","JUL-23-RESTAPI-Turtle01_DESC_New");
    //resp = requestSpec.body(pgmresp).put("/putprogram/"+getPgmId);
    JSONObject body = new JSONObject();
    body.put("programId", getPgmId);
    body.put("programName", "JUL-23-RESTAPI-Turtle01_New");
    body.put("programDescription", "JUL-23-RESTAPI-Turtle01_DESC_New");
    body.put("programStatus", "InActive");
    System.out.println(body);
    resp = requestSpec.body(body).put("/putprogram/" + getPgmId);
  }

  @When("User sends PUT request Body with missing programID for Program Module")
  public void user_sends_put_request_body_with_missing_program_id_for_program_module() {
    JSONObject body = new JSONObject();
    body.put("programId", " ");
    body.put("programName", "JUL-23-RESTAPI-Turtle01_T");
    body.put("programDescription", "Testing");
    body.put("programStatus", "InActive");
    System.out.println(body);
    //AddProgramRequest pgmresp;
    //pgmresp = new AddProgramRequest(id,"JUL-23-RESTAPI-Turtle01_New","InActive","JUL-23-RESTAPI-Turtle01_DESC_New");
    resp = requestSpec.body(body).put("/putprogram/" + 10);
  }

  @Given("User creates PUT Request with programName for Program Module.")
  public void user_creates_put_request_with_program_name_for_program_module() {
    RestAssured.baseURI = baseurl;
    requestSpec = RestAssured.given().header("Content-Type", "application/json");
  }

  @When("User sends PUT request Body with valid programName for Program Module")
  public void user_sends_put_request_body_with_valid_program_name_for_program_module() {
    AddProgramRequest addpgm;
    String valipgmName = "JUL-23-RESTAPI-Turtle02";
    addpgm =new AddProgramRequest( "JUL-23-RESTAPI-Turtle02_New", "InActive","JUL-23-RESTAPI-Turtle02_DESC_New");
    resp = requestSpec.body(addpgm).put("/program/" + valipgmName);
    System.out.println("Update By Name valid-->" + resp.asString());
  }

  @Then("User receives Status for the {string} for Program Module with programName.")
  public void user_receives_status_for_the_for_program_module_with_program_name(String KeyOption  ) {
    valid_resp = resp.then();
    if (KeyOption.equalsIgnoreCase("putNameValid")) {
      String jsonString = resp.asString();
      System.out.println("**After UPdate-->" + jsonString);
      System.out.println("**Status Line-->" + resp.statusLine());
      valid_resp
        .assertThat()
        .statusCode(200)
        .body(
          JsonSchemaValidator.matchesJsonSchema( getClass().getClassLoader().getResourceAsStream("getPgmbyIdjsonschema.json")
          )
        );
    } else if (KeyOption.equalsIgnoreCase("putNameInvalid")) {
      System.out.println("**Status Line-->" + resp.statusLine());
      valid_resp.assertThat().statusCode(404);
    } else if (KeyOption.equalsIgnoreCase("putNameMissing")) {
      System.out.println("**Status Line-->" + resp.statusLine());
      valid_resp.assertThat().statusCode(400);
    }
  }

  @When(
    "User sends PUT request Body with invalid programName for Program Module"
  )
  public void user_sends_put_request_body_with_invalid_program_name_for_program_module() {
    AddProgramRequest addpgm;
    String invalid_name = "Hello-World";
    addpgm =
      new AddProgramRequest(
        invalid_name,
        "InActive",
        "JUL-23-RESTAPI-Turtle02_invalid"
      );
    resp = requestSpec.body(addpgm).put("/program/" + invalid_name);
  }

  @When(  "User sends PUT request Body with missing programName for Program Module")
  public void user_sends_put_request_body_with_missing_program_name_for_program_module() {
    AddProgramRequest addpgm;
    String pgmName =" " , pgmStatus = " ", pgmdesc= " ";
    addpgm = new AddProgramRequest(pgmName,pgmStatus,pgmdesc);
    resp = requestSpec.body(addpgm).put("/program/"+"JUL23-JAN-Turtle00"); 
   
   JSONObject body = new JSONObject();
    //body.put("programId", " ");
	body.put("programName", " ");
	 body.put("programDescription", " ");
	 body.put("programDescription", " ");
    resp = requestSpec.body(body).put("/program/"+"JUL23-Turtles1234");
    
    System.out.println("RESPONSE BODY__**>>"+resp.body());
    String jsonString = resp.asString();
    System.out.println("RESPONSE STRING--**>>"+jsonString);
  }

  // ************************* DELETE ***********************************

	@Given("User creates DELETE Request for the LMS API endpoint for Program Module")
	public void given_endpoint_with_DELETE_By_ProgramName() {
		//requestSpec=RestAssured.given().baseUri(baseurl);
				//.header("Content-Type", "application/json");	
		RestAssured.baseURI=baseurl;
		requestSpec= RestAssured.given().header("Content-Type", "application/json");
	}

	@When("User sends Delete Request with valid or invalid ProgramName {string}.")
	public void user_sends_delete_request_with_valid_invalid_for(String pgmName) {
		String deletePgmName= pgmName;
		System.out.println("PGMName for DELETE->"+deletePgmName);
		resp= requestSpec.delete("/deletebyprogname/"+deletePgmName);    ////capture response from Delete request		    
	}

	@Then("User receives status as {string}  with response body for ProgramModule.")
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
	 
	 @Given("User creates DELETE Request for the LMS API endpoint for Program Module with PGMID")
		public void given_endpoint_with_DELETE_By_ProgramId() {
			//requestSpec=RestAssured.given().baseUri(baseurl);
					//.header("Content-Type", "application/json");	
			RestAssured.baseURI=baseurl;
			requestSpec= RestAssured.given().header("Content-Type", "application/json");
		}
	 
	@When("User sends DELETE Request with valid ProgramID")
	public void user_sends_delete_request_with_valid_ProgramID() {
		Integer getPgmId=0;
		//System.out.println("static id-->"+PgmIDForDelete);
		 //resp = requestSpec.queryParam("programName", "JUL-23-RESTAPI-Turtle02").when().get("/allPrograms");
		resp = requestSpec.when().get("/allPrograms");		
		
			JsonPath jsonPathEvaluator = resp.jsonPath();				
			List<Program> allpgms = jsonPathEvaluator.getList("", Program.class);
			for(Program pgm : allpgms)
			{
				if(pgm.programName.equalsIgnoreCase("JUL-23-RESTAPI-Turtle02_New")) {
					getPgmId= pgm.programId;
					System.out.println("ID FOR DELETE->"+getPgmId);
					System.out.println("PgmName FOR DELETE->"+pgm.programName);
					resp= requestSpec.delete("/deletebyprogid/"+getPgmId);					
				}			
			}		
	}
	@Then("User receives status for valid  Programid for ProgramModule")
	public void user_receives_status_as_with_valid_Programid() {			
			String jsonString =resp.asString();
			System.out.println("Json String After DELETE Command--->"+jsonString);
			resp.then()
			.assertThat()
			.statusCode(200);		
			assertEquals(jsonString.contains("deleted Successfully!"), true);		    
	}
	@When("User sends DELETE Request with invalid ProgramID")
	public void user_sends_delete_request_with_invalid_ProgramID() {
		Integer getPgmId=20;
		//System.out.println("static id-->"+PgmIDForDelete);
		// resp = requestSpec.queryParam("programName", "JUL-23-RESTAPI-Turtle02").when().get("/allPrograms");
		//resp = requestSpec.when().get("/allPrograms");				
			System.out.println("ID FOR DELETE->"+getPgmId);
			resp= requestSpec.delete("/deletebyprogid/"+getPgmId);
	}

	@Then("User receives status for invalid  Programid for ProgramModule")
	public void user_receives_status_as_with_Programid_invalid() {
		
			String jsonString =resp.asString();
			System.out.println("Json String After DELETE Command--->"+jsonString);
			resp.then()
			.assertThat()
			.statusCode(404);		
			assertEquals(jsonString.contains("no record found"), true);	    
	}	
}

