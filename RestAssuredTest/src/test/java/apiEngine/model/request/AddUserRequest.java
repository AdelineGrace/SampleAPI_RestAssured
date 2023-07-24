package apiEngine.model.request;

import java.util.ArrayList;
import java.util.List;

public class AddUserRequest {

	public String userComments;
	public String userEduPg;
	public String userEduUg;
	public String userFirstName;
	public String userLastName;
	public String userLinkedinUrl;
	public String userLocation;
	public String userMiddleName;
	public Integer userPhoneNumber;
	public List<UserRoleMap> userRoleMaps;
	public String userTimeZone;
	public String userVisaStatus;
	
	public AddUserRequest(String userFirstName, String userLastName, String userMiddleName, 
			String userComments, String userEduPg, String userEduUg, String userLinkedinUrl, String userLocation,
			Integer userPhoneNumber, String roleId, String userRoleStatus, String userTimeZone, String userVisaStatus)
	{
		this.userComments = userComments;
		this.userEduPg = userEduPg;
		this.userEduUg = userEduUg;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userLinkedinUrl = userLinkedinUrl;
		this.userLocation = userLocation;
		this.userMiddleName = userMiddleName;
		this.userPhoneNumber = userPhoneNumber;
		UserRoleMap userRoleMap = new UserRoleMap(roleId, userRoleStatus);
		this.userRoleMaps = new ArrayList<UserRoleMap>();
		this.userRoleMaps.add(userRoleMap);
		this.userTimeZone = userTimeZone;
		this.userVisaStatus = userVisaStatus;
	}
}
