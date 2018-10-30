package models.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Quiz implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private Boolean isActive;
	private ArrayList<PossibleAnswer> possibleAnswers;
	private Theme theme;
	private User administrator;
	private Timestamp creationDate;
	
	public Quiz() {
		this.id = -1;
		this.title = "notitle";
		this.isActive = true;
		this.possibleAnswers = new ArrayList<PossibleAnswer>();
		this.theme = new Theme("Default");
		this.administrator = null;
		this.creationDate = new Timestamp(System.currentTimeMillis());
	}

	public Quiz(String title, Theme theme, User administrator) {
		this.title = title;
		this.isActive = true;
		this.possibleAnswers = new ArrayList<PossibleAnswer>();
		this.theme = theme;
		this.administrator = administrator;
		this.creationDate = new Timestamp(System.currentTimeMillis());
	}

	public Quiz(Quiz q) {
		this.id = q.getId();
		this.title = q.getTitle();
		this.isActive = q.getIsActive();
		this.possibleAnswers = q.getPossibleAnswers();
		this.theme = q.getTheme();
		this.administrator = q.getAdministrator();
		this.creationDate = q.getCreationDate();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public ArrayList<PossibleAnswer> getPossibleAnswers() {
		return this.possibleAnswers;
	}

	public void setPossibleAnswers(ArrayList<PossibleAnswer> pa) {
		this.possibleAnswers = pa;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public User getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(User u) {
		this.administrator = u;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = "+this.id+"\n");
		sb.append("title = "+this.title+"\n");
		sb.append("isActive = "+this.isActive+"\n");
		sb.append("administrator="+this.administrator.toString()+"\n");
		sb.append("creationDate = "+this.creationDate+"\n");
		return sb.toString();
	}
}
