/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.formbean.AddPostForm;
import org.webapp.formbean.IdForm;
import org.webapp.model.Model;

/**
 * The comment adding process normally does not needed to go to this page and can be done by simply clicking the add button.
 * This action and the comment.jsp page is left for your evaluation of the requirement that came from HW1. 
 * 
 * @author Administrator
 */
@FormClassType(IdForm.class)
@ActionName("addComment.do")
public class AddCommentAction extends Action<IdForm>{

	public AddCommentAction(Model model) {
		super(model);
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		IdForm form = formBeanFactory.create(request);
		if(!form.isPresent()){
			errors.add("Bad request: story id needed when posting new comment.");
			return "error.jsp";
		}
		// get the story id from the previous form and set it to the form that comment needed.
		AddPostForm form2 = new AddPostForm();
		form2.setStoryId(String.valueOf(form.getIdAsInt()));
		request.setAttribute("form", form2);
		return "comment.jsp";
	}
	
	

}
