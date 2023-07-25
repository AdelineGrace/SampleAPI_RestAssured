package apiEngine.model.request;

import utilities.LoggerLoad;

public class AddProgramRequest {

	public String programDescription;
	public String programName;
	public String programStatus;
	
    public AddProgramRequest(String programName, String programStatus, String programDescription)
    {
        this.programName = programName;
        this.programStatus = programStatus;
        this.programDescription = programDescription;
        LoggerLoad.logInfo("Progam Request: programName: "+programName+ "programStatus: "+ programStatus+"programDescription: "+programDescription);
    }

}
