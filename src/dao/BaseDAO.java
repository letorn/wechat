package dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class BaseDAO {
	public DBCollection dbCollection = null;

	public String save(BasicDBObject dbObject) {
		this.dbCollection.insert(dbObject);
		return dbObject.getObjectId("_id").toString();
	}

	public BasicDBObject get(String id) {
		return (BasicDBObject) this.dbCollection.findOne(new BasicDBObject("_id", new ObjectId(id)));
	}

	public List<BasicDBObject> find(BasicDBObject query) {
		List<BasicDBObject> dbObjects = new ArrayList<BasicDBObject>();
		DBCursor dbCursor = this.dbCollection.find(query);
		while (dbCursor.hasNext()) {
			dbObjects.add((BasicDBObject) dbCursor.next());
		}
		return dbObjects;
	}

	public List<BasicDBObject> findAll() {
		List<BasicDBObject> dbObjects = new ArrayList<BasicDBObject>();
		DBCursor dbCursor = this.dbCollection.find();
		while (dbCursor.hasNext()) {
			dbObjects.add((BasicDBObject) dbCursor.next());
		}
		return dbObjects;
	}
}
