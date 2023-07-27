package apiEngine.model.request;

import java.io.Serializable;

public class PutSubmission {

	public Integer submissionId;
	public Integer assignmentId;
	public String userId;
	public String subDesc;
	public String subcomments;
	public String subPathAttach1;
	public String subPathAttach2;
	public String subPathAttach3;
	public String subPathAttach4;
	public String subPathAttach5;
	public String subDateTime;
	public String gradedBy;
	public String gradedDateTime;
	public String grade;

	public PutSubmission(Integer submissionId,Integer assignmentId, String userId, Serializable subDesc2, String subComments,
			String subPathAttach1, String subPathAttach2, String subPathAttach3, String subPathAttach4,
			String subPathAttach5, String subDateTime, String gradedBy, String gradedDateTime, String grade)
	{
	
		this.submissionId = submissionId;
		this.assignmentId = assignmentId;
		this.userId = userId;
		this.subDesc = (String) subDesc2;
		this.subcomments = subComments;
		this.subPathAttach1 = subPathAttach1;
		this.subPathAttach2 = subPathAttach2;
		this.subPathAttach3 = subPathAttach3;
		this.subPathAttach4= subPathAttach4;
		this.subPathAttach5 = subPathAttach5;
		this.subDateTime =subDateTime ;
		this.gradedBy = gradedBy;
		this.gradedDateTime = gradedDateTime;
		this.grade = grade;
	

	}}
