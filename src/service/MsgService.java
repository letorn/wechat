package service;

import com.mongodb.BasicDBObject;

import dao.MsgDAO;
import factory.DAOFactory;

public class MsgService {
	private MsgDAO msgDAO = DAOFactory.newInstance(MsgDAO.class);

	public boolean check(BasicDBObject dbObject) {
		return this.msgDAO.find(dbObject.getString("msgId")).size() <= 0;
	};

	public BasicDBObject textMsgService(BasicDBObject dbObject) {
		this.msgDAO.save(dbObject);

		BasicDBObject msgDBObject = new BasicDBObject();
		msgDBObject.put("toUserName", dbObject.getString("fromUserName"));
		msgDBObject.put("fromUserName", dbObject.getString("toUserName"));
		msgDBObject.put("createTime", System.currentTimeMillis());
		msgDBObject.put("msgType", "text");
		msgDBObject.put("content", dbObject.getString("content"));

		return msgDBObject;
	}

	public BasicDBObject imageMsgService(BasicDBObject dbObject) {
		this.msgDAO.save(dbObject);

		BasicDBObject msgDBObject = new BasicDBObject();
		msgDBObject.put("toUserName", dbObject.getString("fromUserName"));
		msgDBObject.put("fromUserName", dbObject.getString("toUserName"));
		msgDBObject.put("createTime", System.currentTimeMillis());
		msgDBObject.put("msgType", "image");
		msgDBObject.put("image", new BasicDBObject("mediaId", dbObject.getString("mediaId")));

		return msgDBObject;
	}

	public BasicDBObject voiceMsgService(BasicDBObject dbObject) {
		this.msgDAO.save(dbObject);

		BasicDBObject msgDBObject = new BasicDBObject();
		msgDBObject.put("toUserName", dbObject.getString("fromUserName"));
		msgDBObject.put("fromUserName", dbObject.getString("toUserName"));
		msgDBObject.put("createTime", System.currentTimeMillis());
		msgDBObject.put("msgType", "voice");
		msgDBObject.put("voice", new BasicDBObject("mediaId", dbObject.getString("mediaId")));

		return msgDBObject;
	}

	public BasicDBObject videoMsgService(BasicDBObject dbObject) {
		this.msgDAO.save(dbObject);

		BasicDBObject msgDBObject = new BasicDBObject();
		msgDBObject.put("toUserName", dbObject.getString("fromUserName"));
		msgDBObject.put("fromUserName", dbObject.getString("toUserName"));
		msgDBObject.put("createTime", System.currentTimeMillis());
		msgDBObject.put("msgType", "video");

		BasicDBObject videoDBObject = new BasicDBObject();
		videoDBObject.put("mediaId", dbObject.getString("mediaId"));
		videoDBObject.put("title", "------视频消息------");
		videoDBObject.put("description", "无");
		msgDBObject.put("video", videoDBObject);

		return msgDBObject;
	}

	public BasicDBObject locationMsgService(BasicDBObject dbObject) {
		this.msgDAO.save(dbObject);

		BasicDBObject msgDBObject = new BasicDBObject();
		msgDBObject.put("toUserName", dbObject.getString("fromUserName"));
		msgDBObject.put("fromUserName", dbObject.getString("toUserName"));
		msgDBObject.put("createTime", System.currentTimeMillis());
		msgDBObject.put("msgType", "text");

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer.append("------地理位置消息------");
		contentBuffer.append("\n" + "Location_X: " + dbObject.getString("location_Y"));
		contentBuffer.append("\n" + "Location_Y: " + dbObject.getString("location_Y"));
		contentBuffer.append("\n" + "Scale: " + dbObject.getString("scale"));
		contentBuffer.append("\n" + "Label: " + dbObject.getString("label"));
		msgDBObject.put("content", contentBuffer.toString());

		return msgDBObject;
	}

	public BasicDBObject linkMsgService(BasicDBObject dbObject) {
		this.msgDAO.save(dbObject);

		BasicDBObject msgDBObject = new BasicDBObject();
		msgDBObject.put("toUserName", dbObject.getString("fromUserName"));
		msgDBObject.put("fromUserName", dbObject.getString("toUserName"));
		msgDBObject.put("createTime", System.currentTimeMillis());
		msgDBObject.put("msgType", "text");

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer.append("------链接消息------");
		contentBuffer.append("\n" + "Title: " + dbObject.getString("title"));
		contentBuffer.append("\n" + "Description: " + dbObject.getString("description"));
		contentBuffer.append("\n" + "Url: " + dbObject.getString("url"));
		msgDBObject.put("content", contentBuffer.toString());

		return msgDBObject;
	}
}
