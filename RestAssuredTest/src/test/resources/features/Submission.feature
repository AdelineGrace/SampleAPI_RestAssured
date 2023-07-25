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
## (Comments)s
#Sample Feature Definition Template
@Submission_Module
Feature: Submission Module
  I want to use this template for my feature file

    @tag1_Get_All_Submission
    Scenario: Get Request for all submission
    Given Get request for all submission module
    When User sends HTTPS Request for submission module
    Then User receives response ok status with response body for submission module
    
    @tag2_Get_Grade_by_Valid_AssignmentID		
    Scenario: Get Request get grade by Assignment ID
    Given Check if user able to retrieve a grades with valid Assignment ID
    When User creates GET Request for the LMS API endpoint with valid Assignment ID in submission module
    Then User receives response ok status with response body for assignment ID in submission module
    
    @tag3__Get_Grade_by_InValid_AssignmentID
    Scenario: Get Request get grade by Invalid Assignment ID
    Given User creates GET Request for the LMS API endpoint with invalid Assignemnt ID
    When User sends HTTPS Request for invalid assignment ID
    Then User receives Not Found Status with message and boolean success details for submission module
    
    @tag4__Get_Grade_by_Valid_StudentID
    Scenario: Get Request get grade by Student ID
    Given User creates GET Request for the LMS API endpoint with valid Student Id for submission module
    When  User sends HTTPS Request for Student ID for submission module
    Then   User receives response ok status with response body for student ID for submission module
    
    @tag5_Get_Grade_by_InValid_StudentID
    Scenario: Get Request get grade by Invalid Student ID
    Given User creates GET Request for the LMS API endpoint with invalid Student ID for submission module
    When User sends HTTPS Request for Invalid Student ID for submission module
    Then User receives Not Found Status with message and boolean success details for student ID for submission module
   
   
    @tag6_Get_Grade_by_Valid_BatchID
    Scenario: Get Request get grade by Batch ID
    Given User creates GET Request for the LMS API endpoint with valid Batch Id for submission module
    When  User sends HTTPS Request for Batch ID for submission module
    Then   User receives response ok status with response body for Batch ID for submission module
    
    @tag7_Get_Grade_by_InValid_BatchID
    Scenario: Get Request get grade by Invalid Batch ID
    Given User creates GET Request for the LMS API endpoint with invalid Batch ID for submission module
    When User sends HTTPS Request for Invalid Batch ID for submission module
    Then User receives Not Found Status with message and boolean success details for Batch ID for submission module
    
    @tag8_Get_Submission_by_Valid_BatchID
    Scenario: Get Request get submission by Batch ID
    Given User creates GET Request for the LMS API endpoint with valid Batch Id for submission 
    When  User sends HTTPS Request for submission by Batch ID for submission module
    Then   User receives response ok status with response body for submission by Batch ID
    
    @tag9_Get_Submission_by_InValid_BatchID
    Scenario: Get Request get submission by Invalid Batch ID
    Given User creates GET Request for the LMS API endpoint with invalid Batch ID for submission
    When User sends HTTPS Request for submission by Invalid Batch ID
    Then User receives Not Found Status with message and boolean success details for submission by Batch ID
    
    @tag10_Get_Submission_by_Valid_UserID
    Scenario: Get Request get submission by UserID
    Given User creates GET Request for the LMS API endpoint with valid User Id for submission
    When  User sends HTTPS Request for submission by UserID
    Then   User receives response ok status with response body for submission by UserID
    
    @tag11_Get_Submission_by_Invalid_UserID
    Scenario: Get Request get submission by Invalid UserID
    Given User creates GET Request for the LMS API endpoint with invalid UserID for submission
    When User sends HTTPS Request for submission by Invalid UserID
    Then User receives Not Found Status with message and boolean success details for submission by UserID
    
    @tag12_PostRequest
    Scenario: create a POST submission  with valid endpoint and request body
    Given User creates POST Request for the LMS API endpoint for submission
    When User sends HTTPS Request and request Body with Mandatory field	
    Then User receives ok created status with response body for post request submission
    
    @tag13_PostRequest_Negative_Scenario1
    Scenario: create a POST request for submission with valid endpoint and Invalid request body
    Given User creates POST Request for the LMS API endpoint for submission module
    When User sends HTTPS Request and request Body with Mandatory field	from " <Sheetname>" and <RowNumber> submission module
    Then User receives Bad request status created status with response body for post request submission module
    
     Examples:
        
          |Sheetname |RowNumber|
          |PostsubNeg|        0|
          
   
    
    @tag14_PostRequest_Negative_Scenario2
    Scenario: create a POST request for submission with valid endpoint and without request body
    Given User creates POST Request for the submission module LMS API endpoint
    When User sends HTTPS Request and request Body without Mandatory field	from "<Sheetname>" and <RowNumber> submission module
    Then User receives Bad request with response body for post request submission module
    
          Examples:
          |Sheetname |RowNumber|
          |PostsubNeg|        1|
          
    
    
    
    
    
    
    