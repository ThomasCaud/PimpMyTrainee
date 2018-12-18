package models.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.iterator.Container;
import models.iterator.Iterator;

public class Quiz implements Serializable, Container {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private Boolean isActive;
	private ArrayList<Question> questions;
	private Theme theme;
	private User creator;
	private Timestamp creationDate;

	public Quiz() {
		this.id = -1;
		this.title = "notitle";
		this.isActive = true;
		this.questions = new ArrayList<Question>();
		this.theme = new Theme("Default");
		this.creator = null;
		this.creationDate = new Timestamp(System.currentTimeMillis());
	}

	public Quiz(String title, Theme theme, User administrator) {
		this.title = title;
		this.isActive = true;
		this.questions = new ArrayList<Question>();
		this.theme = theme;
		this.creator = administrator;
		this.creationDate = new Timestamp(System.currentTimeMillis());
	}

	public Quiz(Quiz q) {
		this.id = q.getId();
		this.title = q.getTitle();
		this.isActive = q.getIsActive();
		this.setQuestions(q.getQuestions());
		this.theme = q.getTheme();
		this.creator = q.getCreator();
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

	public ArrayList<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		questions.sort(Comparator.comparing(Question::getPosition));

		this.questions = questions;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User u) {
		this.creator = u;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String toString() {
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	@JsonIgnore
	public Iterator getIterator() {
		return new QuestionIterator();
	}

	private class QuestionIterator implements Iterator, Serializable {

		private static final long serialVersionUID = 1L;
		int index;

		@Override
		public boolean hasNext() {
			return index < questions.size();
		}

		@Override
		public Object next() {
			if (this.hasNext()) {
				return questions.get(index++);
			}
			return null;
		}

	}
}
