/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.dao;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.PrimaryKey;
import org.genericdao.RollbackException;
import org.webapp.entity.Story;
import org.webapp.util.DateComparator;

@PrimaryKey("id")
public class StoryDAO extends GenericDAO<Story> {

	public StoryDAO(String tableName,
			ConnectionPool connectionPool) throws DAOException {
		super(Story.class, tableName, connectionPool);
	}
	
	/**
	 *
	 * @param ids
	 * @return sort by date, desc
	 * @throws RollbackException
	 */
	public Story[] readByUserIds(int[] ids) throws RollbackException{
		int size = ids.length;
		//TODO Figure out what to return.
		if(size==0)
			return new Story[0];
		MatchArg[] mas = new MatchArg[size];
		//Add friend's ID to the constraints list
		//form an sql like: where dstId = 1 or dstId = 2 or dstId = 3...
		for(int i = 0;i<size;i++){
			mas[i] = MatchArg.equals("ownerId", ids[i]);
		}
		MatchArg ma = MatchArg.or(mas);
		Story[] results = match(ma);
		//The stories are sorted in descending order.
		Arrays.sort(results, new DateComparator(DateComparator.DESCENDING));
		return results;
	}
}
