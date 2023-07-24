package context;

import apiEngine.endpoints.AssignmentEndpoints;
import apiEngine.endpoints.ProgramEndpoints;
import apiEngine.endpoints.BatchEndpoints;
import apiEngine.endpoints.UserEndpoints;

public class TestContext {
	
	private final String baseUrl = "https://bookstore.toolsqa.com";

	private ProgramEndpoints programEndpoints;
	private BatchEndpoints batchEndpoints;
	private UserEndpoints userEndpoints;
	private AssignmentEndpoints assignmentEndpoints;

	public TestContext() {
		programEndpoints = new ProgramEndpoints(baseUrl);
		batchEndpoints = new BatchEndpoints(baseUrl);
		userEndpoints = new UserEndpoints(baseUrl);
		assignmentEndpoints = new AssignmentEndpoints(baseUrl);
	}

	public ProgramEndpoints ProgramEndpoints() {
        return programEndpoints;
    }
	
	public BatchEndpoints BatchEndpoints() {
        return batchEndpoints;
    }
	
	public UserEndpoints UserEndpoints() {
        return userEndpoints;
    }
	
	public AssignmentEndpoints AssignmentEndpoints() {
        return assignmentEndpoints;
    }

}
