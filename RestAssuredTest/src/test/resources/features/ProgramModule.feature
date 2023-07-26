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
    Given User creates POST Request with fields "<KeyOption>" and "<Sheetname>" from excel
    When User sends request Body with mandatory , additional  fields.
    Then User receives Status with response body "<KeyOption>" and "<Sheetname>" from excel.

    Examples: 
      | KeyOption   |   Sheetname     |
      | postNew     |   ProgramModule |
      | postNew1    |   ProgramModule |
      | postExist   |   ProgramModule |
      | postMissing |   ProgramModule |

  #@GetAllTag
  #Scenario: Check if user able to retrieve all programs with valid LMS API
  #Given User creates GET Request for the LMS API endpoint for Program Module
  #When User sends HTTPS Request in Program Module
  #Then User receives 200 OK Status with response body.
  @Get_pgmId
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates GET Request for the LMS API endpoint for Program Module
    When User sends HTTPS Request with valid or invalid  Program Id from "<KeyOption>" and "<Sheetname>" Program Module. 
    Then User receives valid or invalid  "<KeyOption>" with response body for ProgramModule.

    Examples: 
      | KeyOption  | Sheetname     |
      | Get_Id_valid   | ProgramModule |
      | Get_Id_invalid | ProgramModule |

  @PutByID_Valid
  Scenario Outline: Check if user able to update a program with "<KeyOption>" valid programID
    Given User creates PUT Request with programID for Program Module.
    When User sends PUT request with valid programID from "<KeyOption>" and "<Sheetname>" for Program Module
    Then User receives Status with response body for the "<KeyOption>" for Program Module.

    Examples: 
      | KeyOption  | Sheetname     |
      | putIdValid | ProgramModule |

  @PutByID_invalid
  Scenario Outline: Check if user able to update a program with invalid programID
    Given User creates PUT Request with programID for Program Module.
    When User sends PUT request with invalid programID from "<KeyOption>" and "<Sheetname>" for Program Module
    Then User receives Status with response body for the "<KeyOption>" for Program Module.

    Examples: 
      | KeyOption    | Sheetname     |
      | putIdInvalid | ProgramModule |

  @PutByID_missing
  Scenario Outline: Check if user able to update a program with missing field Program Id
    Given User creates PUT Request with programID for Program Module.
    When User sends PUT request  with missing programID from "<KeyOption>" and "<Sheetname>" for Program Module
    Then User receives Status with response body for the "<KeyOption>" for Program Module.

    Examples: 
      | KeyOption    | Sheetname     |
      | putIdMissing | ProgramModule |

  @PutByName_Valid
  Scenario Outline: Check if user able to update a program with valid programName
    Given User creates PUT Request with programName for Program Module.
    When User sends PUT request Body with valid programName from "<KeyOption>" and "<Sheetname>"  for Program Module
    Then User receives Status for the "<KeyOption>" for Program Module with programName.

    Examples: 
      | KeyOption    | Sheetname     |
      | putNameValid | ProgramModule |

  @PutByName_invalid
  Scenario Outline: Check if user able to update a program with invalid programName
    Given User creates PUT Request with programName for Program Module.
    When User sends PUT request Body with invalid programName from "<KeyOption>" and "<Sheetname>"  for Program Module
    Then User receives Status for the "<KeyOption>" for Program Module with programName.

    Examples: 
      | KeyOption      | Sheetname     |
      | putNameInvalid | ProgramModule |

  @PutByName_missing
  Scenario Outline: Check if user able to update a program with missing field programName
    Given User creates PUT Request with programName for Program Module.
    When User sends PUT request Body with missing programName from "<KeyOption>" and "<Sheetname>"  for Program Module
    Then User receives Status for the "<KeyOption>" for Program Module with programName.

    Examples: 
      | KeyOption      | Sheetname     |
      | putNameMissing | ProgramModule |

  @Delete_pgmName
  Scenario Outline: Check if user able to retrieve a program with valid & invalid program ID and LMS API
    Given User creates DELETE Request for the LMS API endpoint for Program Module
    When User sends Delete Request with valid or invalid ProgramName from "<KeyOption>" and "<Sheetname>" for Program Module
    Then User receives status as "<KeyOption>"  with response body for ProgramModule.

    Examples: 
      | KeyOption  | Sheetname     |
      | Delete_pgmNamevalid   | ProgramModule |
      | Delete_pgmNameinvalid | ProgramModule |       

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
