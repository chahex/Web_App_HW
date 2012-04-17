/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.dao.UserDAO;
import org.webapp.entity.Photo;
import org.webapp.entity.User;
import org.webapp.formbean.UploadForm;
import org.webapp.model.Model;
import org.webapp.model.PhotoDAO;

/**
 * This photo upload only deal with profile picture upload. So it did two things: 
 * 1. create the photo
 * 2. update the login user profile.
 * Sounds weird, hmm
 * 
 * @author Administrator
 *
 */
@ActionName("photoUpload.do")
@FormClassType(UploadForm.class)
public class PhotoUploadAction extends Action<UploadForm>{
	
	private PhotoDAO photoDAO = null;
	private UserDAO userDAO = null;
	
	public PhotoUploadAction(Model model) {
		super(model);
		this.photoDAO = model.getPhotoDAO();
		this.userDAO = model.getUserDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		//When there is no file uploaded.
		String conType = request.getContentType();
		if(conType==null||!conType.startsWith("multipart/form-data")){
			errors.add("Content Type should be mutlipart/form-data while got:"+conType);
			return "error.jsp";
		}
		UploadForm form = formBeanFactory.create(request);
		errors.addAll(form.getValidationErrors());
		if(errors.size()>0){
			return "error.jsp";
		}
		User user = (User)request.getSession().getAttribute("user");
		Transaction.begin();
		//Create the photo, then get the photo ID and give it back to the user.
		Photo p = new Photo();
		p.setContentType(form.getFile().getContentType());
		p.setBytes(form.getFile().getBytes());
		photoDAO.createAutoIncrement(p);
		int pid = p.getId();
		//Set the profile picture id to the user.
		user.setProfilePicId(pid);
		userDAO.update(user);
		Transaction.commit();
		//Uploaded completed. Return to the profile update page.
		//TODO this should 
		return request.getContextPath()+"/profileUpdate.do";
	}
}
