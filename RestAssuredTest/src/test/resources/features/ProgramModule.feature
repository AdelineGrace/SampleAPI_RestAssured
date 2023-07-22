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
@ProgramModule
Feature: PROGRAM MODULE

  @GetAllTag_01
  Scenario: Check if user able to retrieve all programs with valid LMS API
    Given User creates GET Request for the LMS API endpoint for Program Module
    When User sends HTTPS Request in Program Module
    Then User receives 200 OK Status with response body.

  @Get_pgmId_02
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates GET Request for the LMS API endpoint for Program Module
    When User sends HTTPS Request with valid or invalid for  <programId>
    Then User receives valid or invalid as "<option>" and <status> with response body.

    Examples: 
     |option | programId |status|
      |valid	| 10669 |200|
      |invalid| 10		|	404|


  @PostTag_01
  Scenario Outline: Check if user able to create a program for existing, non existing and missing mandatory field.
    Given User creates POST Request with fields "<Sheetname>" and <RowNumber> from excel
    When User sends request Body with mandatory , additional  fields.
    Then User receives Status with response body

    Examples: 
      | Sheetname | RowNumber |
      | ProgramPost|	0	|
      | ProgramPost|	1	|
      | ProgramPost|	2	|
    
  @PutByID_01
  Scenario Outline: Check if user able to update a program with valid/invalid programID and missing field
    Given User creates PUT Request with fields "<Sheetname>" and <RowNumber> from excel
    When User sends request Body with valid/invalid programID and missing field
    Then User receives Status with response body

    Examples: 
      | Sheetname | RowNumber |
      | ProgramPost|	3	|
      | ProgramPost|	4	|
      | ProgramPost|	5	|
    
    
 
