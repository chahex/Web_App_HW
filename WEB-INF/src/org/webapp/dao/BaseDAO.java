/**Xinkai He, Xinkaih@andrew.cmu.edu, Feb 21*/
package org.webapp.dao;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;

public abstract class BaseDAO<B> extends GenericDAO<B> {

	protected ConnectionPool connectionPool;
	
	protected String tableName;


	public BaseDAO(Class<B> beanClass, String tableName,
			ConnectionPool connectionPool) throws DAOException {
		super(beanClass, tableName, connectionPool);
		this.connectionPool = connectionPool;
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}

	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}

}
