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
Feature: User 
  



 @GetAllUsers
  Scenario:Check if user able to retrieve all user with valid LMS API
    Given User creates GET Request for the LMS API All User endpoint
    When User sends HTTPS Request 
    Then User receives OK Status with response body containing all users  
    
 @GetUserwithValidUserId
  Scenario Outline: Check if user able to retrieve a user with valid User ID
    Given User creates GET Request for the LMS API endpoint with "<userId>" valid User ID
    When User sends HTTPS Request 
    Then User receives OK Status with response body containing all users  
     Examples:
      | userId |
      | U7988  |
      
 @GetUserwithinValidUserId
  Scenario Outline: Check if user able to retrieve a user with invalid User ID
    Given User creates GET Request for the LMS API endpoint with "<userId>" invalid User ID
    When User sends HTTPS Request 
    Then User receives "404" Not Found Status with message and boolean success details
     Examples:
      | userId |
      | U79  |
      
  @GetAllStaff
  Scenario:Check if user able to retrieve a user with valid LMS API
    Given User creates GET Request for the LMS API All Staff endpoint
    When User sends HTTPS Request 
    Then User receives 200 OK Status with response body
    
  @GetAllUsersWithRoles
  Scenario:Check if user able to retrieve a user with valid LMS API
    Given User creates GET Request for the LMS API User Roles endpoint
    When User sends HTTPS Request 
    Then User receives 200 OK Status with response body