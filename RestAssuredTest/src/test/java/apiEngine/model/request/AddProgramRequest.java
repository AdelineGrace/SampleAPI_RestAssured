package apiEngine.model.request;

public class AddProgramRequest {
	
	public String programDescription;
	public String programName;
	public String programStatus;
	
    public AddProgramRequest(String programName, String programStatus, String programDescription)
    {
        this.programName = programName;
        this.programStatus = programStatus;
        this.programDescription = programDescription;
    }
    

}
