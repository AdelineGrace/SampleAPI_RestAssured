package apiEngine.routes;

public class SubmissionRoutes {
	
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
		return "/assignmentsubmission/getGrades/" + studentId;
	}
		
	public static String getGradeBybatchId(Integer batchId)
	{
		return "/assignmentsubmission/getGrades/" + batchId;
	}

	public static String getsubmissionByUserId(String userId)
	{
		return "/assignmentsubmission/student/" + userId;
	}
	
	public static String getsubmissionBybatchId(Integer subbatchid)
	{
		return "/assignmentsubmission/studentbatch" + subbatchid;
	}
	
}
