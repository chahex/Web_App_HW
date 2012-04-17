/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;


import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.UserDAO;
import org.webapp.entity.User;
import org.webapp.formbean.RegisterForm;
import org.webapp.model.Model;

@ActionName("register.do")
@FormClassType(RegisterForm.class)
public class RegisterAction extends Action<RegisterForm>{

//	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory
//			.getInstance(RegisterForm.class);
	UserDAO userDAO = null;
	
	public RegisterAction(Model model) {
		super(model);
		userDAO = model.getUserDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		RegisterForm form = formBeanFactory.create(request);
		request.setAttribute("form", form);
		request.setAttribute("gmtIDStrings", request.getServletContext().getAttribute("gmtIDStrings"));
		if(!form.isPresent()){
			return "register.jsp";
		}
		errors.addAll(form.getValidationErrors());
		if(errors.size()!=0){
			return "register.jsp";
		}
		Transaction.begin();
		User user = userDAO.readByUsername(form.getUsername());
		if(user!=null){
			errors.add("User name already exists.");
			return "register.jsp";
		}
		user = new User(form.getUsername(), form.getPassword(), form.getFname(), form.getLname(), form.getTimezone());
		userDAO.createAutoIncrement(user);
		request.getSession().setAttribute("user", user);
		Transaction.commit();
		return request.getContextPath()+"/home.do";
	}
}
