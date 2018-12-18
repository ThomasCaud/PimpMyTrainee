package models.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A bean that represents a user of the application
 */
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
	private models.beans.E_Role role;
	private User manager;

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
		this.role = E_Role.TRAINEE;
		this.manager = null;
	}

	public User(User u) {
		this.id = u.getId();
		this.firstname = u.getFirstname();
		this.lastname = u.getLastname();
		this.email = u.getEmail();
		this.password = u.getPassword();
		this.company = u.getCompany();
		this.phone = u.getPhone();
		this.creationDate = u.getCreationDate();
		this.isActive = u.getIsActive();
		this.role = u.getRole();
		this.setManager(u.getManager());
	}

	/**
	 * Gets user's id
	 * 
	 * @return An integer representing the user's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the user's id
	 * 
	 * @param id An integer containing the user's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets user's first name
	 * 
	 * @return A String representing the user's first name
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the user's first name
	 * 
	 * @param firstname A String containing the user's first name
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets user's last name
	 * 
	 * @return A String representing the user's last name
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the user's last name
	 * 
	 * @param lastname A String containing the user's last name
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets user's email
	 * 
	 * @return A String representing the user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email
	 * 
	 * @param email A String containing the user's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets user's password
	 * 
	 * @return A String representing the user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password
	 * 
	 * @param password A String containing the user's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets user's password
	 * 
	 * @return A String representing the user's password
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Sets the user's company
	 * 
	 * @param company A String containing the user's company
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Gets user's first phone
	 * 
	 * @return A String representing the user's phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the user's phone
	 * 
	 * @param phone A String containing the user's phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the date at which the user has been created
	 * 
	 * @return A Timestamp representing the user's creation date
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the date at which the user has been created
	 * 
	 * @param creationDate A Timestamp corresponding to the date at which the user
	 *                     has been created
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Knows whether a user is active or not
	 * 
	 * @return A boolean representing the user's status : active or inactive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Sets a boolean to determine whether the user is active or not
	 * 
	 * @param isActive A boolean corresponding to the status of the user : active or
	 *                 inactive
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Gets user's role
	 * 
	 * @return A E_Role representing the user's role
	 */
	public E_Role getRole() {
		return role;
	}

	/**
	 * Sets the user's role
	 * 
	 * @param role An E_Role corresponding to the role of the user
	 */
	public void setRole(E_Role role) {
		this.role = role;
	}

	/**
	 * Gets a string that represents the user
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id = " + this.id + "\n");
		sb.append("firstname = " + this.firstname + "\n");
		sb.append("lastname = " + this.lastname + "\n");
		sb.append("email = " + this.email + "\n");
		sb.append("password = " + this.password + "\n");
		sb.append("company = " + this.company + "\n");
		sb.append("phone = " + this.phone + "\n");
		sb.append("creationDate = " + this.creationDate + "\n");
		sb.append("isActive = " + this.isActive + "\n");
		sb.append("role = " + this.role);
		return sb.toString();
	}

	public User getManager() {
		return manager;
	}

	/**
	 * Sets the user's manager, if the user has a manager
	 * 
	 * @param manager A User corresponding to the user's manager
	 */
	public void setManager(User manager) {
		this.manager = manager;
	}
}
