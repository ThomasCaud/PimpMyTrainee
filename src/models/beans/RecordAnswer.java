package models.beans;

import java.io.Serializable;

public class RecordAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    private Record record;
    private Answer answer;

    public RecordAnswer() {
	super();
	this.record = new Record();
	this.answer = new Answer();
    }

    public RecordAnswer(Record record, Answer answer) {
	super();
	this.record = record;
	this.answer = answer;
    }

    public Record getRecord() {
	return record;
    }

    public void setRecord(Record record) {
	this.record = record;
    }

    public Answer getAnswer() {
	return answer;
    }

    public void setAnswer(Answer answer) {
	this.answer = answer;
    }
}
