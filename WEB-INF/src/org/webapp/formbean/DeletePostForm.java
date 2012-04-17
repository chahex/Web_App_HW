/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.List;

import org.webapp.formbean.annotations.ValCheck;

/**
 * The class holds an type and an IdForm to hold Id. The type can either be
 * 
 * @author Administrator
 * 
 */
public class DeletePostForm extends BaseForm {

	private String type = "";
	private String id = "";
	private String ownerId = "";
	/**Only used for the comment deletetion.*/
	private String storyOwnerId = "";

	public void setType(String type) {
		if (type != null)
			this.type = type.trim();
	}

	public String getType() {
		return type;
	}
	
	public void setId(String id) {
		if (id != null)
			this.id = id.trim();
	}

	public String getId() {
		return id;
	}

	public void setStoryOwnerId(String str) {
		if(str != null)
			this.storyOwnerId = str;
	}

	public String getStoryOwnerId() {
		return storyOwnerId;
	}
	
	@ValCheck(valName="owner id", isInteger=true)
	public String getOwnerId(){
		return ownerId;
	}
	
	public void setOwnerId(String ownerId){		if(ownerId!=null)			this.ownerId = ownerId.trim();	}

	/**
	 * For usage to delete a comment only.
	 * 
	 * @return
	 */
	public int getStoryOwnerIdAsInt() {
		return Integer.parseInt(storyOwnerId);
	}

	public int getIdAsInt() {
		return Integer.parseInt(id);
	}
	
	public int getOwnerIdAsInt(){
		return Integer.parseInt(ownerId);
	}
	

	@Override
	public List<String> getValidationErrors() {
		List<String> errors = validate();
		// check whether the id is valid.
		if (!(AddPostForm.COMMENT_TYPE).equals(type)
				&& !(AddPostForm.STORY_TYPE).equals(type)) {
			errors.add("Type not valid:" + type);
		}
		// check the storyOwnerId only for comment deletions.
		if(AddPostForm.COMMENT_TYPE.equals(type)){
			if(storyOwnerId.length()==0){
				errors.add("Story Owner Id can't be null.");
			}
			try{
				Integer.parseInt(storyOwnerId);
			}catch(NumberFormatException e){
				errors.add("Number format error in parsing id.");
			}
		}
		return errors;
	}
}
