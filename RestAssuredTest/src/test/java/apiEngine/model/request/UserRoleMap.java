package apiEngine.model.request;

public class UserRoleMap {
	
	public String roleId;
	public String userRoleStatus;
	
	public UserRoleMap(String roleId, String userRoleStatus)
    {
        this.roleId = roleId;
        this.userRoleStatus = userRoleStatus;
    }

	@Override
	public String toString() {
		return "UserRoleMap [roleId=" + roleId + ", userRoleStatus=" + userRoleStatus + "]";
	}
	

}
