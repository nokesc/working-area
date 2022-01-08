package org.nebula.io;

import static org.nebula.io.IOTemplate.IO_TEMPLATE;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class IOUtil {

    private IOUtil() {

    }

    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static final int ONE_KB = 1024;

    public static final int ONE_MB = ONE_KB * 1024;

    public static final long ONE_GB = ONE_MB * 1024;

    public static URL newURL(String spec) {
        return IO_TEMPLATE.get(() -> new URL(spec));
    }

    /**
     * 
     * @param src Source of the String
     * @return The content of the Reader as a String
     * @throws IOException
     */
    public static final String asString(final Reader src) throws IOException {
        char[] chars = new char[1024 * 4];
        int charsRead = 0;
        StringBuilder out = new StringBuilder(512);
        while ((charsRead = src.read(chars)) > 0) {
            out.append(chars, 0, charsRead);
        }
        return out.toString();
    }

    public static final List<String> readLines(final BufferedReader bufferedReader) throws IOException {
        List<String> result = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

    public static final void readLines(final BufferedReader bufferedReader,
            BiFunction<Integer, String, Boolean> lineHandler) throws IOException {
        String line;
        int count = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (!lineHandler.apply(count, line)) {
                return;
            }
            count++;
        }
    }

    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        pipe(in, out);
        return out.toByteArray();
    }

    public static void pipe(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        int read = -1;
        while ((read = in.read(buffer)) > 0) {
            out.write(buffer, 0, read);
        }
    }
}