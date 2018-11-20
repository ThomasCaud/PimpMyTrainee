package models.beans;

import java.io.Serializable;

public class RecordAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int recordId;
	private int answerId;

	public RecordAnswer() {
		super();
		this.recordId = -1;
		this.answerId = -1;
	}

	public RecordAnswer(int record, int answer) {
		super();
		this.recordId = record;
		this.answerId = answer;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int record) {
		this.recordId = record;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answer) {
		this.answerId = answer;
	}
}
