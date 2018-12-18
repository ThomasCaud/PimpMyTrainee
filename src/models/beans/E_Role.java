package models.beans;

public enum E_Role {
	ADMIN("Admin"), TRAINEE("Trainee");

	private String label;

	private E_Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
