package models.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import models.iterator.Container;
import models.iterator.Iterator;

/**
 * Bean that represents a Question in a Quiz
 * 
 * @author Thomas
 *
 */
public class Question implements Serializable, Container {
	private static final long serialVersionUID = 1L;
	private int id;
	private String label;
	private Boolean isActive;
	private int position;
	private ArrayList<Answer> possibleAnswers;
	private Answer correctAnswer;

	/**
	 * Default constructor
	 */
	public Question() {
		this.id = -1;
		this.label = "nolabel";
		this.isActive = true;
		this.position = -1;
		this.possibleAnswers = new ArrayList<Answer>();
		this.correctAnswer = new Answer();
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param label
	 * @param position
	 * @param possibleAnswers
	 */
	public Question(String label, int position, ArrayList<Answer> possibleAnswers) {
		this.label = label;
		this.isActive = true;
		this.position = position;
		this.setPossibleAnswers(possibleAnswers);

		for (Answer possibleAnswer : possibleAnswers) {
			if (possibleAnswer.getIsCorrect()) {
				this.correctAnswer = possibleAnswer;
			}
		}
	}

	/**
	 * Copy Constructor
	 * 
	 * @param q
	 */
	public Question(Question q) {
		this.id = q.getId();
		this.label = q.getLabel();
		this.isActive = q.getIsActive();
		this.position = q.getPosition();
		this.setPossibleAnswers(q.getPossibleAnswers());
		this.correctAnswer = q.getCorrectAnswer();
	}

	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Get isActive in order to know if the question is still used
	 * 
	 * @return isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * 
	 * @param isActive
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Get position of the question in the quiz
	 * 
	 * @return position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Set position of the question in the quiz
	 * 
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Get possible answers of the quiz
	 * 
	 * @return possibleAnswers
	 */
	public ArrayList<Answer> getPossibleAnswers() {
		return this.possibleAnswers;
	}

	/**
	 * Set the possible Answers
	 * 
	 * @param pa
	 *            an ArrayList of Answer
	 */
	public void setPossibleAnswers(ArrayList<Answer> pa) {
		if (pa != null)
			pa.sort(Comparator.comparing(Answer::getPosition));
		this.possibleAnswers = pa;
	}

	/**
	 * Get the correct answer of the question
	 * 
	 * @return correctAnswer
	 */
	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * Set the correct answer of the question
	 * 
	 * @param correctAnswer
	 */
	public void setCorrectAnswer(Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	/**
	 * Overwrite toString function
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = " + this.id + "\n");
		sb.append("label = " + this.label + "\n");
		sb.append("isActive = " + this.isActive + "\n");
		sb.append("position = " + this.position + "\n");
		return sb.toString();
	}

	@Override
	@JsonIgnore
	public Iterator getIterator() {
		return new AnswerIterator();
	}

	/**
	 * Iterator in order to not have more than one possible answer at the same
	 * position in the question
	 */
	private class AnswerIterator implements Iterator {

		int index;

		@Override
		public boolean hasNext() {
			return index < possibleAnswers.size();
		}

		@Override
		public Object next() {
			if (this.hasNext()) {
				return possibleAnswers.get(index++);
			}
			return null;
		}

	}
}
