package apiEngine.model.request;

public class AddProgramRequest {
	
	public Integer programID;
	public String programDescription;
	public String programName;
	public String programStatus;
	
    public AddProgramRequest(String programName, String programStatus, String programDescription)
    {
        this.programName = programName;
        this.programStatus = programStatus;
        this.programDescription = programDescription;
    }
    public AddProgramRequest(Integer programID,String programName, String programStatus, String programDescription)
    {
    	this.programID = programID;
        this.programName = programName;
        this.programStatus = programStatus;
        this.programDescription = programDescription;
    }

}
