#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@ProgramBatch
Feature: Program Batch
  

  @Get_All_001
  Scenario Outline: Check if user able to retrieve all batches with "<dataKey>" endpoint
    Given User creates GET Request for the LMS API "batchModule"
    When User sends HTTPS Request for endpoint from "<sheetName>" with "<dataKey>"
    Then User receives Status Code  with response body for endpoint "<sheetName>" with "<dataKey>"
    
    Examples: 
      | dataKey         |sheetName|
      | Get_All_Valid   |batch|
      | Get_All_Invalid |batch|
      
  @Get_ByBatchId_001
  Scenario Outline: Check if user able to retrieve a batch with "<dataKey>" batchId
    Given User creates GET Request for the LMS API "batchModule" 
    When User sends HTTPS Request for endpoint from "<sheetName>" with "<dataKey>"
    Then User receives Status Code  with response body for endpoint "<sheetName>" with "<dataKey>"
    
    Examples: 
      | dataKey | sheetName|
      | Get_ByBatchId_Valid   |batch|
      | Get_ByBatchId_Invalid |batch|
      
  @Get_ByBatchName_001
  Scenario Outline: Check if user able to retrieve a batch with "<dataKey>" batchName
    Given User creates GET Request for the LMS API "batchModule" 
    When User sends HTTPS Request for endpoint from "<sheetName>" with "<dataKey>"
    Then User receives Status Code  with response body for endpoint "<sheetName>" with "<dataKey>"
    Examples: 
      | dataKey | sheetName|
      | Get_ByBatchName_Valid   |batch|
      | Get_ByBatchName_Invalid |batch|
      
  @Get_ByProgramId_001
  Scenario Outline: Check if user able to retrieve a batch with "<dataKey>" ProgramName
    Given User creates GET Request for the LMS API "batchModule" 
    When User sends HTTPS Request for endpoint from "<sheetName>" with "<dataKey>"
    Then User receives Status Code  with response body for endpoint "<sheetName>" with "<dataKey>"
    Examples: 
      | dataKey | sheetName|
      | Get_ByProgramId_Valid   |batch|
      | Get_ByProgramId_Invalid |batch|
      
  @AddBatch
  Scenario Outline: Check if user able to add a record with "<dataKey>" endpoint and request body (non existing values)
    Given User creates POST Request for the LMS API "batchModule"
    When User sends POST Request with mandatory and additional fields  "<sheetName>" with "<dataKey>"
    Then User receives Status with response body for post "<sheetName>" with "<dataKey>"
        Examples: 
      | dataKey | sheetName|
      | Post_Batch_Valid |batch|
      
      
      
      
      
      
      
      
      
                  