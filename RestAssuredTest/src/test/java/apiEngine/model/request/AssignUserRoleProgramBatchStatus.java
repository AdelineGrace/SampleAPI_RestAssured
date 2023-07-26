package apiEngine.model.request;

import java.util.ArrayList;
import java.util.List;

public class AssignUserRoleProgramBatchStatus {
	public Integer programId;
	public String roleId;
	public String userId;
	public List<UserRoleProgramBatches> userRoleProgramBatches;
	
	
	@Override
	public String toString() {
		return "AssignUserRoleProgramBatchStatus [programId=" + programId + ", roleId=" + roleId + ", userId=" + userId
				+ ", userRoleProgramBatchesList=" + userRoleProgramBatches + "]";
	}


	public AssignUserRoleProgramBatchStatus(Integer programId, String roleId, String userId,
			Integer batchId, String userRoleProgramBatchStatus) {
		
		this.programId = programId;
		this.roleId = roleId;
		this.userId = userId;
		UserRoleProgramBatches userRoleProgramBatche = new UserRoleProgramBatches(batchId, userRoleProgramBatchStatus);
		
		this.userRoleProgramBatches = new ArrayList<UserRoleProgramBatches>();
		userRoleProgramBatches.add(userRoleProgramBatche);
	}
	
	
	
}
