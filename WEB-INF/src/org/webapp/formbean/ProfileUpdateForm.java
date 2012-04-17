/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.List;
import java.util.TimeZone;

import org.webapp.formbean.annotations.ValCheck;


public class ProfileUpdateForm extends BaseForm {
	
	private String fname = "";
	private String lname = "";
	private String timezone = "";
	private String profileText = "";

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

	/***
	 * The personal profile agree you to use <> or *, but convert them.
	 *  
	 * @param profileText
	 */
	@ValCheck(valName="Profile description", isNotEmpty=false, isValidChar=false)
	public String getProfileText() {
		return profileText;
	}
	
	public void setProfileText(String profileText) {
		if(profileText!=null)
		this.profileText = trimAndConvert(profileText, "<>\"*&");
	}
	
	public void setFname(String fname) {
		if(fname!=null)
		this.fname = trimAndConvert(fname, "<>\"*&");
	}

	public void setLname(String lname) {
		if(lname!=null)
		this.lname = trimAndConvert(lname, "<>\"*&");
	}

	public void setTimezone(String timezone) {
		if(timezone!=null)
		this.timezone = trimAndConvert(timezone, "<>*&");
	}
	
	
	@Override
	public List<String> getValidationErrors() {
		List<String> errors = validate();
		if(errors.size()>0){
			return errors;
		}
		// Invalid Time zone id input
		if (!"GMT".equalsIgnoreCase(timezone)
				&& TimeZone.getTimeZone(timezone).equals(
						TimeZone.getTimeZone("GMT"))) {
			errors.add("Invalid time zone ID: " + timezone);
		}
		return errors;
	}

}