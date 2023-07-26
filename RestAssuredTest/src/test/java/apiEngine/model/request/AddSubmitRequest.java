package apiEngine.model.request;

public class AddSubmitRequest {
	
	public Integer assignmentId;
	public String userId;
	public String subDesc;
	public String subComments;
	public String subPathAttach1;
	public String subPathAttach2;
	public String subPathAttach3;
	public String subPathAttach4;
	public String subPathAttach5;
	public String subDateTime;
	
	public AddSubmitRequest(Integer assignmentId, String userId, String subDesc, String subComments,
			String subPathAttach1, String subPathAttach2, String subPathAttach3, String subPathAttach4,
			String subPathAttach5, String subDateTime)
	{
		this.assignmentId = assignmentId;
		this.userId = userId;
		this.subDesc = subDesc;
		this.subComments = subComments;
		this.subPathAttach1 = subPathAttach1;
		this.subPathAttach2 = subPathAttach2;
		this.subPathAttach3 = subPathAttach3;
		this.subPathAttach4 = subPathAttach4;
		this.subPathAttach5 = subPathAttach5;
		this.subDateTime = subDateTime;
	}
}
