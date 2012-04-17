/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.List;

import org.webapp.formbean.annotations.ValCheck;

/**
 * Pass query string for general purposes, like find friends.
 * 
 * @author Administrator
 *
 */
public class QueryForm extends BaseForm {
	
	private String query;
	
	@ValCheck(valName="Query String", isNotEmpty=true)
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		if(query!=null)
		this.query = trimAndConvert(query, "<>\"*&");
	}

	@Override
	public List<String> getValidationErrors() {
		return validate();
	}
}