package com.andreasfink.utils.text;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * A very stripped down version of this <a href="http://javacsv.sourceforge.net/com/csvreader/CsvWriter.html">com.csvreader.CsvWriter</a>.<br/>
 * The text-quoting is always <code>"</code>.<br/>
 * 
 * You write every single cell with <code>write(String content)</code> and end the row with <code>endRecord()</code>.<br/>
 * Get the CSV from the supplied writer's {@link java.io.Writer toString()} and call <code>close()</code> afterwards.
 * 
 * @author af@andreasfink.com
 *
 */
public class CsvWriter {
	
	private final PrintWriter writer;
	private final char delimiter;
	private final char textQualifier = '"';

	boolean firstColumn;

	public CsvWriter(final Writer writer, final char delimiter) {
		this.writer = new PrintWriter(writer);
		this.delimiter = delimiter;

		firstColumn = true;
	}

	public void write(String content) {
		if (null == content) {
			content = "";
		}

		if (!firstColumn) {
			writer.write(delimiter);
		}

		if (content.length() > 0) {
			content = content.trim();
		}

		boolean textQualify = false;
		if (!textQualify
				&& (content.indexOf(textQualifier) > -1
						|| content.indexOf(delimiter) > -1
						|| (content.indexOf('\n') > -1 || content.indexOf('\r') > -1)
						|| (firstColumn && content.length() > 0 && content.charAt(0) == '#') ||
				// check for empty first column, which if on its own line must
				// be qualified or the line will be skipped
				(firstColumn && content.length() == 0))) {
			textQualify = true;
		}

		if (!textQualify && content.length() > 0) {
			char firstLetter = content.charAt(0);

			if (firstLetter == ' ' || firstLetter == '\t') {
				textQualify = true;
			}

			if (!textQualify && content.length() > 1) {
				char lastLetter = content.charAt(content.length() - 1);

				if (lastLetter == ' ' || lastLetter == '\t') {
					textQualify = true;
				}
			}
		}

		if (textQualify) {
			writer.write(textQualifier);
			content = replace(content, "" + textQualifier, "" + textQualifier + textQualifier);
		}

		writer.write(content);

		if (textQualify) {
			writer.write(textQualifier);
		}

		firstColumn = false;
	}

	public void endRecord() {
		writer.println();
		firstColumn = true;
	}

	public void close() {
		writer.flush();
		writer.close();
	}

	private String replace(final String original, final String pattern, final String replace) {
		final int len = pattern.length();
		int found = original.indexOf(pattern);

		if (found > -1) {
			final StringBuffer sb = new StringBuffer();
			int start = 0;

			while (found != -1) {
				sb.append(original.substring(start, found));
				sb.append(replace);
				start = found + len;
				found = original.indexOf(pattern, start);
			}

			sb.append(original.substring(start));

			return sb.toString();
		} else {
			return original;
		}
	}

}
