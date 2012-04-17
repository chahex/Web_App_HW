/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.List;

public class AddStoryForm extends BaseForm{
	
	private String content = "";
	private String title = "";
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if(content!=null)
		this.content = trimAndConvert(content, "<>\"*&");
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title!=null)
		this.title = trimAndConvert(title, "<>\"*&");
	}
	
	/**
	 * Validate whether the type submitted by the form matches the types defined by the static values of this class.
	 */
	@Override
	public List<String> getValidationErrors() {
		return validate();
	}


}
