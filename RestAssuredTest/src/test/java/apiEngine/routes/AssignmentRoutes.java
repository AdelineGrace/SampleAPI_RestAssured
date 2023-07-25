package apiEngine.routes;

public class AssignmentRoutes {
	
	public static String createAssignment()
	{
		return "/assignments";
	}
	
	public static String getAllAssignments()
	{
		return "/assignments";
	}
	
	public static String getAssignmentByAssignmentId(Integer assignmentId)
	{
		return "/assignments/" + assignmentId;
	}
	
	public static String getAssignmentByBatchId(Integer batchId)
	{
		return "/assignments/batch/" + batchId;
	}
	
	public static String deleteAssignmentById(Integer assignmentId)
	{
		return "/assignments/" + assignmentId;
	}

}
