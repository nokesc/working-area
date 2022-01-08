package org.nebula.lang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Utility methods for java.lang.System
 */
public class SystemUtil {

	public static final String LINE_SEPARATOR_KEY = "line.separator";

	public static final String LINE_SEPARATOR = System.getProperty(LINE_SEPARATOR_KEY);

	public static final String FILE_SEPARATOR_KEY = "file.separator";

	public static final String FILE_SEPARATOR = System.getProperty(FILE_SEPARATOR_KEY);

	public static final String USER_HOME_KEY = "user.home";

	public static final String USER_HOME = System.getProperty(USER_HOME_KEY);

	private SystemUtil() {
		super();
	}

	/**
	 * @return A new PrintWriter that wraps System.out
	 */
	public final static PrintWriter sysout() {
		return new PrintWriter(new OutputStreamWriter(System.out), true);
	}

	/**
	 * @return A new BufferedReader that wraps System.in
	 */
	public final static BufferedReader sysin() {
		return new BufferedReader(new InputStreamReader(System.in));
	}

	public final static Map<String, String> getPropertiesSorted() {
		Properties props = System.getProperties();
		@SuppressWarnings("unchecked")
		Set<String> keySet = (Set<String>) (Object) props.keySet();
		List<String> keys = new ArrayList<String>(keySet);
		Collections.sort(keys);
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (String key : keys) {
			result.put(key, props.getProperty(key));
		}
		return result;
	}

	public final static String getOsName() {
		return System.getProperty("os.name");
	}

	public final static boolean doesOsNameContainWindows() {
		String osName = getOsName();
		return osName != null && osName.toLowerCase().contains("windows");
	}

	public final static void dumpPropertiesSorted() {
		Map<String, String> propsSorted = SystemUtil.getPropertiesSorted();
		for (Map.Entry<String, String> entry : propsSorted.entrySet()) {
			System.out.printf("%s=%s\n", entry.getKey(), entry.getValue());
		}
	}

	public static String hexIdentityHashCode(Object o) {
		return Integer.toHexString(System.identityHashCode(o));
	}

	public static String getTypeAndIdentity(Object o) {
		if (o != null) {
			StringBuilder sb = new StringBuilder(32);
			SystemUtil.appendTypeAndIdentity(sb, o);
			return sb.toString();
		}
		return null;
	}

	static StringBuilder appendTypeAndIdentity(StringBuilder sb, Object o) {
		if (o != null) {
			Class<?> cl = o.getClass();
			sb.append(cl.getName()).append("@").append(hexIdentityHashCode(o));
		}
		return sb;
	}

	public static String getDescription(Class<?> type) {
		if (type == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(64);
		SystemUtil.appendClassDescription(type, sb);
		return sb.toString();
	}

	public static StringBuilder appendClassDescription(Class<?> type, StringBuilder sb) {
		sb.append(type.getName()).append("@");
		sb.append(hexIdentityHashCode(type));
		ClassLoader classLoader = type.getClassLoader();
		if (classLoader != null) {
			sb.append("/");
			appendTypeAndIdentity(sb, classLoader);
		}
		return sb;
	}

	public static String getTypeAndIdentityAndClassLoaderIdentity(Object o) {
		if (o == null) {
			return null;
		}
		Class<? extends Object> type = o.getClass();
		StringBuilder sb = new StringBuilder(64);
		appendTypeAndIdentity(sb, o);
		ClassLoader classLoader = type.getClassLoader();
		if (classLoader != null) {
			sb.append(" [");
			appendTypeAndIdentity(sb, classLoader);
			sb.append("]");
		} else {
			sb.append(" [null classloader]");
		}
		return sb.toString();
	}
}