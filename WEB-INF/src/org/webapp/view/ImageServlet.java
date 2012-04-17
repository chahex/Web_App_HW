/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.webapp.entity.Photo;


/**
 * This servlet is the "view" for images.  It looks at the "photo"
 * request attribute and sends it's image bytes to the client browser.
 * 
 * We need to use a servlet instead of a JSP for the "view" of the image
 * because we need to send back the image bits and not HTML.
 */
public class ImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	Photo photo = (Photo) request.getAttribute("photo");
        if (photo == null) {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }
//        System.out.println("ImageServlet: got id:"+request.getParameter("id")+" :while photo passed has id:"+photo.getId()+" and object:"+photo);
        response.setContentType(photo.getContentType());

        ServletOutputStream out = response.getOutputStream();
        out.write(photo.getBytes());
        request.setAttribute("photo", null);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
