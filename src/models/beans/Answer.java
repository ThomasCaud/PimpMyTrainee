package models.beans;

import java.io.Serializable;

/**
 * A bean that represents a possible answer from a quiz
 * 
 * @author Thomas
 */
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String label;
	private Boolean isCorrect;
	private Boolean isActive;
	private int position;

	/**
	 * Default constructor
	 */
	public Answer() {
		this.id = -1;
		this.label = "nolabel";
		this.isActive = true;
		this.isCorrect = false;
		this.position = -1;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param label
	 * @param isCorrect
	 * @param position
	 * @param question
	 */
	public Answer(String label, Boolean isCorrect, int position, Question question) {
		this.label = label;
		this.isActive = true;
		this.isCorrect = isCorrect;
		this.position = position;
	}

	/**
	 * Copy constructor
	 * 
	 * @param pa
	 * 
	 */
	public Answer(Answer pa) {
		this.id = pa.getId();
		this.label = pa.getLabel();
		this.isActive = pa.getIsActive();
		this.isCorrect = pa.getIsCorrect();
		this.position = pa.getPosition();
	}

	/**
	 * Get answer id
	 * 
	 * @return id integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set answer id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get answer label
	 * 
	 * @return label String
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set answer label
	 * 
	 * @param label
	 *            String
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Get answer isActive
	 *
	 * @return Boolean
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Set answer isActive
	 *
	 * @param isActive
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Get answer isCorrect
	 *
	 * @return Boolean
	 */
	public Boolean getIsCorrect() {
		return isCorrect;
	}

	/**
	 * Set answer isCorrect
	 *
	 * @param isCorrect
	 */
	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	/**
	 * Get answer position
	 *
	 * @return int
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Set answer position
	 *
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Overwrite toString function
	 */
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
