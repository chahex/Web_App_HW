/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.viewbean;

import org.webapp.entity.Friendship;
import org.webapp.entity.User;

/**
 * This class contains two object, the friendship and the destination friend that has this friendship. 
 * 
 * @author Administrator
 */
public class FriendView {
	
	private Friendship friendship;
	private User friend;
	
	public Friendship getFriendship() {
		return friendship;
	}
	public void setFriendship(Friendship friendship) {
		this.friendship = friendship;
	}
	public User getFriend() {
		return friend;
	}
	public void setFriend(User friend) {
		this.friend = friend;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FriendView:").append(friendship.getId()).append(":srcID:").append(friendship.getSrcId())
		.append(":dstID:").append(friendship.getDstId()).append(":dstUsername").append(friend.getUsername());
		return sb.toString();
	}
}
