/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.CommentDAO;
import org.webapp.dao.StoryDAO;
import org.webapp.entity.Comment;
import org.webapp.entity.Story;
import org.webapp.entity.User;
import org.webapp.formbean.AddPostForm;
import org.webapp.model.Model;

@ActionName("addPost.do")
@FormClassType(AddPostForm.class)
public class AddPostAction extends Action<AddPostForm>{
	
	private CommentDAO commentDAO;
	private StoryDAO storyDAO;

	public AddPostAction(Model model) {
		super(model);
		this.commentDAO = model.getCommentDAO();
		this.storyDAO = model.getStoryDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		String contextPath = request.getContextPath();
		AddPostForm form = formBeanFactory.create(request);
		request.setAttribute("form", form);
		// This request does not need to go JSP page.
		if(!form.isPresent()){
			errors.add("Invaid request.");
			return "error.jsp";
		}
		// the validation errors are: invalid storyId, empty comment content, invalid type 
		// TODO where to return when the content is empty?
		errors.addAll(form.getValidationErrors());
		if(errors.size()>0){
			return "error.jsp";
		}
		// Get the current login user
		User user = (User)request.getSession().getAttribute("user");
		// This type should be validated by the form's validation.
		String reqType = form.getType();
		
		// Post comment request
		if(AddPostForm.COMMENT_TYPE.equals(reqType)){
			Comment c = new Comment();
			c.setStoryId(form.getStoryIdAsInt());
			c.setContent(form.getContent());
			c.setDate(new Date());
			c.setOwnerId(user.getId());
			// TODO, this might be low efficiency, but the logic is right.
			c.setStoryOwnerId(storyDAO.read(form.getStoryIdAsInt()).getOwnerId());
			c.setOwnerFirstname(user.getFirstname());
			c.setOwnerLastname(user.getLastname());
			c.setPicId(user.getProfilePicId());
			commentDAO.createAutoIncrement(c);
			messages.add("Adding comment succeeded.");
			return contextPath+"/home.do";
		}
		
		// Post Story request
		if(AddPostForm.STORY_TYPE.equals(reqType)){
			Story s = new Story();
			s.setContent(form.getContent());
			s.setDate(new Date());
			s.setOwnerId(user.getId());
			s.setOwnerFirstname(user.getFirstname());
			s.setOwnerLastname(user.getLastname());
			s.setPicId(user.getProfilePicId());
			storyDAO.createAutoIncrement(s);
			messages.add("Adding story succeeded.");
			return contextPath+"/home.do";
		}
		
		//Should not be processed to here.
		return contextPath+"/home.do";
	}
	
	

}
