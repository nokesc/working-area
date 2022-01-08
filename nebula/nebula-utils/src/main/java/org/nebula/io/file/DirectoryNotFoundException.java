package org.nebula.io.file;

import java.io.File;

public class DirectoryNotFoundException extends FileNotFoundRuntimeException {

	private static final long serialVersionUID = 1L;

	public DirectoryNotFoundException(File file) {
		super(file);
	}
}
