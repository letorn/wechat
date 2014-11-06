package util;

import java.util.Timer;
import java.util.TimerTask;

import com.mongodb.BasicDBObject;

public class TokenUtil {
	private static String id = "wxc52fb7ade4338f53";
	private static String secret = "de6e06ccd7058d8102750f062991ddd9";
	private static String accessToken = null;
	private static Integer expiresIn = null;

	private static boolean token() {
		boolean isPass = false;
		try {
			BasicDBObject dbObject = MsgUtil.request("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + id + "&secret=" + secret);
			if (dbObject.containsField("access_token") && dbObject.containsField("expires_in")) {
				accessToken = dbObject.getString("access_token");
				expiresIn = dbObject.getInt("expires_in");
				isPass = true;
			}
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		return isPass;
	}

	/**
	 * 获取accessToken
	 * @return
	 */
	public static String getAccessToken() {
		if (accessToken == null) {
			if (token()) {
				new Timer().schedule(new TimerTask() {
					public void run() {
						token();
					}
				}, expiresIn * 1000, expiresIn * 1000);
			}
		}
		return accessToken;
	}
}
