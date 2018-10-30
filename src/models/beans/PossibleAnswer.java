package models.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class PossibleAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String label;
	private Boolean isCorrect;
	private Boolean isActive;
    private int position;
    private Question question;

	public PossibleAnswer() {
		this.id = -1;
		this.label = "nolabel";
		this.isActive = true;
		this.isCorrect = false;
        this.position = -1;
        this.question = new Question();
	}

	public PossibleAnswer(String label, Boolean isCorrect, int position, Question question) {
		this.label = label;
		this.isActive = true;
		this.isCorrect = isCorrect;
        this.position = position;
        this.question = question;
	}

	public PossibleAnswer(PossibleAnswer pa) {
		this.id = pa.getId();
		this.label = pa.getLabel();
		this.isActive = pa.getIsActive();
		this.isCorrect = pa.getIsCorrect();
        this.position = pa.getPosition();
        this.question = pa.getQuestion();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = "+this.id+"\n");
		sb.append("label = "+this.label+"\n");
		sb.append("isActive = "+this.isActive+"\n");
		sb.append("isCorrect = "+this.isCorrect+"\n");
        sb.append("position = "+this.position+"\n");
        sb.append("question = "+this.question+"\n");
		return sb.toString();
	}
}
