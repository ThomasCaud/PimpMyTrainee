package models.beans;

import java.io.Serializable;

/**
 * Bean that represents a RecordAnswer
 * 
 * @author Thomas
 *
 */
public class RecordAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int recordId;
	private int answerId;

	/**
	 * Default constructor
	 */
	public RecordAnswer() {
		super();
		this.recordId = -1;
		this.answerId = -1;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param record
	 * @param answer
	 */
	public RecordAnswer(int record, int answer) {
		super();
		this.recordId = record;
		this.answerId = answer;
	}

	/**
	 * get recordId
	 * 
	 * @return recordId
	 */
	public int getRecordId() {
		return recordId;
	}

	/**
	 * set recordId
	 * 
	 * @param record
	 *            of type int
	 */
	public void setRecordId(int record) {
		this.recordId = record;
	}

	/**
	 * get answerId
	 * 
	 * @return answerId
	 */
	public int getAnswerId() {
		return answerId;
	}

	/**
	 * set answerId
	 * 
	 * @param answer
	 *            of type int
	 */
	public void setAnswerId(int answer) {
		this.answerId = answer;
	}
}
