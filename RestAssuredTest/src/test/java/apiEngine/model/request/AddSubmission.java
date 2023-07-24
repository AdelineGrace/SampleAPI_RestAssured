package apiEngine.model.request;

public class AddSubmission {

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

	public void submissionId(Integer submissionId) {
		this.submissionId = submissionId;
	}

	public void assignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
	}

	public void userId(String userId) {
		this.userId = userId;
	}

	public void subDesc(String subDesc) {
		this.subDesc = subDesc;
	}

	public void subcomments(String subcomments) {
		this.subcomments = subcomments;
	}

	public void subPathAttach1(String subPathAttach1) {
		this.subPathAttach1 = subPathAttach1;
	}

	public void subPathAttach2(String subPathAttach2) {
		this.subPathAttach2 = subPathAttach2;
	}

	public void subPathAttach3(String subPathAttach3) {
		this.subPathAttach3 = subPathAttach3;
	}
	
	public void subPathAttach4(String subPathAttach4) {
		this.subPathAttach4= subPathAttach4;
	}

	public void subPathAttach5(String subPathAttach5) {
		this.subPathAttach5 = subPathAttach5;
	}

	public void subDateTime(String subDateTime) {
		this.subDateTime =subDateTime ;
	}

	public void gradedBy(String gradedBy) {
		this.gradedBy = gradedBy;
	}
	
	public void gradedDateTime(String gradedDateTime) {
		this.gradedDateTime = gradedDateTime;
	}
	
	public void grade(String grade) {
		this.grade = grade;
	}

	}
