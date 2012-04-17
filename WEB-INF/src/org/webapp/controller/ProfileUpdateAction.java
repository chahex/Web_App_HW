/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.UserDAO;
import org.webapp.entity.User;
import org.webapp.formbean.ProfileUpdateForm;
import org.webapp.model.Model;

@ActionName("profileUpdate.do")
@FormClassType(ProfileUpdateForm.class)
public class ProfileUpdateAction extends Action<ProfileUpdateForm>{

	private UserDAO userDAO;
	
	public ProfileUpdateAction(Model model) {
		super(model);
		this.userDAO = model.getUserDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		ProfileUpdateForm form = formBeanFactory.create(request);
		request.setAttribute("form", form);
		User user = (User)request.getSession().getAttribute("user");
		if(!form.isPresent()){
			form.setFname(user.getFirstname());
			form.setLname(user.getLastname());
			form.setTimezone(user.getTimezone());
			form.setProfileText(user.getProfileText());
			return "profileUpdate.jsp";
		}
		errors.addAll(form.getValidationErrors());
		if(errors.size()>0){
			return "profileUpdate.jsp";
		}
		user.setTimezone(form.getTimezone());
		user.setFirstname(form.getFname());
		user.setLastname(form.getLname());
		user.setProfileText(form.getProfileText());
		userDAO.update(user);
		return request.getContextPath()+"/home.do";
	}

}
