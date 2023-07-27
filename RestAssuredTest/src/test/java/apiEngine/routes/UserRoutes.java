package apiEngine.routes;

import dataProviders.ConfigReader;

public class UserRoutes {
	
	public static String createUser()
	{
		return ConfigReader.getUserPostUrl();
	}
	
	public static String getAllUsers()
	{
		return ConfigReader.getUserGetAllUrl();
	}
	
	public static String getUserWithUserID(String userId)
	{
		return ConfigReader.getUserGetByUserIdUrl() + userId;
	}
	
	public static String getAllStaffUsers()
	{
		return ConfigReader.getUserGetAllStaffUrl();
	}
	
	public static String getAllUserWithRoles()
	{
		return ConfigReader.getUserGetAllUsersWithRolesUrl();
	}
	
	public static String deleteUserById(String userId)
	{
		return ConfigReader.getUserDeleteByIdUrl() + userId;
	}

}
