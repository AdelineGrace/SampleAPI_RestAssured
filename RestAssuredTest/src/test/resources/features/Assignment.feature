@Assignment
Feature: Assignment module

  @CreateAssignment
  Scenario Outline: Check if user able to add a record with "<dataKey>" endpoint and request body
    Given User creates POST Assignment Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP POST Assignment Request
    Then User receives response for POST "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName  | dataKey                  |
      | assignment | Post_Assignment_Valid    |
      | assignment | Post_Assignment_Existing |

  @GetAllAssignments
  Scenario: Check if user able to retrieve a record with valid LMS API
    Given User creates GET Request for the LMS API endpoint
    When User sends GetAllAssignments Request
    Then User receives OK Status with response body containing all assignments

  @GetAssignmentByAssignmentID @GetAssignmentByBatchID
  Scenario Outline: Check if user able to retrieve a record with valid Assignment or Batch ID
    Given User creates GET Request for the LMS API endpoint with "<scenario>" scenario
    When User sends "<scenario>" Request with valid id
    Then User receives OK Status with response body containing valid assignment "<scenario>"

    Examples: 
      | scenario                         |
      | Get_Assignment_ValidAssignmentId |
      | Get_Assignment_ValidBatchId      |

  @DeleteAssignmentByAssignmentID
  Scenario Outline: Check if user able to delete a record with valid and invalid Assignment ID
    Given User creates DELETE Request for the LMS API endpoint with "<dataKey>" scenario
    When User sends the HTTP Delete Request
    Then User receives response for DELETE "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName  | dataKey                     |
      | assignment | Delete_Assignment_ValidId   |
      | assignment | Delete_Assignment_DeletedId |

  @GetAssignmentByDeletedAssignmentID @GetAssignmentByDeletedBatchID
  Scenario Outline: Check if user able to retrieve a record with invalid Assignment or batch ID
    Given User creates GET Request for the LMS API endpoint with "<scenario>"
    When User sends "<scenario>" Request with invalid id
    Then User receives Not Found Status with message and boolean success details "<scenario>"

    Examples: 
      | scenario                           |
      | Get_Assignment_DeletedAssignmentId |
      | Get_Assignment_DeletedBatchId      |
