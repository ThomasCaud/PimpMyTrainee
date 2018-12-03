package models.forms;

import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import models.beans.Answer;
import models.beans.Record;
import models.beans.RecordAnswer;

public class RecordAnswerForm {

	// Variables that represents each field of the form
	private RecordAnswerDAO mRecordAnswerDAO;
	private RecordDAO mRecordDAO;

	public RecordAnswerForm(RecordAnswerDAO pRecordAnswerDAO, RecordDAO pRecordDAO) {
		super();
		this.mRecordAnswerDAO = pRecordAnswerDAO;
		this.mRecordDAO = pRecordDAO;
	}

	// Main method called by the servlet to process the registration
	public void recordAnAnswer(Record pRecord, Answer pAnswer) {
		RecordAnswer recordAnswer = new RecordAnswer();
		recordAnswer.setAnswerId(pAnswer.getId());
		recordAnswer.setRecordId(pRecord.getId());
		this.mRecordAnswerDAO.createRecordAnswer(recordAnswer);

		this.mRecordDAO.update("duration", pRecord.getDuration(), "id", pRecord.getId());
		this.mRecordDAO.update("score", pRecord.getScore(), "id", pRecord.getId());
	}

}
