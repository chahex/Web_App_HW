/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.entity.Photo;
import org.webapp.formbean.IdForm;
import org.webapp.model.Model;
import org.webapp.model.PhotoDAO;

@ActionName("image.do")
@FormClassType(IdForm.class)
public class ImageAction extends Action<IdForm>{
	
	private PhotoDAO photoDAO;
	
	public ImageAction(Model model) {
		super(model);
		this.photoDAO = model.getPhotoDAO();
	}

	@Override
	public String perform(HttpServletRequest request) throws RollbackException,
			FormBeanException {
		IdForm form = formBeanFactory.create(request);
		if(!form.isPresent()){
			errors.add("Please specify the photo with id.");
			return "error.jsp";
		}
		request.setAttribute("form", form);
		errors.addAll(form.getValidationErrors());
		if(errors.size()>0)
			return "error.jsp";
		//must return an valid type
		int id = form.getIdAsInt();
		Photo p = null;
		//Display the default photo for the profile picture
		if(id==-1){
			ServletContext app = request.getServletContext();
			p = (Photo)app.getAttribute("defaultProfilePic");
			//If the photo does not currently exist in the application context, create and store one. 
//			if(p==null){
				String rp = "images/defaulthead.jpg";
				p = createDefaultProfilePic(rp, app);
				app.setAttribute("defaultProfilePic", p);
//			}
		}else
			p = photoDAO.read(id);
		//Not necessary cause the servlet will deal with such coincidence.
//		if(p==null){
//			errors.add("The photo with ID:"+id+" could not be found.");
//			return "error.jsp";
//		}
		request.setAttribute("photo", p);
//		System.out.println("ImageAction: got id:"+id+":id from param:"+request.getParameter("id")+" :while photo passed has id:"+p.getId()+" and object:"+p);
		return "image";
	}
	
	private Photo createDefaultProfilePic(String path, ServletContext app){
		Photo p = new Photo();
		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		try{
			out = new ByteArrayOutputStream(2000);
			in = new BufferedInputStream(app.getResourceAsStream(path));
			byte[] bts = new byte[2000];
			int count = 0;
			while((count = in.read(bts))!=-1){
				out.write(bts, 0, count);
			}
			p.setBytes(out.toByteArray());
			p.setContentType("image/jpeg");
		} catch (IOException e) {
			// Shouldn't happen.
			throw new AssertionError(e);
		}finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}
	
}
