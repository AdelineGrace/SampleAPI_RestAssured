package apiEngine.routes;

public class UserRoutes {
	
	public static String getAllUsers()
	{
		return "/users/users";
	}
	
	public static String getUserWithUserID(String userId)
	{
		return "/users/users/" + userId;
	}
	public static String getAllStaffUsers()
	{
		return "/users/users/getAllStaff";
	}
	public static String getAllUserWithRoles()
	{
		return "/users/users/roles";
	}
	
	public static String deleteUser(String userId)
	{
		return "/users/users/" + userId;
	}

}
