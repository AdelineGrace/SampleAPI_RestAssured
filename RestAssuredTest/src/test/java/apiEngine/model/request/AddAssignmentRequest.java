package apiEngine.model.request;

public class AddAssignmentRequest {

	public String assignmentDescription;
	public String assignmentName;
	public Integer batchId;
	public String comments;
	public String createdBy;
	public String dueDate;
	public String graderId;
	public String pathAttachment1;
	public String pathAttachment2;
	public String pathAttachment3;
	public String pathAttachment4;
	public String pathAttachment5;
	
	public AddAssignmentRequest(String assignmentName, String assignmentDescription, Integer batchId, 
			String comments, String createdBy, String dueDate, String graderId, String pathAttachment1,
			String pathAttachment2, String pathAttachment3, String pathAttachment4, String pathAttachment5)
	{
		this.assignmentDescription = assignmentDescription;
		this.assignmentName = assignmentName;
		this.batchId = batchId;
		this.comments = comments;
		this.createdBy = createdBy;
		this.dueDate = dueDate;
		this.graderId = graderId;
		this.pathAttachment1 = pathAttachment1;
		this.pathAttachment2 = pathAttachment2;
		this.pathAttachment3 = pathAttachment3;
		this.pathAttachment4 = pathAttachment4;
		this.pathAttachment5 = pathAttachment5;
	}
}
