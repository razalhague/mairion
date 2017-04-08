package org.penny_craal.mairion;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * A simple DTO for the information needed for registering a user account.
 */
public class RegistrationDTO {
	@Size(min = 3)
	private String name;

	@Email
	private String email;

	@Size(min = 10)
	private String password;

	public RegistrationDTO(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public RegistrationDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
