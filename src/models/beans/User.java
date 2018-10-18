package models.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String company;
	private String phone;
	private Timestamp creationDate;
	private Boolean isActive;
	
	public User() {
		this.id = -1;
		this.firstname = "noFirstname";
		this.lastname = "noLastname";
		this.email = "noEmail";
		this.password = "noPassword";
		this.company = "noCompany";
		this.phone = "noPhone";
		this.creationDate = new Timestamp(System.currentTimeMillis());
		this.isActive = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Timestamp getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = "+this.id+"\n");
		sb.append("firstname = "+this.firstname+"\n");
		sb.append("lastname = "+this.lastname+"\n");
		sb.append("email = "+this.email+"\n");
		sb.append("password = "+this.password+"\n");
		sb.append("company = "+this.company+"\n");
		sb.append("phone = "+this.phone+"\n");
		sb.append("creationDate = "+this.creationDate+"\n");
		sb.append("isActive = "+this.isActive);
		return sb.toString();
	}
}
