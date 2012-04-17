/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.FriendshipDAO;
import org.webapp.dao.UserDAO;
import org.webapp.entity.Friendship;
import org.webapp.entity.User;
import org.webapp.formbean.IdForm;
import org.webapp.model.Model;

@ActionName("addFriend.do")
@FormClassType(IdForm.class)
public class AddFriendAction extends Action<IdForm>{
	
	private FriendshipDAO friendshipDAO;
	private UserDAO userDAO;
	
	public AddFriendAction(Model model) {
		super(model);
		this.friendshipDAO = model.getFriendshipDAO();
		this.userDAO = model.getUserDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		IdForm form = formBeanFactory.create(request);
		errors.addAll(form.getValidationErrors());
		System.out.println(errors.size());
		if(errors.size()>0){
			return "search.jsp";
		}
		// Get the current login user, do the friendship check
		User currentUser = (User)request.getSession().getAttribute("user");
		//The user can't add himself as friend
		if(currentUser.getId()==form.getIdAsInt()){
			errors.add("User: can't add self as friend.");
			return "search.jsp";
		}
		//Check whether the friend to be added exists.
		int dstId = form.getIdAsInt();
		User friend = userDAO.read(dstId);
		if(friend==null){
			errors.add("The UserID:"+dstId+" does not exists.");
			return "search.jsp";
		}
		// Check whether the relationship already exists.
		int srcId = currentUser.getId();
		Friendship fship = friendshipDAO.readByIds(srcId, dstId);
		if(fship!=null){
			errors.add("The login user already friend with user:"+dstId);
			return "search.jsp";
		}
		// Create the friendship.
		fship = new Friendship();
		fship.setSrcId(srcId);
		fship.setCreateTime(new Date());
		fship.setSrcId(currentUser.getId());
		fship.setDstId(form.getIdAsInt());
		friendshipDAO.createAutoIncrement(fship);
		
		//Redirect to the home action and refresh the friend list.
		String webapp = request.getContextPath();
		return webapp + "/home.do";
	}
}