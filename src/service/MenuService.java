package service;

import util.MsgUtil;
import util.TokenUtil;

import com.mongodb.BasicDBObject;

import dao.MenuDAO;
import factory.DAOFactory;

public class MenuService {
	private MenuDAO menuDAO = DAOFactory.newInstance(MenuDAO.class);

	public BasicDBObject get() {
		// BasicDBObject dbObject = MsgUtil.request("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + TokenUtil.getAccessToken());
		// BasicDBObject menuDBObject = (BasicDBObject) dbObject.get("menu");
		BasicDBObject menuDBObject = this.menuDAO.findLastOne();
		if (menuDBObject != null) {
			return MsgUtil.menuToNode(menuDBObject);
		} else {
			return new BasicDBObject();
		}
	}

	public BasicDBObject save(BasicDBObject dbObject) {
		BasicDBObject menuDBObject = MsgUtil.nodeToMenu(dbObject);
		this.menuDAO.save(menuDBObject);
		BasicDBObject responseDBObject = MsgUtil.request("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + TokenUtil.getAccessToken(), "POST", menuDBObject.toString());
		Integer errcode = responseDBObject.getInt("errcode");
		if (0 == errcode) {
			return new BasicDBObject("success", true);
		} else {
			return new BasicDBObject("success", false);
		}
	}
}
