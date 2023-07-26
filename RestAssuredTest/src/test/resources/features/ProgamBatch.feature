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
Feature: Program Batch

  @AddBatch
  Scenario Outline: Check if user able to add a record with "<dataKey>" endpoint and request body (non existing values)
    Given User creates POST Batch Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP POST Batch Request
    Then User receives response for POST Batch "<sheetName>" with "<dataKey>"

    Examples: 
      | dataKey                        | sheetName |
      | Post_Batch_Valid               | batch     |
      | Post_Batch_Existing            | batch     |
      | Post_Batch_Missing_BatchStatus | batch     |
      | Post_Batch_Missing_BatchName   | batch     |
      | Post_Batch_Missing_NoOfClasses | batch     |
      | Post_Batch_Missing_ProgramId   | batch     |

  @Get_All_001
  Scenario Outline: Check if user able to retrieve all batches with "<dataKey>" endpoint
    Given User creates GET Batch Request for the LMS API
    When User sends HTTPS GET all batches Request with "<dataKey>"
    Then User receives Status Code  with response body for endpoint "<dataKey>"

    Examples: 
      | dataKey |
      | Valid   |
      | Invalid |

  @Get_ByBatchId_001
  Scenario Outline: Check if user able to retrieve a batch with "<dataKey>" batchId
    Given User creates GET Batch Request for the LMS API
    When User sends HTTPS GET batch Request with batchId with "<dataKey>"
    Then User receives Status Code  with response body for a batchId with "<dataKey>"

    Examples: 
      | dataKey |
      | Valid   |
      | Invalid |

  @Get_ByBatchName_001
  Scenario Outline: Check if user able to retrieve a batch with "<dataKey>" batchName
    Given User creates GET Batch Request for the LMS API
    When User sends HTTPS GET batch Request with batchName with "<dataKey>"
    Then User receives Status Code  with response body for a batchName with "<dataKey>"

    Examples: 
      | dataKey |
      | Valid   |
      | Invalid |

  @Get_ByProgramId_001
  Scenario Outline: Check if user able to retrieve a batch with "<dataKey>" ProgramName
    Given User creates GET Batch Request for the LMS API
    When User sends HTTPS GET batch Request with progamId with "<dataKey>"
    Then User receives Status Code  with response body for a programId with "<dataKey>"

    Examples: 
      | dataKey |
      | Valid   |
      | Invalid |

  @UpdateBatch
  Scenario Outline: Check if user able to update a Batch with "<dataKey>" batchID and mandatory request body
    Given User creates PUT Batch Request for the LMS API with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP PUT Request "<dataKey>"
    Then User receives response for PUT Batch "<sheetName>" with "<dataKey>"

    Examples: 
      | dataKey                       | sheetName |
      | Put_Batch_Valid               | batch     |
      | Put_Batch_Invalid             | batch     |
      | Put_Batch_Missing_BatchStatus | batch     |
      | Put_Batch_Missing_BatchName   | batch     |
      | Put_Batch_Missing_NoOfClasses | batch     |
      | Put_Batch_Missing_ProgramId   | batch     |

  @DeleteBatch
  Scenario Outline: Check if user able to delete a Batch with "<dataKey>" batchID
    Given User creates DELETE Request for the LMS API endpoint with "<dataKey>" batch Id
    When User sends DELETE Request for "<dataKey>" batch Id
    Then User receives Status with response body for "<dataKey>" batch Id

    Examples: 
      | dataKey |
      | Valid   |
      | Invalid |
