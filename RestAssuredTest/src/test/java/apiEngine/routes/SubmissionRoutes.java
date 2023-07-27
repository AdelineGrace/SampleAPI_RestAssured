package apiEngine.routes;

import dataProviders.ConfigReader;

import java.net.URI;

public class SubmissionRoutes {

	public static String getAllAssignments()
	{
		return ConfigReader.getSubmitGetAllUrl();
	}
	
	public static String getsubmissionByAssignmentId(int assignmentid)
	{
		return "/assignmentsubmission/getGrades/" + assignmentid;
	}
	
	public static String getGradeByStudentId(String studentid)
	{
		System.out.println(studentid);
		return "/assignmentsubmission/getGradesByStudentId/" + studentid;
	}
		
	public static String getGradeBybatchId(int batchId)
	{
		return ConfigReader.getSubmitGetGradeByBatchIdUrl() + batchId;
	}

	public static String getsubmissionByUserId(String userId)
	{
		return ConfigReader.getSubmitGetSubmissionByUserIdUrl() + userId;
	}
	
	public static String getsubmissionBybatchId(int batchId)
	{
		return "/assignmentsubmission/studentbatch/" + batchId;
	}

	public static String postsubmission() {
		return "/assignmentsubmission";

	}

	public static String putResubmit(Integer submissionId) {
		return "/assignmentsubmission/" +submissionId;

	}

	public static String putGradesubmission(Integer submissionId) {
		return "/assignmentsubmission/gradesubmission/" +submissionId ;

	}

	public static String deletesubmissionID(String submissionId) {
		return "/assignmentsubmission/" +submissionId;

	}

	public static String createAssignmentSubmission() {

		return "/assignmentsubmission";
	}

	public static String deleteSubmissionBySubmissionId(Integer submissionId)
	{
		return ConfigReader.getSubmitDeleteByIdUrl() + submissionId;
	}
	
}
