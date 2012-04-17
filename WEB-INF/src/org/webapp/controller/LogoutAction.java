/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBean;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.model.Model;

@ActionName("logout.do")
public class LogoutAction extends Action<FormBean>{

	public LogoutAction(Model model) {
		super(model);
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		request.getSession().setAttribute("user", null);
		return request.getContextPath()+"/welcome";
	}

}
