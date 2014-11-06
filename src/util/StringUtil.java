package util;

public class StringUtil {
	/**
	 * 字符串首字母转小写
	 * @param string
	 * @return
	 */
	public static String toLowerCaseFirstOne(String string) {
		char[] array = string.toCharArray();
		array[0] = Character.toLowerCase(array[0]);
		return String.valueOf(array);
	}

	/**
	 * 字符串首字母转大写
	 * @param string
	 * @return
	 */
	public static String toUpperCaseFirstOne(String string) {
		char[] array = string.toCharArray();
		array[0] = Character.toUpperCase(array[0]);
		return String.valueOf(array);
	}
}
