/**
 * Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Feb 06, 2012
 */
package org.xinkaih.util;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 */
public class StringUtil {

	public static final String[] DEFAULT_INVALID_STRINGS = new String[] { "<",
			">", "&", "/", "\"" };

	/**
	 * Convert: <,>,&, /, " Used when putting string value directly to HTML or
	 * JSP page
	 * 
	 * @param str
	 * @return
	 */
	public static String avoidHtmlHack(String str) {

		if (str != null) {
			str = str.replace("&", "&amp;").replace("<", "&lt;")
					.replace(">", "&gt;").replace("/", "&#47;")
					.replace("\"", "&quot;");
		}
		return str;
	}

	public static boolean containsInvalidChars(String str) {
		if (str == null) {
			return containsInvalidChars(str, DEFAULT_INVALID_STRINGS);
		}

		return false;
	}

	public static boolean containsInvalidChars(String input,
			String[] invalidStrings) {
		if (invalidStrings == null) {
			// we can either return true or return the result with default
			// string array
			return false;
		}
		for (String s : invalidStrings) {
			if (input.contains(s)) {
				return true;
			}
		}
		return false;
	}

	public static String sanite(String input) {
		return avoidHtmlHack(input);
	}

	public static boolean inputNotEmptyCheck(String[] paramNames,
			String[] paramVals, List<String> errlist) {
		boolean checked = true;
		for (int i = 0; i < paramVals.length; i++) {
			if (paramVals[i] == null || paramVals[i].length() == 0) {
				checked = false;
				errlist.add("Input " + paramNames[i] + " is Empty");
			}
		}
		return checked;
	}

	/**
	 * Get parameter values from parameter map, according to the param names
	 * given by the string array. Then do the not null check. During the check,
	 * the param read will be trimmed, and if the size after trim is 0, it will
	 * be treated as empty.
	 * 
	 * The other useful feature could be added will be normalize the check. Put
	 * all the check in a stack and call them sequentially.
	 * 
	 * @param paramNames
	 *            , the key to get values
	 * @param paramDesc
	 *            , the description of the parameter
	 * @param errlist
	 *            , the string list holds all the errors during the not null
	 *            check. Not null please.
	 */

	public static String[] inputCheck(Map<String, String[]> paramMap,
			String[] paramNames, String[] paramDesc, List<String> errlist) {
		int size = paramNames.length;
		String[] paramVals = new String[size];
		for (int i = 0; i < size; i++) {
			//TODO How to fix this problem?
			String str = paramMap.get(paramNames[i])[0];
			if (str == null || str.trim().length() == 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(paramDesc[i]).append(" can't be empty.");
			}
		}
		return paramVals;
	}
	
	/**
	 * Or we could return the boolean as well.
	 * 
	 * @param paramMap
	 * @param paramNames
	 * @param paramDesc
	 * @param paramVals
	 * @param errlist
	 * @return
	 */
    public static boolean inputCheck(Map<String, String[]> paramMap,
            String[] paramNames, String[] paramDesc, String[] paramVals, List<String> errlist) {
        boolean checked = true;
        int size = paramNames.length;
        for (int i = 0; i < size; i++) {
            String str = paramMap.get(paramNames[i])[0];
            if (str == null || str.trim().length() == 0) {
                checked = false;
                StringBuilder sb = new StringBuilder();
                sb.append(paramDesc[i]).append(" can't be empty.");
            }
        }
        return checked;
    }
}
