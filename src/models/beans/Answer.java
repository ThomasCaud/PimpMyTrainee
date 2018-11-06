package models.beans;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String label;
    private Boolean isCorrect;
    private Boolean isActive;
    private int position;

    public Answer() {
	this.id = -1;
	this.label = "nolabel";
	this.isActive = true;
	this.isCorrect = false;
	this.position = -1;
    }

    public Answer(String label, Boolean isCorrect, int position, Question question) {
	this.label = label;
	this.isActive = true;
	this.isCorrect = isCorrect;
	this.position = position;
    }

    public Answer(Answer pa) {
	this.id = pa.getId();
	this.label = pa.getLabel();
	this.isActive = pa.isActive();
	this.isCorrect = pa.isCorrect();
	this.position = pa.getPosition();
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

    public Boolean isActive() {
	return isActive;
    }

    public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
    }

    public Boolean isCorrect() {
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

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("id = " + this.id + "\n");
	sb.append("label = " + this.label + "\n");
	sb.append("isActive = " + this.isActive + "\n");
	sb.append("isCorrect = " + this.isCorrect + "\n");
	sb.append("position = " + this.position + "\n");
	return sb.toString();
    }
}
