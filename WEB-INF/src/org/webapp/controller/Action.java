package org.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBean;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import org.webapp.controller.annotations.ActionName;
import org.webapp.controller.annotations.FormClassType;
import org.webapp.model.Model;

/**
 * 
 * @author Administrator
 * @param <FT> the form bean class needed. If given and not equals to the formClassType annotation assigned class
 * then ClassCastException may be thrown.
 * @thrown ClassCastException
 */
public abstract class Action<FT extends FormBean> {

	public abstract String perform(HttpServletRequest request)
			throws RollbackException, FormBeanException;

	// The list used to hold error messages.
	protected final List<String> errors = new ArrayList<String>();

	// The list used to hold general messages.
	protected final List<String> messages = new ArrayList<String>();

	// The class must hold a model as operation context.
	protected final Model model;

	// The class must hold a FormBeanFactory to produce form beans
	protected FormBeanFactory<FT> formBeanFactory;

	// ****FAIL****
	// If the class variable can be set to be a copy of each thread, then this is ok. 
	// The class must have a FormBean that of the specified type by class
	// signature
	// this form should be one for each thread
	// protected FT form;
	
	/**
	 * The constructor of this abstract class reads the annotation on sub class to find
	 * the form bean class needed. Then construct formBeanFactory according to bean class.
	 * 
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	public Action(Model model) {
		this.model = model;
		FormClassType fct = this.getClass().getAnnotation(FormClassType.class);
		if(fct==null){
			System.out.println("INFO:"+this.getClass().getName()+" declared no FormBean class annotation.");
		}else{
			Class<FT> cls = (Class<FT>) fct.value();
			formBeanFactory = FormBeanFactory.getInstance(cls);
		}
	}

	protected FormBeanFactory<FT> getFormBeanFactory() {
		return formBeanFactory;
		
	}

	/**
	 * The sub class call this method to add a message to the error message
	 * list.
	 * 
	 * @param error
	 *            message string
	 */
	protected void addError(String err) {
		// Avoid adding null string to the list
		if (err != null)
			errors.add(err);
	}

	//
	// Class methods to manage dispatching to Actions
	// 
	private static Map<String, Action<? extends FormBean>> hash = new HashMap<String, Action<? extends FormBean>>();

	/**
	 * This method reads from action the ActionName annotation to know the name of the action.
	 * @param a
	 */
	public static void add(Action<?> a) {
		ActionName anno = a.getClass().getAnnotation(ActionName.class);

		if (anno == null || anno.value().length()==0) {
			throw new AssertionError(
					"Action should always be annonated with name:"
							+ a.getClass());
		}
		synchronized (hash) {
			// what if the action already in the list?
			hash.put(anno.value(), a);
		}
	}

	public static String perform(String name, HttpServletRequest request) {
		//Set the servlet action name
		
		Action<?> a;
		synchronized (hash) {
			a = hash.get(name);
		}
		if (a == null)
			return null;
		// When new request coming, clear the messages lists
		a.errors.clear();
		a.messages.clear();
		String result = null;
		// Add messages lists to the request
		request.setAttribute("errors", a.errors);
		request.setAttribute("messages", a.messages);
		try {
			result = a.perform(request);
		} catch (RollbackException e) {
			a.errors.add(e.getMessage());
			e.printStackTrace();
			return "error.jsp";
		} catch (FormBeanException e) {
			e.printStackTrace();
			a.errors.add(e.getMessage());
			return "error.jsp";
		} finally{
			if(Transaction.isActive()){
				Transaction.rollback();
			}
		}
		return result;
	}
}
