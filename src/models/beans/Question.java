package models.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String label;
	private Boolean isActive;
	private int position;
	private ArrayList<Answer> possibleAnswers;
	
	public Question() {
		this.id = -1;
		this.label = "nolabel";
		this.isActive = true;
		this.position = -1;
		this.possibleAnswers = new ArrayList<Answer>();
	}

	public Question(String label, int position, ArrayList<Answer> possibleAnswers) {
		this.label = label;
		this.isActive = true;
		this.position = position;
		this.possibleAnswers = possibleAnswers;
	}

	public Question(Question q) {
		this.id = q.getId();
		this.label = q.getLabel();
		this.isActive = q.getIsActive();
		this.position = q.getPosition();
		this.possibleAnswers = q.getPossibleAnswers();
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public ArrayList<Answer> getPossibleAnswers() {
		return this.possibleAnswers;
	}

	public void setPossibleAnswers(ArrayList<Answer> pa) {
		this.possibleAnswers = pa;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = "+this.id+"\n");
		sb.append("label = "+this.label+"\n");
		sb.append("isActive = "+this.isActive+"\n");
		sb.append("position = "+this.position+"\n");
		return sb.toString();
	}
}
