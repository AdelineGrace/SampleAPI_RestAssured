package apiEngine.routes;

import dataProviders.ConfigReader;

public class SubmissionRoutes {
	
	public static String createAssignmentSubmission()
	{
		return ConfigReader.getSubmitPostUrl();
	}
	
	public static String getAllAssignments()
	{
		return ConfigReader.getSubmitGetAllUrl();
	}
	
	public static String getsubmissionByAssignmentId(Integer assignmentId)
	{
		return ConfigReader.getSubmitGetByAssignmentIdUrl() + assignmentId;
	}
	
	public static String getGradeByStudentId(String studentId)
	{
		System.out.println(studentId);
		return ConfigReader.getSubmitGetGradeByStudentIdUrl() + studentId;
	}
		
	public static String getGradeBybatchId(Integer batchId)
	{
		return ConfigReader.getSubmitGetGradeByBatchIdUrl() + batchId;
	}

	public static String getsubmissionByUserId(String userId)
	{
		return ConfigReader.getSubmitGetSubmissionByUserIdUrl() + userId;
	}
	
	public static String getsubmissionBybatchId(Integer subbatchid)
	{
		return ConfigReader.getSubmitGetSubmissionByBatchIdUrl() + subbatchid;
	}
	
	public static String deleteSubmissionBySubmissionId(Integer submissionId)
	{
		return ConfigReader.getSubmitDeleteByIdUrl() + submissionId;
	}
	
}
