/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.webapp.entity.Comment;
import org.webapp.entity.Story;
import org.webapp.util.DateComparator;

public class CommentDAO extends GenericDAO<Comment>{

	public CommentDAO(String tableName,
			ConnectionPool connectionPool) throws DAOException {
		super(Comment.class, tableName, connectionPool);
	}
	
	/**
	 * Return a map that holds comments for specified stories.
	 * key is the story id, value is the list that holds the comment, in cornological order.
	 * 
	 * @param stories
	 * @return the comment list are sorted ascendingly
	 * @throws RollbackException 
	 */
	public Map<Integer, List<Comment>> readCommentsByStories(Story[] stories) throws RollbackException{
		Map<Integer, List<Comment>> map = new HashMap<Integer, List<Comment>>();
		//Not empty check
		if(stories==null||stories.length==0){
			return map;
		}
		int size = stories.length;
		// The match arguments should be as long as the input stories size
		MatchArg[] mas = new MatchArg[size];
		for(int i = 0;i<size;i++){
			mas[i] = MatchArg.equals("storyId", stories[i].getId());
		}
		MatchArg ma = MatchArg.or(mas);
		Comment[] cmts = match(ma);
		//Sorting ascendingly.
		Arrays.sort(cmts, new DateComparator(DateComparator.ASCENDING));
		for(Comment c:cmts){
			int storyId = c.getStoryId();
			List<Comment> l = map.get(storyId);
			if(l==null)
				l = new ArrayList<Comment>();
			//Put the comment into the comment list belongs to this story.
			l.add(c);
			map.put(storyId, l);
		}
		return map;
	}
}
