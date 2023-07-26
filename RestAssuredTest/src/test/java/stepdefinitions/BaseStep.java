package stepdefinitions;

import apiEngine.endpoints.AssignmentEndpoints;
import apiEngine.endpoints.AssignmentSubmitEndpoints;
import apiEngine.endpoints.BatchEndpoints;
import apiEngine.endpoints.ProgramEndpoints;
import apiEngine.endpoints.UserEndpoints;

public class BaseStep {
	
	String baseUrl = "https://lms-api-hackathon-june2023-930a8b0f895d.herokuapp.com/lms";
	
	ProgramEndpoints programEndpoints;
	BatchEndpoints batchEndpoints;
	UserEndpoints userEndpoints;
	AssignmentEndpoints assignmentEndpoints;
	AssignmentSubmitEndpoints submitEndpoints;

    public BaseStep() 
    {
    	programEndpoints = new ProgramEndpoints(baseUrl);
		batchEndpoints = new BatchEndpoints(baseUrl);
		userEndpoints = new UserEndpoints(baseUrl);
		assignmentEndpoints = new AssignmentEndpoints(baseUrl);
		submitEndpoints = new AssignmentSubmitEndpoints(baseUrl);
    }
}
