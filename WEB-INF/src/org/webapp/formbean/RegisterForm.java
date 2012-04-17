/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.List;
import java.util.TimeZone;

import org.webapp.formbean.annotations.ValCheck;

public class RegisterForm extends BaseForm {

	private String username;
	private String password;
	private String fname;
	private String lname;
	private String timezone;

	@ValCheck(valName = "User name", isNotEmpty = true, isValidChar = true)
	public String getUsername() {
		return username;
	}

	@ValCheck(valName = "Password", isNotEmpty = true, isValidChar = true)
	public String getPassword() {
		return password;
	}

	@ValCheck(valName = "First name", isNotEmpty = true, isValidChar = true)
	public String getFname() {
		return fname;
	}

	@ValCheck(valName = "Last name", isNotEmpty = true, isValidChar = true)
	public String getLname() {
		return lname;
	}

	@ValCheck(valName = "Time zone", isNotEmpty = true, isValidChar=true)
	public String getTimezone() {
		return timezone;
	}

	public void setUsername(String username) {
		this.username = trimAndConvert(username, "<>\"*&");
	}

	public void setPassword(String password) {
		this.password = trimAndConvert(password, "<>\"*&");
	}

	public void setFname(String fname) {
		this.fname = trimAndConvert(fname, "<>\"*&");
	}

	public void setLname(String lname) {
		this.lname = trimAndConvert(lname, "<>\"*&");
	}

	public void setTimezone(String timezone) {
		this.timezone = trimAndConvert(timezone, "<>*&");
	}

	@Override
	public List<String> getValidationErrors() {
		List<String> errors = validate();
		// Invalid Time zone id input
		if (!"GMT".equalsIgnoreCase(timezone)
				&& TimeZone.getTimeZone(timezone).equals(
						TimeZone.getTimeZone("GMT"))) {
			errors.add("Invalid time zone ID: " + timezone);
		}
		return errors;
	}

}