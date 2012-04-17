/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.controller.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mybeans.form.FormBean;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormClassType {
	/**The formBean class this class uses. */
	public Class<? extends FormBean> value();
}
