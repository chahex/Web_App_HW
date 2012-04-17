/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * It's crucial important to let this annotation to be retained at Runtime.
 * Other options include class(discard after class is loaded), and source
 * (discard after compilation, eg SuppressWarning, Override)
 * 
 * @author Administrator
 *
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValCheck {
	
	public String valName();
	public boolean isNotEmpty() default true;
	public String emptyErrorMsg() default "can't be empty";
	public boolean isValidChar() default false;
	public String invalidPattern() default ".*(<|>|\"|(&gt;)|(&lt;)).*";
	public String invalidCharMsg() default "contains brakets or quot";
	public boolean isInteger() default false;
	public String numFmtErrMsg() default "is not an acceptable number";

}

