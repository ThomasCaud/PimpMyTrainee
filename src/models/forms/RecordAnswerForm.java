package models.forms;

import dao.interfaces.RecordAnswerDAO;
import models.beans.Answer;
import models.beans.Record;
import models.beans.RecordAnswer;

public class RecordAnswerForm {

	// Variables that represents each field of the form
	private RecordAnswerDAO mRecordAnswerDAO;

	public RecordAnswerForm(RecordAnswerDAO pRecordAnswerDAO) {
		super();
		this.mRecordAnswerDAO = pRecordAnswerDAO;
	}

	// Main method called by the servlet to process the registration
	public void recordAnAnswer(Record pRecord, Answer pAnswer) {
		RecordAnswer recordAnswer = new RecordAnswer();
		recordAnswer.setAnswerId(pAnswer.getId());
		recordAnswer.setRecordId(pRecord.getId());
		this.mRecordAnswerDAO.createRecordAnswer(recordAnswer);
	}

}
