/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.webapp.entity.User;
import org.webapp.model.Model;

public class Controller extends HttpServlet {
	
	public static final boolean TEST_MODE = true;
	
	private static final long serialVersionUID = -1L;

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());
		// Action.add(new ChangePwdAction(model));
		Action.add(new LoginAction(model));
		Action.add(new LogoutAction(model));
		Action.add(new RegisterAction(model));
		Action.add(new AddFriendAction(model));
		Action.add(new DisFriendAction(model));
		Action.add(new HottestUsersAjaxAction(model));
		Action.add(new HomeAction(model));
		Action.add(new ProfileUpdateAction(model));
		 Action.add(new PhotoUploadAction(model));
		 Action.add(new ImageAction(model));
		 Action.add(new SearchAction(model));
		 
		 //Post Actions
		 Action.add(new AddPostAction(model));
		 Action.add(new AddStoryAction(model));
		 Action.add(new AddCommentAction(model));
		 Action.add(new DeletePostAction(model));
		 
		 // Test Action
		 Action.add(new TestAction(model, getServletContext()));
		 
		// Set up context for register page to show the available GMT strings.
		 //This context has some problem, once removed, nobody knows how to get them back.
		ServletContext application = getServletContext();
		if (application.getAttribute("gmtIDStrings") == null) {
			String[] strs = TimeZone.getAvailableIDs();
			java.util.Arrays.sort(strs);
			application.setAttribute("gmtIDStrings", strs);
		}
		
		// set test mode to application attributes
		// not a very good way since the attributes are not immutable.
		application.setAttribute("test", TEST_MODE);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}

	/**
	 * 
	 * Extracts the requested action and (depending on whether the user is
	 * logged in) perform it (or make the user login).
	 * 
	 * @param request
	 * @return the next page (the view)
	 */

	private String performTheAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		// TODO figure out why after I used /* pattern the getServeletPath
		// returned "".
		String servletPath = request.getServletPath();
		String contextPath = request.getContextPath();
		User user = (User) session.getAttribute("user");
		String action = getActionName(servletPath);
		System.out.println("servletPath=" + servletPath + " requestURI="
				+ request.getRequestURI() + "  user=" + user+" queryString="+request.getQueryString());
//			File file = null;
//			System.out.println(file = new File(getServletContext().getResource("images/head.jpg").getFile()));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
		// Called some action that requires login. So redirect to the original
		// request after logged in.
		if("hello.do".equals(action)){
			System.out.println("The colorful egg.");
			return "hello";
		}
		if("welcome".equals(action)){
			action = "login.do";
		}
		if (user == null) {
			if ("register.do".equals(action) || "login.do".equals(action)
					|| "logout.do".equals(action) || "hottestUsersAjax.do".equals(action) || ("image.do").equals(action) || ("test.do").equals(action)) {
				// Allow these actions without logging in
				return Action.perform(action, request);
			}
			//If query string exists, then redirect with query stirng.
			request.setAttribute("redirectTo", request.getRequestURI() + ((request.getQueryString()==null)?"":"?"
					+ request.getQueryString()));
			return Action.perform("login.do", request);
		}else

		if (action.equals("welcome") || ("register.do").equals(action) || ("login.do".equals(action))) {
			// If he's logged in but back at the /start page, send him to home
			return contextPath+"/home.do";
		}

		// Let the logged in user run his chosen action
		return Action.perform(action, request);
	}

	/**
	 * 
	 * If nextPage is null, send back 404 If nextPage starts with a '/',
	 * redirect to this page. In this case, the page must be the whole servlet
	 * path including the webapp name Otherwise dispatch to the page (the view)
	 * This is the common case
	 * 
	 * Note: If nextPage equals "image", we will dispatch to /image. In the
	 * web.xml file, "/image"
	 * 
	 * is mapped to the ImageServlet will which return the image bytes for
	 * display.
	 */

	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// egg
		if("hello".equals(nextPage)){
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/hello.jsp");
			rd.forward(request, response);
			return;
		}
		
		// System.out.println("nextPage="+nextPage);
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					request.getServletPath());
			return;
		}

		if (nextPage.charAt(0) == '/') {
			String host = request.getServerName();
			String port = ":" + String.valueOf(request.getServerPort());
			if (port.equals(":80"))
				port = "";
			response.sendRedirect("http://" + host + port + nextPage);
			return;
		}
		RequestDispatcher d = request.getRequestDispatcher(nextPage);
		d.forward(request, response);
	}

	/*
	 * 
	 * Returns the path component after the last slash removing any "extension"
	 * 
	 * if present.
	 */

	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}
}
