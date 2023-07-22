package apiEngine.model.request;

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
	
		public void UserComments(String userComments) {
		this.userComments = userComments;
		}

		public void UserEduPg(String userEduPg) {
		this.userEduPg = userEduPg;
		}

		public void UserEduUg(String userEduUg) {
		this.userEduUg = userEduUg;
		}

		public void UserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
		}

		public void UserLastName(String userLastName) {
		this.userLastName = userLastName;
		}

		public void UserLinkedinUrl(String userLinkedinUrl) {
		this.userLinkedinUrl = userLinkedinUrl;
		}

		public void UserLocation(String userLocation) {
		this.userLocation = userLocation;
		}

		public void UserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
		}

		public void UserPhoneNumber(Integer userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
		}

		public void UserRoleMaps(List<UserRoleMap> userRoleMaps) {
		this.userRoleMaps = userRoleMaps;
		}

		public void UserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
		}

		public void UserVisaStatus(String userVisaStatus) {
		this.userVisaStatus = userVisaStatus;
		}
}
