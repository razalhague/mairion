package org.penny_craal.mairion;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * A DTO for passing login information from the view to the controller.
 */
public class LoginDTO {
	@Email
	private String email;

	@Size(min = 10)
	private String password;

	public LoginDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public LoginDTO() {
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
