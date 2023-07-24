@Assignment
Feature: Assignment module

  @CreateAssignment
  Scenario: Check if user able to add a record with valid endpoint and request body
    Given User creates POST Request for the LMS API endpoint
    When User sends POST Request with mandatory and additional fields
    Then User receives Created Status with response body

  @GetAllAssignments
  Scenario: Check if user able to retrieve a record with valid LMS API
    Given User creates GET Request for the LMS API endpoint
    When User sends GetAllAssignments Request
    Then User receives OK Status with response body containing all assignments

  @GetAssignmentByID
  Scenario: Check if user able to retrieve a record with valid Assignment ID
    Given User creates GET Request for the LMS API endpoint with valid Assignment ID
    When User sends GetAssignmentByAssignmentid Request with valid id
    Then User receives OK Status with response body containing valid assignment

  @GetAssignmentByID
  Scenario: Check if user able to retrieve a record with invalid Assignment ID
    Given User creates GET Request for the LMS API endpoint with invalid Assignment ID
    When User sends GetAssignmentByAssignmentid Request with invalid id
    Then User receives Not Found Status with message and boolean success details

  @DeleteAssignmentByID
  Scenario: Check if user able to delete a record with valid Assignment ID
    Given User creates DELETE Request for the LMS API endpoint with valid Assignment Id
    When User sends the Delete Request
    Then User receives Ok status with message

  @DeleteAssignmentByID
  Scenario: Check if user able to delete a record with valid LMS API and invalid Assignment Id
    Given User creates DELETE Request for the LMS API endpoint with invalid Assignment Id
    When User sends the Delete Request with invalid id
    Then User receives Not Found Status with valid message and boolean success details
