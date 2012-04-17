/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.List;

import org.webapp.formbean.annotations.ValCheck;

public class IdForm extends BaseForm {

	private String id;

	@ValCheck(isInteger = true, valName = "Target Id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null)
			this.id = id.trim();
	}

	/**
	 * 
	 * @return
	 * @throws NumberFormatException. But
	 *             you can get the error from getValidationErrors before head.
	 */
	public int getIdAsInt() {
		return Integer.parseInt(id);
	}

	@Override
	public List<String> getValidationErrors() {
		return validate();
	}
}