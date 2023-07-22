package apiEngine.model.request;

public class AddBatchRequest {
	
	public String batchDescription;
	public String batchName;
	public Integer batchNoOfClasses;
	public String batchStatus;
	public Integer programId;
	
    public AddBatchRequest(String batchName, String batchStatus, String batchDescription, Integer batchNoOfClasses, Integer programId)
    {
        this.batchName = batchName;
        this.batchStatus = batchStatus;
        this.batchDescription = batchDescription;
        this.batchNoOfClasses = batchNoOfClasses;
        this.programId = programId;
    }

}
