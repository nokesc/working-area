package org.nebula.io.file;

import java.io.File;

public class ExpectedFileNotDirectoryException extends FileException {

	private static final long serialVersionUID = 1L;

	/**
	 * Thrown when {@link File#isDirectory()} returns true and a file was
	 * expected.
	 * 
	 * @param file
	 *            A file that is a directory.
	 */
	public ExpectedFileNotDirectoryException(File file) {
		super(file, null);
	}
}
