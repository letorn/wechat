package test;

import org.junit.Test;

import util.MsgUtil;
import util.TokenUtil;

import com.mongodb.BasicDBObject;

public class AccessTokenUtilTest {
	@Test
	public void token() {
		String accessToken = TokenUtil.getAccessToken();
		System.out.println(accessToken);
	}

	@Test
	public void menu() {
		BasicDBObject dbObject = MsgUtil.request("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + TokenUtil.getAccessToken());
		System.out.println(dbObject);
	}
}
