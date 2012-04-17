/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.dao;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.webapp.entity.Friendship;

public class FriendshipDAO extends GenericDAO<org.webapp.entity.Friendship>{

	public FriendshipDAO(String tableName,
			ConnectionPool connectionPool) throws DAOException {
		super(Friendship.class, tableName, connectionPool);
	}
	
	public Friendship[] readAllBySrcId(int srcId) throws RollbackException{
		MatchArg ma = MatchArg.equals("srcId", srcId);
		return match(ma);
	}
	
	public Friendship readByIds(int srcId, int dstId) throws RollbackException {
		Friendship[] fs = match(MatchArg.and(MatchArg.equals("srcId", srcId), MatchArg.equals("dstId", dstId)));
		if(fs.length==0)
			return null;
		else
			return fs[0];
		
	}
	
}
