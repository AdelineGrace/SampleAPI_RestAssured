package apiEngine.routes;

import dataProviders.ConfigReader;
import utilities.LoggerLoad;

public class UserRoutes {
	
	public static String createUser()
	{
		return ConfigReader.getUserPostUrl();
	}
	
	public static String getAllUsers()
	{
		return ConfigReader.getUserGetAllUrl();
	}
	
	public static String getUserById(String userId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint= ConfigReader.getUserGetByUserIdUrl() + "00000" ;
		else
			endpoint= ConfigReader.getUserGetByUserIdUrl() + userId;
		return endpoint;
	}
	
	public static String getAllStaffUsers()
	{
		return ConfigReader.getUserGetAllStaffUrl();
	}
	
	public static String getAllUserWithRoles()
	{
		return ConfigReader.getUserGetAllUsersWithRolesUrl();
	}
	
	public static String deleteUserById(String userId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = ConfigReader.getUserDeleteByIdUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getUserDeleteByIdUrl() + userId;
		return endpoint;
	}

	public static String deleteUserById(String userId)
	{
		return ConfigReader.getUserDeleteByIdUrl() + userId;
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
