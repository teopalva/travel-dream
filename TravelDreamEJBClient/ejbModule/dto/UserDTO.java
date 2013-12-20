package dto;

import entity.User;

public class UserDTO {
	private String mail;
	private String password;
	private String group;
	private String firstName;
	private String lastName;
	
	public UserDTO(User user) throws FieldNotPresentException {
		try {
			this.mail = user.getMail();
			this.password = null;
			this.group = user.getGroups().get(0).getId();
			this.firstName = user.getFirstName();
			this.lastName = user.getLastName();
		} catch(NullPointerException e) {
			throw new FieldNotPresentException();
		}
	}
	
	public UserDTO(String mail, String password, String group,
			String firstName, String lastName) {
		super();
		this.mail = mail;
		this.password = password;
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
