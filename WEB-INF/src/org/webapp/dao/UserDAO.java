/**Xinkai He, Xinkaih@andrew.cmu.edu, Feb 21*/
/**
 * @version Mar 16 2012
 */
package org.webapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.genericdao.ConnectionException;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.webapp.entity.Friendship;
import org.webapp.entity.Story;
import org.webapp.entity.User;
import org.webapp.viewbean.HottestUsersView;

/**
 * Questions: 1. how to lock the database when selecting the maximum ID number
 * from the database? 2. Does the ConnectionPool provide thread safe getConn
 * method? 3. Where to put the DAO? Application Context? Where to initialize
 * them if there are multiple Servlets gonna use them? 4. What if two connection
 * tried to get to inser an item at the same time? How to get the correct rowid?
 * 5. Can the generic DAO decideds the null or unique constraint? Foreign Key?
 * 
 * Problems:
 * 
 * @update 3/24/2012 add add search username also to the searchUserByName criteria
 * <br>although it seems to me facebook don't have this criteria, added
 * 
 * @update 3/24/2012 the username search function is removed.
 * 
 * @author Administrator
 * 
 */

public class UserDAO extends BaseDAO<org.webapp.entity.User> {
	String tablename = null;

	public UserDAO(String tableName, ConnectionPool connectionPool)
			throws DAOException {
		super(User.class, tableName, connectionPool);
		this.tablename = tableName;
	}

	@Override
	public void createAutoIncrement(User user) throws RollbackException {
		super.createAutoIncrement(user);
	}

	public User readByUsername(String username) throws RollbackException {
		MatchArg match = MatchArg.equalsIgnoreCase("username", username);
		User[] users = match(match);
		// find no users, return null
		if (users.length == 0)
			return null;
		// return first element returned by the array.
		return users[0];
	}
	
	/**
	 * 
	 * This method takes an query string, split the string with the first space char range it found
	 * 
	 * @param query
	 * @return empty array if no result found, query is null or empty string
	 * @throws RollbackException 
	 */
	public User[] searchByName(String query) throws RollbackException{
		User[] result = null;
		if(query==null||(query = query.trim()).length()==0){
			result = new User[0];
			return result;
		}
		MatchArg matchArgs = null;
		//result
		//Split the query into parts by blank characters
		//Here only two parts is tried, i.e. first name and last name.
		Pattern p1 = Pattern.compile("\\s");
		// pattern to find non-space char after space
		Pattern p2 = Pattern.compile("\\S");
		Matcher m1 = p1.matcher(query);
		Matcher m2 = p2.matcher(query);
		String q1 = null;
		String q2 = null;
		//If there is space char, then use it as separator, got two query strings
		//Disregard the second space chars
		//If there are two criteria, then the two can be either first/last name
		if(m1.find()){
			q1  = query.substring(0, m1.start());
			//use m2 to find the next non-whitespace char. Since the string is trimmed before match, the 
			//next non-white char must exist
			if(!m2.find(m1.start())){
				throw new RuntimeException("System Error at when processing query string:("+query+").");
			}
			q2 = query.substring(m2.start());
			//Use the two strings as searching criteria
			//q1 begins the first name and q2 begins the last name
			//OR q1 begins the last name and q2 begins the first name
			matchArgs = MatchArg.or(
					MatchArg.and(MatchArg.startsWithIgnoreCase("firstname", q1),
							MatchArg.startsWithIgnoreCase("lastname", q2)), 
					MatchArg.and(MatchArg.startsWithIgnoreCase("lastname", q1),
							MatchArg.startsWithIgnoreCase("firstname", q2)));
		}else
			//Only one query criterion, either it's for First name, or for last name.
			matchArgs = MatchArg.or(MatchArg.startsWithIgnoreCase("lastname", query),
					MatchArg.startsWithIgnoreCase("firstname", query));
//		System.out.println("Query:"+query+", q1:("+q1+"); q2:("+q2+")");
		
		return match(matchArgs);
	}
	
	/**
	 * Return the friend users in an array according to the given friendships.
	 * This method assume all the friendship has the same srcId 
	 * 
	 * @param friendships
	 * @return null if input is null.
	 * @throws RollbackException
	 */
	public User[] readFriendsByFriendship(Friendship[] friendships) throws RollbackException{
		int size = friendships.length;
		//TODO Figure out what to return.
		if(size==0)
			return new User[0];
		int srcId = 0;
		MatchArg[] mas = new MatchArg[size];
		//Add friend's ID to the constraints list
		//form an sql like: where dstId = 1 or dstId = 2 or dstId = 3...
		for(int i = 0;i<size;i++){
			//record the srcId
			if(i==0)srcId = friendships[0].getSrcId();
			else
			if(srcId!=friendships[i].getSrcId())
				throw new AssertionError("UserDAO error, input Friendships do not have consistent srcIds. " +
						"Expected:"+srcId+", but found "+friendships[i].getSrcId()+" at No."+i+" element.");
			mas[i] = MatchArg.equals("id", friendships[i].getDstId());
		}
		MatchArg ma = MatchArg.or(mas);
		return match(ma);
	}
	
	/**
	 * Alternatively, a view in the database is better built first and then we can fetch the items from this 
	 * view. We can build an alternatively DAO called FriendViewDAO, and this DAO fetch data from the 
	 * view.
	 * 
	 * @return
	 * @throws RollbackException
	 */
	public List<HottestUsersView> readHottestUsersView() throws RollbackException{
		try {
			String userTablename = "xinkaih_user";
			String friendshipTablename = "xinkaih_friendship";
			String sql = "select u.id, u.firstname, u.lastname, u.username, count(srcId), u.profilepicid from " + 
					friendshipTablename+" right join "+userTablename+" u on u.id=srcid group by u.id, u.firstname, u.lastname, u.username, u.profilepicid order by count(srcId) desc " +
							"limit 5";
//			sql = "select u.id, u.firstname, u.lastname, u.username, count(srcid) from xinkaih_friendship right join xinkaih_user u on u.id=srcid group by u.id order by count(srcid) desc limit 5";
			Connection conn = connectionPool.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<HottestUsersView> list = new ArrayList<HottestUsersView>();
			while(rs.next()){
				HottestUsersView hv = new HottestUsersView();
				hv.setUserId(rs.getInt(1));
				hv.setFirstname(rs.getString(2));
				hv.setLastname(rs.getString(3));
				hv.setUsername(rs.getString(4));
				hv.setFriendCount(rs.getInt(5));
				hv.setProfilePicId(rs.getInt(6));
				list.add(hv);
			}
			return list;
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
//		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
//		for (StackTraceElement trace : traces) {
//			System.out.println(trace.getClassName());
//			System.err.println(trace.getMethodName());
//		}
		String url = "jdbc:mysql:///webapp";
		String driver = "com.mysql.jdbc.Driver";
		ConnectionPool pool = new ConnectionPool(driver, url);
		Transaction.begin();
		StoryDAO sdao = new StoryDAO("story", pool);
		Story s = new Story();
		String str = "1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja1fjjfjfjfjfjjfjfjfjfjfjfjjfjfjfjjfjfjfjjfjfjjfjfjfjfjjfjfjjfjfjfjfja";
		s.setContent(str);
		sdao.createAutoIncrement(s);
		Transaction.commit();
		System.out.println("S.length:"+str.length());
		UserDAO dao = new UserDAO("users", pool);
		 MatchArg match = MatchArg.equalsIgnoreCase("username", "ff1222");
		 User[] users = dao.match(match);
		 System.out.println(users.length);
//		 user = users[0];
//		 dao.get
//		 dao.createAutoIncrement(user);
//		dao.createAutoIncrement(new User("ceshi"+(new Random()).nextInt(100), "123", "san", "zhang", "gmt"));
//		dao.createAutoIncrement(new User("ceshi"+(new Random()).nextInt(100), "123", "zhang", "san", "gmt"));
//		dao.createAutoIncrement(new User("ceshi"+(new Random()).nextInt(100), "123", "sa", "zha", "gmt"));
//		dao.createAutoIncrement(new User("ceshi"+(new Random()).nextInt(100), "123", "zhansan", "asan", "gmt"));
//		dao.createAutoIncrement(new User("ceshi"+(new Random()).nextInt(100), "123", "z", "s", "gmt"));
		System.out.println(dao.getCount());
		System.out.println(Arrays.toString(dao.searchByName("     		s \t\t\tzh\t\t\t")));
		System.out.println(Arrays.toString(dao.searchByName("ZhAn    s")));
		System.out.println(Arrays.toString(dao.searchByName("    s   ZhAn    ")));
		System.out.println(Arrays.toString(dao.searchByName("      s\t\t\t\t\t\nt  \t\t\t\t   ")));
		System.out.println(Arrays.toString(dao.searchByName("      s ")));
		System.out.println(Arrays.toString(dao.searchByName(null)));
		System.out.println(dao.readHottestUsersView());
	}
}
