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


  @PostTag
  Scenario Outline: Check if user able to create a program for existing, non existing and missing mandatory field.
    Given User creates POST Request with fields "<RowNumber>" and "<Sheetname>" from excel
    When User sends request Body with mandatory , additional  fields.
    Then User receives Status with response body "<RowNumber>" and "<Sheetname>" from excel

    Examples: 
      | Sheetname | RowNumber |
      | ProgramPost|	postNew	|
      | ProgramPost|	postNew1	|
      | ProgramPost|	postExist	|
      | ProgramPost|	postMissing|
      
#  @GetAllTag
#  Scenario: Check if user able to retrieve all programs with valid LMS API
 #   Given User creates GET Request for the LMS API endpoint for Program Module
 #   When User sends HTTPS Request in Program Module
 #   Then User receives 200 OK Status with response body.

  @Get_pgmId
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates GET Request for the LMS API endpoint for Program Module
    When User sends HTTPS Request with valid or invalid for  <programId>
    Then User receives valid or invalid as "<option>" and <status> with response body.

    Examples: 
     |option | programId |status|
      |valid	| 10669 |200|
      |invalid| 10		|	404|

  @PutByID
  Scenario Outline: Check if user able to update a program with valid/invalid programID and missing field
    Given User creates PUT Request with fields "<Sheetname>" and <RowNumber> from excel
    When User sends PUT request Body with valid/invalid programID and missing field
    Then User receives Status with response body

    Examples: 
      | Sheetname | RowNumber |
      | Program|	putIdValid	|
      | Program|	putIdInvalid	|
      | Program|	putIdMissing	|
    
    
  @PutByName
  Scenario Outline: Check if user able to update a program with valid/invalid programID and missing field
    Given User creates PUT Request with fields "<Sheetname>" and <RowNumber> from excel
    When User sends PUT request Body with valid/invalid ProgramName and missing field
    Then User receives Status with response body

    Examples: 
      | Sheetname | RowNumber |
      | Program|	putNameValid	|
      | Program|	putNameInvalid	|
      | Program|	putNameMissing	|
    

  @Delete_pgmName
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates DELETE Request for the LMS API endpoint for Program Module
    When User sends Delete Request with valid or invalid ProgramName "<programName>".
    Then User receives status as "<option>"  with response body.

    Examples: 
     |option | programName |
      |valid	| JUL-23-RESTAPI-Turtle01 |
      |invalid| JUL		|
    
     @Delete_pgmID
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates DELETE Request for the LMS API endpoint for Program Module
    When User sends DELETE Request with valid or invalid for <programId>
    Then User receives status as "<option>" with valid or invalid Programid.

    Examples: 
     |option | programId |status|
      |valid	| 10669 |200|
      |invalid| 10		|	404|
   