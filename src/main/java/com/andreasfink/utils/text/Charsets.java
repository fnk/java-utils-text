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
import java.io.InputStream;

import org.mozilla.universalchardet.UniversalDetector;

import com.google.common.io.Closeables;

/**
 * Detect charsets using <a href="http://code.google.com/p/juniversalchardet/">http://code.google.com/p/juniversalchardet/</a><br>
 * In cases of UTF-8/16 where the content is short and no BOM prepended <code>null</code> might be returned.
 * You want to check for <code>null</code> and supply a default encoding in that case.
 * 
 * TODO create maven project for juniversalchardet, its sources are included here for now.
 * 
 * @author oss@andreasfink.com
 */
public class Charsets {

    public static String detectCharset(final File file) {
        final FileInputStream fis;
        
        try {
            fis = new FileInputStream(file);
        } catch (final FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to detect charset!", e);
        }
        
        final String encoding = detectCharset(fis);
        Closeables.closeQuietly(fis);

        return encoding;
    }

    public static String detectCharset(final InputStream stream) {
        final byte[] buf = new byte[4096];
        final UniversalDetector detector = new UniversalDetector(null);

        try {
            int nread;
            while ((nread = stream.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
        } catch (final Exception e) {
            throw new RuntimeException("Unable to detect charset!", e);
        }

        final String encoding = detector.getDetectedCharset();
        detector.reset();

        return encoding;
    }

}
