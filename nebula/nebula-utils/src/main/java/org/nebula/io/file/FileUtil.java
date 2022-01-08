package org.nebula.io.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.nebula.lang.SystemUtil;

/**
 * 
 */
public class FileUtil {

	public static final FileFilter DIR_FILTER = new FileFilter() {
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};

	public static final File WORKING_DIR = getCanonicalFile(new File("."));

	public static final File USER_HOME_DIR;

	static {
		File dir = null;
		try {
			dir = getCanonicalFile(new File(SystemUtil.USER_HOME));
		} catch (RuntimeException e) {
		}
		USER_HOME_DIR = dir;
	}

	public static File userFile(String path) {
		return getCanonicalFile(new File(USER_HOME_DIR, path));
	}

	public static boolean mkParentDir(File file) {
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			return parentFile.mkdirs();
		}
		return true;
	}

	// /**
	//  * Creates a list of files that contains all of the directories between the
	//  * root path and the target file. The list does <b>not</b> contain the
	//  * rootPath directory.
	//  * 
	//  * @param rootPath
	//  * @param childFile
	//  * @return
	//  * @throws FileException
	//  *             If root path is not an ancestor of resource.
	//  * @throws FileNotFoundRuntimeException
	//  *             If childFile does not exist.
	//  */
	// public static final LinkedList<File> createPath(String rootPath, File childFile) {
	// 	verifyExists(childFile);

	// 	String absoluteResourcePath = childFile.getAbsolutePath();
	// 	if (!absoluteResourcePath.startsWith(rootPath) || absoluteResourcePath.equals(rootPath)) {
	// 		throw new FileException(childFile, "File must be a child of directory " + rootPath);
	// 	}
	// 	LinkedList<File> directoryPath = new LinkedList<File>();
	// 	File parentDirectory = childFile;
	// 	if (parentDirectory.isFile()) {
	// 		parentDirectory = parentDirectory.getParentFile();
	// 	}
	// 	while (parentDirectory.getAbsolutePath().length() > rootPath.length()) {
	// 		directoryPath.addFirst(parentDirectory);
	// 		parentDirectory = parentDirectory.getParentFile();
	// 	}
	// 	return directoryPath;
	// }

	public static File find(final File root, final String fileName, int depth) {
		if (fileName.equals(root.getName())) {
			return root;
		}
		File[] files = root.listFiles();
		return find(files, fileName, depth - 1);
	}

	public static File find(final File[] files, final String fileName, final int depth) {
		if (files == null || depth == 0) {
			return null;
		}
		for (File file : files) {
			if (fileName.equals(file.getName())) {
				return file;
			}
		}

		int nextDepth = depth - 1;

		File result = null;
		for (File element : files) {
			result = find(element.listFiles(), fileName, nextDepth);
			if (result != null) {
				break;
			}

		}
		return result;
	}

	public static File findFromPath(final File[] dirs, final String fileName) {
		File result = null;
		for (File dir : dirs) {
			File childFile = new File(dir, fileName);
			if (childFile.exists()) {
				result = childFile;
				break;
			}
		}

		return result;
	}

	public static File getCanonicalFile(final File file, String path) {
		return getCanonicalFile(new File(file, path));
	}

	public static File getCanonicalFile(final File file) {
		try {
			return file.getCanonicalFile();
		} catch (IOException ioe) {
			throw new FileException(file, ioe);
		}
	}

	public static String getCanonicalPath(File file) {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			throw new FileException(file, e);
		}
	}

	/**
	 * Returns the extension of the file. That is, the text after the last "."
	 * in the file name.
	 * 
	 * @param file
	 *            The file to inspect.
	 * @return The file extension if a "." exists in the file, otherwise null.
	 */
	public static final String getExtension(final File file) {
		return getExtension(file.getName());
	}

	public static final String getExtension(final String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex > 0 && lastDotIndex != fileName.length() - 1) {
			return fileName.substring(lastDotIndex + 1);
		}
		return null;
	}

	/**
	 * Returns a file based on a directory and relative path.
	 * 
	 * @param directory
	 *            A valid directory.
	 * @param path
	 *            A relative path.
	 * @return The file from relative from the directory.
	 * @throws FileException
	 *             If directory is not a directory or path does not resolve to a
	 *             valid file.
	 */
	public static File resolveFile(final File directory, final String path) {
		FileUtil.verifyDirectory(directory);
		File childFile = new File(directory, path);
		childFile = FileUtil.verifyFile(childFile);
		return childFile;
	}

	public static URL[] toURLs(File... files) {
		URL[] urls = new URL[files.length];
		for (int i = 0; i < files.length; i++) {
			urls[i] = toURL(files[i]);
		}
		return urls;
	}

	public static URL toURL(File file) {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException mue) {
			throw new UncheckedIOException("Unable to create URL for file " + file, mue);
		}
	}

	public static File toFile(URL url) {
		try {
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			return new File(url.getPath());
		}
	}

	/**
	 * Checks that the directory exists and is not a file.
	 * 
	 * @param directory
	 *            Target for verification as a canonical file.
	 * @throws DirectoryNotFoundException
	 *             If the directory doesn't exist.
	 * @throws ExpectedDirectoryNotFileException
	 *             If the directory is a file instead of a directory.
	 */
	public static final File verifyDirectory(final File directory) {
		if (!directory.exists()) {
			throw new DirectoryNotFoundException(directory);
		}
		if (!directory.isDirectory()) {
			throw new ExpectedDirectoryNotFileException(directory);
		}
		return getCanonicalFile(directory);
	}

	/**
	 * Same checks as {@link #verifyDirectory(File)} with an added check that
	 * the directory is writable.
	 * 
	 * @param directory
	 *            Target for verification.
	 * @throws DirectoryNotWritableException
	 *             If the specified directory is not writeable.
	 * @see #verifyDirectory(File)
	 */
	public static final File verifyDirectoryWritable(final File directory) {
		verifyDirectory(directory);
		if (!directory.canWrite()) {
			throw new DirectoryNotWritableException(directory);
		}
		return directory;
	}

	/**
	 * Checks that the file exists.
	 * 
	 * @param file
	 *            Target for verification.
	 * @throws FileNotFoundRuntimeException
	 *             If the file doesn't exist.
	 */
	public static void verifyExists(final File file) {
		if (!file.exists()) {
			throw new FileNotFoundRuntimeException(file);
		}
	}

	/**
	 * Checks that the file exists and is not a directory.
	 * 
	 * @param file
	 *            Target for verification.
	 * @throws FileNotFoundRuntimeException
	 *             If the file doesn't exist.
	 * @throws ExpectedFileNotDirectoryException
	 *             If file is a directory instead of a file.
	 */
	public static final File verifyFile(final File file) {
		if (!file.exists()) {
			throw new FileNotFoundRuntimeException(file);
		}
		if (!file.isFile()) {
			throw new ExpectedFileNotDirectoryException(file);
		}
		File result = getCanonicalFile(file);
		return result;
	}

	/**
	 * Same checks as {@link #verifyFile} with an added check that the file is
	 * writable.
	 * 
	 * @param file
	 *            Target for verification.
	 * @throws FileNotWritableException
	 *             If the specified directory is not writeable.
	 * @see #verifyFile
	 */
	public static final File verifyFileWritable(final File file) {
		verifyFile(file);
		if (!file.canWrite()) {
			throw new FileNotWritableException(file);
		}
		return file;
	}

	public static File mkChildDir(File file, String name) {
		File child = new File(file, name);
		if (child.exists()) {
			return verifyDirectory(child);
		}
		child.mkdir();
		return child;
	}

	public static File realizeFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new FileException(file, e);
			}
			return getCanonicalFile(file);
		}
		return verifyFile(file);
	}

	public static boolean createNewFile(File file) {
		try {
			return file.createNewFile();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static File createTempFile(String prefix, String suffix) {
		try {
			return File.createTempFile(prefix, suffix);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static File createTempFile(String prefix, String suffix, File dir) {
		try {
			return File.createTempFile(prefix, suffix, dir);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static URL[] toURLArray(List<File> files) {
		URL[] urls = new URL[files.size()];
		for (int i = 0, size = files.size(); i < size; i++) {
			urls[i] = toURL(files.get(i));
		}
		return urls;
	}

	public static List<File> toFiles(File baseDir, String... fileNames) {
		List<File> result = new ArrayList<File>(fileNames.length);
		for (String fileName : fileNames) {
			result.add(new File(baseDir, fileName));
		}
		return result;
	}
}
