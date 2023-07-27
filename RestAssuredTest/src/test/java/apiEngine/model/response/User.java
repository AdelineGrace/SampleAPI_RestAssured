package apiEngine.model.response;

public class User {

	@Override
	public String toString() {
		return "User [userComments=" + userComments + ", userEduPg=" + userEduPg + ", userEduUg=" + userEduUg
				+ ", userFirstName=" + userFirstName + ", userId=" + userId + ", userLastName=" + userLastName
				+ ", userLinkedinUrl=" + userLinkedinUrl + ", userLocation=" + userLocation + ", userMiddleName="
				+ userMiddleName + ", userPhoneNumber=" + userPhoneNumber + ", userTimeZone=" + userTimeZone
				+ ", userVisaStatus=" + userVisaStatus + "]";
	}
	public String userComments;
	public String userEduPg;
	public String userEduUg;
	public String userFirstName;
	public String userId;
	public String userLastName;
	public String userLinkedinUrl;
	public String userLocation;
	public String userMiddleName;
	public Integer userPhoneNumber;
	public String userTimeZone;
	public String userVisaStatus;
}
