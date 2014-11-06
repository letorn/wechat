package util;

import java.io.BufferedReader;
import java.io.InputStream;

public class IOUtil {
	/**
	 * 数据流转字符串
	 * @param bufferedReader
	 * @return
	 */
	public static String toString(BufferedReader bufferedReader) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			for (String line; (line = bufferedReader.readLine()) != null;) {
				stringBuffer.append(line);
			}
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		return stringBuffer.length() > 0 ? stringBuffer.toString() : null;
	}

	/**
	 * 数据流转字符串
	 * @param inputStream
	 * @return
	 */
	public static String toString(InputStream inputStream) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			byte[] bytes = new byte[4096];
			for (int length; (length = inputStream.read(bytes)) != -1;) {
				stringBuffer.append(new String(bytes, 0, length));
			}
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		return stringBuffer.length() > 0 ? stringBuffer.toString() : null;
	}
}
