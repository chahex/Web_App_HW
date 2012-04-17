/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.List;

import org.webapp.formbean.annotations.ValCheck;

public class AddPostForm extends BaseForm {

	public static final String STORY_TYPE = "story";
	public static final String COMMENT_TYPE = "comment";

	private String content = "";
	private String storyId = "";
	private String title = "";
	private String type = "";

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if (content != null)
			this.content = trimAndConvert(content, "<>\"*&");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title != null)
			this.title = trimAndConvert(title, "<>\"*&");
	}

	@ValCheck(valName = "Adding request type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.trim();
	}

	public String getStoryId() {
		return storyId;
	}

	public int getStoryIdAsInt() {
		return Integer.parseInt(storyId);
	}

	/** Won't be shown on browser again so no need to convert */
	public void setStoryId(String storyId) {
		if (storyId != null)
			this.storyId = storyId.trim();
	}

	/**
	 * Validate whether the type submitted by the form matches the types defined
	 * by the static values of this class.
	 */
	@Override
	public List<String> getValidationErrors() {
		List<String> errors = validate();
		if (!STORY_TYPE.equals(type) && !COMMENT_TYPE.equals(type)) {
			errors.add("Type not found: " + type);
			return errors;
		}
		
		if (COMMENT_TYPE.equals(type)) {
			// comment content can't be empty, story content, however, can
			if(content.length()==0){
				errors.add("Comment content can't be empty");
				return errors;
			}
			// comment posting requires story id
			try{
				Integer.parseInt(storyId);
			}catch(NumberFormatException e){
				errors.add("Story ID is not a valid positive integer number.");
				return errors;
			}
		}
		return errors;
	}

}
