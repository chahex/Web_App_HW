/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

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

@ActionName("disFriend.do")
@FormClassType(IdForm.class)
public class DisFriendAction extends Action<IdForm>{
	
	FriendshipDAO friendshipDAO = null;
	UserDAO userDAO = null;

	public DisFriendAction(Model model) {
		super(model);
		friendshipDAO = model.getFriendshipDAO();
		userDAO = model.getUserDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		IdForm form = formBeanFactory.create(request);
		errors.addAll(form.getValidationErrors());
		if(errors.size()>0){
			return "home.jsp";
		}
		User loginUser = (User)request.getSession().getAttribute("user");
		int srcId = loginUser.getId();
		int dstId = form.getIdAsInt();
		//First, get the friend ship with the srcId and dstId
		Friendship fs = friendshipDAO.readByIds(srcId, dstId);
		if(fs==null){
			addError("The friendship with target user id:"+dstId+" does not belong to the current login user.");
			return "home.jsp";
		}
		//Remove the friendship, and redirect to the home action to refresh the friend list.
		friendshipDAO.delete(fs.getId());
		String webapp = request.getContextPath();
		return webapp + "/home.do";
	}
}
