package apiEngine.model.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateUserRequest {

	public String userComments;
	public String userEduPg;
	public String userEduUg;
	public String userFirstName;
	public String userLastName;
	public String userLinkedinUrl;
	public String userLocation;
	public String userMiddleName;
	public Integer userPhoneNumber;
	
	public String userTimeZone;
	public String userVisaStatus;

	public UpdateUserRequest(String userFirstName, String userLastName, String userMiddleName, String userComments,
			String userEduPg, String userEduUg, String userLinkedinUrl, String userLocation, Integer userPhoneNumber,
			 String userTimeZone, String userVisaStatus) throws Exception {
		this.userComments = userComments;
		this.userEduPg = userEduPg;
		this.userEduUg = userEduUg;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userLinkedinUrl = userLinkedinUrl;
		this.userLocation = userLocation;
		this.userMiddleName = userMiddleName;
		this.userPhoneNumber = userPhoneNumber;
		
		this.userTimeZone = userTimeZone;
		this.userVisaStatus = userVisaStatus;
		
		
	    
	    List<String> visaStatus = Arrays.asList("Not-Specified",  "NA", "GC-EAD", "H4-EAD", "H4","H1B", "Canada-EAD", "Indian-Citizen", "US-Citizen", "Canada-Citizen", "CST");
	    boolean validVisaStatus = visaStatus.stream().anyMatch(userVisaStatus::contains);
	    
	    List<String> timeZone = Arrays.asList("PST", "MST", "CST", "EST", "IST");
	    boolean validTimeZone = visaStatus.stream().anyMatch(userTimeZone::contains);
	    
	   /* if(!(validStatus && validRole && validVisaStatus && validTimeZone)) {
	    	throw new Exception("Exception message");
	    }*/
	    
	}

	@Override
	public String toString() {
		return "AddUserRequest [userComments=" + userComments + ", userEduPg=" + userEduPg + ", userEduUg=" + userEduUg
				+ ", userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", userLinkedinUrl="
				+ userLinkedinUrl + ", userLocation=" + userLocation + ", userMiddleName=" + userMiddleName
				+ ", userPhoneNumber=" + userPhoneNumber + ",  userTimeZone="
				+ userTimeZone + ", userVisaStatus=" + userVisaStatus + "]";
	}
}
