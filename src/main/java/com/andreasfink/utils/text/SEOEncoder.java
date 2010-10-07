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

import java.text.Normalizer;
import java.util.BitSet;

/**
 * String encoding for SEO purposes, for example beautiful urls or wiki-words.
 *
 * @author oss@andreasfink.com
 */
public class SEOEncoder {

    private static final BitSet dontReplace;
    private static final String[] replace;

    static {
        dontReplace = new BitSet(256);
        
        for (int i = 'a'; i <= 'z'; i++) {
            dontReplace.set(i);
        }
        
        for (int i = 'A'; i <= 'Z'; i++) {
            dontReplace.set(i);
        }
        
        for (int i = '0'; i <= '9'; i++) {
            dontReplace.set(i);
        }
        
        dontReplace.set('-');
        dontReplace.set('_');
        dontReplace.set('+');

        replace = new String[0xFFFF]; // UTF-8/16 assumed as maximum
        replace[(int) '\u00c4'] = "Ae";
        replace[(int) '\u00d6'] = "Oe";
        replace[(int) '\u00dc'] = "Ue";
        replace[(int) '\u00e4'] = "ae";
        replace[(int) '\u00f6'] = "oe";
        replace[(int) '\u00fc'] = "ue";
        replace[(int) '\u00df'] = "ss";
        replace[(int) '&'] = "+";
        replace[(int) ' '] = "_";
        replace[(int) '/'] = "-";
    }

    private SEOEncoder() {}

    /**
     * German Umlauts and Eszett are collated as in phonebooks (e.g.: Ä to Ae).<br>
     * & is converted to +, whitespace to _, / to -.<br>
     * All other chars except A-Z, a-z, 0-9, -, _, + are discarded.
     *
     * @param text
     * @return
     */
    public static String encodeGermanFast(final String text) {
        final StringBuilder out = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            final int c = (int) text.charAt(i);

            if (dontReplace.get(c)) {
                out.append((char) c);
            } else if (replace[c] != null) {
                out.append(replace[c]);
            }
        }

        return out.toString();
    }

    /**
     * Replaces all charactars unsuitable for URLs with logical alternatives using <code>java.text.Normalizer</code><br>
     * 
     * TODO take care of tapestries url-encoding & -> + -> $002b
     * 
     * @param text
     * @return
     */
    public static String encodeUnicode(final String text) {
    	final String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
    	final String withoutDiacritics = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    	final String encoded = withoutDiacritics
    		.replace('&', '+')
    		.replace('/', '-')
    		.replaceAll("[^\\p{Alnum}-\\+]+", "_");
    	final String trimmed = (encoded.endsWith("_"))
    		? encoded.substring(0, encoded.length()-1)
    		: encoded;

    	return trimmed;
    }

}