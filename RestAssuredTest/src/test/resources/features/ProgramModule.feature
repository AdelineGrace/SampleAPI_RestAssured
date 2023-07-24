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
    Given User creates POST Request with fields "<Sheetname>" and "<option>" from excel
    When User sends request Body with mandatory , additional  fields.
    Then User receives Status with response body "<Sheetname>" and "<option>" from excel.

    Examples: 
      | Sheetname   | option|
      | ProgramPost | postNew|
      | ProgramPost | postNew1|
      | ProgramPost | postExist|
      | ProgramPost | postMissing|

  #  @GetAllTag
  #  Scenario: Check if user able to retrieve all programs with valid LMS API
  #   Given User creates GET Request for the LMS API endpoint for Program Module
  #   When User sends HTTPS Request in Program Module
  #   Then User receives 200 OK Status with response body.
  @Get_pgmId
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates GET Request for the LMS API endpoint for Program Module
    When User sends HTTPS Request with valid or invalid for  <programId>
    Then User receives valid or invalid  "<option>" with response body for ProgramModule.

    Examples: 
      | option  | programId |
      | valid   |     10669 |
      | invalid |        10 |

  @PutByID_Valid
  Scenario Outline: Check if user able to update a program with valid programID
    Given User creates PUT Request with programID for Program Module.
    When User sends PUT request Body with valid programID for Program Module
    Then User receives Status with response body for the "<KeyOption>" for Program Module.

    Examples: 
       | KeyOption    |
       | putIdValid   |
       
      
 @PutByID_invalid
  Scenario Outline: Check if user able to update a program with invalid programID
    Given User creates PUT Request with programID for Program Module.
    When User sends PUT request Body with invalid programID for Program Module
    Then User receives Status with response body for the "<KeyOption>" for Program Module.

    Examples: 
       | KeyOption    |
       | putIdInvalid |
      
      
   @PutByID_missing
  Scenario Outline: Check if user able to update a program with missing field
    Given User creates PUT Request with programID for Program Module.
    When User sends PUT request Body with missing programID for Program Module
    Then User receives Status with response body for the "<KeyOption>" for Program Module.

    Examples: 
       | KeyOption    |
      | putIdMissing |
      
   @PutByName_Valid
  Scenario Outline: Check if user able to update a program with valid programName
    Given User creates PUT Request with programName for Program Module.
    When User sends PUT request Body with valid programName for Program Module
    Then User receives Status for the "<KeyName>" for Program Module with programName.

    Examples: 
       | KeyName    |
       | putNameValid   |
       
      
 @PutByName_invalid
  Scenario Outline: Check if user able to update a program with invalid programName
    Given User creates PUT Request with programName for Program Module.
    When User sends PUT request Body with invalid programName for Program Module
    Then User receives Status for the "<KeyName>" for Program Module with programName.

    Examples: 
       | KeyName    |
       | putNameInvalid |
      
      
   @PutByName_missing
  Scenario Outline: Check if user able to update a program with missing field programName
    Given User creates PUT Request with programName for Program Module.
    When User sends PUT request Body with missing programName for Program Module
    Then User receives Status for the "<KeyName>" for Program Module with programName.

    Examples: 
       | KeyName    |
      | putNameMissing |
      


  @Delete_pgmName
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates DELETE Request for the LMS API endpoint for Program Module
    When User sends Delete Request with valid or invalid ProgramName "<programName>".
    Then User receives status as "<option>"  with response body for ProgramModule.

    Examples: 
      | option  | programName             |
      | valid   | JUL-23-RESTAPI-Turtle01_New |
      | invalid | SQL-20-Teams            |

  @Delete_pgmID_valid
  Scenario: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates DELETE Request for the LMS API endpoint for Program Module with PGMID
    When User sends DELETE Request with valid ProgramID
    Then User receives status for valid  Programid for ProgramModule

      
    @Delete_pgmID_invalid
  Scenario: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates DELETE Request for the LMS API endpoint for Program Module with PGMID
    When User sends DELETE Request with invalid ProgramID
    Then User receives status for invalid  Programid for ProgramModule

  