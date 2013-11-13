/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is "com.andreasfink.utils" Java(TM) utilities module.
 *
 * The Initial Developer of the Original Code is
 * Andreas Fink, oss@andreasfink.com.
 * Portions created by the Initial Developer are Copyright (C) 2010
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package com.andreasfink.utils.text;

/**
 * @author oss@andreasfink.com
 */
public class Strings {
	
	private Strings() {}
	
	/**
	 * Test if string is a textual representation of a negative or positive integer or floating point type.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(final String str) {
		return str.matches("-?\\d+(.\\d+)?");
    }

	/**
	 * Removes whitespace and control characters like <code>String.trim(..)</code> and reduces multiple occurances unless at text start or end to a single space.<br>
	 * For example "\sHello\t\tWorld" becomes "Hello\sWorld".
	 * 
	 * @param text
	 * @return
	 */
	public static String cleanWhitespace(final String text) {
		final StringBuilder sb = new StringBuilder();

		boolean lastCharIsWhitespace = false;
		for (int i=0; i<text.length(); i++) {
			final char c = text.charAt(i);

			if (Character.isWhitespace(c)) {
				if (!lastCharIsWhitespace && i!=0 && i<(text.length()-1)) {
					sb.append(" ");
				}
				lastCharIsWhitespace = true;
			} else {
				sb.append(c);
				lastCharIsWhitespace = false;
			}
		}

		return sb.toString();
	}
	
	public static String removePunctuation(final String text) {
		return text.replaceAll("\\p{Punct}+", "");
	}

}
