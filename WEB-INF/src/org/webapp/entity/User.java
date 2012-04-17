/**
 * Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Feb 06, 2012


 * @edition Mar 15, 2012
 * @edition Mar 20, 2012, profilePicId, string->int.
 */
package org.webapp.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private String hashedPassword = "*";
	private String firstname;
	private String lastname;
	private String timezone;
	
	private int salt = 0;
	private String profileText;
	private int profilePicId = -1;

	public User() {
	}

	public User(String username, String password, String firstname,
			String lastname, String timezone) {
		this.username = username;
		this.setPassword(password);
		this.firstname = firstname;
		this.lastname = lastname;
		this.timezone = timezone;
	}
	
	public void setId(int id) {		this.id = id;	}
	public void setUsername(String username) {		this.username = username;		}
	public void setHashedPassword(String hashedPassword){  this.hashedPassword=hashedPassword;    }
	public void setFirstname(String firstname) {		this.firstname = firstname;		}
	public void setLastname(String lastname) {    this.lastname = lastname;}
	public void setTimezone(String timezone) {    this.timezone = timezone;}
	public void setSalt(int salt){  this.salt = salt;}
	public void setProfileText(String profileText) {		this.profileText = profileText;	}
	public void setProfilePicId(int profilePicId) {this.profilePicId = profilePicId;	}

	public String getHashedPassword() {		return this.hashedPassword;		}
	public int getId() {    return id;}
	public String getUsername() {    return username;}
	public String getFirstname() {    return firstname;}
	public String getLastname() {   return lastname;}
	public String getTimezone() {	return timezone;}
	public int getSalt(){ return salt;}
	public String getProfileText() {		return profileText;	}
	public int getProfilePicId() {		return profilePicId;	}


	/**What if, when populate the bean, setHashedPassword is called first and then this method is called second? 
	 * Or the population won't call this because there is no getter?
	 * It's very important this method not be called by the populater. Wait, but if there is no getters, there won't be a column in the database right?
	 * Done.
	 * 
	 *  This method would only be called by the action to set user password. Once set, the salt value and hashed password won't change and 
	 *  would be persisted
	 */
	public void setPassword(String password) {
		salt = newSalt(); this.hashedPassword = hash(password);		
	}

	@Override
	public String toString(){
		return new StringBuilder().append("User:").append(username).append(";name:").append(firstname).append(' ').append(lastname).toString();
	}
	
	public boolean checkPassword(String clearPassword){
		return hashedPassword.equals(hash(clearPassword));
	}
	
	private String hash(String clearPassword) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(
					"Can't find the SHA1 algorithm in the java.security package");
		}
		String saltString = String.valueOf(salt);
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();
		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i = 0; i < digestBytes.length; i++) {
			int lowNibble = digestBytes[i] & 0x0f;
			int highNibble = (digestBytes[i] >> 4) & 0x0f;
			digestSB.append(Integer.toHexString(highNibble));
			digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();
		return digestStr;

	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192) + 1; // salt cannot be zero, except for
											// uninitialized password
	}

}
