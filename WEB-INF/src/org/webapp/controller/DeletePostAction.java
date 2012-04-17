/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.CommentDAO;
import org.webapp.dao.StoryDAO;
import org.webapp.entity.User;
import org.webapp.formbean.AddPostForm;
import org.webapp.formbean.DeletePostForm;
import org.webapp.model.Model;

@ActionName("deletePost.do")
@FormClassType(DeletePostForm.class)
public class DeletePostAction extends Action<DeletePostForm> {
	
	private StoryDAO storyDAO = null;
	private CommentDAO commentDAO = null;

	public DeletePostAction(Model model) {
		super(model);
		this.storyDAO = model.getStoryDAO();
		this.commentDAO = model.getCommentDAO();
	}

	/**
	 * The deletion on story will not result in cascading deletion of the comments associated to that story.
	 * This could be good or bad.
	 * 
	 */
	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		String contextPath = request.getContextPath();
		DeletePostForm form = formBeanFactory.create(request);
		if(!form.isPresent()){
			errors.add("Invalid request.");
			return "error.jsp";
		}
		errors.addAll(form.getValidationErrors());
		if(errors.size()>0)
			return "error.jsp";
		User user = (User)request.getSession().getAttribute("user");
		String reqType = form.getType();
		int ownerId = form.getOwnerIdAsInt();
		
		// delete story. User can only delete story of self.
		if((AddPostForm.STORY_TYPE).equals(reqType)){
			if(ownerId!=user.getId()){
				errors.add("Invalid request: user can only delete story of self.");
				return "error.jsp";
			}
			storyDAO.delete(form.getIdAsInt());
			messages.add("Delete completed upon request." +
					" The deletion only guarantee the extinction in DB, so don't check whether the item exists before deletion.");
			return contextPath+"/home.do";
		}
		
		// delete comment
		if((AddPostForm.COMMENT_TYPE).equals(reqType)){
			// the comment should either have the owner of current login user,
			// or has the comment's post owner Id.
			int storyOwnerId = form.getStoryOwnerIdAsInt();
			// user can delete whichever comment that owned by self
			if(ownerId==user.getId()){
				commentDAO.delete(form.getIdAsInt());
				messages.add("Delete completed upon request." +
						" The deletion only guarantee the extinction in DB, so don't check whether the item exists before deletion.");
				return contextPath+"/home.do";
			}else{
				// when the comment owner is not the current user, but the story owner is.
				if(storyOwnerId==user.getId()){
					commentDAO.delete(form.getIdAsInt());
					messages.add("Delete complete upon request." +
							" The deletion only guarantee the extinction in DB, so don't check whether the item exists before deletion.");
					return contextPath+"/home.do";
				}else
					// can't delete.
					errors.add("Operation denied.");
				return "error.jsp";
			}
		}
		// never goes here
		return null;
	}
}
