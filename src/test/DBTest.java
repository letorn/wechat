package test;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import factory.DBFactory;

public class DBTest {
	@Test
	public void insert() {
		DB db = DBFactory.newInstance();
		DBCollection dbCollection = db.getCollection("user");
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("name", "letorn");
		dbObject.put("age", 23);
		dbObject.put("createTime", System.currentTimeMillis());
		dbCollection.insert(dbObject);
	}

	@Test
	public void get() {
		DB db = DBFactory.newInstance();
		DBCollection dbCollection = db.getCollection("menu");

		BasicDBObject query = new BasicDBObject("id", "1397739441570");
		BasicDBObject dbObject = (BasicDBObject) dbCollection.findOne(query);
		System.out.println(dbObject.toString());
	}

	@Test
	public void findAll() {
		DB db = DBFactory.newInstance();
		DBCollection dbCollection = db.getCollection("user");

		DBCursor dbCursor = dbCollection.find();
		List<BasicDBObject> dbObjects = new ArrayList<BasicDBObject>();
		while (dbCursor.hasNext()) {
			BasicDBObject dbObject = (BasicDBObject) dbCursor.next();
			dbObjects.add(dbObject);
			System.out.println("id:" + dbObject.getObjectId("_id").toString());
		}
		System.out.println("count: " + dbObjects.size());
	}

	@Test
	public void findOne() {
		DB db = DBFactory.newInstance();
		DBCollection dbCollection = db.getCollection("user");

		BasicDBObject query = new BasicDBObject("_id", new ObjectId("535146f9b9038cab5c362d35"));
		BasicDBObject dbObject = (BasicDBObject) dbCollection.findOne(query);
		System.out.println(dbObject.toString());
	}

	@Test
	public void find() {
		DB db = DBFactory.newInstance();
		DBCollection dbCollection = db.getCollection("menu");
		dbCollection.setObjectClass(BasicDBObject.class);
		DBCursor dbCursor = dbCollection.find().sort(new BasicDBObject("id", -1)).skip(0).limit(1);
		while (dbCursor.hasNext()) {
			BasicDBObject dbObject = (BasicDBObject) dbCursor.next();
			System.out.println(dbObject.toString());
		}
	}
}
