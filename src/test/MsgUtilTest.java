package test;

import org.junit.Test;

import util.MsgUtil;

import com.mongodb.BasicDBObject;

public class MsgUtilTest {
	@Test
	public void encode() {
		String msgString = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[media_id]]></MediaId><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description></Video></xml>";
		BasicDBObject dbObject = MsgUtil.decode(msgString);
		System.out.println(dbObject.toString());
	}

	@Test
	public void decode() {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("toUserName", "fromUserName");
		dbObject.put("fromUserName", "toUserName");
		dbObject.put("createTime", System.currentTimeMillis());
		dbObject.put("msgType", "image");
		dbObject.put("image", new BasicDBObject("mediaId", "mediaId"));
		String msgString = MsgUtil.encode(dbObject);
		System.out.println(msgString);
	}
}
