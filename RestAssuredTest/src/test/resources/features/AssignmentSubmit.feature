@AssignmentSubmit
Feature: Assignment Submit module

  @tag1_CreateAssignmentSubmit
  Scenario Outline: Check if user able to create a submission with "<dataKey>" endpoint and request body
  Given User creates POST Assignment Submit Request with fields from "<sheetName>" with "<dataKey>"
  When User sends HTTP POST Assignment Submit Request for "<dataKey>"
  Then User receives response for POST Assignment Submit "<sheetName>" with "<dataKey>"
  
  Examples:
  | sheetName        | dataKey                                   |
  | assignmentSubmit | Post_AssignmentSubmit_Valid               |
  | assignmentSubmit | Post_AssignmentSubmit_Repeated            |
  | assignmentSubmit | Post_AssignmentSubmit_MissingSubDesc      |
  | assignmentSubmit | Post_AssignmentSubmit_MissingUserId       |
  | assignmentSubmit | Post_AssignmentSubmit_MissingAssignmentId |
  | assignmentSubmit | Post_AssignmentSubmit_MissingSubDateTime  |
  
  @tag2_Get_All_Submission
  Scenario: Get Request for all submission
  Given Get request for all submission module
  When User sends HTTPS Request for submission module
  Then User receives response ok status with response body for submission module
  
  @tag3_Get_Grade_by_Valid_AssignmentID
  Scenario Outline: Get Request get grade by Assignment ID with "<datakey>"
  Given Check if user able to retrieve a grades with valid Assignment ID
  When User creates GET Request for the LMS API endpoint with "<sheetName>" and "<datakey>" valid Assignment ID in submission module
  Then User receives response ok status with response body for assignment ID in submission module
  
  Examples:
  | datakey                | sheetName    |
  | Get_Valid_assignmentid | assignmentid |
  | Get_Valid_assignmentid | assignmentid |
  
  @tag3_Get_Grade_by_Valid_StudentID
  Scenario Outline: Get Request get grade by Student ID
  Given User creates GET Request for the LMS API endpoint with valid Student Id for submission module
  When User sends HTTPS Request for Student ID for "<sheetName>" and "<datakey>" submission module
  Then User receives response ok status with response body for student ID for submission module
  
  Examples:
  | datakey               | sheetName |
  | Get_Valid_StudentId   | Studentid |
  | Get_InValid_StudentId | Studentid |
  
  @tag4_Get_Grade_by_Valid_BatchID
  Scenario Outline: Get Request get grade by Batch ID "<datakey>"
  Given User creates GET Request for the LMS API endpoint with valid Batch Id for submission module
  When User sends HTTPS Request for Batch ID for "<sheetName>" and  "<datakey>" submission module
  Then User receives response ok status with response body for Batch ID for submission module
  
  Examples:
  | datakey             | sheetName |
  | Get_Valid_BatchId   | Batchid   |
  | Get_InValid_BatchId | Batchid   |
  
   
  @tag5_Get_Submission_by_Valid_BatchID
  Scenario Outline: Get Request get submission by Batch ID
    Given User creates GET Request for the LMS API endpoint with valid Batch Id for submission
    When User sends HTTPS Request for submission by Batch ID for "<sheetName>" and "<datakey>" submission module
    Then User receives response ok status with response body for submission by Batch ID

    Examples: 
      | datakey                       | sheetName |
      | Get_Valid_batchIdbysubmission | batchid   |
      | Get_Valid_batchIdbysubmission | batchid   |

  @tag6_Get_Submission_by_UserID
  Scenario Outline: Get Request get submission by UserID
    Given User creates GET Request for the LMS API endpoint with valid User Id for submission
    When User sends HTTPS Request for submission by UserID "<sheetName>" and "<datakey>"
    Then User receives response ok status with response body for submission by UserID

    Examples: 
      | datakey            | sheetName |
      | Get_Valid_userId   | userid    |
      | Get_InValid_userId | userid    |
      
  
  @tag7_PutResubmit_Assignment
  Scenario Outline: Check if user able to update a submission with submissionId and mandatory request body
    Given User creates PUT Request for Resubmitting a submission module in the LMS API submissionModule "<sheetName>" with "<dataKey>"
    When User sends PUT Request for Resubmitting with mandatory and additional fields  "<sheetName>" with "<dataKey>"
    Then User receives Status with response body for put resubmit "<sheetName>" with "<dataKey>" in submission module

    Examples: 
      | dataKey                             | sheetName     |
      | Put_Submission_Valid                | putsubmission |
      | Put_Submission_Invalid              | putsubmission |
      

  @tag8_PutGradeBySubmissionId
  Scenario Outline: Check if user able to update a submission with submissionId and mandatory request body
    Given User creates PUT Request for updating a grade in submission module in the LMS API submissionModule "<sheetName>" with "<dataKey>"
    When User sends PUT Request for Grade submission with mandatory and additional fields  "<sheetName>" with "<dataKey>"
    Then User receives Status with response body for put Grade "<sheetName>" with "<dataKey>" in submission module

    Examples: 
      | dataKey                                  | sheetName     |
      | Put_GradeSubmission_Valid                | putsubmission |
      | Put_GradeSubmission_Invalid              | putsubmission |
      
  
      
      
  @tag9_DeleteAssignmentSubmitBySubmissionId
  Scenario Outline: Check if user able to delete a submission with valid and invalid Submission ID
    Given User creates DELETE Request with "<dataKey>" scenario
    When User sends the HTTP Delete Assignment Submit Request "<dataKey>"
    Then User receives response for DELETE Assignment Submit "<sheetName>" with "<dataKey>"

    Examples: 
      | tsheetName       | dataKey                           |
      | assignmentSubmit | Delete_AssignmentSubmit_ValidId   |
      | assignmentSubmit | Delete_AssignmentSubmit_DeletedId |
