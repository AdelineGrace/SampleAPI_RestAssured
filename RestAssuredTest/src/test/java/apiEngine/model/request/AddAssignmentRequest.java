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

	public void AssignmentDescription(String assignmentDescription) {
		this.assignmentDescription = assignmentDescription;
	}

	public void AssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public void BatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public void Comments(String comments) {
		this.comments = comments;
	}

	public void CreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void DueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void GraderId(String graderId) {
		this.graderId = graderId;
	}

	public void PathAttachment1(String pathAttachment1) {
		this.pathAttachment1 = pathAttachment1;
	}
	
	public void PathAttachment2(String pathAttachment2) {
		this.pathAttachment2 = pathAttachment2;
	}

	public void PathAttachment3(String pathAttachment3) {
		this.pathAttachment3 = pathAttachment3;
	}

	public void PathAttachment4(String pathAttachment4) {
		this.pathAttachment4 = pathAttachment4;
	}

	public void PathAttachment5(String pathAttachment5) {
		this.pathAttachment5 = pathAttachment5;
	}

}
