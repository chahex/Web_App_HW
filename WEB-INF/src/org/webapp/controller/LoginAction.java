/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.FriendshipDAO;
import org.webapp.dao.UserDAO;
import org.webapp.entity.User;
import org.webapp.formbean.LoginForm;
import org.webapp.model.Model;

/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to view the originally
 * requested photo.  If there was no photo originally requested
 * to be viewed (as specified by the "redirect" hidden form
 * value), just redirect to manage.do to allow the user to manage
 * his photos.
 */
@ActionName("login.do")
@FormClassType(LoginForm.class)
public class LoginAction extends Action<LoginForm> {
//	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory
//			.getInstance(LoginForm.class);

	private UserDAO userDAO;
	private FriendshipDAO friendshipDAO;

	public LoginAction(Model model) {
		super(model);
		userDAO = model.getUserDAO();
		friendshipDAO = model.getFriendshipDAO();
	}

	public String perform(HttpServletRequest request) throws RollbackException, FormBeanException{

		LoginForm form = formBeanFactory.create(request);
		request.setAttribute("form", form);
		//TODO get from database the friendship count
		request.setAttribute("userCount", userDAO.getCount());
		request.setAttribute("friendshipCount", friendshipDAO.getCount());
		
		// If the request contains attributes of redirectTo, then add to messages list login required info.
		String redirectTo = null;
		if((redirectTo = (String)request.getAttribute("redirectTo"))!=null){
			messages.add("Login required.");
			form.setRedirectTo(redirectTo);
		}

		// If no params were passed, return with no errors so that the form will
		// be presented (we assume for the first time).
		if (!form.isPresent()) {
			return "welcome.jsp";
		}

		// Any validation errors?
		errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			return "welcome.jsp";
		}

		// Look up the user
		User user = userDAO.readByUsername(form.getUsername());

		if (user == null) {
			errors.add("User Name not found");
			return "welcome.jsp";
		}

		// Check the password
		if (!user.checkPassword(form.getPassword())) {
			errors.add("Incorrect password");
			return "welcome.jsp";
		}

		// Attach (this copy of) the user bean to the session
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		// After successful login send to...
		if (redirectTo != null)
			return redirectTo;

		// If redirectTo is null, redirect to the "home" action
		return request.getContextPath() + "/home.do";
	}
}
