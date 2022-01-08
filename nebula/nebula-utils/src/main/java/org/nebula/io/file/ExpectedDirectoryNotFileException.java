package org.nebula.io.file;

import java.io.File;

public class ExpectedDirectoryNotFileException extends FileException {

	private static final long serialVersionUID = 1L;

	/**
	 * Thrown when {@link File#isFile()} returns true and a directory was
	 * expected.
	 * 
	 * @param file
	 *            A file that is not a directory.
	 */
	public ExpectedDirectoryNotFileException(File file) {
		super(file, null);
	}
}
