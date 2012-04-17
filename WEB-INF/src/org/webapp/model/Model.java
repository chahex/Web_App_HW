/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.webapp.dao.CommentDAO;
import org.webapp.dao.FriendshipDAO;
import org.webapp.dao.StoryDAO;
import org.webapp.dao.UserDAO;


public class Model {
	private UserDAO   userDAO;
	private FriendshipDAO friendshipDAO;
	private PhotoDAO photoDAO;
	private StoryDAO storyDAO;
	private CommentDAO commentDAO;

	private String  localNetAddr;
	private String  localNetPrefix;
	private boolean requireSSL;

	public Model(ServletConfig config) throws ServletException {
		localNetAddr   = config.getInitParameter("localNetAddr");
        localNetPrefix = config.getInitParameter("localNetPrefix");
        requireSSL     = new Boolean(config.getInitParameter("requireSSL"));

		String jdbcDriver = config.getInitParameter("jdbcDriverName");
	    String jdbcURL    = config.getInitParameter("jdbcURL");
	    
	    try {
		    ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
	    	userDAO  = new UserDAO(config.getInitParameter("user_table"), pool);
	    	friendshipDAO = new FriendshipDAO(config.getInitParameter("friendship_table"), pool);
	    	photoDAO = new PhotoDAO(config.getInitParameter("photo_table"), pool);
	    	storyDAO = new StoryDAO(config.getInitParameter("story_table"), pool);
	    	commentDAO = new CommentDAO(config.getInitParameter("comment_table"), pool);
	    	
	    } catch (DAOException e) {
	    	throw new ServletException(e);
	    }
	}
	
	public String    getLocalNetAddr()   { return localNetAddr;   }
	public String    getLocalNetPrefix() { return localNetPrefix; }
	public boolean   getRequireSSL()     { return requireSSL;     }
	
	public UserDAO   getUserDAO()        { return userDAO;        }
	public FriendshipDAO getFriendshipDAO() {		return friendshipDAO;	}
	public PhotoDAO getPhotoDAO() {		return photoDAO;	}
	public StoryDAO getStoryDAO() {		return storyDAO;	}
	public CommentDAO getCommentDAO() {		return this.commentDAO;	}
	
}
