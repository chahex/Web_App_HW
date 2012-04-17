/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.lang.reflect.Method;
import java.util.List;

import org.webapp.formbean.annotations.ValCheck;

public class LoginForm extends BaseForm{
	private String username = "";
	private String password = "";
	private String redirectTo = "";

	@Override
	public List<String> getValidationErrors() {
		return validate();
	}
	
	@ValCheck(valName="User name", isValidChar=true)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		if(username!=null){
			this.username = trimAndConvert(username, "<>\"*&");
		}
	}
	
	@ValCheck(valName="Password", isValidChar=true)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
			this.password = password.trim();
	}
	
	public String getRedirectTo() {
		return redirectTo;
	}
	
	/**
	 * Although redirect string will only be included in User's own browser.
	 * 
	 */
	public void setRedirectTo(String redirectTo) {
		if(redirectTo!=null)
			this.redirectTo = redirectTo.trim();
	}
	
	public static void main(String[] args) {
		Class<?> cls = LoginForm.class;
		Method[] mds = cls.getDeclaredMethods();
		for(Method m: mds){
			ValCheck v = m.getAnnotation(ValCheck.class);
			System.out.println(v);
		}
	}
}
