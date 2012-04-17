/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.entity;

import java.util.Date;

import org.genericdao.PrimaryKey;
import org.webapp.util.HasDate;

/**
 * @author Administrator
 *
 */
@PrimaryKey("id")
public class Comment implements HasDate{
	
	private int id;
	//The id of the owner of the post.
	private int ownerId;
	//The profile pic Id of the user when he posted it.
	private int picId;
	// the story this comment belongs to
	private int storyId;
	// the owner of the story this comment belongs to.
	private int storyOwnerId;
	// the content of the comment
	private String content;
	
	private String ownerFirstname;
	private String ownerLastname;
	private Date date;
	
	public Comment(){}
	
	public Comment(User owner, Story story, String content, Date date){
		this.ownerId = owner.getId();
		this.ownerFirstname = owner.getFirstname();
		this.ownerLastname = owner.getLastname();
		this.picId = owner.getProfilePicId();
		this.storyId = story.getId();
		this.storyOwnerId = story.getOwnerId();
		this.date = date;
		setContent(content);
	}

	public int getId() {		return id;	}
	public String getContent() {		return content;	}
	public int getOwnerId() {		return ownerId;	}
	public String getOwnerFirstname() {		return ownerFirstname;	}
	public String getOwnerLastname() {		return ownerLastname;	}
	public int getPicId() {		return picId;	}
	public int getStoryOwnerId() {		return storyOwnerId;	}
	public int getStoryId() {		return storyId;	}
	@Override public Date getDate() {		return date;	}


	public void setId(int id) {		this.id = id;	}
	public void setContent(String content) {		this.content = ensureLength(content);	}
	public void setOwnerId(int ownerId) {		this.ownerId = ownerId;	}
	public void setOwnerFirstname(String ownerFirstname) {		this.ownerFirstname = ownerFirstname;	}
	public void setOwnerLastname(String ownerLastname) {		this.ownerLastname = ownerLastname;	}
	public void setPicId(int picId) {		this.picId = picId;	}
	public void setStoryId(int storyId) {		this.storyId = storyId;	}
	public void setStoryOwnerId(int storyOwnerId) {		this.storyOwnerId = storyOwnerId;	}
	public void setDate(Date date) {		this.date = date;	}

	// due to the limit of string -> varchar2(255)
	// this function exists to truncate longer strings
	private String ensureLength(String in){
		if(in!=null&&in.length()>255){
			in = in.substring(0,255);
		}
		return in;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [ownerId=").append(ownerId).append(", picId=")
				.append(picId).append(", storyId=").append(storyId)
				.append(", storyOwnerId=").append(storyOwnerId)
				.append(", content=").append(content)
				.append(", ownerFirstname=").append(ownerFirstname)
				.append(", ownerLastname=").append(ownerLastname)
				.append(", date=").append(date).append("]");
		return builder.toString();
	}
	
	

}