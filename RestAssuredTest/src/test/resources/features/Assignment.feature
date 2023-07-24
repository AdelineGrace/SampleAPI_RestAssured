@Assignment
Feature: Assignment module

  @CreateAssignment
  Scenario Outline: Check if user able to add a record with "<dataKey>" endpoint and request body
    Given User creates POST Assignment Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP POST Assignment Request
    Then User receives response for POST "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName | dataKey                        |  
      | batch     | Post_Batch_Valid               |  
      | batch     | Post_Batch_Existing            |  
      | batch     | Post_Batch_Missing_BatchStatus |  
      | batch     | Post_Batch_Missing_BatchName   |  
      | batch     | Post_Batch_Missing_NoOfClasses |  
      | batch     | Post_Batch_Missing_ProgramId   |  
  #@GetAllAssignments
  #Scenario: Check if user able to retrieve a record with valid LMS API
    #Given User creates GET Request for the LMS API endpoint
    #When User sends GetAllAssignments Request
    #Then User receives OK Status with response body containing all assignments
#
  #@GetAssignmentByID
  #Scenario: Check if user able to retrieve a record with valid Assignment ID
    #Given User creates GET Request for the LMS API endpoint with valid Assignment ID
    #When User sends GetAssignmentByAssignmentid Request with valid id
    #Then User receives OK Status with response body containing valid assignment
#
  #@GetAssignmentByID
  #Scenario: Check if user able to retrieve a record with invalid Assignment ID
    #Given User creates GET Request for the LMS API endpoint with invalid Assignment ID
    #When User sends GetAssignmentByAssignmentid Request with invalid id
    #Then User receives Not Found Status with message and boolean success details
#
  #@DeleteAssignmentByID
  #Scenario: Check if user able to delete a record with valid Assignment ID
    #Given User creates DELETE Request for the LMS API endpoint with valid Assignment Id
    #When User sends the Delete Request
    #Then User receives Ok status with message
#
  #@DeleteAssignmentByID
  #Scenario: Check if user able to delete a record with valid LMS API and invalid Assignment Id
    #Given User creates DELETE Request for the LMS API endpoint with invalid Assignment Id
    #When User sends the Delete Request with invalid id
    #Then User receives Not Found Status with valid message and boolean success details
