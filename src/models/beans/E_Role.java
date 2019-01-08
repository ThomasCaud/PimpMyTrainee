package models.beans;

/**
 * Enumeration for Role of user: TRAINEE or ADMIN
 * 
 * @author Thomas
 *
 */
public enum E_Role {
	ADMIN("Admin"), TRAINEE("Trainee");

	private String label;

	/**
	 * Constructor from label
	 * 
	 * @param label
	 */
	private E_Role(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return label
	 */
	public String getLabel() {
		return this.label;
	}
}
