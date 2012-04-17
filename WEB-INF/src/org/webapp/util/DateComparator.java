/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.util;

import java.util.Comparator;
import java.util.Date;


public class DateComparator implements Comparator<HasDate>{
	
	public final static String ASCENDING = "asc";
	
	public final static String DESCENDING = "desc";
	
	private String order;
	
	public DateComparator(){
	}
	
	/**
	 * 
	 * If input asc, then if Date1>Date2, -1 will return. Else, 1 will return. Others will have this too.
	 * 
	 * @param order either "asc" or "desc"
	 */
	public DateComparator(String order){
		if(order==null){
			// Default is aescending
			return;
		}
		this.order = order;
	}

	@Override
	public int compare(HasDate o1, HasDate o2) {
		Date d1 = o1.getDate();
		Date d2 = o2.getDate();
		if(d1==null&&d2==null)
			return 0;
		if(order==null||ASCENDING.equals(order)){
			if(d2==null){
				return 1;
			}
			if(d1==null&&d2!=null)
				return -1;
			if(d1!=null&&d2==null)
				return 1;
			return d1.compareTo(d2);
		}else{
			if(d1==null)
				return 1;
			if(d1!=null&&d2==null)
				return -1;
			return d2.compareTo(d1);
		}
	}
}
