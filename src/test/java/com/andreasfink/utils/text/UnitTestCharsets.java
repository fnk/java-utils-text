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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import junit.framework.TestCase;

/**
 *
 * @author oss@andreasfink.com
 *
 */
public class UnitTestCharsets extends TestCase {

	private final String testBase;
	final File cp1252csv;
	final File utf8csv;

	public UnitTestCharsets() {
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();

		testBase = cl.getResource("csv/").getPath();
		cp1252csv = new File(testBase, "cp1252.csv");
		utf8csv = new File(testBase, "utf8.csv");
	}

	public void testDetect() throws FileNotFoundException {
		final String charset1 = Charsets.detectCharset(cp1252csv);
		assertTrue(charset1.equals("WINDOWS-1252"));

		final String charset2 = Charsets.detectCharset(new FileInputStream(cp1252csv));
		assertTrue(charset2.equals("WINDOWS-1252"));

		final String charset3 = Charsets.detectCharset(utf8csv);
		assertTrue(charset3.equals("UTF-8"));

		final String charset4 = Charsets.detectCharset(new FileInputStream(utf8csv));
		assertTrue(charset4.equals("UTF-8"));
	}

}
