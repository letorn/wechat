package util;

import java.security.MessageDigest;
import java.util.Arrays;

public class SignatureUtil {
	private static String token = "letorn";
	private static MessageDigest messageDigest = null;

	static {
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
	}

	/**
	 * 验证URL有效性
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public static boolean check(String signature, String timestamp, String nonce) {
		String[] arr = { token, timestamp, nonce };
		Arrays.sort(arr);
		try {
			return signature.equals(toHex(messageDigest.digest((arr[0] + arr[1] + arr[2]).getBytes("utf-8"))));
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		return false;
	}

	private static String toHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer();
		for (byte b : bytes) {
			String temp = Integer.toHexString(b & 0xFF);
			if (temp.length() == 1) {
				buf.append("0");
			}
			buf.append(temp);
		}
		return buf.toString();
	}
}
