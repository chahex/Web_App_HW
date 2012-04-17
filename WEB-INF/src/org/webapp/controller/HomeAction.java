/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.dao.CommentDAO;
import org.webapp.dao.FriendshipDAO;
import org.webapp.dao.StoryDAO;
import org.webapp.dao.UserDAO;
import org.webapp.entity.Comment;
import org.webapp.entity.Story;
import org.webapp.entity.User;
import org.webapp.model.Model;
import org.webapp.viewbean.StoryView;

@ActionName("home.do")
@SuppressWarnings("rawtypes")
public class HomeAction extends Action{
	private UserDAO userDAO = null;
	private FriendshipDAO friendshipDAO = null;
	private CommentDAO commentDAO = null;
	private StoryDAO storyDAO = null;

	public HomeAction(Model model) {
		super(model);
		this.userDAO = model.getUserDAO();
		this.friendshipDAO = model.getFriendshipDAO();
		this.commentDAO = model.getCommentDAO();
		this.storyDAO = model.getStoryDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		User loginUser = (User)request.getSession().getAttribute("user");
		int loginUserId = loginUser.getId();
		// fetch all friends of the user.
		User[] friends = userDAO.readFriendsByFriendship(friendshipDAO.readAllBySrcId(loginUserId));
		int flistSize = friends.length;
		int ids[]  = new int[flistSize+1];
		// add first element of id of current user
		ids[0] = loginUser.getId();
		// add ids to the id list.
		for(int i = 1;i<flistSize+1;i++){
			ids[i] = friends[i-1].getId();
		}
		// fetch all the stories of current user and friends
		Story[] stories = storyDAO.readByUserIds(ids);
		Map<Integer, List<Comment>> commentsMap = commentDAO.readCommentsByStories(stories);
		StoryView sv = new StoryView();
		sv.setCmtMap(commentsMap);
		sv.setStories(stories);
		//TODO Why it's session here?
		request.setAttribute("friends", friends);
		request.setAttribute("storyView", sv);
		return "home.jsp";
	}
}
