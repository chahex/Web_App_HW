/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.dao.UserDAO;
import org.webapp.model.Model;
import org.webapp.viewbean.HottestUsersView;

/**
 * The class needs no incoming Form or parameters.
 * 
 * @author Administrator
 *
 */
@ActionName("hottestUsersAjax.do")
@SuppressWarnings("rawtypes")
public class HottestUsersAjaxAction extends Action {
	
	UserDAO userDAO = null;

	public HottestUsersAjaxAction(Model model) {
		super(model);
		userDAO = model.getUserDAO();
	}
	
	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		List<HottestUsersView> hottestUsers = userDAO.readHottestUsersView();
		request.setAttribute("hottestUsers", hottestUsers);
		return "hottest-users-ajax.jsp";
	}
}
