package org.nebula.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import java.util.function.BiFunction;

import org.nebula.io.IOTemplate.ConsumerWithIO;
import org.nebula.io.IOTemplate.FunctionWithIO;
import org.nebula.io.file.FileUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Resources {

	public static final Resources UTF8_RESOURCES = new Resources(IOUtil.UTF8, IOTemplate.IO_TEMPLATE);

	public interface Readable {
		IOTemplate getIOTemplate();

		Reader toReader() throws IOException;

		<R> R read(final FunctionWithIO<Reader, R> function);

		void voidRead(ConsumerWithIO<Reader> function);

		<R> R bufferedRead(FunctionWithIO<BufferedReader, R> function);

		void consumeBufferedRead(ConsumerWithIO<BufferedReader> function);

		default BufferedReader toBufferedReader() throws IOException {
			return new BufferedReader(toReader());
		}

		default String readString() {
			return read(IOUtil::asString);
		}

		default List<String> readLines() {
			return bufferedRead(IOUtil::readLines);
		}

		default void readLines(BiFunction<Integer, String, Boolean> lineHandler) {
			consumeBufferedRead(br -> {
				IOUtil.readLines(br, lineHandler);
			});
		}
	}

	public interface Writable {
		IOTemplate getIOTemplate();

		Writer toWriter() throws IOException;

		default void writeLines(final List<String> src) {
			getIOTemplate().writeConsumer(() -> new PrintWriter(toWriter()), _out -> {
				for (String line : src) {
					_out.println(line);
				}
			});
		}
	}

	public interface StreamReadable {
		IOTemplate getIOTemplate();

		InputStream toInputStream() throws IOException;

		default byte[] toBytes() {
			return getIOTemplate().read(() -> toInputStream(), IOUtil::toBytes);
		}

		default Properties readProperties() {
			return getIOTemplate().read(() -> toInputStream(), inputStream -> {
				Properties properties = new Properties();
				properties.load(inputStream);
				return properties;
			});
		}
	}

	public interface StreamWriteable {
		IOTemplate getIOTemplate();

		OutputStream toOutputStream() throws IOException;

		default void write(final Properties src, final String comments) {
			getIOTemplate().writeConsumer(() -> toOutputStream(), outputStream -> {
				src.store(outputStream, comments);
			});
		}
	}

	@AllArgsConstructor
	public class URLStreamResource implements StreamReadable {

		@Getter
		public final URL url;

		@Override
		public IOTemplate getIOTemplate() {
			return ioTemplate;
		}

		@Override
		public InputStream toInputStream() throws IOException {
			return url.openStream();
		}
	}

	@AllArgsConstructor
	public class URLCharResource implements Readable {

		@Getter
		public final URL url;

		@Override
		public IOTemplate getIOTemplate() {
			return ioTemplate;
		}

		@Override
		public <R> R read(FunctionWithIO<Reader, R> function) {
			return getIOTemplate().read(() -> toReader(), function);
		}

		@Override
		public void voidRead(ConsumerWithIO<Reader> function) {
			getIOTemplate().readConsumer(() -> toReader(), function);
		}

		@Override
		public <R> R bufferedRead(FunctionWithIO<BufferedReader, R> function) {
			return getIOTemplate().read(() -> toBufferedReader(), function);
		}

		@Override
		public void consumeBufferedRead(ConsumerWithIO<BufferedReader> function) {
			getIOTemplate().readConsumer(() -> toBufferedReader(), function);
		}

		@Override
		public InputStreamReader toReader() throws IOException {
			return new InputStreamReader(url.openStream(), charset);
		}
	}

	@AllArgsConstructor
	public class FileStreamResource implements StreamReadable, StreamWriteable {

		@Getter
		public final File file;

		@Override
		public IOTemplate getIOTemplate() {
			return ioTemplate;
		}

		@Override
		public FileOutputStream toOutputStream() throws FileNotFoundException {
			return new FileOutputStream(file);
		}

		public FileOutputStream toOutputStream(boolean append) throws FileNotFoundException {
			return new FileOutputStream(file, append);
		}

		@Override
		public FileInputStream toInputStream() throws FileNotFoundException {
			FileUtil.verifyFile(file);
			return new FileInputStream(file);
		}
	}

	@AllArgsConstructor
	public class FileCharResource implements Readable, Writable {

		@Getter
		private final File file;

		@Override
		public Reader toReader() throws IOException {
			return new FileReader(file, charset);
		}

		@Override
		public IOTemplate getIOTemplate() {
			return ioTemplate;
		}

		@Override
		public <R> R read(FunctionWithIO<Reader, R> function) {
			return getIOTemplate().read(() -> toReader(), function);
		}

		@Override
		public void voidRead(ConsumerWithIO<Reader> function) {
			getIOTemplate().readConsumer(() -> toReader(), function);

		}

		@Override
		public <R> R bufferedRead(FunctionWithIO<BufferedReader, R> function) {
			return getIOTemplate().read(() -> toBufferedReader(), function);
		}

		@Override
		public void consumeBufferedRead(ConsumerWithIO<BufferedReader> function) {
			getIOTemplate().readConsumer(() -> toBufferedReader(), function);
		}

		@Override
		public FileWriter toWriter() throws IOException {
			return new FileWriter(file, charset);
		}

		public FileWriter toWriter(boolean append) throws IOException {
			return new FileWriter(file, charset, append);
		}

		/**
		 * 
		 * @param maxBytesToReadIntoMemory
		 * @return
		 * @throws IllegalArgumentException If file length is greater than
		 *                                  maxBytesToReadIntoMemory
		 */
		public String readString(final int maxBytesToReadIntoMemory) {
			if (file.length() > maxBytesToReadIntoMemory) {
				throw new IllegalArgumentException("file=" + file + "\n\tfile.length()=" + file.length()
						+ "\n\tmaxBytesToReadIntoMemory=" + maxBytesToReadIntoMemory);
			}
			return readString();
		}

		@Override
		public List<String> readLines() {
			return getIOTemplate().get(() -> Files.readAllLines(file.toPath(), charset));
		}

		@Override
		public String readString() {
			return getIOTemplate().get(() -> Files.readString(file.toPath(), charset));
		}
	}

	@Getter
	public final Charset charset;

	@Getter
	public final IOTemplate ioTemplate;

	public FileStreamResource file(File file) {
		return new FileStreamResource(file);
	}

	public FileCharResource fileChars(File file) {
		return new FileCharResource(file);
	}

	public URLStreamResource url(URL url) {
		return new URLStreamResource(url);
	}

	public URLCharResource urlChars(URL url) {
		return new URLCharResource(url);
	}

	public URLCharResource urlChars(String spec) {
		return new URLCharResource(IOUtil.newURL(spec));
	}
}
