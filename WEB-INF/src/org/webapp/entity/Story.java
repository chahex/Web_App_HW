/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.entity;

import java.util.Date;

import org.genericdao.PrimaryKey;
import org.webapp.util.HasDate;

//The Story has getDate() method, so the DateComparator can compare the different Stories based on Date.
@PrimaryKey("id")
public class Story implements HasDate{
	
	private int id;
	//The id of the owner of the post.
	private int ownerId;
	//The profile pic Id of the user when he posted it.
	private int picId;
	private String title;
	private String content;
	// the owner of this story's first name an last names
	private String ownerFirstname;
	private String ownerLastname;
	// the time this story is posted
	private Date date;
	
	public Story(){}
	
	public Story(User owner, String title, String content, Date date){
		this.ownerId = owner.getId();
		this.ownerFirstname = owner.getFirstname();
		this.ownerLastname = owner.getLastname();
		this.picId = owner.getProfilePicId();
		setTitle(title);
		setContent(content);
		this.date = date;
	}
	
	public int getId() {		return id;	}
	public int getOwnerId() {		return ownerId;	}
	public int getPicId() {		return picId;	}
	public String getTitle() {		return title;	}
	public String getContent() {		return content;	}
	public Date getDate() {		return date;	}
	public String getOwnerFirstname() {		return ownerFirstname;	}
	public String getOwnerLastname() {		return ownerLastname;	}

	public void setId(int id) {		this.id = id;	}
	public void setOwnerId(int ownerId) {		this.ownerId = ownerId;	}
	public void setPicId(int picId) {		this.picId = picId;	}
	public void setTitle(String title) {		this.title = ensureLength(title);	}
	public void setContent(String content) {		this.content = ensureLength(content);}
	public void setDate(Date date) {		this.date = date;	}
	public void setOwnerFirstname(String ownerFirstname) {		this.ownerFirstname = ownerFirstname;	}
	public void setOwnerLastname(String ownerLastname) {		this.ownerLastname = ownerLastname;	}

	// due to the limit of string -> varchar2(255)
	// this function exists to truncate longer strings
	private String ensureLength(String in){
		if(in!=null&&in.length()>255){
			return in.substring(0,254);
		}
		return in;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Story [ownerId=").append(ownerId).append(", picId=")
				.append(picId).append(", title=").append(title)
				.append(", content=").append(content)
				.append(", ownerFirstname=").append(ownerFirstname)
				.append(", ownerLastname=").append(ownerLastname)
				.append(", date=").append(date).append("]");
		return builder.toString();
	}
}
