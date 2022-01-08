package org.nebula.io.file;

import java.io.File;

public class FileNotWritableException extends FileException {

	private static final long serialVersionUID = 1L;

	public FileNotWritableException(File file) {
		super(file);
	}
}
