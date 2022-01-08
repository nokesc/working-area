package org.nebula.io.file;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;


/**
 * Thrown when a File fails a {@link org.nebula.io.file.FileUtil} operation.
 */
public class FileException extends UncheckedIOException {

	private static final long serialVersionUID = 1L;

	private static final String formatMessage(String message, final File file) {
		StringBuilder sb = new StringBuilder(message).append("\n");
		describe(file, sb);
		return sb.toString();
	}

	public static final String describe(final File file) {
		StringBuilder sb = new StringBuilder();
		describe(file, sb);
		return sb.toString();
	}

	public static void describe(final File file, StringBuilder sb) {
		sb.append("file=").append(file);
		sb.append("\n\t").append("exists=").append(file.exists());
		if (file.exists()) {
			sb.append("\n\t").append("isFile=").append(file.isFile());
			sb.append("\n\t").append("totalSpace=").append(file.getTotalSpace());
			sb.append("\n\t").append("lastModified=").append(file.lastModified());
		}
	}

	public FileException(File file) {
		super(describe(file), null);
	}

	public FileException(File file, String message, IOException cause) {
		super(formatMessage(message, file), cause);
	}

	public FileException(File file, IOException cause) {
		super(describe(file), cause);
	}
}
