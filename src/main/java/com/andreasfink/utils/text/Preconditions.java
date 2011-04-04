package com.andreasfink.utils.text;

/**
 * @author af@andreasfink.com
 *
 */
public class Preconditions {
	
	/**
	 * Throws {@link IllegalArgumentException} if key is empty
	 * @param string
	 * @return
	 */
	public static String notEmpty(final String string) {
		if (0==string.length()) {
			throw new IllegalArgumentException("String shall not be empty");
		}
		
		return string;
	}

}
