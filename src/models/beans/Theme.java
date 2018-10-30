package models.beans;

import java.io.Serializable;
import java.sql.Timestamp;

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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = "+this.id+"\n");
		sb.append("label = "+this.label);
		return sb.toString();
	}
}
