package apiEngine.model.request;

public class UserRoleProgramBatches {
	@Override
	public String toString() {
		return "UserRoleProgramBatches [batchId=" + batchId + ", userRoleProgramBatchStatus="
				+ userRoleProgramBatchStatus + "]";
	}

	public Integer  batchId;
	public String userRoleProgramBatchStatus;
	
	public UserRoleProgramBatches(Integer batchId, String userRoleProgramBatchStatus) {
		
		this.batchId = batchId;
		this.userRoleProgramBatchStatus = userRoleProgramBatchStatus;
	}
	
	

}
