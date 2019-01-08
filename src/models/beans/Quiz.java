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

/**
 * Bean that represents a Quiz
 * 
 * @author Thomas
 *
 */
public class Quiz implements Serializable, Container {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private boolean isActive;
	private ArrayList<Question> questions;
	private Theme theme;
	private User creator;
	private Timestamp creationDate;
	private int nbOfRecords;

	/**
	 * Default constructor
	 */
	public Quiz() {
		this.id = -1;
		this.title = "notitle";
		this.isActive = true;
		this.questions = new ArrayList<Question>();
		this.theme = new Theme("Default");
		this.creator = null;
		this.creationDate = new Timestamp(System.currentTimeMillis());
		this.nbOfRecords = 0;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param title
	 * @param theme
	 * @param administrator
	 */
	public Quiz(String title, Theme theme, User administrator) {
		this.title = title;
		this.isActive = true;
		this.questions = new ArrayList<Question>();
		this.theme = theme;
		this.creator = administrator;
		this.creationDate = new Timestamp(System.currentTimeMillis());
		this.nbOfRecords = 0;
	}

	/**
	 * Copy constructor
	 * 
	 * @param q
	 *            of Quiz type
	 */
	public Quiz(Quiz q) {
		this.id = q.getId();
		this.title = q.getTitle();
		this.isActive = q.getIsActive();
		this.setQuestions(q.getQuestions());
		this.theme = q.getTheme();
		this.creator = q.getCreator();
		this.creationDate = q.getCreationDate();
		this.nbOfRecords = q.getNbOfRecords();
	}

	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Set isActive
	 * 
	 * @param isActive
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * 
	 * @return questions an ArrayList of Question
	 */
	public ArrayList<Question> getQuestions() {
		return this.questions;
	}

	/**
	 * Set questions
	 * 
	 * @param questions
	 *            arrayList of Question
	 */
	public void setQuestions(ArrayList<Question> questions) {
		questions.sort(Comparator.comparing(Question::getPosition));

		this.questions = questions;
	}

	/**
	 * 
	 * @return theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Set theme
	 * 
	 * @param theme
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/**
	 * 
	 * @return creator of type User
	 */
	public User getCreator() {
		return this.creator;
	}

	/**
	 * Set creator
	 * 
	 * @param u
	 *            of type User
	 */
	public void setCreator(User u) {
		this.creator = u;
	}

	/**
	 * 
	 * @return creationDate
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * Set creationDate
	 * 
	 * @param creationDate
	 *            of type Timestamp
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * 
	 * @return nbOfRecords
	 */
	public int getNbOfRecords() {
		return nbOfRecords;
	}

	/**
	 * Set nbOfRecords
	 * 
	 * @param nbOfRecords
	 *            of type int
	 */
	public void setNbOfRecords(int nbOfRecords) {
		this.nbOfRecords = nbOfRecords;
	}

	/**
	 * @return String with better representation
	 */
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

	/**
	 * Iterator in order to not have more than one question at the same position
	 *
	 */
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
