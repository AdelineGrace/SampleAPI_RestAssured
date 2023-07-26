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
  
@CreateUser
  Scenario Outline: Check if user able to add a user with "<dataKey>" endpoint and request body
    Given User creates POST User Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP POST User Request
    Then User receives response for POST User "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName  | dataKey  |
      | user | Post_User_Valid |
      | user | Post_User_Existing |
      | user |Post_User_Missing_PhoneNumber|
      | user | Post_User_Missing_FirstName|
      | user | Post_User_Missing_LastName|
      | user | Post_User_Missing_RoleId|
      | user | Post_User_Missing_RoleStatus|
      | user | Post_User_Missing_TimeZone|
      | user | Post_User_Missing_VisaStatus|
      
 @UpdateUser
  Scenario Outline: Check if user able to update a user with "<dataKey>" endpoint and request body
    Given User creates PUT User Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP PUT User Request "<dataKey>"
    Then User receives response for PUT User "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName  | dataKey  |
      | user | Put_User_Valid |
      | user |Put_User_Missing_PhoneNumber|
      | user | Put_User_Missing_FirstName|
      | user | Put_User_Missing_LastName|
      | user | Put_User_Missing_TimeZone|
      | user | Put_User_Missing_VisaStatus|
      
  @UpdateUserRole
  Scenario Outline: Check if user able to update a user role with "<dataKey>" endpoint and request body
    Given User creates PUT User Role Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP PUT User Role Request "<dataKey>"
    Then User receives response for PUT User Role "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName  | dataKey  |
      | user | Put_UserRole_Valid |
      | user | Put_UserRole_Invalid |
      | user |Put_UserRole_Missing_RoleId|
      | user | Put_UserRole_Missing_RoleStatus|
      
  @AssignUserBatch
  Scenario Outline: Check if user able to update a user with batch "<dataKey>" endpoint and request body
    Given User creates PUT User batch Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP PUT User batch Request "<dataKey>"
    Then User receives response for PUT User batch "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName  | dataKey  |
      | user | Put_UserBatch_Valid |
      | user | Put_UserBatch_Invalid |
      | user | Put_UserBatch_Missing|
      
