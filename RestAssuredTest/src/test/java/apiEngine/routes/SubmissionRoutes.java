package apiEngine.routes;

public class SubmissionRoutes {
	
	public static String createAssignmentSubmission()
	{
		return "/assignmentsubmission";
	}
	
	public static String getAllAssignments()
	{
		return "/assignmentsubmission";
	}
	
	public static String getsubmissionByAssignmentId(Integer assignmentId)
	{
		return "/assignmentsubmission/getGrades/" + assignmentId;
	}
	
	public static String getGradeByStudentId(String studentId)
	{
		System.out.println(studentId);
		return "/assignmentsubmission/getGradesByStudentId/" + studentId;
	}
		
	public static String getGradeBybatchId(Integer batchId)
	{
		return "/assignmentsubmission/grades/" + batchId;
	}

	public static String getsubmissionByUserId(String userId)
	{
		return "/assignmentsubmission/student/" + userId;
	}
	
	public static String getsubmissionBybatchId(Integer subbatchid)
	{
		return "/assignmentsubmission/studentbatch" + subbatchid;
	}
	
	public static String deleteSubmissionBySubmissionId(Integer submissionId)
	{
		return "/assignmentsubmission/" + submissionId;
	}
	
}
