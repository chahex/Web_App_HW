/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.util.ArrayList;

import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBean;

/**
 * I just copied this file.
 * 
 * @author Administrator
 *
 */
public class UploadForm extends FormBean {
	private String button     = "";
	private String caption    = "";
	private FileProperty file = null;
	
	public static int FILE_MAX_LENGTH = 1024 * 1024;
	
	public String       getButton()         { return button;         }
	public FileProperty getFile()           { return file;           }
	public String       getCaption()        { return caption;        }

	public void setButton(String s)         { button      = s;        }
	public void setCaption(String s)        { caption     = trimAndConvert(s,"<>\""); }
	public void setFile(FileProperty file)  { this.file   = file;     }
	
	public ArrayList<String> getValidationErrors() {
		ArrayList<String> errors = new ArrayList<String>();
		
		if (file == null || file.getFileName().length() == 0) {
			errors.add("You must provide a file");
			return errors;
		}

		if (file.getBytes().length == 0) {
			errors.add("Zero length file");
		}
		
		return errors;
	}
}
