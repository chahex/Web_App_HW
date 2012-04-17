/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.entity;

import java.util.Date;

import org.genericdao.PrimaryKey;
/**
 * id that identify the friendship
 * The PK of friendship is combination of srcId and dstId
 * srcId specifies the owner of this friendship, i.e. id of that user.
 * dstId specifies the target of this friendship, i.e. id of the friend.
 * @author Administrator
 *
 */
@PrimaryKey("id")
public class Friendship {
	
	private int id;
	private int srcId = 0;
	private int dstId = 0;
	private Date createTime;
	
	public int getId() {		return id;	}
	public int getSrcId() {		return srcId;	}
	public int getDstId() {		return dstId;	}
	public Date getCreateTime() {		return createTime;	}

	public void setId(int id) {		this.id = id;	}
	public void setSrcId(int srcId) {		this.srcId = srcId;	}
	public void setDstId(int dstId) {		this.dstId = dstId;	}
	public void setCreateTime(Date createTime) {		this.createTime = createTime;	}

}
