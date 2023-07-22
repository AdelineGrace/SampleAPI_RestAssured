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
@tag
Feature: Title of your feature
  I want to use this template for my feature file

	@AddAssignment
  Scenario: Check if user able to add a record with valid endpoint and request body (non existing values)
    Given User creates POST Request for the LMS API endpoint
    When User sends POST Request with mandatory and additional fields
    Then User receives Created Status with response body.     

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