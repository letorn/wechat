package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import factory.DBFactory;

public class MenuDAO extends BaseDAO {
	public MenuDAO() {
		this.dbCollection = DBFactory.newInstance("menu");
	}

	public BasicDBObject findLastOne() {
		BasicDBObject dbObject = null;
		DBCursor dbCursor = this.dbCollection.find().sort(new BasicDBObject("_id", -1)).skip(0).limit(1);
		while (dbCursor.hasNext()) {
			dbObject = (BasicDBObject) dbCursor.next();
		}
		return dbObject;
	}
}
