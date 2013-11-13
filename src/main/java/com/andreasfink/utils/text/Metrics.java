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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Many methods use AWT-classes.<br>
 * Achtung: for performance benefit do not create instances of this class in loop bodies and make sure to cache returned values for reuse.<br>
 * Also don't forget to set <code>-Djava.awt.headless=true</code> if working on the server side.
 *
 * @author oss@andreasfink.com
 */
public class Metrics {

	private final BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	private final Graphics2D graphics = image.createGraphics();

	private final Font font;
	private final FontMetrics metrics;

	private Integer[] widthMap = new Integer[0xFFFF]; // UTF-8/16 assumed as maximum

	public Metrics(final String fontName, final int size) {
		font = new Font(fontName, Font.PLAIN, size);
		metrics = graphics.getFontMetrics(font);
	}
	
	public Metrics(final File fontFile, final int size) {
		final Font newFont;
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (final Throwable t) {
			throw new RuntimeException("could not load font", t);
		}
		
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(newFont);
		
		font = newFont.deriveFont(Font.PLAIN, size);
		metrics = graphics.getFontMetrics(font);
	}

	/**
	 * font size is in px.
	 * width is exactly the same if text is created with same font, size, style and aliasing in photoshop on the same machine
	 */
	public int getWidth(final String text) {
		int totalWidth = 0;

		for (int i = 0; i < text.length(); i++) {
			final char c = text.charAt(i);
            final int cn = (int)c;

            if (widthMap[cn]==null) {
            	widthMap[cn] = getWidth(c);
            }

            totalWidth += widthMap[cn];
		}

		return totalWidth;
	}

	/**
	 * truncates text if necessary and appends "..."
	 * 
	 * TODO make appendix configurable or just give a width as parameter in case an image is used
	 *
	 * @param text
	 * @param maxWidth
	 * @return
	 */
	public String getTruncated(final String text, final int maxWidth) {
		int currentWidth = 0;
		final StringBuilder sb = new StringBuilder();

		int i = 0;
		do {
			final char c = text.charAt(i);
            final int cn = (int)c;

            if (widthMap[cn]==null) {
            	widthMap[cn] = getWidth(c);
            }

            currentWidth += widthMap[cn];
            sb.append(c);
            i++;
		} while (currentWidth<maxWidth && i<text.length());

		if (!sb.toString().equals(text)) {
			sb.append("...");
		}

		return sb.toString();
	}

	private int getWidth(final char c) {
		final int width = metrics.stringWidth(Character.toString(c));

		return width;
	}

}
