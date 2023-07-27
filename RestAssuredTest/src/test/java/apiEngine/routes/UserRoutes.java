package apiEngine.routes;

import utilities.LoggerLoad;

public class UserRoutes {
	
	public static String createUser()
	{
		return "/users/users/roleStatus";
	}
	
	public static String getAllUsers()
	{
		return "/users/users";
	}
	
	public static String getUserById(String userId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint= "/users/users/00000" ;
		else
			endpoint= "/users/users/" + userId;
		return endpoint;
	}
	public static String getAllStaffUsers()
	{
		return "/users/users/getAllStaff";
	}
	public static String getAllUserWithRoles()
	{
		return "/users/users/roles";
	}
	
	public static String deleteUserById(String userId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = "/users/users/0000" ;
		else 
			endpoint = "/users/users/" + userId;
		return endpoint;
	}
	public static String deleteUserById(String userId)
	{
		return deleteUserById(userId,"Valid");
	}
	
	public static String updateUser(String userId, String dataKey) {
		String endpoint = null;
		if("Put_User_Invalid".equals(dataKey))
			endpoint = "/users/users/00000" ;
		else 
			endpoint = "/users/users/" + userId;;	
		return endpoint;
		
	}
	
	public static String updateUserRole(String userId, String dataKey) {
		String endpoint = null;
		if("Put_UserRole_Invalid".equals(dataKey))
			endpoint = "/users/users/roleStatus/000000" ;
		else 
			endpoint = "/users/users/roleStatus/" + userId;;	
			
		LoggerLoad.logDebug("endpoint"+endpoint);
		return endpoint;
		
	}
	
	public static String updateUserRoleBatch(String userId, String dataKey) {
		String endpoint = null;
		if("Put_UserBatch_Invalid".equals(dataKey))
			endpoint = "/users/users/roleProgramBatchStatus/000000" ;
		else 
			endpoint = "/users/users/roleProgramBatchStatus/" + userId;;	
			
		LoggerLoad.logDebug("endpoint"+endpoint);
		return endpoint;
		
	}
	
	

}
