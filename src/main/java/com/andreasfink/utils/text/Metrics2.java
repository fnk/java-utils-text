package com.andreasfink.utils.text;

import java.io.File;
import java.io.FileInputStream;

import com.google.common.io.Closeables;
import com.google.typography.font.sfntly.*;
import com.google.typography.font.sfntly.table.core.HorizontalMetricsTable;

/**
 * Sfntly version of {@link Metrics}
 * 
 * @author af@andreasfink.com
 *
 */
public class Metrics2 {
	
	private final Font _font;
	
	public Metrics2(final File fontFile, final int fontSize) {
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(fontFile);
			final Font[] f = FontFactory.getInstance().loadFonts(fis);
			
			_font = f[0];
		} catch (final Throwable t) {
			throw new RuntimeException("could not load font", t);
		} finally {
			Closeables.closeQuietly(fis);
		}
	}
	
	public int getWidth(final String text) {
		final HorizontalMetricsTable hmtx = _font.getTable(Tag.hmtx);
		
		
		//return hmtx.
		return 0;//TODO
	}

}
