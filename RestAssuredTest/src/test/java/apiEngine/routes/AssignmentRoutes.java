package apiEngine.routes;

import dataProviders.ConfigReader;

public class AssignmentRoutes {
	
	public static String createAssignment()
	{
		return ConfigReader.getAssignmentPostUrl();
	}
	
	public static String getAllAssignments()
	{
		return ConfigReader.getAssignmentGetAllUrl();
	}
	
	public static String getAssignmentByAssignmentId(Integer assignmentId)
	{
		return ConfigReader.getAssignmentGetByAssignmentIdUrl() + assignmentId;
	}
	
	public static String getAssignmentByBatchId(Integer batchId)
	{
		return ConfigReader.getAssignmentGetByBatchIdUrl() + batchId;
	}
	
	public static String deleteAssignmentById(Integer assignmentId)
	{
		return ConfigReader.getAssignmentDeleteByIdUrl() + assignmentId;
	}
	
	public static String updateAssignmentById(Integer assignmentId)
	{
		return ConfigReader.getAssignmentPutAssignmentUrl() + assignmentId;
	}

}
