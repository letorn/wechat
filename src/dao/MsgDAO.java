package dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import factory.DBFactory;

public class MsgDAO extends BaseDAO {
	public MsgDAO() {
		this.dbCollection = DBFactory.newInstance("msg");
	}

	public List<BasicDBObject> find(String msgId) {
		List<BasicDBObject> dbObjects = new ArrayList<BasicDBObject>();
		DBCursor dbCursor = this.dbCollection.find(new BasicDBObject("msgId", msgId));
		while (dbCursor.hasNext()) {
			dbObjects.add((BasicDBObject) dbCursor.next());
		}
		return dbObjects;
	}
}
