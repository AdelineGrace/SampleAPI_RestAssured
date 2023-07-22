package apiEngine.routes;

public class AssignmentRoutes {
	
	public static String getAllAssignments()
	{
		return "/assignments";
	}
	
	public static String getAssignmentByAssignmentId(Integer assignmentId)
	{
		return "/assignments/" + assignmentId;
	}

}
