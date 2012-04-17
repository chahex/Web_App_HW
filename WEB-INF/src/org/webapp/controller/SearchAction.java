/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.UserDAO;
import org.webapp.entity.User;
import org.webapp.formbean.QueryForm;
import org.webapp.model.Model;

@ActionName("search.do")
@FormClassType(QueryForm.class)
public class SearchAction extends Action<QueryForm>{
	
	UserDAO userDAO = null;

	public SearchAction(Model model) {
		super(model);
		this.userDAO = model.getUserDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		QueryForm form = formBeanFactory.create(request);
		request.setAttribute("form", form);
		if(!form.isPresent()){
			return "search.jsp";
		}
		errors.addAll(form.getValidationErrors());
		if(errors.size()>0)
			return "search.jsp";
		String query = form.getQuery();
		User[] result = (User[])userDAO.searchByName(query);
		request.setAttribute("result", result);
		return "search.jsp";
	}
}
