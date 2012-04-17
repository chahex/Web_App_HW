/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.viewbean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.webapp.entity.Comment;
import org.webapp.entity.Story;

public class StoryView {

	private Story[] stories;
	private Map<Integer, List<Comment>> cmtMap;

	public Story[] getStories() {
		return stories;
	}

	public void setStories(Story[] stories) {
		this.stories = stories;
	}

	public Map<Integer, List<Comment>> getCmtMap() {
		return cmtMap;
	}

	public void setCmtMap(Map<Integer, List<Comment>> cmtMap) {
		this.cmtMap = cmtMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StoryView [stories=").append(Arrays.toString(stories))
				.append(", cmtMap=").append(cmtMap).append("]");
		return builder.toString();
	}
}
