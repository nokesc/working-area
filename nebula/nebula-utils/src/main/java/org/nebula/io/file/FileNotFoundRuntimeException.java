package org.nebula.io.file;

import java.io.File;
import java.io.FileNotFoundException;

public class FileNotFoundRuntimeException extends FileException {

	private static final long serialVersionUID = 1L;

	/**
	 * Thrown by applications when a file must exist and {@link File#exists()}
	 * is false.
	 * 
	 * @param file The file that does not exist.
	 */
	public FileNotFoundRuntimeException(File file) {
		super(file);
	}

	/**
	 * Wraps a {@link FileNotFoundException}.
	 * 
	 * @param file
	 * @param fnfe
	 */
	public FileNotFoundRuntimeException(File file, FileNotFoundException fnfe) {
		super(file, fnfe);
	}
}
