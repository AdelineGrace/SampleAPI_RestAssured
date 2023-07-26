@AssignmentSubmit
Feature: Assignment Submit module

  @CreateAssignmentSubmit
  Scenario Outline: Check if user able to create a submission with "<dataKey>" endpoint and request body
    Given User creates POST Assignment Submit Request with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP POST Assignment Submit Request for "<dataKey>"
    Then User receives response for POST Assignment Submit "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName        | dataKey                                   |
      | assignmentSubmit | Post_AssignmentSubmit_Valid               |
      | assignmentSubmit | Post_AssignmentSubmit_Repeated            |
      | assignmentSubmit | Post_AssignmentSubmit_MissingSubDesc      |
      | assignmentSubmit | Post_AssignmentSubmit_MissingUserId       |
      | assignmentSubmit | Post_AssignmentSubmit_MissingAssignmentId |
      | assignmentSubmit | Post_AssignmentSubmit_MissingSubDateTime  |

  @DeleteAssignmentSubmitBySubmissionId
  Scenario Outline: Check if user able to delete a submission with valid and invalid Submission ID
    Given User creates DELETE Request with "<dataKey>" scenario
    When User sends the HTTP Delete Assignment Submit Request "<dataKey>"
    Then User receives response for DELETE Assignment Submit "<sheetName>" with "<dataKey>"

    Examples: 
      | sheetName        | dataKey                           |
      | assignmentSubmit | Delete_AssignmentSubmit_ValidId   |
      | assignmentSubmit | Delete_AssignmentSubmit_DeletedId |
