/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.viewbean;

public class HottestUsersView {
	
	private int userId;
	private String firstname;
	private String lastname;
	private String username;
	private int profilePicId;
	private int friendCount;
	
	@Override
	public String toString() {
		return "HottestUsersView [userId=" + userId + ", firstname=" + firstname
				+ ", friendCount=" + friendCount + "]";
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getFriendCount() {
		return friendCount;
	}
	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getProfilePicId() {
		return profilePicId;
	}
	public void setProfilePicId(int profilePicId) {
		this.profilePicId = profilePicId;
	}

}
