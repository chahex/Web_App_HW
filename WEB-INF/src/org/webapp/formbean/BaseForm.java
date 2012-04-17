/** Xinkai He, xinkaih@andrew.cmu.edu, 46-864, March 24 2012 */
package org.webapp.formbean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybeans.form.FormBean;
import org.webapp.formbean.annotations.ValCheck;

public abstract class BaseForm extends FormBean {

	// Pointer to the class
	private Class<? extends BaseForm> thisClass = this.getClass();

	// Used to store whether a subclass FormBean has its getters cached in the
	// static map
	private static Map<Class<? extends BaseForm>, Boolean> METHOD_FILLED_STATS_MAP = new HashMap<Class<? extends BaseForm>, Boolean>();

	// Used to store for specific form bean class a list of Methods that should
	// not be empty
	private static Map<Class<? extends BaseForm>, List<Method>> METHOD_CHECK_LIST_MAP = new HashMap<Class<? extends BaseForm>, List<Method>>();

	/**
	 * 
	 * @param cls
	 * @return
	 * @throws NullPointerException
	 *             if the input parameter is null.
	 */
	private static boolean isClassMethodsFilled(Class<? extends BaseForm> cls) {
		if (cls == null)
			throw new NullPointerException("Input class should not be null.");
		Boolean bool = METHOD_FILLED_STATS_MAP.get(cls);
		// If not filled before, treat as unfilled
		if (bool == null)
			return false;
		return bool;
	}

	/**
	 * The validation assume all the return values of the FormBean methods.
	 * Normally getters. But all methods with Annotation of ValCheck will be
	 * processed.
	 * 
	 * <br>
	 * Note: this method will not decide what to check with each method. So it
	 * only sees whether the annotation exists.
	 * 
	 * @param cls
	 * @param mList
	 * @throws AssertionError
	 *             if method annotated has return type other than String
	 */
	private static void fillMethods(Class<? extends BaseForm> cls) {
		if (cls == null)
			throw new NullPointerException("Input params should not be null.");
		// Get all the methods
		Method[] mds = cls.getDeclaredMethods();
		// Now looking for methods with annotation, and put them into the list.
		List<Method> mList = new ArrayList<Method>();
		for (Method m : mds) {
			ValCheck a = m.getAnnotation(ValCheck.class);
			// System.out.println(Arrays.toString(m.getDeclaredAnnotations()));
			// System.out.println("Annotation found:"+a+" for method:"+m);
			// If the annotation exists
			if (a != null) {
				// In fact, this method could work for other method that is not
				// begin with Getter too.
				// But only return type of String could be validate. This is
				// basic assumption.
				if (m.getReturnType() != java.lang.String.class) {
					StringBuilder sb = new StringBuilder();
					sb.append(cls.getName())
							.append('.')
							.append(m.getName())
							.append(" could not be added to validation list due to return type not String");
					throw new AssertionError(sb.toString());
				}
				mList.add(m);
			}
			METHOD_CHECK_LIST_MAP.put(cls, mList);
			METHOD_FILLED_STATS_MAP.put(cls, true);
		}
	}

	private List<Method> getMethodCheckList(Class<? extends BaseForm> cls) {
		if (cls == null) {
			return null;
		}
		return METHOD_CHECK_LIST_MAP.get(cls);
	}

	/*
	 * **********Separation line between static methods and Class
	 * methods**************
	 */

	/**
	 * Sub classes must implement this method to provide interface getting error
	 * messages.
	 */
	@Override
	public abstract List<String> getValidationErrors();

	/**
	 * By default, the method returns stats of the current class.
	 * 
	 * @return
	 */
	protected boolean isMethodsFilled() {
		return isClassMethodsFilled(thisClass);
	}

	/**
	 * Pass false to fillGettersMethod(boolean), normal running.
	 */
	protected void fillMethodsCheckList() {
		fillMethodsCheckList(false);
	}

	/**
	 * @param force
	 *            , if true the method will fill the method disregard of whether
	 *            it is filled before <br>
	 *            this could be useful when there is a need to fill the method
	 *            again.
	 */
	protected void fillMethodsCheckList(boolean force) {
		// Will return the child class
		// If the method already filled for this class and this is not a force
		// operation, then exit
		if (isClassMethodsFilled(thisClass) && !force)
			return;
		fillMethods(thisClass);
	}// end of fillMethodsCheckList

	protected List<Method> getMethodsCheckList() {
		return getMethodCheckList(thisClass);
	}

	private Object[] emptyObjectArray = new Object[] {};

	/**
	 * Traverse all the method in the Method List, Check it's annotation invoke
	 * on current Class and check according to annotation.
	 * 
	 * If there is a problem, add to the error list the message.
	 * 
	 * @return A list contains error messages generated. NOTE: this validate will not assure whether the form is present or not. 
	 * It once return empty list when form is absent, but this is ambiguous as when no error found also returned empty list.
	 * 
	 */
	protected List<String> validate() {
		// Build the error message list.
		List<String> errors = new ArrayList<String>();

		//Gone. Mar 20
		// If the form is present, then consider this is a new request.
		// Jump the validation
//		if (!this.isPresent()) {
//			return errors;
//		}
		if (!isMethodsFilled()) {
			this.fillMethodsCheckList();
		}
		List<Method> mList = this.getMethodsCheckList();

		for (Method m : mList) {
			// Get the annotation from Method
			ValCheck a = m.getAnnotation(ValCheck.class);
			if (a == null) {
				continue;
			}
			String s = null;
			try {
				s = (String) m.invoke(this, emptyObjectArray);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			// due to the capacity limit of dao, ...
			if(s.length()>255){
				s = s.substring(0, 254);
			}
			if (s == null) {
				StringBuilder sb = new StringBuilder();
				sb.append(a.valName().length()==0?"Param":a.valName()).append(" can't be null.");
				errors.add(sb.toString());
			} else {
				// The value should be trimmed in the bean.
				s = s.trim();
				if (a.isNotEmpty() && s.length() == 0) {
					StringBuilder sb = new StringBuilder();
					sb.append(a.valName()).append(' ')
							.append(a.emptyErrorMsg()).append('.');
					errors.add(sb.toString());
					// next check item
					continue;
				}
				if (a.isValidChar()) {
					String pattern = a.invalidPattern();
					if (s.matches(pattern)) {
						StringBuilder sb = new StringBuilder();
						sb.append(a.valName()).append(' ')
								.append(a.invalidCharMsg()).append('.');
						errors.add(sb.toString());
						continue;
					}
				}
				//Number format check
				if (a.isInteger()) {
					try{
						Integer.parseInt(s);
					}catch(NumberFormatException e){
						//If the message got be shown in the browser and have something came from user, have to sanite it.
						errors.add(new StringBuilder().append(trimAndConvert(s, "\"*&<>")).append(":").append(a.numFmtErrMsg()).toString());
						continue;
					}
				}
			}
		}
		return errors;
	}

	public static void main(String[] args) {
		LoginForm form = new LoginForm();
		form.setUsername("hxk<sss>   <ffff>rrr<eee>");
		form.setPassword("hahaha,>ffff");
		form.setPresent(true);
		List<String> list = form.validate();
		System.out.println(METHOD_FILLED_STATS_MAP);
		System.out.println(METHOD_CHECK_LIST_MAP);
		System.out.println(form.getMethodsCheckList());
		System.out.println(list);
	}
}
