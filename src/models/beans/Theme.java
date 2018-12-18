package models.beans;

import java.io.Serializable;

/**
 * A bean that represents the theme of a quiz
 */
public class Theme implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String label;

	public Theme() {
		this.id = -1;
		this.label = "nolabel";
	}

	public Theme(String label) {
		this.label = label;
	}

	/**
	 * Get the id of the theme
	 * 
	 * @return the id of the theme
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the id of the theme
	 * 
	 * @param id An int that represents the id of the theme
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the label of the theme
	 * 
	 * @return the label of the theme
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set the label of the theme
	 * 
	 * @param label A String that contains the label of the theme
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = " + this.id + "\n");
		sb.append("label = " + this.label);
		return sb.toString();
	}
}
