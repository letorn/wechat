package factory;

import java.net.UnknownHostException;

import util.LoggerUtil;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DBFactory {
	private static String host = "localhost";
	private static Integer port = 27017;
	private static DB db = null;

	public static DB newInstance() {
		if (db == null) {
			try {
				MongoClient mongoClient = new MongoClient(host, port);
				db = mongoClient.getDB("letorn");
			} catch (UnknownHostException e) {
				LoggerUtil.severe(e);
			}
		}
		return db;
	}

	public static DBCollection newInstance(String name) {
		if (db == null) {
			try {
				MongoClient mongoClient = new MongoClient(host, port);
				db = mongoClient.getDB("letorn");
			} catch (UnknownHostException e) {
				LoggerUtil.severe(e);
			}
		}
		return db.getCollection(name);
	}
}
