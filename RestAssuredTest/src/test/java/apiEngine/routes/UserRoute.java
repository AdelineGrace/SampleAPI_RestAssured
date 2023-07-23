package apiEngine.routes;

public class UserRoute {
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

}
