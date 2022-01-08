package org.nebula.io.file;

import java.io.File;

public class DirectoryNotWritableException extends FileException {

	private static final long serialVersionUID = 1L;

	public DirectoryNotWritableException(File file) {
		super(file, null);
	}
}
